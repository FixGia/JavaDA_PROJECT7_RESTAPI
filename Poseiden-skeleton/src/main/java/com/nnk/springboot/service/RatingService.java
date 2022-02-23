package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingRequest;

import java.util.List;

public interface RatingService {


    Rating saveRating(RatingRequest Ratingrequest);

    List<Rating> findAllRating();

     Rating getRatingById(Integer id);

    void updateRating(RatingRequest ratingRequest, Integer id);

    void deleteRatingById(Integer id);
}
