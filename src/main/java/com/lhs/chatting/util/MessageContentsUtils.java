package com.lhs.chatting.util;

import java.util.List;
import java.util.stream.Collectors;

import com.lhs.chatting.model.entity.User;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageContentsUtils {
    
    public String makeInviteMessageContents(User user) {
        return user.getNickname() + "님이 초대되었습니다.";
    }
    
    public String makeMultipleInviteMessageContents(List<User> memberUsers) {
        List<String> memberNames = memberUsers.stream()
                .map(User::getNickname)
                .collect(Collectors.toList());
        return String.join("님, ", memberNames) + "님이 초대되었습니다.";
    }
    
    public String makeLeaveMessageContents(User user) {
        return user.getNickname() + "님이 나갔습니다.";
    }
    
}
