package com.lhs.chatting.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lhs.chatting.model.Room;
import com.lhs.chatting.util.RoomNumberGenerateUtils;

@Controller
public class MainController {

	private final List<Room> rooms = new ArrayList<>();

	@GetMapping("/chat")
	public String chatPage() {
		return "chat.html";
	}

	@GetMapping("/room")
	public String roomPage() {
		return "room.html";
	}

	@PostMapping("/api/rooms")
	@ResponseBody
	public List<Room> createRoom(@RequestParam("roomName") String roomName) {
		if (StringUtils.hasText(roomName)) {
			Room room = Room.builder()
					.number(RoomNumberGenerateUtils.getNextRoomNumber())
					.name(roomName)
					.build();
			rooms.add(room);
		}
		return rooms;
	}

	@GetMapping("/api/rooms")
	@ResponseBody
	public List<Room> getRooms() {
		return rooms;
	}

	@GetMapping("/api/rooms/{roomNumber}")
	@ResponseBody
	public Room getRoom(@PathVariable("roomNumber") String roomNumber) {
		return rooms.get(Integer.parseInt(roomNumber) - 1);
	}

	@RequestMapping("/chatting")
	public String chatting(@RequestParam("roomNumber") Integer roomNumber, @RequestParam("roomName") String roomName, Model model) {
		Room room = Room.builder()
				.number(roomNumber)
				.name(roomName)
				.build();
		if (isRoomExist(room.getNumber())) {
			// TODO : ModelAndView가 꼭 필요한지 체크
			model.addAttribute(room);
			return "chat.html";
		} else {
			return "room.html";
		}
	}

	private boolean isRoomExist(int roomNumber) {
		return rooms.stream().anyMatch(room -> room.getNumber() == roomNumber);
	}
}