package com.nnk.springboot.service.Impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListRequest;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.exception.NotConformDataException;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BidServiceImpl implements BidService {

    private BidListRepository bidListRepository;

    public BidServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }


    @Override
    public List<BidList> findAllBidList() {


        try {
            log.info("displayed BidLists");
            return bidListRepository.findAll();
        } catch (DataNotFoundException e)
        {
            log.error("BidList doesn't exist in DB ");
            return Collections.emptyList();
        }
    }

    @Override
    public BidList getBidById(Integer id) {

        Optional<BidList> bidListToFind = bidListRepository.findById(id);
        if (bidListToFind.isPresent()) {
            log.info("BidList with id {} was found", id);
            return bidListToFind.get();
        }
        throw new DataNotFoundException("BidList with id : " + id + "wasn't found in DB");
    }

    @Override
    public void updateBidList(BidListRequest bidList, Integer id) {

        Optional<BidList> searchBidList = bidListRepository.findById(id);

        if (searchBidList.isPresent()) {

            searchBidList.get().setBidListId(id);

            BidList updateBidList = searchBidList.get();
            String account = bidList.getAccount();
            if (account != null) {
                updateBidList.setAccount(account);
            }
            String type = bidList.getType();
            if (type != null) {
                updateBidList.setType(type);
            }
            double bidQuantity = bidList.getBidQuantity();
            updateBidList.setBidQuantity(bidQuantity);

            log.info("bid with id{} was updated", searchBidList.get().getBidListId());
            bidListRepository.save(updateBidList);
            return;
        }
        throw new DataNotFoundException("bid with id: " + id + " wasn't found");
    }

    @Override
    public void deleteBidById(Integer id) {

        try {
            bidListRepository.deleteById(id);

        } catch (DataNotFoundException e) {
            log.error("BidList with id {} doesn't found in DB", id);
        }
    }


    public BidList saveBid(BidListRequest bidList) {

       try {
           BidList bidListToCreate = new BidList();
            bidListToCreate.setAccount(bidList.getAccount());
            bidListToCreate.setType(bidList.getType());
            bidListToCreate.setBidQuantity(bidList.getBidQuantity());
            bidListRepository.save(bidListToCreate);
            return bidListToCreate;
        } catch (NotConformDataException exception) {
           exception.printStackTrace();
           log.error("BidList wasn't valid Data, Bidlist wasn't save in DB");
           return null;
       }
    }
}

