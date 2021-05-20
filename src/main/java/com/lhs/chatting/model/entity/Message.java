package com.lhs.chatting.model.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lhs.chatting.model.type.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "MESSAGE",
        indexes = @Index(name = "idx_room_id", unique = true, columnList = "room_id")
)
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne(targetEntity = Room.class)
    @Column(name = "room_id")
    private Room room;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "contents", columnDefinition = "TEXT", nullable = false)
    private String contents;

    @Column(name = "type", nullable = false)
    private MessageType msgType;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    public static Message of(Long roomId, Long userId, String contents, MessageType msgType) {
        return Message.builder()
                .room(Room.builder().id(roomId).build())
                .user(User.builder().id(userId).build())
                .contents(contents)
                .msgType(msgType)
                .createdTime(LocalDateTime.now())
                .build();
    }
}