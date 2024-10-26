
package com.zeotap.ruleengine.controller;

import com.zeotap.ruleengine.Controller.RuleController;
import com.zeotap.ruleengine.Model.Rule;
import com.zeotap.ruleengine.Service.RuleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RuleControllerTest {

    @InjectMocks
    private RuleController ruleController;

    @Mock
    private RuleService ruleService;

    private Rule testRule;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testRule = new Rule();
        testRule.setId(1L);
        testRule.setRuleString("((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 50000 OR experience > 5)");
        testRule.setAst("Generated AST");
    }

    @Test
    public void createRule_ShouldReturnCreatedRule() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("ruleString", testRule.getRuleString());
        when(ruleService.createRule(testRule.getRuleString())).thenReturn(testRule);
        ResponseEntity<Rule> response = ruleController.createRule(requestBody);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testRule, response.getBody());
        verify(ruleService, times(1)).createRule(testRule.getRuleString());
    }



    @Test
    public void getRuleById_ShouldReturnRule_WhenExists() {
        when(ruleService.getRuleById(anyLong())).thenReturn(testRule);

        ResponseEntity<Rule> response = ruleController.getRuleById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testRule, response.getBody());
    }

    @Test
    public void getRuleById_ShouldReturnNotFound_WhenDoesNotExist() {
        when(ruleService.getRuleById(anyLong())).thenReturn(null);

        ResponseEntity<Rule> response = ruleController.getRuleById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getAllRules_ShouldReturnListOfRules() {
        List<Rule> rules = new ArrayList<>();
        rules.add(testRule);
        when(ruleService.getAllRules()).thenReturn(rules);

        ResponseEntity<List<Rule>> response = ruleController.getAllRules();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(rules, response.getBody());
    }

    @Test
    public void updateRule_ShouldReturnOk_WhenUpdated() {
        when(ruleService.getRuleById(anyLong())).thenReturn(testRule);

        ResponseEntity<Void> response = ruleController.updateRule(1L, testRule);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(ruleService, times(1)).updateRule(anyLong(), any(Rule.class));
    }

    @Test
    public void updateRule_ShouldReturnNotFound_WhenRuleDoesNotExist() {
        when(ruleService.getRuleById(anyLong())).thenReturn(null);

        ResponseEntity<Void> response = ruleController.updateRule(1L, testRule);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteRule_ShouldReturnNoContent_WhenDeleted() {
        doNothing().when(ruleService).deleteRule(anyLong());

        ResponseEntity<Void> response = ruleController.deleteRule(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(ruleService, times(1)).deleteRule(anyLong());
    }
}
