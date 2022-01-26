package com.nnk.springboot.ServiceTest;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleRequest;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.Impl.RuleNameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleServiceTest {

    private RuleName ruleName;
    private RuleRequest ruleRequest;

    @Mock
    RuleNameRepository ruleNameRepository;
    @InjectMocks
    RuleNameServiceImpl ruleNameService;

    @BeforeEach()
    public void setUp(){

        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("name");
        ruleName.setSqlPart("sqlpart");
        ruleName.setTemplate("template");
        ruleName.setJson("json");
        ruleName.setSqlStr("sqlStr");
        ruleName.setDescription("description");


        ruleRequest = new RuleRequest();
        ruleRequest.setDescription("descriptionTest");
        ruleRequest.setId(1);
        ruleRequest.setJson("jsonTest");
        ruleRequest.setName("nameTest");
        ruleRequest.setSqlPart("sqlparttest");
        ruleRequest.setSqlStr("sqlstrTest");


    }
    @Test
    public void findAllRuleNametest() {
        ruleNameService.findAllRule();
        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    public void getRuleByIdTest(){
        lenient().when(ruleNameRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(ruleName));
        ruleNameService.getRuleById(1);
        verify(ruleNameRepository, times(1)).findById(1);
    }
    @Test
    public void saveRuleTest() {
        ruleNameService.saveRuleName(ruleRequest);
        verify(ruleNameRepository, times(1)).save(any(RuleName.class));
    }

    @Test
    public void deleteRuleById(){
        ruleNameService.deleteRuleName(1);
        verify(ruleNameRepository, times(1)).deleteById(1);
    }
    @Test
    public void updateRuleTest(){
        lenient().when(ruleNameRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(ruleName));
        ruleNameService.updateRule(ruleRequest, 1);
        verify(ruleNameRepository, times(1)).save(ruleName);
    }
}
