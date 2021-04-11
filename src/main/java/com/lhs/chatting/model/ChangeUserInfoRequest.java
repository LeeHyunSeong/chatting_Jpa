package com.lhs.chatting.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangeUserInfoRequest {

    private final String password;

    private final String email;

    private final String nickname;

}