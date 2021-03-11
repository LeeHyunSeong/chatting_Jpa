package com.lhs.chatting.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.Member;
import com.lhs.chatting.entity.Message;
import com.lhs.chatting.entity.MessageType;
import com.lhs.chatting.entity.Room;
import com.lhs.chatting.entity.User;
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

    public void inviteFriendToRoom(Long userId, Long roomId, String roomAlias) {
        Member member = Member.of(userId, roomId, roomAlias);
        memberRepository.save(member);
        Message inviteMessage = makeNoticeMessage("INVITE", userId, roomId);
        messageRepository.save(inviteMessage);
    }

    public void changeRoomSetting(Long memberId, String roomAlias, String roomSetting) {
        Member targetMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Can not found Member entity"));
        if (roomAlias != null)
            targetMember.setRoomAlias(roomAlias);
        if (roomSetting != null)
            targetMember.setSettingMeta(roomSetting);
        memberRepository.save(targetMember);
    }

    public void leaveRoom(Long memberId) {
        Member targetMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Can not found Member entity"));
        Room targetRoom = roomRepository.findById(targetMember.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Can not found Room entity"));
        Message exitMessage = makeNoticeMessage("INVITE", targetMember.getUser().getId(),
                targetMember.getRoom().getId());
        messageRepository.save(exitMessage);
        memberRepository.delete(targetMember);
    }

    public void updateLastEntranceTime(Long id) {
        Member targetMember = memberRepository.getOne(id);
        LocalDateTime current = LocalDateTime.now();
        targetMember.setLastEntranceTime(current);
    }

    private Message makeNoticeMessage(String type, Long userId, Long roomId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Can not found User entity"));
        String contents = new String();
        if (type == "INVITE")
            contents = user.getNickname() + "님이 초대되었습니다.";
        else if (type == "EXIT")
            contents = user.getNickname() + "님이 퇴장하였습니다.";

        Message inviteMessage = Message.of(roomId, userId, contents, MessageType.NOTICE);

        return inviteMessage;
    }
}
