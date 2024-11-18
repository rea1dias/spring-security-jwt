package com.example.systemauthorization.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FriendDto {
    private Long id;
    private String username;
    private String email;
}
