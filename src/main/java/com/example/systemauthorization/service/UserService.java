package com.example.systemauthorization.service;

import com.example.systemauthorization.dto.UserDto;

public interface UserService {

    boolean register(UserDto dto);
    boolean login(String username, String password);
}
