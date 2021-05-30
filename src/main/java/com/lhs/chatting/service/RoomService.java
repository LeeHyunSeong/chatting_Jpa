package com.lhs.chatting.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lhs.chatting.exception.UserNotFoundException;
import com.lhs.chatting.model.entity.Member;
import com.lhs.chatting.model.entity.Room;
import com.lhs.chatting.model.entity.User;
import com.lhs.chatting.repository.MemberRepository;
import com.lhs.chatting.repository.RoomRepository;
import com.lhs.chatting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    public boolean makeRoom(List<Long> userIds, String name) {
        LocalDateTime createTime = LocalDateTime.now();
        Room room = Room.of(createTime);
        String roomName = makeDefaultRoomName(userIds);
        for (Long userId : userIds) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            Member member = Member.of(userId, room.getId(), roomName, createTime);
            memberRepository.save(member);
        }
        roomRepository.save(room);
        return true;
    }

    public boolean deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
        return true;
    }

    private String makeDefaultRoomName(List<Long> userIds) {
        String roomName = "";
        for (Long userId : userIds) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            if (userIds.indexOf(userId) != userIds.size() - 1)
                roomName += user.getNickname() + ", ";
            else
                roomName += user.getNickname();
        }
        return roomName;
    }
}