package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface BidService {

    public BidList saveBid(BidList bidList);

    public List<BidList> findAllBidList();

    public BidList getBidById(Integer id);

    public BidList updateBidList(BidList bidList);

    public void deleteBidById(Integer id);
}
