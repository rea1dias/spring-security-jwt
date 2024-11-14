package com.example.systemauthorization.service.impl;

import com.example.systemauthorization.dto.UserDto;
import com.example.systemauthorization.entity.User;
import com.example.systemauthorization.mapper.UserMapper;
import com.example.systemauthorization.model.Role;
import com.example.systemauthorization.repository.UserRepository;
import com.example.systemauthorization.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<User> searchUserByUsername(String username) {
        return repository.findByUsernameContainingIgnoreCase(username);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("No authenticated user found");
        } else {
            return (User) authentication.getPrincipal();
        }
    }
}
