package com.lhs.chatting.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.lhs.chatting.model.type.FriendRelationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@SequenceGenerator(
        name = "FRIEND_SEQ_GENERATOR",
        sequenceName = "FRIEND_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Table(
        name = "FRIEND",
        indexes = @Index(name = "idx_user_id_relation_type", unique = true, columnList = "user_id, relation_type")
)
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FRIEND_SEQ_GENERATOR")
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
                .user(User.pseudo(userId))
                .targetUser(User.pseudo(targetUserId))
                .relationType(relationType)
                .createdTime(LocalDateTime.now())
                .build();
    }
}
