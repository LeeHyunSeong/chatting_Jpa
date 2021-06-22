package com.lhs.chatting.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lhs.chatting.exception.MemberNotFoundException;
import com.lhs.chatting.exception.RoomNotFoundException;
import com.lhs.chatting.exception.UserNotFoundException;
import com.lhs.chatting.model.entity.Member;
import com.lhs.chatting.model.entity.Room;
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

    public boolean inviteFriend(Long hostUserId, Long targetUserId, Long roomId) {
        LocalDateTime now = LocalDateTime.now();
        Member hostUserMember = memberRepository.findByUserIdAndRoomId(hostUserId, roomId)
                .orElseThrow(() -> new MemberNotFoundException(hostUserId, roomId));
        Member targetUserMember = Member.builder()
                .user(User.pseudo(targetUserId))
                .room(hostUserMember.getRoom())
                .roomAlias(hostUserMember.getRoomAlias())
                .joinedTime(now)
                .lastEntranceTime(now)
                .build();
        memberRepository.save(targetUserMember);
        return true;
    }

    public List<Member> getMembers(Long userId) {
        return memberRepository.findAllByUserId(userId);
    }
    
    public Member getMember(Long userId, Long roomId) {
        return memberRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new MemberNotFoundException(userId, roomId));
    }
    
    public boolean changeRoomAlias(Long memberId, String roomAlias) {
        Member existedMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        existedMember.setRoomAlias(roomAlias);
        memberRepository.save(existedMember);
        return true;
    }
    
    public boolean updateLastEntranceTime(Long memberId) {
        Member existedMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        existedMember.setLastEntranceTime(LocalDateTime.now());
        memberRepository.save(existedMember);
        return true;
    }
    
    public boolean leaveRoom(Long memberId) {
        memberRepository.deleteById(memberId);
        return true;
    }
}
