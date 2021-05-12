package com.lhs.chatting.service;

import org.springframework.stereotype.Service;

import com.lhs.chatting.exception.MemberNotFoundException;
import com.lhs.chatting.model.entity.Member;
import com.lhs.chatting.repository.MemberRepository;
import com.lhs.chatting.repository.MessageRepository;
import com.lhs.chatting.repository.RoomRepository;
import com.lhs.chatting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    public boolean inviteFriend(Long userId, Long roomId) {
        Member member = Member.of(userId, roomId);
        memberRepository.save(member);
        return true;
    }

    public boolean leaveRoom(Long memberId) {
        Member targetMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        memberRepository.delete(targetMember);
        return true;
    }
}
