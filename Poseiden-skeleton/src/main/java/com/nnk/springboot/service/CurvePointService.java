package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointRequest;

import java.util.List;

public interface CurvePointService {


    CurvePoint saveCurvePoint(CurvePointRequest curvePointRequest);

    List<CurvePoint> findAllCurvePoint();

    CurvePoint getCurvePointById(Integer id);

    void updateCurvePoint(CurvePointRequest curvePointRequest, Integer id);

    void deleteCurvePointById( Integer id);

}
