package com.lhs.chatting.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.lhs.chatting.exception.MemberNotFoundException;
import com.lhs.chatting.exception.UserNotFoundException;
import com.lhs.chatting.model.MemberRoom;
import com.lhs.chatting.model.MemberUser;
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
    
    public boolean inviteFriend(Long hostUserId, Long roomId, Long targetUserId) {
        LocalDateTime now = LocalDateTime.now();
        List<MemberUser> memberUsers = memberRepository.findAllMemberUsersByRoomId(roomId);
        List<User> users = getUsersByMemberUsers(memberUsers);
        
        Member targetUserMember = Member.builder()
                .user(User.pseudo(targetUserId))
                .room(Room.pseudo(roomId))
                .roomAlias(makeInvitedMemberName(users))
                .joinedTime(now)
                .lastEntranceTime(now)
                .build();
        memberRepository.save(targetUserMember);
        return true;
    }
    
    public List<MemberRoom> getRooms(Long userId) {
        return memberRepository.findAllMemberRoomsByUserId(userId);
    }
    
    public boolean changeRoomAlias(Long userId, Long roomId, String roomAlias) {
        Member existedMember = memberRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new MemberNotFoundException(userId, roomId));
        existedMember.setRoomAlias(roomAlias);
        memberRepository.save(existedMember);
        return true;
    }
    
    public boolean updateLastEntranceTime(Long userId, Long roomId) {
        Member existedMember = memberRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new MemberNotFoundException(userId, roomId));
        existedMember.setLastEntranceTime(LocalDateTime.now());
        memberRepository.save(existedMember);
        return true;
    }
    
    public boolean leaveRoom(Long userId, Long roomId) {
        memberRepository.deleteByUserIdAndRoomId(userId, roomId);
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
    
    private List<User> getUsersByMemberUsers(List<MemberUser> memberUsers) {
        List<Long> userIds = memberUsers.stream()
                .map(MemberUser::getUserId)
                .collect(Collectors.toList());
        List<User> users = userRepository.findAllById(userIds);
        
        Set<Long> unselectedUserIds = new HashSet<>(userIds);
        users.forEach(user -> unselectedUserIds.remove(user.getId()));
        if (!unselectedUserIds.isEmpty()) {
            throw new UserNotFoundException(unselectedUserIds);
        }
        return users;
    }
    
    private String makeInvitedMemberName(List<User> Users) {
        List<String> memberNames = Users.stream()
                .map(User::getNickname)
                .collect(Collectors.toList());
        return String.join(", ", memberNames);
    }
}