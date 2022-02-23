package com.nnk.springboot.service.Impl;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingRequest;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.exception.NotConformDataException;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    @Override
    public Rating saveRating(RatingRequest ratingRequest) {

        try {
            Rating ratingToAdd = new Rating();
            ratingToAdd.setFitchRating(ratingRequest.getFitchRating());
            ratingToAdd.setMoodysRating(ratingRequest.getMoodysRating());
            ratingToAdd.setSandPRating(ratingRequest.getSandPRating());
            ratingToAdd.setOrderNumber(ratingRequest.getOrderNumber());
            log.info("add a new Rating");
            ratingRepository.save(ratingToAdd);
            return ratingToAdd;
        } catch (NotConformDataException e) {
            log.error("can't add a new Rating");
            return null;
        }
    }



    @Override
    public List<Rating> findAllRating() {

        try {
            log.info("displayed RatingList");
            return ratingRepository.findAll();
        } catch (DataNotFoundException e)
        {
            log.error("RatingList doesn't exist in DB ");
            return Collections.emptyList();
        }
    }

    @Override
    public Rating getRatingById(Integer id) {

        try {
            Optional<Rating> getRating = ratingRepository.findById(id);
            log.info("Rating with id {} found", id);
            return getRating.get();
        } catch (DataNotFoundException e) {
            log.error("Rating with id {} doesn't found", id);
            return null;
        }
    }

    @Override
    public void updateRating(RatingRequest ratingRequest, Integer id) {

        Optional<Rating> ratingToUpdate= ratingRepository.findById(id);
        if (ratingToUpdate.isPresent()){

            ratingToUpdate.get().setId(id);

            String fitchRating = ratingRequest.getFitchRating();
            if (fitchRating!= null) {
                ratingToUpdate.get().setFitchRating(fitchRating);
            }
            String moodyRating = ratingRequest.getMoodysRating();
            if (moodyRating != null) {
                ratingToUpdate.get().setMoodysRating(moodyRating);
            }
            String sandPRating = ratingRequest.getSandPRating();
            if (sandPRating != null) {
                ratingToUpdate.get().setSandPRating(sandPRating);
            }
            ratingToUpdate.get().setOrderNumber(ratingRequest.getOrderNumber());
            ratingRepository.save(ratingToUpdate.get());
            log.info("Rating with id {} was updated", id);
            ratingToUpdate.get();
            return;
        }
        throw  new DataNotFoundException("Rating doesn't found in DB");
    }

    @Override
    public void deleteRatingById(Integer id) {

        try {
            ratingRepository.deleteById(id);
        } catch (DataNotFoundException e) {
            log.error("Rating with id {} doesn't found in DB", id);
        }
    }
}
