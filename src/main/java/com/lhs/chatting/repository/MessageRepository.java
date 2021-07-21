package com.lhs.chatting.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lhs.chatting.model.ChatMessage;
import com.lhs.chatting.model.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m.id AS id, m.user.id AS userId, m.contents AS contents, m.createdTime AS createdTime FROM Message m WHERE m.room.id = :roomId AND m.msgType = com.lhs.chatting.model.type.MessageType.MESSAGE ORDER BY m.createdTime desc")
    Page<ChatMessage> findChatMessageByRoomId(@Param("roomId") Long roomId, Pageable pageable);

}