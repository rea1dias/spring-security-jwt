package com.example.systemauthorization.repository;

import com.example.systemauthorization.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);


    List<Friendship> findByReceiverIdAndAcceptedFalse(Long receiverId);

}
