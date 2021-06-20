package com.lhs.chatting.model;

import java.util.List;

import com.lhs.chatting.model.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GetAllMemberResponse {
    
    private final List<Member> members;
    
}
