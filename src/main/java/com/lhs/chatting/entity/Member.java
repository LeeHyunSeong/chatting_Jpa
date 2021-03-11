package com.lhs.chatting.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "member")
@Builder
@Getter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = Room.class)
    @JoinColumn(name = "room_id")
    private Room room;

    @Setter
    @Column(name = "room_alias", length = 20, nullable = false)
    private String roomAlias;

    @Setter
    @Column(name = "setting_meta", columnDefinition = "TEXT", nullable = false)
    private String settingMeta;

    @Setter
    @Column(name = "last_entrance_time")
    private LocalDateTime lastEntranceTime;

    @Column(name = "joined_time")
    private LocalDateTime joinedTime;

    public static Member of(Long userId, Long roomId, String roomAlias) {
        return Member.builder()
                .roomAlias(roomAlias)
                .settingMeta("NORMAL")
                .user(User.builder().id(userId).build())
                .room(Room.builder().id(roomId).build())
                .joinedTime(LocalDateTime.now())
                .lastEntranceTime(LocalDateTime.now())
                .build();
    }
}