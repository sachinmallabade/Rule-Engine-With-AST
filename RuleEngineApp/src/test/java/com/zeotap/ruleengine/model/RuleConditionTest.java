package com.zeotap.ruleengine.model;

import com.zeotap.ruleengine.Model.RuleCondition;
import com.zeotap.ruleengine.Repository.ConditionRepository;
import com.zeotap.ruleengine.Service.Impl.ConditionServiceImpl;
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

class RuleConditionTest {

    @Mock
    private ConditionRepository ruleConditionRepository;

    @InjectMocks
    private ConditionServiceImpl ruleConditionService;

    private RuleCondition ruleCondition;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ruleCondition = new RuleCondition();
        ruleCondition.setId(1L);
        ruleCondition.setAttribute("age");
        ruleCondition.setOperator(">");
        ruleCondition.setValue("30");
    }

    @Test
    void testCreateRuleCondition() {
        when(ruleConditionRepository.save(any(RuleCondition.class))).thenReturn(ruleCondition);
        RuleCondition createdRuleCondition = ruleConditionService.createCondition(ruleCondition);
        assertNotNull(createdRuleCondition);
        assertEquals("age", createdRuleCondition.getAttribute());
    }

    @Test
    void testGetRuleConditionById() {
        when(ruleConditionRepository.findById(anyLong())).thenReturn(Optional.of(ruleCondition));
        RuleCondition foundRuleCondition = ruleConditionService.getConditionById(1L);
        assertNotNull(foundRuleCondition);
        assertEquals(ruleCondition.getAttribute(), foundRuleCondition.getAttribute());
    }

    @Test
    void testGetAllRuleConditions() {
        List<RuleCondition> ruleConditions = new ArrayList<>();
        ruleConditions.add(ruleCondition);
        when(ruleConditionRepository.findAll()).thenReturn(ruleConditions);
        List<RuleCondition> foundRuleConditions = ruleConditionService.getAllConditions();
        assertEquals(1, foundRuleConditions.size());
    }

    @Test
    void testUpdateRuleCondition() {
        when(ruleConditionRepository.findById(anyLong())).thenReturn(Optional.of(ruleCondition));
        when(ruleConditionRepository.save(any(RuleCondition.class))).thenReturn(ruleCondition);
        
        RuleCondition updatedRuleCondition = new RuleCondition();
        updatedRuleCondition.setAttribute("department");
        ruleConditionService.updateCondition(1L, updatedRuleCondition);
        
        assertEquals("department", ruleCondition.getAttribute());
    }

    @Test
    void testDeleteRuleCondition() {
        doNothing().when(ruleConditionRepository).deleteById(anyLong());
        ruleConditionService.deleteCondition(1L);
        verify(ruleConditionRepository, times(1)).deleteById(1L);
    }
}
