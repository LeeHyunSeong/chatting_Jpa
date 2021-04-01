package com.lhs.chatting.model;

import com.lhs.chatting.entity.UserInfoType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangeUserInformationRequest {
    UserInfoType userInfoType;
    String contents;
}