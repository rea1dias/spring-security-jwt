package com.example.systemauthorization.service.impl;

import com.example.systemauthorization.dto.FriendDto;
import com.example.systemauthorization.dto.FriendshipDto;
import com.example.systemauthorization.entity.Friendship;
import com.example.systemauthorization.entity.User;
import com.example.systemauthorization.mapper.FriendshipMapper;
import com.example.systemauthorization.mapper.UserMapper;
import com.example.systemauthorization.repository.FriendshipRepository;
import com.example.systemauthorization.repository.UserRepository;
import com.example.systemauthorization.service.FriendshipService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository repository;
    private final UserRepository userRepository;
    private final FriendshipMapper mapper;
    private final UserMapper userMapper;

    public FriendshipServiceImpl(FriendshipRepository repository, UserRepository userRepository, FriendshipMapper mapper, UserMapper userMapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.userMapper = userMapper;
    }

    @Override
    public void sendFriendRequest(Long senderId, Long receiverId) {

        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (repository.existsBySenderIdAndReceiverId(senderId, receiverId) || repository.existsBySenderIdAndReceiverId(receiverId, senderId)) {
            throw new RuntimeException("Sender and Receiver already exists");
        }
        Friendship friendship = new Friendship();
        friendship.setSender(sender);
        friendship.setReceiver(receiver);
        friendship.setAccepted(false);
        repository.save(friendship);
    }

    @Override
    public List<FriendshipDto> getPendingFriendRequests(Long receiverId) {
        List<Friendship> pendingRequests = repository.findByReceiverIdAndAcceptedFalse(receiverId);

        List<FriendshipDto> dtos = new ArrayList<>();

        for (Friendship friendship : pendingRequests) {
            FriendshipDto dto = new FriendshipDto(
                    friendship.getId(),
                    friendship.getSender().getId(),
                    friendship.getSender().getUsername(),
                    friendship.getSender().getEmail(),
                    friendship.getReceiver().getId(),
                    friendship.isAccepted()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public void acceptPendingRequests(Long requestId) {
        Friendship friendship = repository.findById(requestId).orElseThrow(() -> new RuntimeException("Friendship not found"));
        friendship.setAccepted(true);
        repository.save(friendship);
    }

    @Override
    public void declinePendingRequests(Long requestId) {
        Friendship friendship = repository.findById(requestId).orElseThrow(() -> new RuntimeException("Friendship not found"));
        repository.delete(friendship);
    }

    @Override
    public List<FriendDto> getAllFriends(Long userId) {
        List<Friendship> allFriends = repository.findAllFriends(userId);
        List<FriendDto> dtos = new ArrayList<>();
        for (Friendship friendship : allFriends) {
            if (Objects.equals(friendship.getSender().getId(), userId)) {
                dtos.add(userMapper.toFriendDto(friendship.getReceiver()));
            } else if (Objects.equals(friendship.getReceiver().getId(), userId)) {
                dtos.add(userMapper.toFriendDto(friendship.getSender()));
            }
        }
        return dtos;
    }
}
