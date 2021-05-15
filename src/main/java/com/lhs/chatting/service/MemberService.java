package com.lhs.chatting.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lhs.chatting.exception.MemberNotFoundException;
import com.lhs.chatting.exception.UserNotFoundException;
import com.lhs.chatting.model.entity.Member;
import com.lhs.chatting.model.entity.User;
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

    public boolean inviteFriend(Long userId, Long targetUserId, Long roomId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        LocalDateTime createTime = LocalDateTime.now();
        Member userMember = Member.of(userId, roomId, user.getNickname(), createTime);
        Member targetUserMember = Member.of(targetUserId, roomId, targetUser.getNickname(), createTime);
        memberRepository.save(userMember);
        memberRepository.save(targetUserMember);
        return true;
    }

    public List<Member> getMembers(Long userId){
        return memberRepository.findAllByUserId(userId);
    }
    
    public Member getMember(Long userId, Long roomId) {
        return memberRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new MemberNotFoundException(userId, roomId));
    }
    
    public boolean leaveRoom(Long memberId) {
        Member targetMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        memberRepository.delete(targetMember);
        return true;
    }
}
