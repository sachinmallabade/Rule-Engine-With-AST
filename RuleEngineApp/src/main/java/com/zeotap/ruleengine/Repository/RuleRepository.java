package com.zeotap.ruleengine.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeotap.ruleengine.Model.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long>{

}
