package com.lhs.chatting.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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

    @Transactional
    public boolean makeRoom(List<Long> userIds) {
        Room room = Room.newInstance();
        roomRepository.save(room);

        List<User> users = getUsersByIds(userIds);
        inviteUsersToRoom(room, users);
        return true;
    }

    private List<User> getUsersByIds(List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);

        Set<Long> unselectedUserIds = new HashSet<>(userIds);
        users.forEach(user -> unselectedUserIds.remove(user.getId()));
        if (!unselectedUserIds.isEmpty()) {
            throw new UserNotFoundException(unselectedUserIds);
        }
        return users;
    }

    private void inviteUsersToRoom(Room room, List<User> users) {
        List<Member> members = users.stream()
                .map(user -> {
                    String defaultRoomAlias = makeJoinedMemberNameWithoutUser(users, user);
                    return Member.builder()
                            .room(room)
                            .user(user)
                            .roomAlias(defaultRoomAlias)
                            .joinedTime(room.getCreatedTime())
                            .lastEntranceTime(room.getCreatedTime())
                            .build();
                })
                .collect(Collectors.toList());
        memberRepository.saveAll(members);
    }

    private String makeJoinedMemberNameWithoutUser(List<User> memberUsers, User user) {
        List<String> memberNamesWithoutUser = memberUsers.stream()
                .filter(memberUser -> memberUser != user)
                .map(User::getNickname)
                .collect(Collectors.toList());
        return String.join(", ", memberNamesWithoutUser);
    }
}