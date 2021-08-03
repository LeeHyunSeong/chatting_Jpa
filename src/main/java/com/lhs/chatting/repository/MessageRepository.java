package com.lhs.chatting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lhs.chatting.model.entity.Message;
import com.lhs.chatting.model.type.MessageType;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByRoomIdAndMsgTypeOrderByCreatedTimeDesc(Long roomId, MessageType msgType, Pageable pageable);
    
}