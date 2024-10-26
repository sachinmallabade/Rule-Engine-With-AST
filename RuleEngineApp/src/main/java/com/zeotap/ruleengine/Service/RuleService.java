package com.zeotap.ruleengine.Service;

import java.util.List;
import java.util.Map;

import com.zeotap.ruleengine.Model.Node;
import com.zeotap.ruleengine.Model.Rule;

public interface RuleService {
	// Rule createRule(Rule rule);
	Rule getRuleById(Long id);

	List<Rule> getAllRules();

	void updateRule(Long id, Rule ruleUpdates);

	void deleteRule(Long id);

	public Rule createRule(String ruleString);

	public String getASTByRuleId(Long ruleId);

	Node createRuleFromString(String ruleString);

	Node combineRules(List<String> rules);

	boolean evaluateRule(Node ast, Map<String, Object> data);
}