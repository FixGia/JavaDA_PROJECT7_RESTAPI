package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointRequest;
import com.nnk.springboot.exception.NotConformDataException;
import com.nnk.springboot.service.CurvePointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/curvePoint")
public class CurveController {

    private final CurvePointService curvePointService;

    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @RequestMapping("/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.findAllCurvePoint());
        return "curvePoint/list";
    }

    @GetMapping("/add")
    public String addBidForm(CurvePointRequest curvePoint, Model model) {

        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CurvePointRequest curvePoint, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            curvePointService.saveCurvePoint(curvePoint);
            model.addAttribute("curvePoints", curvePointService.findAllCurvePoint());
            return "redirect:/curvePoint/list";
        }

        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        CurvePoint curvePoint = curvePointService.getCurvePointById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute("curvePoint") CurvePointRequest curvePoint,
                            BindingResult result, Model model) {

        if (result.hasErrors()) {

            return "curvePoint/update";
        }
        curvePointService.updateCurvePoint(curvePoint, id);
        model.addAttribute("CurvePoints", curvePointService.findAllCurvePoint());
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

        try {
            curvePointService.deleteCurvePointById(id);
            model.addAttribute("curvePoints", curvePointService.findAllCurvePoint());

            return "redirect:/curvePoint/list";
        } catch (NotConformDataException e) {
            model.addAttribute("curvePoints", curvePointService.findAllCurvePoint());
            return "redirect:/curvePoint/list";
        }
    }
}
