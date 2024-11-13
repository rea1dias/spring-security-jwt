package com.example.systemauthorization.controller;

import com.example.systemauthorization.config.JwtUtil;
import com.example.systemauthorization.dto.UserDto;
import com.example.systemauthorization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private JwtUtil util;
    @Autowired
    private AuthenticationManager manager;

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userDto") UserDto dto, Model model) {
        boolean isRegistered = service.register(dto);
        if (!isRegistered) {
            model.addAttribute("error", "Email already in use");
            return "users/register";
        }
        return "redirect:/users/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("userDto") UserDto dto, Model model) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
            String token = util.generateToken(dto.getUsername());
            model.addAttribute("token", token);
            return "redirect:/home";
        } catch (BadCredentialsException e) {
            model.addAttribute("error", "Invalid email or password");
            return "users/login";
        }
    }

    @GetMapping("/login")
    public String showLogin() {
        return "users/login";
    }
}
