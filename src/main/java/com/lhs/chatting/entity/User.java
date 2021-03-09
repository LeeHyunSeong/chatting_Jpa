package com.lhs.chatting.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = "USER")
@Builder
@Getter
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "email", columnDefinition="TEXT")
    private String email;

    @Setter
    @Column(name = "password", columnDefinition="TEXT")
    private String password;

    @Column(name = "username", columnDefinition="TEXT")
    private String username;
    
    @Setter
    @Column(name = "nickname", columnDefinition="TEXT")
    private String nickname;

    @Setter
    @Column(name = "profile_image", columnDefinition="TEXT")
    private String profileImage;

    @Column(name = "signed_time")
    private Timestamp signedTime;

    public static User of(String username, String password, String email, String nickname) {
        return User.builder()
                .email(email)
                .password(password)
                .username(username)
                .nickname(nickname)
                .profileImage(null)
                .signedTime(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}