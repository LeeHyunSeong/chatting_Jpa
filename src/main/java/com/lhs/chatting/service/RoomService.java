package com.lhs.chatting.service;

import com.lhs.chatting.exception.UserNotFoundException;
import com.lhs.chatting.model.entity.Member;
import com.lhs.chatting.model.entity.Room;
import com.lhs.chatting.model.entity.User;
import com.lhs.chatting.repository.MemberRepository;
import com.lhs.chatting.repository.RoomRepository;
import com.lhs.chatting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    // 기존의 makeRoom은 N명 초대 시 userRepository.findById를 2N번 호출
    // connection 리소스 비용이 심하고 불필요한 호출이 있다.
    // TODO : 확인 후에는 주석 지울 것
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
        for (User user : users) {
            String defaultRoomAlias = getJoinedMemberNameWithoutUser(users, user);

            Member member = Member.builder()
                    .room(room)
                    .user(user)
                    .roomAlias(defaultRoomAlias)
                    .joinedTime(room.getCreatedTime())
                    .lastEntranceTime(room.getCreatedTime())
                    .build();
            memberRepository.save(member);
        }
    }

    private String getJoinedMemberNameWithoutUser(List<User> memberUsers, User user) {
        List<String> memberNamesWithoutUser = memberUsers.stream()
                .filter(memberUser -> memberUser != user)
                .map(User::getNickname)
                .collect(Collectors.toList());
        return String.join(", ", memberNamesWithoutUser);
    }
}