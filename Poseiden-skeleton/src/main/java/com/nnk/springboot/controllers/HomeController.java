package com.nnk.springboot.controllers;
import com.nnk.springboot.dto.LoginRequest;
import com.nnk.springboot.dto.UserRequest;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@AllArgsConstructor
public class HomeController


{
	@RequestMapping("/")
	public String home() {

		return "home";
	}





	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/user/list";
	}


}
