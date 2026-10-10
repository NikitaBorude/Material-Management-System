package com.mms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.mms.model.User;
import com.mms.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class LoginController {

	

	    @Autowired
	    private UserRepository repo;

	    @Autowired
	    private BCryptPasswordEncoder passwordEncoder;

	    @GetMapping("/login")
	    public String loginPage() {
	        return "login";
	    }

	    @PostMapping("/register")
	    public String register(User user) {

	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        repo.save(user);

	        return "redirect:/login";
	    }
	
}