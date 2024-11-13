package com.example.systemauthorization.mapper;

import com.example.systemauthorization.dto.UserDto;
import com.example.systemauthorization.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
}
