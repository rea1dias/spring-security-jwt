package com.example.systemauthorization.service;

import com.example.systemauthorization.dto.UserDto;
import com.example.systemauthorization.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean register(UserDto dto);
    List<User> searchUserByUsername(String username, Long currentUserId);
    User getCurrentUser();
    User findByUsername(String username);
    Optional<User> findById(Long userId);

}
