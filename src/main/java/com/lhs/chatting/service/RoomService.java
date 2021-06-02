package com.lhs.chatting.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public boolean makeRoom(List<Long> userIds) {
        LocalDateTime createTime = LocalDateTime.now();
        Room room = Room.newInstance();
        String roomName = makeDefaultRoomName(userIds);
        for (Long userId : userIds) {
            User user = userRepository.findUserById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            Member member = Member.builder()
                    .user(User.pseudo(userId))
                    .room(Room.pseudo(room.getId()))
                    .roomAlias(roomName)
                    .joinedTime(createTime)
                    .lastEntranceTime(createTime)
                    .build();
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
        ArrayList<String> nameArrayList = new ArrayList<>();
        for (Long userId : userIds) {
            User user = userRepository.findUserById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            nameArrayList.add(user.getNickname());
        }
        return String.join(", ", nameArrayList);
    }
}