package com.lhs.chatting.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.entity.Room;
import com.lhs.chatting.entity.User;
import com.lhs.chatting.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RoomApiController {
	private final RoomService roomService;

	@MessageMapping("/rooms/{bodyMap}")
	@SendTo("/topic/public")
	public List<Room> makeRoom(@DestinationVariable Map<String, Object> bodyMap) {
		String name = String.valueOf(bodyMap.get("roomName"));
		List<User> users = new ArrayList();
		if (StringUtils.hasText(name)) {
			roomService.makeRoom(name, users);
		}
		return roomService.getRooms();
	}

	@MessageMapping("/rooms")
	@SendTo("/topic/public")
	public List<Room> getRooms() {
		return roomService.getRooms();
	}

	@MessageMapping("/rooms/{roomMap}")
	@SendTo("/topic/public")
	public Room getRoom(@DestinationVariable Map<String, Object> roomMap) {
		Room room = (Room) roomMap.get("room");
		return roomService.getRoom(room);
	}
}
