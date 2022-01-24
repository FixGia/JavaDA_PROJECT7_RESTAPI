package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointRequest;

import java.util.List;

public interface CurvePointService {


    public CurvePoint saveCurvePoint(CurvePointRequest curvePointRequest);

    public List<CurvePoint> findAllCurvePoint();

    public CurvePoint getCurvePointById(Integer id);

    public CurvePoint updateCurvePoint(CurvePointRequest curvePointRequest, Integer id);

    public void deleteCurvePointById( Integer id);

}
