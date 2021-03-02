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
@Table(name = "friend")
public class Friend {
    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;
    
    @Column(name="relation_type", length=30, nullable=false)
    private String relationType;

    @Column(name="created_time")
    private Timestamp createdTime;
    
    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id")
    private User user;
    
    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="friend_id")
    private User friend;
    
    public Friend(User user, User friend) {
    	relationType = "NORMAL";
    	createdTime = new Timestamp(System.currentTimeMillis());
    	this.user = user;
    	this.friend = friend;
    }
}
