package com.example.systemauthorization.controller;

import com.example.systemauthorization.dto.FriendshipDto;
import com.example.systemauthorization.entity.User;
import com.example.systemauthorization.service.FriendshipService;
import com.example.systemauthorization.service.UserService;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/friends")
public class FriendshipController {

    private final FriendshipService service;
    private final UserService userService;
    private final PropertyPlaceholderAutoConfiguration propertyPlaceholderAutoConfiguration;

    public FriendshipController(FriendshipService service, UserService userService, PropertyPlaceholderAutoConfiguration propertyPlaceholderAutoConfiguration) {
        this.service = service;
        this.userService = userService;
        this.propertyPlaceholderAutoConfiguration = propertyPlaceholderAutoConfiguration;
    }

    @GetMapping("/list")
    public String showFriends(Model model) {
        return "friendship/list";
    }

    @GetMapping("/search")
    public String showSearch(Model model) {
        return "friendship/search";
    }

    @GetMapping("/result")
    public String searchFriends(Model model, @RequestParam(value = "username", required = false) String username) {
        User currentUser = userService.getCurrentUser();
        List<User> users = new ArrayList<>();
        if (username != null && !username.trim().isEmpty()) {
            users = userService.searchUserByUsername(username);
        }
        model.addAttribute("users", users);
        model.addAttribute("senderId", currentUser.getId());
        return "friendship/search";
    }

    @PostMapping("/send")
    public String sendFriendRequest(@RequestParam("senderId") Long senderId,
                                    @RequestParam("receiverId") Long receiverId,
                                    Model model) {
        try {
            service.sendFriendRequest(senderId, receiverId);
            model.addAttribute("message", "Friend request sent successfully");
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "friendship/search";
    }

    @GetMapping("/requests")
    public String showPendingRequests(Model model, Authentication authentication) {
        try {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            List<FriendshipDto> pendingRequests = service.getPendingFriendRequests(user.getId());
            model.addAttribute("pendingRequests", pendingRequests);

            return "friendship/request";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "friendship/request";
        }
    }

}
