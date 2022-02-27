package com.nnk.springboot.controllers;

import com.nnk.springboot.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@Log4j2
@RequestMapping("app")
public class LoginController {


    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.findAllUser());
        mav.setViewName("user/list");
        return mav;
    }

    @GetMapping("error")
    public ModelAndView error(
            @AuthenticationPrincipal final OAuth2User principal) {

        ModelAndView mav = new ModelAndView();

        String errorMessage = "You are not authorized"
                + " for the requested data.";

        mav.addObject("errorMsg", errorMessage);

        if (principal != null) {

            mav.addObject("login", principal
                    .getAttribute("login"));
        }

        mav.setViewName("403");

        log.error("Login redirect 403 error");

        return mav;
    }


    @RequestMapping("/login?error")
    public String loginError(final Model model) {

        log.error("invalid user login attempt");

        model.addAttribute("loginError", "invalid user credentials or session");

        log.trace("Display login view");

        return "/Index";
    }

}
