package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingRequest;

import java.util.List;

public interface RatingService {


    public Rating saveRating(RatingRequest Ratingrequest);

    public List<Rating> findAllRating();

    public Rating getRatingById(Integer id);

    public Rating updateRating(RatingRequest ratingRequest, Integer id);

    public void deleteRatingById(Integer id);
}
