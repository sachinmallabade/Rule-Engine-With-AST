package com.zeotap.ruleengine.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeotap.ruleengine.Exception.RuleNotFoundException;
import com.zeotap.ruleengine.Model.Node;
import com.zeotap.ruleengine.Model.Rule;
import com.zeotap.ruleengine.Repository.RuleRepository;
import com.zeotap.ruleengine.Service.RuleService;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Service
public class RuleServiceImpl implements RuleService {

	@Autowired
	private RuleRepository ruleRepository;

	public RuleServiceImpl(RuleRepository ruleRepository) {
		this.ruleRepository = ruleRepository;
	}

	@Override
	public Rule createRule(String ruleString) {
		String ast = generateAST(ruleString);
		Rule rule = new Rule(ruleString, ast);
		return ruleRepository.save(rule);
	}

	private String generateAST(String ruleString) {
		Node astNode = parseToAST(ruleString);
		return astNode != null ? astNode.toString() : null;
	}

	@Override
	public String getASTByRuleId(Long ruleId) {
		Rule rule = ruleRepository.findById(ruleId).orElse(null);
		if (rule == null) {
			throw new EntityNotFoundException("Rule not found.");
		}
		if (rule.getAst() == null) {
			throw new IllegalArgumentException("No AST associated with this rule.");
		}
		return rule.getAst();
	}

	@Override
	public Rule getRuleById(Long id) {
		return ruleRepository.findById(id)
				.orElseThrow(() -> new RuleNotFoundException("Rule not found with id: " + id));
	}

	@Override
	public List<Rule> getAllRules() {
		return ruleRepository.findAll();
	}

	@Override
	public void updateRule(Long id, Rule ruleUpdates) {
		Rule existingRule = getRuleById(id);
		if (ruleUpdates.getRuleString() != null) {
			existingRule.setRuleString(ruleUpdates.getRuleString());
		}

		ruleRepository.save(existingRule);
	}

	@Override
	public void deleteRule(Long id) {
		Rule existingRule = getRuleById(id);
		ruleRepository.delete(existingRule);
	}

	@Override
	public Node createRuleFromString(String ruleString) {
		return parseToAST(ruleString);
	}

	private int precedence(String operator) {
		switch (operator) {
		case "AND":
			return 1;
		case "OR":
			return 0;
		default:
			return -1;
		}
	}

	private Node parseToAST(String ruleString) {

		ruleString = ruleString.trim().replaceAll(" +", " ");

		List<String> tokens = new ArrayList<>();
		StringBuilder currentToken = new StringBuilder();

		for (char ch : ruleString.toCharArray()) {
			if (ch == ' ' || ch == '(' || ch == ')') {
				if (currentToken.length() > 0) {
					tokens.add(currentToken.toString());
					currentToken.setLength(0);
				}
				if (ch == '(' || ch == ')') {
					tokens.add(String.valueOf(ch));
				}
			} else {
				currentToken.append(ch);
			}
		}

		if (currentToken.length() > 0) {
			tokens.add(currentToken.toString());
		}

		Stack<Node> nodeStack = new Stack<>();
		Stack<String> operatorStack = new Stack<>();
		int index = 0;

		while (index < tokens.size()) {
			String token = tokens.get(index);
			System.out.println("Processing token: " + token);

			if (token.equals("AND") || token.equals("OR")) {
				while (!operatorStack.isEmpty() && precedence(token) <= precedence(operatorStack.peek())) {
					String op = operatorStack.pop();
					Node right = nodeStack.pop();
					Node left = nodeStack.pop();
					nodeStack.push(new Node("operator", left, right, op));
				}
				operatorStack.push(token);
			} else if (token.equals("(")) {
				operatorStack.push(token);
			} else if (token.equals(")")) {
				while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
					String op = operatorStack.pop();
					Node right = nodeStack.pop();
					Node left = nodeStack.pop();
					nodeStack.push(new Node("operator", left, right, op));
				}
				operatorStack.pop();
			} else {

				String leftOperand = token;
				index++;

				if (index < tokens.size() && (tokens.get(index).matches("[<>!=]+"))) {
					String operator = tokens.get(index);
					index++;

					if (index < tokens.size() && (tokens.get(index).matches("'[a-zA-Z]+'|\\d+"))) {
						String value = tokens.get(index).replace("'", "");

						Node leftNode = new Node("operand", null, null, leftOperand);
						Node rightNode = new Node("operand", null, null, value);

						Node conditionNode = new Node("operator", leftNode, rightNode, operator);
						nodeStack.push(conditionNode);
					} else {
						System.out.println(
								"Expected a value token after the operator for: " + leftOperand + " " + operator);
					}
				} else {
					System.out.println("Expected an operator after the left operand: " + leftOperand);
				}
			}
			index++;
		}

		while (!operatorStack.isEmpty()) {
			String op = operatorStack.pop();
			Node right = nodeStack.pop();
			Node left = nodeStack.pop();
			nodeStack.push(new Node("operator", left, right, op));
		}

		return nodeStack.isEmpty() ? null : nodeStack.pop();
	}

	@Override
	public Node combineRules(List<String> rules) {
		Node combinedAst = null;
		for (String rule : rules) {
			Node ast = createRuleFromString(rule);
			combinedAst = combineASTs(combinedAst, ast);
		}
		return combinedAst;
	}

	private Node combineASTs(Node existingAst, Node newAst) {
		if (existingAst == null) {
			return newAst;
		}
		return new Node("OR", existingAst, newAst);
	}

	@Override
	public boolean evaluateRule(Node ast, Map<String, Object> data) {
		return evaluateAST(ast, data);
	}

	private boolean evaluateAST(Node ast, Map<String, Object> data) {
		if (ast.getType().equals("operand")) {
			String[] parts = ((String) ast.getValue()).split(" ");
			String attribute = parts[0];
			String operator = parts[1];
			String valueStr = parts[2];

			Object attributeValue = data.get(attribute);

			if (attributeValue == null) {
				return false;
			}

			if (attributeValue instanceof Number) {

				int attributeIntValue = ((Number) attributeValue).intValue();
				int comparisonValue = Integer.parseInt(valueStr);

				switch (operator) {
				case ">":
					return attributeIntValue > comparisonValue;
				case "<":
					return attributeIntValue < comparisonValue;
				case "=":
					return attributeIntValue == comparisonValue;
				case ">=":
					return attributeIntValue >= comparisonValue;
				case "<=":
					return attributeIntValue <= comparisonValue;
				default:
					throw new IllegalArgumentException("Invalid operator for numeric comparison: " + operator);
				}
			} else if (attributeValue instanceof String) {

				String attributeStringValue = (String) attributeValue;

				String comparisonValue = valueStr.replace("'", "");

				switch (operator) {
				case "=":
					return attributeStringValue.equals(comparisonValue);
				case "!=":
					return !attributeStringValue.equals(comparisonValue);
				default:
					throw new IllegalArgumentException("Invalid operator for string comparison: " + operator);
				}
			} else {
				throw new IllegalArgumentException("Unsupported attribute type for comparison");
			}
		} else if (ast.getType().equals("operator")) {
			boolean leftResult = evaluateAST(ast.getLeft(), data);
			boolean rightResult = evaluateAST(ast.getRight(), data);

			switch ((String) ast.getValue()) {
			case "AND":
				return leftResult && rightResult;
			case "OR":
				return leftResult || rightResult;
			default:
				throw new IllegalArgumentException("Invalid operator: " + ast.getValue());
			}
		}
		throw new IllegalArgumentException("Invalid AST node type: " + ast.getType());
	}

}