package com.spring.uhdiya;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class test {

	@GetMapping("/")
	public String home() {
		return "index";
	}
	@GetMapping("/about")
	public String about() {
		return "about";
	}
}