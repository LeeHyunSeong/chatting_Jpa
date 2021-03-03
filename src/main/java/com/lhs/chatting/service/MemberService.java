package com.lhs.chatting.service;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.Member;
import com.lhs.chatting.entity.Room;
import com.lhs.chatting.repository.MemberRepository;
import com.lhs.chatting.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	MemberRepository memberRepository;
	RoomRepository roomRepository;

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
		// 방 인원 -1
		// " "님이 방을 나갔습니다 메시지 생성
		memberRepository.delete(targetMember);
	}

	public void updateLastEntrance(Long id) {
		Member targetMember = memberRepository.getOne(id);
		Timestamp current = new Timestamp(System.currentTimeMillis());
		targetMember.setLastEntranceTime(current);
	}
}
