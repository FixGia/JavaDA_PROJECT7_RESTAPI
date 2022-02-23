package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeRequest;

import java.util.List;

public interface TradeService {

    Trade saveTrade(TradeRequest tradeRequest);

    List<Trade> findAllTrade();

    Trade getTradeById(Integer id);

    void updateTrade(TradeRequest tradeRequest, Integer id);

    void deleteTradeById(Integer id);

}
