package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.exception.NotConformDataException;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

        List<BidList> bidLists = bidListRepository.findAll();
        if (bidLists != null) {
            log.info(" BidList is displayed ");
            return bidLists;
        }
        throw new DataNotFoundException("BidList wasn't found in DB");
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
    public BidList updateBidList(BidList bidList) {

        Optional<BidList> searchBidList = bidListRepository.findById(bidList.getBidListId());

        if (searchBidList.isPresent()) {
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
            return updateBidList;
        }
        throw new DataNotFoundException("bid with id: " + searchBidList.get().getBidListId() + " wasn't found");
    }

    @Override
    public void deleteBidById(Integer id) {

        Optional<BidList> bidListToFind = bidListRepository.findById(id);
        if (bidListToFind.isPresent()) {
            log.info("BidList with id {} was found", id);
            bidListRepository.delete(bidListToFind.get());
        }
        throw new DataNotFoundException("BidList with id : " + id + "wasn't found in DB");
    }

    public BidList saveBid(BidList bidList) {

        if (bidList != null) {
            bidList.setAccount(bidList.getAccount());
            bidList.setType(bidList.getType());
            bidList.setBidQuantity(bidList.getBidQuantity());
            bidListRepository.save(bidList);
            return bidList;
        }
        throw new NotConformDataException("BidList wasn't valid Data, Bidlist wasn't save in DB");
    }
}

