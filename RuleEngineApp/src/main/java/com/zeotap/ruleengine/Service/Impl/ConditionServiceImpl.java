package com.zeotap.ruleengine.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeotap.ruleengine.Exception.ConditionNotFoundException;
import com.zeotap.ruleengine.Model.RuleCondition;
import com.zeotap.ruleengine.Repository.ConditionRepository;
import com.zeotap.ruleengine.Service.ConditionService;

@Service
public class ConditionServiceImpl implements ConditionService {

	@Autowired
	private ConditionRepository ruleConditionRepository;

	@Override
	public RuleCondition getConditionById(Long id) {
		return ruleConditionRepository.findById(id)
				.orElseThrow(() -> new ConditionNotFoundException("Condition not found with id: " + id));
	}

	@Override
	public List<RuleCondition> getAllConditions() {
		return ruleConditionRepository.findAll();
	}

	@Override
	public RuleCondition createCondition(RuleCondition condition) {
		return ruleConditionRepository.save(condition);
	}

	@Override
	public void updateCondition(Long id, RuleCondition conditionUpdates) {
		RuleCondition existingCondition = getConditionById(id);

		if (conditionUpdates.getAttribute() != null) {
			existingCondition.setAttribute(conditionUpdates.getAttribute());
		}
		if (conditionUpdates.getOperator() != null) {
			existingCondition.setOperator(conditionUpdates.getOperator());
		}
		if (conditionUpdates.getValue() != null) {
			existingCondition.setValue(conditionUpdates.getValue());
		}

		ruleConditionRepository.save(existingCondition); // Save updated condition
	}

	@Override
	public void deleteCondition(Long id) {
		RuleCondition condition = getConditionById(id);
		if (condition != null) {
			ruleConditionRepository.deleteById(id);
		}
	}
}
