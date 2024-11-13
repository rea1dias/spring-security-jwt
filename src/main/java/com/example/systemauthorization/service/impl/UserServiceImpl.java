package com.example.systemauthorization.service.impl;

import com.example.systemauthorization.dto.UserDto;
import com.example.systemauthorization.entity.User;
import com.example.systemauthorization.mapper.UserMapper;
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
    private final UserMapper mapper;

    @Override
    public boolean register(UserDto dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            return false;
        }
        User user = mapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);
        repository.save(user);
        return true;
    }
}
