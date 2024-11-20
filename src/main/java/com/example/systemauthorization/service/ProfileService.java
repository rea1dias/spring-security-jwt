package com.example.systemauthorization.service;

import com.example.systemauthorization.dto.ProfileDto;
import com.example.systemauthorization.dto.UserDto;
import com.example.systemauthorization.entity.Profile;

public interface ProfileService {

    Profile getProfileById(Long userId);
    boolean updateProfile(Long userId, UserDto updatedUserDto, ProfileDto updatedProfileDto);
}
