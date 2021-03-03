package com.lhs.chatting.controller;

import com.lhs.chatting.entity.Room;
import com.lhs.chatting.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainPageController {
	private final RoomService roomService;

	@GetMapping("/chat")
	public String chatPage() {
		return "chat.html";
	}

	@GetMapping("/room")
	public String roomPage() {
		return "room.html";
	}

	@RequestMapping("/chatting")
	public String chatting(@RequestParam("roomNumber") Long roomId) {
		return roomService.isRoomExist(roomId) ? "chat.html" : "room.html";
	}
}