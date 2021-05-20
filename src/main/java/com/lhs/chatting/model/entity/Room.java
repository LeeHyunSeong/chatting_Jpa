package com.lhs.chatting.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lhs.chatting.model.type.FriendRelationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ROOM")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Setter
    @Column(name = "last_msg_id")
    private Message lastMsgId;

    @Column(name = "created_time")
    private LocalDateTime createdTime;
}