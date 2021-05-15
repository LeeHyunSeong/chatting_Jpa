package com.lhs.chatting.exception;

import com.lhs.chatting.model.entity.Member;

public class MemberNotFoundException extends NotFoundException{
    public MemberNotFoundException(Long memberId) {
        super(Member.class, String.format("Id = %d", memberId));
    }
    
    public MemberNotFoundException(Long userId, Long roomId) {
        super(Member.class, String.format("userId = %d, roomId = %d", userId, roomId));
    }
}
