package com.example.systemauthorization.service.impl;

import com.example.systemauthorization.dto.UserDto;
import com.example.systemauthorization.entity.User;
import com.example.systemauthorization.model.Role;
import com.example.systemauthorization.repository.UserRepository;
import com.example.systemauthorization.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public boolean register(UserDto dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            return false;
        }
        User user = toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save(user);
        return true;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(Role.USER);
        return user;
    }

}
