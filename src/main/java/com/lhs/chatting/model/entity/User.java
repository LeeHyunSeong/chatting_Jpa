package com.lhs.chatting.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lhs.chatting.model.ChangeUserInfoRequest;
import com.lhs.chatting.model.RegisterUserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    // username, nickname은 대부분 255자 미만이므로 TEXT를 쓸 필요가 없을 거 같다.
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

    // String 연속 of로 객체 생성 시 순서 유의점 때문에 객체로 받는게 훨씬 안전
    // 예를 들어, username과 password의 순서를 잘못 알아서 바꿔서 대입한다 하더라도 에러가 발생하지 않는다. (안전하지 않은 코드)
    // 밖에서 빌더를 쓰던지 그냥 of 생성자에는 객체를 넘겨주는게 안전!
    public static User of(RegisterUserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .nickname(request.getNickname())
                .profileImage(null)
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