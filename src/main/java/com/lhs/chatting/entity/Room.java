package com.lhs.chatting.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private long id;
        
    @Column(name="created_time")
    private Timestamp createdTime;
    
    @Column(name="last_msg_id")
    private Message lastMsgId;
    
    public Room() {
    	createdTime = new Timestamp(System.currentTimeMillis());
    	lastMsgId = null;
    }
}