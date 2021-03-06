package com.lhs.chatting.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.Member;
import com.lhs.chatting.entity.Room;
import com.lhs.chatting.entity.User;
import com.lhs.chatting.repository.MemberRepository;
import com.lhs.chatting.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getRooms(Long userId) {
        List<Member> members = memberRepository.findAll();
        List<Member> targetMembers = new ArrayList<>();
        for (Member member : members) {
            if (member.getId() == userId)
                targetMembers.add(member);
        }
        return targetMembers;
    }

    public void makeRoom(String name, List<Long> userIds) {
        Room room = Room.of();
        for (Long userId : userIds) {
            Member member = Member.of(userId, room.getId(), name);
            memberRepository.save(member);
        }

        roomRepository.save(room);
    }

    public boolean isRoomExist(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Can not found User entity")) != null;
    }
}
