package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeRequest;

import java.util.List;

public interface TradeService {

    public Trade saveTrade(TradeRequest tradeRequest);

    public List<Trade> findAllTrade();

    public Trade getTradeById(Integer id);

    public Trade updateTrade(TradeRequest tradeRequest, Integer id);

    public void deleteTradeById(Integer id);

}
