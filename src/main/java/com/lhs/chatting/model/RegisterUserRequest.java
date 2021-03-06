package com.lhs.chatting.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterUserRequest {
    String email;
    String password;
    String username;
    String nickname;
    String profileImage;
}
