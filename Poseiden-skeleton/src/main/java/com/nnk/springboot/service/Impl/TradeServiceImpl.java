package com.nnk.springboot.service.Impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeRequest;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.exception.NotConformDataException;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TradeServiceImpl implements TradeService {

    private TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public Trade saveTrade(TradeRequest tradeRequest) {

        try {
            Trade tradeToAdd= new Trade();
            tradeToAdd.setAccount(tradeRequest.getAccount());
            tradeToAdd.setType(tradeRequest.getType());
            tradeToAdd.setBuyQuantity(tradeRequest.getBuyQuantity());
            log.info("add a new Trade");
            tradeRepository.save(tradeToAdd);
            return tradeToAdd;
        } catch (NotConformDataException e) {
            log.error("can't add a new Trade");
         return null;
        }
    }

    @Override
    public List<Trade> findAllTrade() {


        try {
            log.info("displayed TradeList");
            return tradeRepository.findAll();
        } catch (DataNotFoundException e)
        {
            log.error("Trade List doesn't exist in DB ");
            return Collections.emptyList();
        }
    }

    @Override
    public Trade getTradeById(Integer id) {

        try {
            Optional<Trade> getTrade = tradeRepository.findById(id);
            log.info("Trade with id {} was found", id);
            return getTrade.get();
        } catch (DataNotFoundException e) {
            log.error("Trade doesn't found in DB");
            return null;
        }
    }

    @Override
    public void updateTrade(TradeRequest tradeRequest, Integer id) {
        Optional<Trade> tradeToUpdate = tradeRepository.findById(id);
        if (tradeToUpdate.isPresent()) {

            tradeToUpdate.get().setTradeId(id);
            tradeToUpdate.get().setAccount(tradeRequest.getAccount());
            tradeToUpdate.get().setType(tradeRequest.getType());
            tradeToUpdate.get().setBuyQuantity(tradeRequest.getBuyQuantity());
            tradeRepository.save(tradeToUpdate.get());
            log.info("Trade with id {} was updated", id);
            tradeToUpdate.get();
            return;
        } throw new DataNotFoundException("Trade doesn't found in DB");
    }

    @Override
    public void deleteTradeById(Integer id) {
        try {
            tradeRepository.deleteById(id);
            log.info("Trade with id {} was deleted", id);
        } catch (DataNotFoundException e) {
            log.error("Trade with id {} doesn't found in DB", id);
        }
    }
}
