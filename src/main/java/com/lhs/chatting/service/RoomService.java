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
import com.lhs.chatting.model.InviteMessage;
import com.lhs.chatting.model.LeaveMessage;
import com.lhs.chatting.model.MemberRoom;
import com.lhs.chatting.model.MultipleInviteMessage;
import com.lhs.chatting.model.entity.Member;
import com.lhs.chatting.model.entity.Message;
import com.lhs.chatting.model.entity.Room;
import com.lhs.chatting.model.entity.User;
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
    public MultipleInviteMessage makeRoom(Long hostUserId, List<Long> userIds) {
        Room room = Room.newInstance();
        roomRepository.save(room);

        List<User> users = getUsersByIds(userIds);
        inviteUsersToRoom(room, users);
        return saveCreateMessage(room.getId(), hostUserId, users);
    }
    
    public InviteMessage inviteFriend(Long roomId, Long userId) {
        saveMemberOfInvitedUser(roomId, userId);
        return saveInviteMessage(roomId, userId);
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
        memberRepository.deleteByUserIdAndRoomId(userId, roomId);
        return saveLeaveMessage(roomId, userId);
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
        memberRepository.saveAll(members);
    }

    private String makeJoinedMemberNameWithoutUser(List<User> memberUsers, Long userId) {
        List<String> memberNamesWithoutUser = memberUsers.stream()
                .filter(memberUser -> memberUser.getId() != userId)
                .map(User::getNickname)
                .collect(Collectors.toList());
        return String.join(", ", memberNamesWithoutUser);
    }
    
    private void saveMemberOfInvitedUser(Long roomId, Long userId) {
        List<User> users = memberRepository.findAllMemberUsersByRoomId(roomId);
        LocalDateTime now = LocalDateTime.now();

        Member invitedUserMember = Member.builder()
                .user(User.pseudo(userId))
                .room(Room.pseudo(roomId))
                .roomAlias(makeJoinedMemberNameWithoutUser(users, userId))
                .joinedTime(now)
                .lastEntranceTime(now)
                .build();
      
        memberRepository.save(invitedUserMember);
    }
    
    private MultipleInviteMessage saveCreateMessage(Long roomId, Long hostUserId, List<User> users) {
        Message multipleInviteMessage = Message.multipleInviteMessageOf(roomId, hostUserId, users);
        messageRepository.save(multipleInviteMessage);
        return MultipleInviteMessage.builder()
                .contents(multipleInviteMessage.getContents())
                .build();
    }
    
    private InviteMessage saveInviteMessage(Long roomId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Message invitedMessage = Message.inviteMessageOf(roomId, user);
        messageRepository.save(invitedMessage);
        return InviteMessage.builder()
                .contents(invitedMessage.getContents())
                .build();
    }
    
    private LeaveMessage saveLeaveMessage(Long roomId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Message leaveMessage = Message.leaveMessageOf(roomId, user);
        messageRepository.save(leaveMessage);
        return LeaveMessage.builder()
                .contents(leaveMessage.getContents())
                .build();
    }
}