package com.example.systemauthorization.service;

import com.example.systemauthorization.dto.UserDto;
import com.example.systemauthorization.entity.User;

import java.util.List;

public interface UserService {
    boolean register(UserDto dto);
    List<User> searchUserByUsername(String username);
    User getCurrentUser();
    User findByUsername(String username);
}
