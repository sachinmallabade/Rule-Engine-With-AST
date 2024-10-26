package com.zeotap.ruleengine.model;

import com.zeotap.ruleengine.Model.Rule;
import com.zeotap.ruleengine.Repository.RuleRepository;
import com.zeotap.ruleengine.Service.Impl.RuleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class RuleTest {

    @Mock
    private RuleRepository ruleRepository;

    @InjectMocks
    private RuleServiceImpl ruleService;

    private Rule rule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rule = new Rule();
        rule.setId(1L);
        rule.setRuleString("((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing'))");
        rule.setAst("Generated AST");
    }

    @Test
    void testCreateRule() {
        when(ruleRepository.save(any(Rule.class))).thenReturn(rule);
        Rule createdRule = ruleService.createRule(rule.getRuleString());
        assertNotNull(createdRule);
        assertEquals("((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing'))", createdRule.getRuleString());
    }

    @Test
    void testGetRuleById() {
        when(ruleRepository.findById(anyLong())).thenReturn(Optional.of(rule));
        Rule foundRule = ruleService.getRuleById(1L);
        assertNotNull(foundRule);
        assertEquals(rule.getRuleString(), foundRule.getRuleString());
    }

    @Test
    void testGetAllRules() {
        List<Rule> rules = new ArrayList<>();
        rules.add(rule);
        when(ruleRepository.findAll()).thenReturn(rules);
        List<Rule> foundRules = ruleService.getAllRules();
        assertEquals(1, foundRules.size());
    }

    @Test
    void testUpdateRule() {
        when(ruleRepository.findById(anyLong())).thenReturn(Optional.of(rule));
        when(ruleRepository.save(any(Rule.class))).thenReturn(rule);
        
        Rule updatedRule = new Rule();
        updatedRule.setRuleString("Updated Rule");
        ruleService.updateRule(1L, updatedRule);
        
        assertEquals("Updated Rule", rule.getRuleString());
    }

    @Test
    void testDeleteRule() {
        doNothing().when(ruleRepository).deleteById(anyLong());
        ruleService.deleteRule(1L);
        verify(ruleRepository, times(1)).deleteById(1L);
    }
}
