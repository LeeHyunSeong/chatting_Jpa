package com.lhs.chatting.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue
    @Column(name="id", length=45, nullable=false)
    private long id;
    
    @Column(name="email", length=45, nullable=false)
    private String email;
    
    @Column(name="passward", length=45, nullable=false)
    private String passward;
    
    @Column(name="username", length=30, nullable=false)
    private String username;
    
    @Column(name="nickname", length=45, nullable=false)
    private String nickname;
    
    @Column(name="profile_image", length=50)
    private String profileImage;
    
    @Column(name="signed_time")
    private Timestamp signedTime;
}