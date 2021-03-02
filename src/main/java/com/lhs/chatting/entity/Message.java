package com.lhs.chatting.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;
    
    @Column(name="contents", length=21844, nullable=false)
    private String contents;

    @Column(name="type", length=20, nullable=false)
    private String type;
    
    @Column(name="created_time")
    private Timestamp createdTime;
    
    @ManyToOne(targetEntity=Room.class)
    @Column(name="room_id")
    private Room room;
    
    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id")
    private User user;
}
