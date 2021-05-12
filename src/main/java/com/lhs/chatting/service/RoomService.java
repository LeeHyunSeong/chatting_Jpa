package com.lhs.chatting.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lhs.chatting.model.entity.Member;
import com.lhs.chatting.model.entity.Room;
import com.lhs.chatting.repository.MemberRepository;
import com.lhs.chatting.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    public boolean makeRoom(List<Long> userIds, String name) {
        Room room = Room.builder()
                .createdTime(LocalDateTime.now())
                .lastMsgId(null)
                .build();
        for (Long userId : userIds) {
            Member member = Member.of(userId, room.getId());
            memberRepository.save(member);
        }
        roomRepository.save(room);
        return true;
    }
}
