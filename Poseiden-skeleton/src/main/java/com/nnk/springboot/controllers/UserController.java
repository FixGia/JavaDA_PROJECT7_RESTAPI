package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserRequest;
import com.nnk.springboot.exception.NotConformDataException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }


    @RequestMapping("/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAllUser());
        return "user/list";
    }

    @GetMapping("/add")
    public String addUser(final UserRequest user, Model model) {


        model.addAttribute("user", user);
        return "/user/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("user") UserRequest user, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            userService.saveUser(user);
            model.addAttribute("users", userService.findAllUser());
            return "redirect:/user/list";
        }

        return "user/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserRequest user,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "user/update";
        }

        userService.updateUser(user, id);
        model.addAttribute("users", userService.findAllUser());
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {

        try {
            userService.deleteUserById(id);
            model.addAttribute("users", userService.findAllUser());
            return "redirect:/user/list";
        } catch (NotConformDataException e) {
            model.addAttribute("users", userService.findAllUser());
            return "redirect:/user/list";
        }
    }
}
