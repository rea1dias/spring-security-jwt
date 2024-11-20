package com.example.systemauthorization.service.impl;

import com.example.systemauthorization.dto.ProfileDto;
import com.example.systemauthorization.dto.UserDto;
import com.example.systemauthorization.entity.Profile;
import com.example.systemauthorization.entity.User;
import com.example.systemauthorization.mapper.ProfileMapper;
import com.example.systemauthorization.mapper.UserMapper;
import com.example.systemauthorization.repository.ProfileRepository;
import com.example.systemauthorization.repository.UserRepository;
import com.example.systemauthorization.service.ProfileService;
import com.example.systemauthorization.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository repository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ProfileMapper mapper;
    private final UserMapper userMapper;

    public ProfileServiceImpl(ProfileRepository repository,
                              UserRepository userRepository,
                              UserService userService,
                              ProfileMapper mapper,
                              UserMapper userMapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.mapper = mapper;
        this.userMapper = userMapper;
    }

    @Override
    public Profile getProfileById(Long userId) {
        return repository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Profile Not Found"));
    }

    @Override
    public boolean updateProfile(Long userId, UserDto updatedUserDto, ProfileDto updatedProfileDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));

        user.setUsername(updatedUserDto.getUsername());
        user.setEmail(updatedUserDto.getEmail());

        Profile profile = user.getProfile();
        profile.setBio(updatedProfileDto.getBio());
        profile.setAvatarUrl(updatedProfileDto.getAvatarUrl());
        profile.setBirthDate(updatedProfileDto.getBirthDate());
        profile.setLocation(updatedProfileDto.getLocation());

        repository.save(profile);

        return true;
    }


}

