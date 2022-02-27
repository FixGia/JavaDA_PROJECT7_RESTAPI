package com.nnk.springboot.ControllerTest;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListRequest;
import com.nnk.springboot.service.BidService;
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
public class BidListControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;



    private static BidList bidList;

    @MockBean
    private BidService bidService;


    @BeforeEach
    public void contextLoads() {


        bidList = new BidList();
        bidList.setBidQuantity(1.0);
        bidList.setBidListId(1);
        bidList.setType("typeTest");
        bidList.setAccount("accountTest");

        BidListRequest bidListRequest = new BidListRequest();
        bidListRequest.setBidQuantity(2.0);
        bidListRequest.setAccount("account2Test");
        bidListRequest.setType("type2Test");
        bidListRequest.setBidListId(1);

        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        when(bidService.saveBid(bidListRequest)).thenReturn(bidList);

    }


    @Test
    public void TestAddBidTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/bidList/add"))
                .andExpect(model().attributeExists("bidList"))
                .andExpect(model().size(2))
                .andExpect(view().name("bidList/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestGetDeleteBidList() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/bidList/delete/1"))
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(status().is(302));
        verify(bidService, times(1)).deleteBidById(1);

    }

    @Test
    public void TestGetBidListDeleteButNull() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/bidList/delete/"))
                .andExpect(redirectedUrl(null))
                .andExpect(status().is(404))
                .andExpect(status().isNotFound());
        verify(bidService, times(0)).deleteBidById(1);
    }

    @Test
    public void TestGetBidList() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
                .andExpect(model().attributeExists("bidList"))
                .andExpect(model().size(1))
                .andExpect(view().name("bidList/list"))
                .andExpect(status().isOk());

    }

    @Test
    public void TestGetBidListButEmpty() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
                .andExpect(model().size(1))
                .andExpect(view().name("bidList/list"))
                .andExpect(status().isOk());

        verify(bidService, times(1)).findAllBidList();
        Assertions.assertEquals(bidService.findAllBidList(), Collections.emptyList());
    }

    @Test
    public void TestGetBidListUpdateById() throws Exception {
        when(bidService.getBidById(1)).thenReturn(bidList);
        mvc.perform(MockMvcRequestBuilders.get("/bidList/update/1"))
                .andExpect(model().attributeExists("bidList"))
                .andExpect(model().size(1))
                .andExpect(view().name("bidList/update"))
                .andExpect(status().isOk());
        verify(bidService).getBidById(1);
    }
}

