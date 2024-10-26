package com.zeotap.ruleengine.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeotap.ruleengine.Model.RuleCondition;

@Repository
public interface ConditionRepository extends JpaRepository<RuleCondition, Long>{

}
