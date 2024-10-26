package com.zeotap.ruleengine.service;

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
import static org.mockito.Mockito.*;

public class RuleServiceTest {

    @Mock
    private RuleRepository ruleRepository;

    @InjectMocks
    private RuleServiceImpl ruleService;

    private Rule rule;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rule = new Rule();
        rule.setId(1L);
        rule.setRuleString("((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing'))");
        rule.setAst("Generated AST");
    }

    @Test
    public void testCreateRule() {
        when(ruleRepository.save(any(Rule.class))).thenReturn(rule);
        Rule createdRule = ruleService.createRule(rule.getRuleString());
        assertNotNull(createdRule);
        assertEquals(rule.getId(), createdRule.getId());
        assertEquals(rule.getRuleString(), createdRule.getRuleString());
        verify(ruleRepository, times(1)).save(any(Rule.class));
    }

    @Test
    public void testGetRuleById() {
        when(ruleRepository.findById(1L)).thenReturn(Optional.of(rule));

        Rule foundRule = ruleService.getRuleById(1L);

        assertNotNull(foundRule);
        assertEquals(rule.getId(), foundRule.getId());
        assertEquals(rule.getRuleString(), foundRule.getRuleString());
    }

    @Test
    public void testGetRuleByIdNotFound() {
        when(ruleRepository.findById(1L)).thenReturn(Optional.empty());

        Rule foundRule = ruleService.getRuleById(1L);

        assertNull(foundRule);
    }

    @Test
    public void testGetAllRules() {
        List<Rule> rules = new ArrayList<>();
        rules.add(rule);
        when(ruleRepository.findAll()).thenReturn(rules);

        List<Rule> foundRules = ruleService.getAllRules();

        assertNotNull(foundRules);
        assertEquals(1, foundRules.size());
        assertEquals(rule.getId(), foundRules.get(0).getId());
    }

    @Test
    public void testUpdateRule() {
        when(ruleRepository.findById(1L)).thenReturn(Optional.of(rule));
        when(ruleRepository.save(any(Rule.class))).thenReturn(rule);

        Rule updatedRule = new Rule();
        updatedRule.setRuleString("((age > 35 AND department = 'Sales'))");
        ruleService.updateRule(1L, updatedRule);

        assertEquals(updatedRule.getRuleString(), rule.getRuleString());
        verify(ruleRepository, times(1)).save(rule);
    }

    @Test
    public void testUpdateRuleNotFound() {
        when(ruleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            ruleService.updateRule(1L, rule);
        });
    }

    @Test
    public void testDeleteRule() {
        doNothing().when(ruleRepository).deleteById(1L);

        ruleService.deleteRule(1L);

        verify(ruleRepository, times(1)).deleteById(1L);
    }
}
