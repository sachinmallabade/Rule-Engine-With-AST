package com.zeotap.ruleengine.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeotap.ruleengine.Exception.RuleNotFoundException;
import com.zeotap.ruleengine.Model.Node;
import com.zeotap.ruleengine.Model.Rule;
import com.zeotap.ruleengine.Service.RuleService;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

	@Autowired
	private RuleService ruleService;

	@PostMapping
	public ResponseEntity<Rule> createRule(@RequestBody Map<String, String> requestBody) {
		String ruleString = requestBody.get("ruleString");
		if (ruleString == null || ruleString.isEmpty()) {
			return ResponseEntity.badRequest().body(null);
		}

		Rule createdRule = ruleService.createRule(ruleString);
		return new ResponseEntity<>(createdRule, HttpStatus.CREATED);
	}

	@GetMapping("/{ruleId}/ast")
	public ResponseEntity<Object> getASTByRuleId(@PathVariable Long ruleId) {
		try {
			String ast = ruleService.getASTByRuleId(ruleId);
			return ResponseEntity.ok(ast);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving AST: " + e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Rule> getRuleById(@PathVariable Long id) {
		Rule rule = ruleService.getRuleById(id);
		if (rule == null) {
			throw new RuleNotFoundException("Rule not found with id: " + id);
		}
		return new ResponseEntity<>(rule, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Rule>> getAllRules() {
		List<Rule> rules = ruleService.getAllRules();
		return new ResponseEntity<>(rules, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateRule(@PathVariable Long id, @RequestBody Rule rule) {
		ruleService.updateRule(id, rule);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
		ruleService.deleteRule(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/create")
	public ResponseEntity<Node> createRuleFromString(@RequestBody Map<String, String> requestBody) {
		String ruleString = requestBody.get("ruleString");
		Node ast = ruleService.createRuleFromString(ruleString);
		return ResponseEntity.ok(ast);
	}

	public class RuleResponse {
		private String combinedRule;

		public RuleResponse(String combinedRule) {
			this.combinedRule = combinedRule;
		}

		public String getCombinedRule() {
			return combinedRule;
		}

		public void setCombinedRule(String combinedRule) {
			this.combinedRule = combinedRule;
		}
	}

	@PostMapping("/combine")
	public ResponseEntity<Node> combineRules(@RequestBody CombineRequest request) {
		List<String> rules = request.getRules();
		if (rules == null || rules.isEmpty()) {
			return ResponseEntity.badRequest().body(null);
		}

		Node combinedAST = ruleService.combineRules(rules);

		return ResponseEntity.ok(combinedAST);
	}

	// RuleController.java

	@PostMapping("/evaluate")
	public ResponseEntity<Map<String, Object>> evaluateRule(@RequestBody Map<String, Object> requestBody) {
		ObjectMapper objectMapper = new ObjectMapper();
		Node ast;

		try {
			LinkedHashMap<?, ?> astMap = (LinkedHashMap<?, ?>) requestBody.get("ast");
			ast = objectMapper.convertValue(astMap, Node.class); // Convert AST from request to Node
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Collections.singletonMap("error", "Invalid AST structure"));
		}

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> usersData = (List<Map<String, Object>>) requestBody.get("data");

		if (ast == null || usersData == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Collections.singletonMap("error", "Missing AST or user data"));
		}

		List<Boolean> results = new ArrayList<>();

		// Evaluate rule for each user
		for (Map<String, Object> user : usersData) {
			try {
				boolean result = ruleService.evaluateRule(ast, user);
				results.add(result);
			} catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(Collections.singletonMap("error", e.getMessage()));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(Collections.singletonMap("error", "Error evaluating the rule"));
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("results", results);

		return ResponseEntity.ok(response);
	}

}