package com.inhatc.spring.capstone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Maincontroller {

	@GetMapping
	public String main() {
		return "main";
	}
}
