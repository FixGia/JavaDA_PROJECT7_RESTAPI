package com.nnk.springboot.ControllerTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingRequest;
import com.nnk.springboot.service.RatingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RatingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    private static Rating rating;

    @MockBean
    private RatingService ratingService;

    @BeforeEach
    public void contextLoads(){

        rating = new Rating();
        RatingRequest ratingRequest = new RatingRequest();
        rating.setId(1);
        rating.setFitchRating("fitchRatingTest");
        rating.setMoodysRating("MoodysRatingTest");
        rating.setOrderNumber(1);
        rating.setSandPRating("SandPRatingTest");
        ratingRequest.setOrderNumber(1);
        ratingRequest.setFitchRating("fitchRating2Test");
        ratingRequest.setMoodysRating("moodysRating2test");
        ratingRequest.setSandPRating("sangPRating2Test");

        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        when(ratingService.saveRating(ratingRequest)).thenReturn(rating);
    }

    @Test
    public void TestAddRatingTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/rating/add"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().size(2))
                .andExpect(view().name("rating/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestGetDeleteRating() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/rating/delete/1"))
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(status().is(302));
        verify(ratingService, times(1)).deleteRatingById(1);

    }

    @Test
    public void TestGetRatingListDeleteButNull() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/rating/delete/"))
                .andExpect(redirectedUrl(null))
                .andExpect(status().is(404))
                .andExpect(status().isNotFound());
        verify(ratingService, times(0)).deleteRatingById(1);
    }

    @Test
    public void TestGetRatingList() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().size(1))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isOk());

    }

    @Test
    public void TestGetRatingListtButEmpty() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/rating/list"))
                .andExpect(model().size(1))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isOk());

        verify(ratingService, times(1)).findAllRating();
        Assertions.assertEquals(ratingService.findAllRating(), Collections.emptyList());
    }

    @Test
    public void TestGetRatingUpdateById() throws Exception {
        when(ratingService.getRatingById(1)).thenReturn(rating);
        mvc.perform(MockMvcRequestBuilders.get("/rating/update/1"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().size(1))
                .andExpect(view().name("rating/update"))
                .andExpect(status().isOk());
        verify(ratingService).getRatingById(1);
    }
}
