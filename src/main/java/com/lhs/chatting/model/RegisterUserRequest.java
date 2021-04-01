package com.lhs.chatting.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterUserRequest {
    String username;
    String password;
    String email;
    String nickname;
}
