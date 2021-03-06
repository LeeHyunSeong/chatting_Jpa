package com.lhs.chatting.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangeUserInformationRequest {
    String password;
    String nickname;
    String profileImage;
}
