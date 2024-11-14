package com.example.systemauthorization.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FriendshipDto {
    private Long senderId;
    private Long receiverId;
    private boolean accepted;
}
