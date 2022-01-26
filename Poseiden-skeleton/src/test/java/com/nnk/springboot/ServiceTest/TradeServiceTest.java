package com.nnk.springboot.ServiceTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeRequest;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.Impl.TradeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    private Trade trade;
    private TradeRequest tradeRequest;

    @Mock
    private TradeRepository tradeRepository;
    @InjectMocks
    private TradeServiceImpl tradeService;


    @BeforeEach
    public void setUp(){

        trade = new Trade();
        trade.setTradeId(1);
        trade.setBuyQuantity(50.2);
        trade.setType("type");
        trade.setAccount("account");

        tradeRequest = new TradeRequest();
        tradeRequest.setAccount("accountTest");
        tradeRequest.setType("typeTest");
        tradeRequest.setBuyQuantity(20.5);

    }
    @Test
    public void saveTradeTest(){
        tradeService.saveTrade(tradeRequest);
        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test
    public void deleteTradeTest(){
        tradeService.deleteTradeById(1);
        verify(tradeRepository, times(1)).deleteById(1);
    }
    @Test
    public void findAllTradeTest(){
        tradeService.findAllTrade();
        verify(tradeRepository, times(1)).findAll();
    }
    @Test
    public void getTradeByIdTest(){
        lenient().when(tradeRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(trade));
        tradeService.getTradeById(1);
        verify(tradeRepository, times(1)).findById(1);
    }
    @Test
    public void updateTradeTest(){
        lenient().when(tradeRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(trade));
        tradeService.updateTrade(tradeRequest, 1);
        verify(tradeRepository, times(1)).save(trade);
    }
}
