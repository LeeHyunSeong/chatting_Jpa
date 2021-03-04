package com.lhs.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lhs.chatting.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}