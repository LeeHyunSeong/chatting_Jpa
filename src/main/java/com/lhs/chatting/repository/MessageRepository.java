package com.lhs.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lhs.chatting.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}