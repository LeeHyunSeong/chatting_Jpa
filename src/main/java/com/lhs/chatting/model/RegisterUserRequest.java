package com.lhs.chatting.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterUserRequest {

    private final String username;

    private final String password;

    private final String email;

    private final String nickname;

}
