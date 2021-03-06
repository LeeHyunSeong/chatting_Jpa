package com.lhs.chatting.service;

import java.sql.Timestamp;

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
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private MessageRepository messageRepository;

	public void inviteFriend(Long userId, Long roomId, String roomAlias) {
		Member member = Member.of(userId, roomId, roomAlias);
		memberRepository.save(member);
		Message inviteMessage = makeNoticeMessage("INVITE", userId, roomId);
		messageRepository.save(inviteMessage);
	}

	public void changeRoomAlias(Long memberId, String roomAlias) {
		Member targetMember = memberRepository.findById(memberId)
				.orElseThrow(() -> new RuntimeException("Can not found Member entity"));
		targetMember.setRoomAlias(roomAlias);
		memberRepository.save(targetMember);
	}

	public void changeSetting(Long memberId, String roomSetting) {
		Member targetMember = memberRepository.findById(memberId)
				.orElseThrow(() -> new RuntimeException("Can not found Member entity"));
		targetMember.setSettingMeta(roomSetting);
		memberRepository.save(targetMember);
	}

	public void leaveRoom(Long memberId) {
		Member targetMember = memberRepository.findById(memberId)
				.orElseThrow(() -> new RuntimeException("Can not found Member entity"));
		Room targetRoom = roomRepository.findById(targetMember.getRoom().getId())
				.orElseThrow(() -> new RuntimeException("Can not found Room entity"));
		Message exitMessage = makeNoticeMessage("INVITE", targetMember.getUser().getId(), targetMember.getRoom().getId());
		messageRepository.save(exitMessage);
		memberRepository.delete(targetMember);
	}

	public void updateLastEntrance(Long id) {
		Member targetMember = memberRepository.getOne(id);
		Timestamp current = new Timestamp(System.currentTimeMillis());
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

		Message inviteMessage = Message.of(contents, MessageType.NOTICE, roomId, userId);
		
		return inviteMessage;
	}
}
