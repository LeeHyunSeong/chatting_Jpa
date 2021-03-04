package com.lhs.chatting.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.Member;
import com.lhs.chatting.entity.Message;
import com.lhs.chatting.entity.Room;
import com.lhs.chatting.entity.User;
import com.lhs.chatting.repository.MemberRepository;
import com.lhs.chatting.repository.MessageRepository;
import com.lhs.chatting.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private MessageRepository messageRepository;

	public void inviteFriend(User friend, Room room, String alias) {
		Member member = new Member(alias, friend, room);
		memberRepository.save(member);
		Message inviteMessage = makeNoticeMessage("INVITE", friend, room);
		messageRepository.save(inviteMessage);
	}

	public void changeRoomAlias(Member member, String name) {
		Member targetMember = memberRepository.getOne(member.getId());
		targetMember.setRoomAlias(name);
	}

	public void changeSetting(Member member, String setting) {
		Member targetMember = memberRepository.getOne(member.getId());
		targetMember.setSettingMeta(setting);
	}

	public void leaveRoom(Member member) {
		Member targetMember = memberRepository.getOne(member.getId());
		Room targetRoom = roomRepository.getOne(targetMember.getRoom().getId());
		Message exitMessage = makeNoticeMessage("INVITE", targetMember.getUser(), targetMember.getRoom());
		messageRepository.save(exitMessage);
		memberRepository.delete(targetMember);
	}

	public void updateLastEntrance(Long id) {
		Member targetMember = memberRepository.getOne(id);
		Timestamp current = new Timestamp(System.currentTimeMillis());
		targetMember.setLastEntranceTime(current);
	}

	private Message makeNoticeMessage(String type, User user, Room room) {
		Message inviteMessage = new Message();
		if (type == "INVITE")
			inviteMessage.setContents(user.getNickname() + "님이 초대되었습니다.");
		else if (type == "EXIT")
			inviteMessage.setContents(user.getNickname() + "님이 퇴장하였습니다.");
		inviteMessage.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		inviteMessage.setRoom(room);
		inviteMessage.setUser(user);
		inviteMessage.setType("NOTICE");

		return inviteMessage;
	}
}
