package com.lhs.chatting.model.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.lhs.chatting.model.ChangeUserInfoRequest;
import com.lhs.chatting.model.RegisterUserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "USER",
        indexes = @Index(name = "idx_email", unique = true, columnList = "email")
)
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Setter
    @Column(name = "password", length = 15)
    private String password;

    @Column(name = "email")
    private String email;

    @Setter
    @Column(name = "nickname", length = 20)
    private String nickname;

    @Setter
    @Column(name = "profile_image", columnDefinition = "TEXT")
    private String profileImage;

    @Column(name = "signed_time")
    private LocalDateTime signedTime;

    public static User of(RegisterUserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .nickname(request.getNickname())
                .signedTime(LocalDateTime.now())
                .build();
    }

    public void changeWith(ChangeUserInfoRequest request) {
        if (request.getNickname() != null) {
            this.nickname = request.getNickname();
        }
        if (request.getEmail() != null) {
            this.email = request.getEmail();
        }
        if (request.getPassword() != null) {
            this.password = request.getPassword();
        }
    }
}