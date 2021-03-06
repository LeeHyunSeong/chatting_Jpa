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
    @Column(name = "id", length = 45, nullable = false)
    private long id;

    @Column(name = "email", length = 45)
    private String email;

    @Setter
    @Column(name = "password", length = 45)
    private String password;

    @Column(name = "username", length = 30)
    private String username;

    @Setter
    @Column(name = "nickname", length = 45, nullable = false)
    private String nickname;

    @Setter
    @Column(name = "profile_image", length = 50)
    private String profileImage;

    @Column(name = "signed_time")
    private Timestamp signedTime;
    
	public static User of(String email, String password, String username, String nickname) {
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