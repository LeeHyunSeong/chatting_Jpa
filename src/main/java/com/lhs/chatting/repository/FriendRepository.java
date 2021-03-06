package com.lhs.chatting.repository;

import com.lhs.chatting.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByUserIdAndFriendId(Long userId, Long friendId);

}