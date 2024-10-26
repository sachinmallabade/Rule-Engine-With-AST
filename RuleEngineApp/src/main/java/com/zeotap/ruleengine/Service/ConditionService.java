package com.zeotap.ruleengine.Service;

import java.util.List;

import com.zeotap.ruleengine.Model.RuleCondition;



public interface ConditionService {

	RuleCondition createCondition(RuleCondition condition);
    RuleCondition getConditionById(Long id);
    List<RuleCondition> getAllConditions();
    void updateCondition(Long id, RuleCondition conditionUpdates);
    void deleteCondition(Long id);
}
