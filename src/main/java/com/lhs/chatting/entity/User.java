package com.lhs.chatting.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER")
@Builder
@Getter
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "username", length = 10)
    private String username;

    @Setter
    @Column(name = "password", length = 15)
    private String password;

    @Column(name = "email", columnDefinition = "TEXT")
    private String email;

    @Setter
    @Column(name = "nickname", length = 20)
    private String nickname;

    @Setter
    @Column(name = "profile_image", columnDefinition = "TEXT")
    private String profileImage;

    @Column(name = "signed_time")
    private LocalDateTime signedTime;

    public static User of(String username, String password, String email, String nickname) {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .profileImage(null)
                .signedTime(LocalDateTime.now())
                .build();
    }
}