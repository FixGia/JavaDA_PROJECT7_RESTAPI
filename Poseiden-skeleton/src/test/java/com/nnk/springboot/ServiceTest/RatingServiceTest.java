package com.nnk.springboot.ServiceTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingRequest;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.Impl.RatingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    private Rating rating;
    private RatingRequest ratingRequest;

    @Mock
    private RatingRepository ratingRepository;
    @InjectMocks
    private RatingServiceImpl ratingService;


    @BeforeEach
    public void setUp(){

        rating = new Rating();
        rating.setSandPRating("sandPRating");
        rating.setMoodysRating("MoodysRating");
        rating.setFitchRating("fitchRating");
        rating.setOrderNumber(1);
        rating.setId(1);

        ratingRequest = new RatingRequest();
        ratingRequest.setFitchRating("fitchRatingTest");
        ratingRequest.setMoodysRating("MoodysRatingTest");
        ratingRequest.setSandPRating("SandPRatingTest");
        ratingRequest.setOrderNumber(2);
    }

    @Test
    public void saveRatingTest(){
        ratingService.saveRating(ratingRequest);
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }
    @Test
    public void findAllRatingTest(){
        ratingService.findAllRating();
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    public void getRatingByIdTest(){
        lenient().when(ratingRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(rating));
        ratingService.getRatingById(1);
        verify(ratingRepository, times(1)).findById(1);
    }
    @Test
    public void deleteByIdTest(){
        ratingService.deleteRatingById(1);
        verify(ratingRepository, times(1)).deleteById(1);
    }
    @Test
    public void updateRatingTest(){
        lenient().when(ratingRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(rating));
        ratingService.updateRating(ratingRequest, 1);
        verify(ratingRepository, times(1)).save(rating);
    }


}
