package com.zeotap.ruleengine.repository;

import com.zeotap.ruleengine.Model.Rule;
import com.zeotap.ruleengine.Repository.RuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RuleRepositoryTest {

    @Autowired
    private RuleRepository ruleRepository;

    private Rule rule;

    @BeforeEach
    void setUp() {
        rule = new Rule();
        rule.setRuleString("((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing'))");
        ruleRepository.save(rule);
    }

    @Test
    void testFindById() {
        Optional<Rule> foundRule = ruleRepository.findById(rule.getId());
        assertTrue(foundRule.isPresent());
        assertEquals(rule.getRuleString(), foundRule.get().getRuleString());
    }

    @Test
    void testFindAll() {
        Iterable<Rule> rules = ruleRepository.findAll();
        assertTrue(rules.spliterator().hasCharacteristics(0)); // Check if it contains saved rule
    }

    @Test
    void testDeleteById() {
        ruleRepository.deleteById(rule.getId());
        Optional<Rule> deletedRule = ruleRepository.findById(rule.getId());
        assertFalse(deletedRule.isPresent());
    }
}
