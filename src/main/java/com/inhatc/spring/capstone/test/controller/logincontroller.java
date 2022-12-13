package com.inhatc.spring.capstone.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class logincontroller {

	@GetMapping("/loginpage")
	public String login() {
			return "/Login/Login";
	}
	
	
}
