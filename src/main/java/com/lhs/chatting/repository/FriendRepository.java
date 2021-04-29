package com.lhs.chatting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lhs.chatting.model.entity.Friend;
import com.lhs.chatting.model.type.FriendRelationType;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    
    List<Friend> findAllByUserId(Long userId);
    
    List<Friend> findAllByUserIdAndFriendRelationType(Long userId, FriendRelationType type);

}