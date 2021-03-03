package com.lhs.chatting.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.Member;
import com.lhs.chatting.entity.Room;
import com.lhs.chatting.entity.User;
import com.lhs.chatting.repository.MemberRepository;
import com.lhs.chatting.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private MemberRepository memberRepository;

	public List<Room> getRooms() {
		return roomRepository.findAll();
	}

	public Room getRoom(Room room) {
		return roomRepository.getOne(room.getId());
	}

	public void makeRoom(String name, List<User> users) {
		Room room = new Room();
		for (User user : users) {
			Member member = new Member(name, user, room);
			memberRepository.save(member);
		}

		roomRepository.save(room);
	}

	public boolean isRoomExist(Long id) {
		return roomRepository.getOne(id) != null;
	}
}
