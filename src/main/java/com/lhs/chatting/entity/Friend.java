package com.lhs.chatting.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "friend")
@Builder
@Getter
public class Friend {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "friend_id")
    private User friend;

    @Setter
    @Column(name = "relation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendRelationType relationType;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    public static Friend of(Long userId, Long friendId, FriendRelationType relationType) {
        return Friend.builder()
                .user(User.builder().id(userId).build())
                .friend(User.builder().id(friendId).build())
                .relationType(relationType)
                .createdTime(LocalDateTime.now())
                .build();
    }
}
