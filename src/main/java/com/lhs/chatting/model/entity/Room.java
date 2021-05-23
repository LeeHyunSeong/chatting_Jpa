package com.lhs.chatting.model.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@SequenceGenerator(
        name = "ROOM_SEQ_GENERATOR",
        sequenceName = "ROOM_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "ROOM")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROOM_SEQ_GENERATOR")
    @Column(name = "id")
    private long id;

    @Setter
    @OneToOne(targetEntity = Message.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "last_msg_id")
    private Message lastMsg;

    @Column(name = "created_time")
    private LocalDateTime createdTime;
    
    public static Room pseudo(Long id) {
        return Room.builder()
                .id(id)
                .build();
    }
}