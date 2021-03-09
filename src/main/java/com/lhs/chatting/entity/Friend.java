package com.lhs.chatting.entity;

import java.sql.Timestamp;

import javax.persistence.*;

import lombok.*;

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
    private Timestamp createdTime;

    public static Friend of(Long userId, Long friendId, FriendRelationType relationType) {
        return Friend.builder()
                .user(User.builder().id(userId).build())
                .friend(User.builder().id(friendId).build())
                .relationType(relationType)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
