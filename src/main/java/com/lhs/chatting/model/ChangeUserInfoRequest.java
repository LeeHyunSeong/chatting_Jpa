package com.lhs.chatting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChangeUserInfoRequest {

    private String password;

    private String email;

    private String nickname;

}