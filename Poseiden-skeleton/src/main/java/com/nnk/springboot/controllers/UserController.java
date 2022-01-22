package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserRequest;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @RequestMapping("/list")
    public String home(Model model)
    {
        model.addAttribute("users", userService.findAllUser());
        return "user/list";
    }

    @GetMapping("/add")
    public String addUser(final UserRequest user, Model model) {


        model.addAttribute("user", user);
        return "/user/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("user")UserRequest user, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            userService.saveUser(user);
            model.addAttribute("users", userService.findAllUser());
            return "redirect:/user/list";
        }
        log.error("can't add user");
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
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
