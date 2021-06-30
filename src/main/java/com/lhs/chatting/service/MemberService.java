package com.lhs.chatting.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lhs.chatting.exception.MemberNotFoundException;
import com.lhs.chatting.model.RoomInfoResponse;
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
        String roomAlias = memberRepository.findRoomAliasByUserIdAndRoomId(hostUserId, roomId)
                .orElseThrow(() -> new MemberNotFoundException(hostUserId, roomId));
        Member targetUserMember = Member.builder()
                .user(User.pseudo(targetUserId))
                .room(Room.pseudo(roomId))
                .roomAlias(roomAlias)
                .joinedTime(now)
                .lastEntranceTime(now)
                .build();
        memberRepository.save(targetUserMember);
        return true;
    }

    public List<RoomInfoResponse> getMembers(Long userId) {
        return getRoomListResponse(userId);
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
    
    private List<RoomInfoResponse> getRoomListResponse(Long userId) {
        List<Object[]> infos = memberRepository.findAllRoomResponseByUserId(userId);
        List<RoomInfoResponse> response = infos.stream()
                .map(info -> RoomInfoResponse.builder()
                        .roomId(toLong(info[0]))
                        .roomAlias(toString(info[1]))
                        .lastEntranceTime(toLocalDateTime(info[2]))
                        .build())
                .collect(Collectors.toList());
        return response;
    }
    
    private long toLong(Object roomIdObj) {
        return ((BigInteger)roomIdObj).longValue();
    }
    
    private String toString(Object roomAliasObj) {
        return (String)roomAliasObj;
    }
    
    private LocalDateTime toLocalDateTime(Object lastEntanceTimeObj) {
        return ((Timestamp)lastEntanceTimeObj).toLocalDateTime();
    }
}
