package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListRequest;

import java.util.List;

public interface BidService {

    BidList saveBid(BidListRequest bidList);

    List<BidList> findAllBidList();

    BidList getBidById(Integer id);

    void updateBidList(BidListRequest bidList, Integer id);

    void deleteBidById(Integer id);
}
