package com.inhatc.spring.capstone.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/loginpage")
public class logincontroller {

	@GetMapping()
	public String login() {
			return "/Login/Login";
	}
	
	
}
