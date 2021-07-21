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
import com.lhs.chatting.model.CreateMessage;
import com.lhs.chatting.model.InviteMessage;
import com.lhs.chatting.model.LeaveMessage;
import com.lhs.chatting.model.MemberRoom;
import com.lhs.chatting.model.Nickname;
import com.lhs.chatting.model.entity.Member;
import com.lhs.chatting.model.entity.Message;
import com.lhs.chatting.model.entity.Room;
import com.lhs.chatting.model.entity.User;
import com.lhs.chatting.model.type.MessageType;
import com.lhs.chatting.repository.MemberRepository;
import com.lhs.chatting.repository.MessageRepository;
import com.lhs.chatting.repository.RoomRepository;
import com.lhs.chatting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public CreateMessage makeRoom(List<Long> userIds) {
        Room room = Room.newInstance();
        roomRepository.save(room);

        List<User> users = getUsersByIds(userIds);
        return inviteUsersToRoom(room, users);    
    }
    
    public InviteMessage inviteFriend(Long hostUserId, Long roomId, Long targetUserId) {
        LocalDateTime now = LocalDateTime.now();
        List<User> users = memberRepository.findAllMemberUsersByRoomId(roomId);
        Nickname targetUserNickname = userRepository.findNicknameById(targetUserId)
                .orElseThrow(() -> new UserNotFoundException(targetUserId));
        Member targetUserMember = Member.builder()
                .user(User.pseudo(targetUserId))
                .room(Room.pseudo(roomId))
                .roomAlias(makeJoinedMemberNameWithoutUser(users, targetUserId))
                .joinedTime(now)
                .lastEntranceTime(now)
                .build();
        Message invitedMessage = Message.of(roomId, targetUserId, targetUserNickname.getNickname() + "님이 초대되었습니다.", MessageType.NOTICE);
        memberRepository.save(targetUserMember);
        messageRepository.save(invitedMessage);
        return InviteMessage.builder()
                .contents(invitedMessage.getContents())
                .build();
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
    
    public LeaveMessage leaveRoom(Long userId, Long roomId) {
        Nickname userNickname = userRepository.findNicknameById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Message leaveMessage = Message.of(roomId, userId, userNickname.getNickname() + "님이 나갔습니다.", MessageType.NOTICE);

        memberRepository.deleteByUserIdAndRoomId(userId, roomId);
        messageRepository.save(leaveMessage);
        return LeaveMessage.builder()
                .contents(leaveMessage.getContents())
                .build();
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

    private CreateMessage inviteUsersToRoom(Room room, List<User> users) {
        List<Member> members = users.stream()
                .map(user -> {
                    String defaultRoomAlias = makeJoinedMemberNameWithoutUser(users, user.getId());
                    return Member.builder()
                            .room(room)
                            .user(user)
                            .roomAlias(defaultRoomAlias)
                            .joinedTime(room.getCreatedTime())
                            .lastEntranceTime(room.getCreatedTime())
                            .build();
                })
                .collect(Collectors.toList());
        Message noticeMessage = Message.of(room.getId(), 1L, makeNoticeContents(users), MessageType.NOTICE);
        memberRepository.saveAll(members);
        messageRepository.save(noticeMessage);
        return CreateMessage.builder()
                .contents(noticeMessage.getContents())
                .build();
    }

    private String makeJoinedMemberNameWithoutUser(List<User> memberUsers, Long userId) {
        List<String> memberNamesWithoutUser = memberUsers.stream()
                .filter(memberUser -> memberUser.getId() != userId)
                .map(User::getNickname)
                .collect(Collectors.toList());
        return String.join(", ", memberNamesWithoutUser);
    }
    
    private String makeNoticeContents(List<User> memberUsers) {
        List<String> memberNames = memberUsers.stream()
                .map(User::getNickname)
                .collect(Collectors.toList());
        return String.join("님, ", memberNames) + "님이 초대되었습니다.";
    }
}