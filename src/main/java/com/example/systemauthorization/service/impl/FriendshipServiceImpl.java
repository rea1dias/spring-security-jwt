package com.example.systemauthorization.service.impl;

import com.example.systemauthorization.dto.FriendshipDto;
import com.example.systemauthorization.entity.Friendship;
import com.example.systemauthorization.entity.User;
import com.example.systemauthorization.mapper.FriendshipMapper;
import com.example.systemauthorization.repository.FriendshipRepository;
import com.example.systemauthorization.repository.UserRepository;
import com.example.systemauthorization.service.FriendshipService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository repository;
    private final UserRepository userRepository;
    private final FriendshipMapper mapper;

    public FriendshipServiceImpl(FriendshipRepository repository, UserRepository userRepository, FriendshipMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public void sendFriendRequest(Long senderId, Long receiverId) {

        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Sender not found"));

        if (repository.existsBySenderIdAndReceiverId(senderId, receiverId)) {
            throw new RuntimeException("Sender and Receiver already exists");
        }

        Friendship friendship = new Friendship();
        friendship.setSender(sender);
        friendship.setReceiver(receiver);
        friendship.setAccepted(false);
        repository.save(friendship);
    }

    public Long getSenderIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::getId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public List<FriendshipDto> getPendingFriendRequests(Long receiverId) {
        List<Friendship> friendships = repository.findAllByReceiverIdAndAcceptedFalse(receiverId);

        return friendships.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
