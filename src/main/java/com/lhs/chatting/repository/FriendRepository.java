package com.lhs.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lhs.chatting.entity.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long>{ }