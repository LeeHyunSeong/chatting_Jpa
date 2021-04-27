package com.lhs.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lhs.chatting.model.entity.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

}