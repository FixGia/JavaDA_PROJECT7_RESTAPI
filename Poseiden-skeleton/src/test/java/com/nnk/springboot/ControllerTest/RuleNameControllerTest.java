package com.nnk.springboot.ControllerTest;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleRequest;
import com.nnk.springboot.service.RuleNameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    private static RuleName ruleName;

    @MockBean
    private RuleNameService ruleNameService;

    @BeforeEach
    public void contextLoads(){
        ruleName = new RuleName();
        RuleRequest ruleRequest = new RuleRequest();
        ruleName.setDescription("DescriptionTest");
        ruleName.setSqlStr("sqlStrTest");
        ruleName.setJson("JsonTest");
        ruleName.setTemplate("TemplateTest");
        ruleName.setId(1);
        ruleName.setName("nameTest");

        ruleRequest.setSqlPart("sqlPart2Test");
        ruleRequest.setName("name2test");
        ruleRequest.setId(10);
        ruleRequest.setJson("Json2Test");
        ruleRequest.setDescription("Description2Test");
        ruleRequest.setTemplate("template2Test");
        ruleRequest.setSqlStr("sqlStr2test");


        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        when(ruleNameService.saveRuleName(ruleRequest)).thenReturn(ruleName);

    }


    @Test
    public void TestAddRuleTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(model().size(2))
                .andExpect(view().name("ruleName/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestGetDeleteRule() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/1"))
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(status().is(302));


    }

    @Test
    public void TestGetRuleNameDeleteButNull() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/bidList/delete/"))
                .andExpect(redirectedUrl(null))
                .andExpect(status().is(404))
                .andExpect(status().isNotFound());
        verify(ruleNameService, times(0)).deleteRuleName(1);
    }

    @Test
    public void TestGetRuleNameList() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andExpect(model().size(1))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());

    }

    @Test
    public void TestGetRuleNameListButEmpty() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(model().size(1))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());

        verify(ruleNameService, times(1)).findAllRule();
        Assertions.assertEquals(ruleNameService.findAllRule(), Collections.emptyList());
    }

    @Test
    public void TestGetBidListUpdateById() throws Exception {
        when(ruleNameService.getRuleById(1)).thenReturn(ruleName);
        mvc.perform(MockMvcRequestBuilders.get("/ruleName/update/1"))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(model().size(1))
                .andExpect(view().name("ruleName/update"))
                .andExpect(status().isOk());
    }
}

