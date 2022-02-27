package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingRequest;
import com.nnk.springboot.exception.NotConformDataException;
import com.nnk.springboot.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("rating")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/list")
    public String home(Model model) {

        model.addAttribute("ratings", ratingService.findAllRating());
        return "rating/list";
    }

    @GetMapping("/add")
    public String addRatingForm(final RatingRequest rating, Model model) {

        model.addAttribute("rating", rating);
        return "rating/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute  RatingRequest rating, BindingResult result, Model model) {

        if (!result.hasErrors()){
            ratingService.saveRating(rating);
            model.addAttribute("ratings", ratingService.findAllRating());
            return "redirect:/rating/list";
        }
        model.addAttribute("rating", rating);
        return "rating/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        Rating rating = ratingService.getRatingById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid @ModelAttribute RatingRequest rating,
                             BindingResult result, Model model) {

        if (result.hasErrors()){
            model.addAttribute("rating", ratingService.getRatingById(id));
            return "rating/update";
        }
        ratingService.updateRating(rating, id);
        model.addAttribute("ratings", ratingService.findAllRating());
        return "redirect:/rating/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {

        try {
            ratingService.deleteRatingById(id);
            model.addAttribute("ratings", ratingService.findAllRating());
            return "redirect:/rating/list";

        } catch (NotConformDataException e){

            model.addAttribute("ratings", ratingService.findAllRating());
            return "redirect:/rating/list";
        }
    }
}
