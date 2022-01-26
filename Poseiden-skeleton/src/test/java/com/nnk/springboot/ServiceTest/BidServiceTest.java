package com.nnk.springboot.ServiceTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListRequest;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.Impl.BidServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidServiceTest {

    private BidList bidList;
    private BidListRequest bidListRequest;

    @Mock
    private BidListRepository bidListRepository;
    @InjectMocks
    private BidServiceImpl bidService;

    @BeforeEach
    public void setUp() {

        bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setType("type");
        bidList.setAccount("account");
        bidList.setBidQuantity(20.2);

        bidListRequest = new BidListRequest();
        bidListRequest.setAccount("accountTest");
        bidListRequest.setType("TypeTest");
        bidListRequest.setBidQuantity(50.1);
    }

    @Test
    public void findAllBidTest(){
        bidService.findAllBidList();
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    public void getBidByIdTest(){
        lenient().when(bidListRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(bidList));
        bidService.getBidById(1);
        verify(bidListRepository, times(1)).findById(1);
    }
    @Test
    public void updateBidListTest(){
        lenient().when(bidListRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(bidList));
        bidService.updateBidList(bidListRequest, 1);
        verify(bidListRepository,times(1)).save(bidList);
    }
    @Test
    public void deleteBidListTest(){
        bidService.deleteBidById(1);
        verify(bidListRepository, times(1)).deleteById(1);
    }
    @Test
    public void saveBidTest(){
        bidService.saveBid(bidListRequest);
        verify(bidListRepository, times(1)).save(any(BidList.class));
    }
}
