package com.lhs.chatting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegisterUserRequest {

    private String username;

    private String password;

    private String email;

    private String nickname;

}
