package com.nnk.springboot.ControllerTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeRequest;
import com.nnk.springboot.service.TradeService;
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
public class TradeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    private static Trade trade;

    @MockBean
    private TradeService tradeService;

    @BeforeEach
    public void contextLoads() {

        trade = new Trade();
        TradeRequest tradeRequest = new TradeRequest();
        trade.setAccount("accountTest");
        trade.setTradeId(1);
        trade.setBuyQuantity(20.0);
        trade.setBenchmark("benchmarkTest");
        trade.setTrader("traderTest");
        trade.setBook("bookTest");
        trade.setBuyPrice(30.0);
        tradeRequest.setType("type2test");
        tradeRequest.setAccount("account2test");
        tradeRequest.setAccount("account2test");
        tradeRequest.setBuyQuantity(50.0);


        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        when(tradeService.saveTrade(tradeRequest)).thenReturn(trade);

    }


    @Test
    public void TestAddTradeTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/trade/add"))
                .andExpect(model().attributeExists("trade"))
                .andExpect(model().size(2))
                .andExpect(view().name("trade/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestGetDeleteTrade() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/trade/delete/1"))
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(status().is(302));
        verify(tradeService, times(1)).deleteTradeById(1);

    }

    @Test
    public void TestGetTradeDeleteButNull() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/trade/delete/"))
                .andExpect(redirectedUrl(null))
                .andExpect(status().is(404))
                .andExpect(status().isNotFound());
        verify(tradeService, times(0)).deleteTradeById(1);
    }

    @Test
    public void TestGetTradeList() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andExpect(model().size(1))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk());

    }

    @Test
    public void TestGetTradeListButEmpty() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(model().size(1))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk());

        verify(tradeService, times(1)).findAllTrade();
        Assertions.assertEquals(tradeService.findAllTrade(), Collections.emptyList());
    }

    @Test
    public void TestGetTradeUpdateById() throws Exception {
        when(tradeService.getTradeById(1)).thenReturn(trade);
        mvc.perform(MockMvcRequestBuilders.get("/trade/update/1"))
                .andExpect(model().attributeExists("trade"))
                .andExpect(model().size(1))
                .andExpect(view().name("trade/update"))
                .andExpect(status().isOk());

    }
}
