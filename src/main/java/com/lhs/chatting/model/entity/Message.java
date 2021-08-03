package com.lhs.chatting.model.entity;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.lhs.chatting.model.type.MessageType;
import com.lhs.chatting.util.MessageContentsUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(
        name = "MESSAGE_SEQ_GENERATOR",
        sequenceName = "MESSAGE_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "MESSAGE")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ_GENERATOR")
    @Column(name = "id")
    private long id;

    @ManyToOne(targetEntity = Room.class)
    @JoinColumn(name = "room_id")
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

    public static Message chatMessageOf(Long roomId, Long userId, String contents) {
        return Message.of(
                roomId,
                userId,
                contents,
                MessageType.MESSAGE
        );
    }
    
    public static Message inviteMessageOf(Long roomId, User user) {
        return Message.of(
                roomId,
                user.getId(),
                MessageContentsUtils.makeInviteMessageContents(user),
                MessageType.NOTICE
        );
    }

    public static Message multipleInviteMessageOf(Long roomId, Long hostUserId, List<User> memberUsers) {
        return Message.of(
                roomId,
                hostUserId,
                MessageContentsUtils.makeMultipleInviteMessageContents(memberUsers),
                MessageType.NOTICE
        );
    }

    public static Message leaveMessageOf(Long roomId, User user) {
        return Message.of(
                roomId,
                user.getId(),
                MessageContentsUtils.makeLeaveMessageContents(user),
                MessageType.NOTICE
        );
    }
    
    private static Message of(Long roomId, Long userId, String contents, MessageType msgType) {
        return Message.builder()
                .room(Room.pseudo(roomId))
                .user(User.pseudo(userId))
                .contents(contents)
                .msgType(msgType)
                .createdTime(LocalDateTime.now())
                .build();
    }
    
    public static Message pseudo(Long id) {
        return Message.builder()
                .id(id)
                .build();
    }
}