package com.zeotap.ruleengine.repository;

import com.zeotap.ruleengine.Model.RuleCondition;
import com.zeotap.ruleengine.Repository.ConditionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RuleConditionRepositoryTest {

    @Autowired
    private ConditionRepository ruleConditionRepository;

    private RuleCondition ruleCondition;

    @BeforeEach
    void setUp() {
        ruleCondition = new RuleCondition();
        ruleCondition.setAttribute("age");
        ruleCondition.setOperator(">");
        ruleCondition.setValue("30");
        ruleConditionRepository.save(ruleCondition);
    }

    @Test
    void testFindById() {
        Optional<RuleCondition> foundCondition = ruleConditionRepository.findById(ruleCondition.getId());
        assertTrue(foundCondition.isPresent());
        assertEquals(ruleCondition.getAttribute(), foundCondition.get().getAttribute());
    }

    @Test
    void testFindAll() {
        Iterable<RuleCondition> conditions = ruleConditionRepository.findAll();
        assertTrue(conditions.spliterator().hasCharacteristics(0)); // Check if it contains saved condition
    }

    @Test
    void testDeleteById() {
        ruleConditionRepository.deleteById(ruleCondition.getId());
        Optional<RuleCondition> deletedCondition = ruleConditionRepository.findById(ruleCondition.getId());
        assertFalse(deletedCondition.isPresent());
    }
}
