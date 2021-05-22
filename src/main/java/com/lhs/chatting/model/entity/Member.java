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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "MEMBER",
        indexes = @Index(name = "idx_room_id_user_id", unique = true, columnList = "room_id, user_id")
)
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "last_entrance_time")
    private LocalDateTime lastEntranceTime;

    @Column(name = "joined_time")
    private LocalDateTime joinedTime;

    public static Member of(Long userId, Long roomId, String userName, LocalDateTime createTime) {
        return Member.builder()
                .roomAlias(userName)
                .user(User.pseudo(userId))
                .room(Room.pseudo(roomId))
                .joinedTime(createTime)
                .lastEntranceTime(createTime)
                .build();
    }
}