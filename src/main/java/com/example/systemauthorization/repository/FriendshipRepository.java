package com.example.systemauthorization.repository;

import com.example.systemauthorization.dto.FriendshipDto;
import com.example.systemauthorization.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<Friendship> findByReceiverIdAndAcceptedFalse(Long receiverId);

    @Query("SELECT f " +
            "FROM Friendship  f " +
            "WHERE f.accepted = true " +
            "AND (f.receiver.id = :userId OR f.sender.id = :userId)")
    List<Friendship> findAllFriends(@Param("userId") Long userId);

}
