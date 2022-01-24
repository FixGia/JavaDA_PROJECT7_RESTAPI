package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListRequest;

import java.util.List;

public interface BidService {

    public BidList saveBid(BidListRequest bidList);

    public List<BidList> findAllBidList();

    public BidList getBidById(Integer id);

    public BidList updateBidList(BidListRequest bidList, Integer id);

    public void deleteBidById(Integer id);
}
