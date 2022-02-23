package com.nnk.springboot.service.Impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointRequest;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.exception.NotConformDataException;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CurvePointServiceImpl implements CurvePointService {


    private CurvePointRepository curvePointRepository;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }


    @Override
    public CurvePoint saveCurvePoint(CurvePointRequest curvePointRequest) {

        try {
            CurvePoint curvePointToAdd= new CurvePoint();
            curvePointToAdd.setCurveId(curvePointRequest.getCurveId());
            curvePointToAdd.setTerm(curvePointRequest.getTerm());
            curvePointToAdd.setValue(curvePointRequest.getValue());

            log.info("add a new curvePoint");
            curvePointRepository.save(curvePointToAdd);
            return curvePointToAdd;
        } catch (NotConformDataException e) {
            log.error("Can't add a new CurvePoint");
            return null;
        }
    }

    @Override
    public List<CurvePoint> findAllCurvePoint() {

       try {
           log.info("displayed CurvePointList");
           return curvePointRepository.findAll();
       } catch (DataNotFoundException e)
       {
           log.error("CurvePoint List doesn't exist in DB ");
           return Collections.emptyList();
       }
    }

    @Override
    public CurvePoint getCurvePointById(Integer id) {

        try {
            Optional<CurvePoint> getCurvePoint = curvePointRepository.findById(id);
            log.info("curvePoint with id {} was found in DB", id);
            return getCurvePoint.get();
        } catch (DataNotFoundException e) {
            log.error("curvePoint with id {} doesn't found in DB", id);
            return null;
        }
    }
    @Override
    public void updateCurvePoint(CurvePointRequest curvePointRequest, Integer id) {

        Optional<CurvePoint> curvePointToUpdate = curvePointRepository.findById(id);
        if( curvePointToUpdate.isPresent()){

            curvePointToUpdate.get().setId(id);
            curvePointToUpdate.get().setCurveId(curvePointRequest.getCurveId());
            curvePointToUpdate.get().setValue(curvePointRequest.getValue());
            curvePointToUpdate.get().setTerm(curvePointRequest.getTerm());
            curvePointRepository.save(curvePointToUpdate.get());

            log.info("CurvePoint with id {} wad updated", id );
            curvePointToUpdate.get();
            return;

        } throw new DataNotFoundException("CurvePoint doesn't found in DB");
    }

    @Override
    public void deleteCurvePointById(Integer id) {

        try {
            curvePointRepository.deleteById(id);
            log.info("curvePoint with id {} was deleted with success", id);
        } catch (DataNotFoundException e) {
            log.error("CurvePoint with Id {}, doesn't found in DB", id);
        }
    }
}
