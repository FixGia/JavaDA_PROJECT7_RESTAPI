package com.nnk.springboot.ServiceTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointRequest;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.Impl.CurvePointServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    private CurvePoint curvePoint;
    private CurvePointRequest curvePointRequest;

    @Mock
    private CurvePointRepository curvePointRepository;
    @InjectMocks
    private CurvePointServiceImpl curvePointService;


    @BeforeEach
    public void setUp() {

        curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setTerm(45.1);
        curvePoint.setValue(30.2);
        curvePoint.setId(1);

        curvePointRequest = new CurvePointRequest();
        curvePointRequest.setCurveId(2);
        curvePointRequest.setTerm(45.5);
        curvePointRequest.setValue(30.5);
    }

    @Test
    public void saveCurvePointTest() {
        curvePointService.saveCurvePoint(curvePointRequest);
        verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
    }

    @Test
    public void deleteCurvePointTest() {
        curvePointService.deleteCurvePointById(1);
        verify(curvePointRepository, times(1)).deleteById(1);
    }

    @Test
    public void getCurvePointByIdTest() {
        lenient().when(curvePointRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(curvePoint));
        curvePointService.getCurvePointById(1);
        verify(curvePointRepository, times(1)).findById(1);
    }
    @Test
    public void findAllCurvePointTest(){
        curvePointService.findAllCurvePoint();
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    public void updateCurvePointTest(){
        lenient().when(curvePointRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(curvePoint));
        curvePointService.updateCurvePoint(curvePointRequest,1);
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

}
