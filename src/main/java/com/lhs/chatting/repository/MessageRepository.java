package com.lhs.chatting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lhs.chatting.model.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByRoomId(Long roomId);

    @Query(value = "SELECT m FROM Message m WHERE m.roomId = roomId and m.content LIKE %:content%")
    List<Message> findAllByRoomIdAndContent(Long roomId, String content);
        
}