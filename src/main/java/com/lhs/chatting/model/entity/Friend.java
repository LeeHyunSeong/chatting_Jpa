package com.lhs.chatting.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lhs.chatting.model.type.FriendRelationType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "FRIEND",
       indexes = @Index(name = "idx_user_id_relation_type", unique = true, columnList = "user_id, relation_type"))
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
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    @Setter
    @Column(name = "relation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendRelationType relationType;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    public static Friend of(Long userId, Long targetUserId, FriendRelationType relationType) {
        return Friend.builder()
                .user(User.builder().id(userId).build())
                .targetUser(User.builder().id(targetUserId).build())
                .relationType(relationType)
                .createdTime(LocalDateTime.now())
                .build();
    }
}
