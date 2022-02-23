package com.nnk.springboot.ControllerTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointRequest;
import com.nnk.springboot.service.CurvePointService;
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
public class CurveControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    private static CurvePoint curvePoint;

    @MockBean
    private CurvePointService curvePointService;

    @BeforeEach
    public void contextLoads(){

        curvePoint = new CurvePoint();
        CurvePointRequest curvePointRequest = new CurvePointRequest();
        curvePoint.setCurveId(1);
        curvePoint.setId(2);
        curvePoint.setTerm(20.0);
        curvePoint.setValue(22.0);

        curvePointRequest.setCurveId(1);
        curvePointRequest.setId(2);
        curvePointRequest.setTerm(23.0);
        curvePointRequest.setValue(25.0);

        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        when(curvePointService.saveCurvePoint(curvePointRequest)).thenReturn(curvePoint);
    }

    @Test
    public void addCurvePointTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().size(2))
                .andExpect(view().name("curvePoint/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void GetCurvePointDeleteTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1"))
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(status().is(302));
        verify(curvePointService, times(1)).deleteCurvePointById(1);

    }

    @Test
    public void GetCurvePointDeleteButNullTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/"))
                .andExpect(redirectedUrl(null))
                .andExpect(status().is(404))
                .andExpect(status().isNotFound());
        verify(curvePointService, times(0)).deleteCurvePointById(1);
    }

    @Test
    public void GetCurvePointTest() throws Exception{

        mvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(model().size(1))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestGetCurvePointListButEmpty() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
                .andExpect(model().size(1))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isOk());

        verify(curvePointService, times(1)).findAllCurvePoint();
        Assertions.assertEquals(curvePointService.findAllCurvePoint(), Collections.emptyList());
    }

    @Test
    public void TestGetCurvePointUpdateById() throws Exception {
        when(curvePointService.getCurvePointById(1)).thenReturn(curvePoint);
        mvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/1"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().size(1))
                .andExpect(view().name("curvePoint/update"))
                .andExpect(status().isOk());
        verify(curvePointService).getCurvePointById(1);
    }
}
