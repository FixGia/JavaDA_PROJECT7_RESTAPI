package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListRequest;
import com.nnk.springboot.exception.NotConformDataException;
import com.nnk.springboot.service.BidService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/bidList")
@Slf4j
public class BidListController {


    private final BidService bidService;

    public BidListController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping("/list")
    public String home(Model model) {

            model.addAttribute("bidList", bidService.findAllBidList());
            return "bidList/list";

    }


    @GetMapping("/add")
    public String addBidForm(BidListRequest bid, Model model) {

        model.addAttribute("bidList", bid);
        return "bidList/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute BidListRequest bid, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            bidService.saveBid(bid);
            model.addAttribute("bidLists", bidService.findAllBidList());

            return "redirect:/bidList/list";
        }

        log.error("Request post for bidList/validate"
                + " Error(s) {} ", result);

        model.addAttribute("bidList", bid);
        return "bidList/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidService.getBidById(id);
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListRequest bidList,
                             BindingResult result, Model model) {
       if(result.hasErrors()){


           model.addAttribute("bidList", bidService.getBidById(id));
           return "bidList/update";
       }

        bidService.updateBidList(bidList, id);
       model.addAttribute("AllBidList", bidService.findAllBidList());
       return "redirect:/bidList/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

        try {

            bidService.deleteBidById(id);
            model.addAttribute("bidList", bidService.findAllBidList());
            return "redirect:/bidList/list";

        } catch (NotConformDataException e) {

            model.addAttribute(bidService.findAllBidList());
            return "redirect:/bidList/list";
        }
    }
}
