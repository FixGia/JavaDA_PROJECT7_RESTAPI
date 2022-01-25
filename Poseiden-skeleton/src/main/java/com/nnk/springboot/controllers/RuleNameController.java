package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleRequest;
import com.nnk.springboot.exception.NotConformDataException;
import com.nnk.springboot.service.RuleNameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/ruleName")
public class RuleNameController {

    private RuleNameService ruleNameService;

    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @RequestMapping("/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNames", ruleNameService.findAllRule());
        return "ruleName/list";
    }

    @GetMapping("/add")
    public String addRuleForm(RuleRequest ruleRequest, Model model) {

        model.addAttribute("ruleName", ruleRequest);
        return "ruleName/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute RuleRequest ruleName, BindingResult result, Model model) {

        if (!result.hasErrors()){
            ruleNameService.saveRuleName(ruleName);
            model.addAttribute("ruleNames", ruleNameService.findAllRule());
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        RuleName ruleName = ruleNameService.getRuleById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleRequest ruleName,
                             BindingResult result, Model model) {

        if (result.hasErrors()){
            return "ruleName/update";
        }
        ruleNameService.updateRule(ruleName, id);
        model.addAttribute("ruleNames", ruleNameService.findAllRule());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {

        try {
            ruleNameService.deleteRuleName(id);
            model.addAttribute("ruleNames", ruleNameService.findAllRule());
            return "redirect:/ruleName/list";

        } catch (NotConformDataException e) {

            model.addAttribute("ruleNames", ruleNameService.findAllRule());
            return "redirect:/ruleName/list";
        }
    }
}
