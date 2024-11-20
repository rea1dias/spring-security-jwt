package com.example.systemauthorization.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileDto {

    private Long id;
    private String bio;
    private String avatarUrl;
    private UserDto user;
    private LocalDate birthDate;
    private String location;

}
