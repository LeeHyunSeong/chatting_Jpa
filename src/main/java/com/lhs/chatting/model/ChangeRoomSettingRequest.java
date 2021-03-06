package com.lhs.chatting.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangeRoomSettingRequest {
    String roomAlias;
    String setting;
}
