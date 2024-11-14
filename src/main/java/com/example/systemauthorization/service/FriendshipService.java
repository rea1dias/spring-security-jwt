package com.example.systemauthorization.service;

import com.example.systemauthorization.dto.FriendshipDto;

import java.util.List;

public interface FriendshipService {

    void sendFriendRequest(Long senderId, Long receiverId);
    List<FriendshipDto> getPendingFriendRequests(Long receiverId);
}
