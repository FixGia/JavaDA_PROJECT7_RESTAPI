package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeRequest;
import com.nnk.springboot.exception.NotConformDataException;
import com.nnk.springboot.service.TradeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/trade")
public class TradeController {

    private TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/list")
    public String home(Model model)
    {
        model.addAttribute("trades", tradeService.findAllTrade());
        return "trade/list";
    }

    @GetMapping("/add")
    public String addUser(final TradeRequest tradeRequest, Model model) {

        model.addAttribute("trade", tradeRequest);
        return "trade/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute TradeRequest trade, BindingResult result, Model model) {
       if (!result.hasErrors()){
           tradeService.saveTrade(trade);
           model.addAttribute("trades", tradeService.findAllTrade());
           return "redirect:/trade/list";
       }

        return "trade/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        Trade trade = tradeService.getTradeById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeRequest trade,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "redirect:/trade/update";
        }
        tradeService.updateTrade(trade, id);
        model.addAttribute("trades", tradeService.findAllTrade());
        return "redirect:/trade/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {

        try {
            tradeService.deleteTradeById(id);
            model.addAttribute("trades", tradeService.findAllTrade());
            return "redirect:/trade/list";

        } catch (NotConformDataException e) {
            model.addAttribute("trades", tradeService.findAllTrade());
            return "redirect:/trade/list";
        }
    }
}
