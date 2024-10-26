package com.zeotap.ruleengine.service;

import com.zeotap.ruleengine.Exception.ConditionNotFoundException;
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
import static org.mockito.Mockito.*;

public class RuleConditionServiceTest {

    @Mock
    private ConditionRepository ruleConditionRepository;

    @InjectMocks
    private ConditionServiceImpl ruleConditionService;

    private RuleCondition ruleCondition;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ruleCondition = new RuleCondition();
        ruleCondition.setId(1L);
        ruleCondition.setAttribute("age");
        ruleCondition.setOperator(">");
        ruleCondition.setValue("30");
    }

    @Test
    public void testCreateRuleCondition() {
        when(ruleConditionRepository.save(any(RuleCondition.class))).thenReturn(ruleCondition);

        RuleCondition createdCondition = ruleConditionService.createCondition(ruleCondition);

        assertNotNull(createdCondition);
        assertEquals(ruleCondition.getId(), createdCondition.getId());
        assertEquals(ruleCondition.getAttribute(), createdCondition.getAttribute());
        verify(ruleConditionRepository, times(1)).save(ruleCondition);
    }

    @Test
    public void testGetRuleConditionById() {
        when(ruleConditionRepository.findById(1L)).thenReturn(Optional.of(ruleCondition));

        RuleCondition foundCondition = ruleConditionService.getConditionById(1L);

        assertNotNull(foundCondition);
        assertEquals(ruleCondition.getId(), foundCondition.getId());
    }

    @Test
    public void testGetRuleConditionByIdNotFound() {
        when(ruleConditionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ConditionNotFoundException.class, () -> {
            ruleConditionService.getConditionById(1L);
        });
    }

    @Test
    public void testGetAllRuleConditions() {
        List<RuleCondition> conditions = new ArrayList<>();
        conditions.add(ruleCondition);
        when(ruleConditionRepository.findAll()).thenReturn(conditions);

    }
}