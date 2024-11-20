package com.example.systemauthorization.controller;

import com.example.systemauthorization.dto.PostDto;
import com.example.systemauthorization.dto.ProfileDto;
import com.example.systemauthorization.dto.UserDto;
import com.example.systemauthorization.entity.Profile;
import com.example.systemauthorization.entity.User;
import com.example.systemauthorization.service.PostService;
import com.example.systemauthorization.service.ProfileService;
import com.example.systemauthorization.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService service;
    private final UserService userService;
    private final PostService postService;

    public ProfileController(ProfileService service,
                             UserService userService,
                             PostService postService) {
        this.service = service;
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/view")
    public String getProfile(Model model,
                             Authentication auth,
                             @RequestParam(value = "userId", required = false) Long userId) {
        User currentUser = (User) auth.getPrincipal();

        if (userId != null && !userId.equals(currentUser.getId())) {
            Profile profile = service.getProfileById(userId);
            List<PostDto> userPosts = postService.getPostsByUserId(userId);
            model.addAttribute("posts", userPosts);
            model.addAttribute("profile", profile);
            model.addAttribute("isOwnProfile", false);
        } else {
            Profile profile = service.getProfileById(currentUser.getId());
            List<PostDto> userPosts = postService.getPostsByUserId(currentUser.getId());
            model.addAttribute("profile", profile);
            model.addAttribute("posts", userPosts);
            model.addAttribute("isOwnProfile", true);
        }
        return "/profile/profile";
    }

    @PostMapping("/update")
    public String updateProfile(@RequestParam Long userId,
                                @RequestParam String username,
                                @RequestParam String email,
                                @RequestParam String birthDate,
                                @RequestParam String location,
                                @RequestParam String bio,
                                @RequestParam String avatarUrl
                                ) {

        LocalDate parsedBirthDate = LocalDate.parse(birthDate);
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setUsername(username);
        updatedUserDto.setEmail(email);

        ProfileDto updatedProfileDto = new ProfileDto();
        updatedProfileDto.setBirthDate(parsedBirthDate);
        updatedProfileDto.setLocation(location);
        updatedProfileDto.setBio(bio);
        updatedProfileDto.setAvatarUrl(avatarUrl);

        service.updateProfile(userId, updatedUserDto, updatedProfileDto);
        return "redirect:/profile/view";
    }

    @PostMapping("/postCreate")
    public String createPost(@RequestParam String title,
                             @RequestParam String content,
                             Authentication auth) {

        User currentUser = (User) auth.getPrincipal();
        PostDto dto = new PostDto();
        dto.setTitle(title);
        dto.setUsername(currentUser.getUsername());
        dto.setContent(content);
        postService.createPost(currentUser.getId(), dto);
        return "redirect:/profile/view";
    }

    @PostMapping("/editPost")
    public String editPost(@RequestParam Long postId,
                           @ModelAttribute PostDto dto,
                           Authentication auth) {
        dto.setId(postId);
        postService.editPost(postId,dto);
        return "redirect:/profile/view";
    }

    @PostMapping("/deletePost")
    public String deletePost(@RequestParam Long postId) {
        postService.deletePost(postId);
        return "redirect:/profile/view";
    }

}
