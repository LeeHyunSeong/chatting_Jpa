package com.lhs.chatting.controller;

import com.lhs.chatting.model.Room;
import com.lhs.chatting.util.RoomNumberGenerateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/chatting")
    public ModelAndView chatting(@RequestParam("roomNumber") int roomNumber, @RequestParam("roomName") String roomName) {
        ModelAndView modelAndView = new ModelAndView();
        Room room = Room.builder()
                .number(roomNumber)
                .name(roomName)
                .build();

        if (isRoomExist(room.getNumber())) {
            // TODO : ModelAndView가 꼭 필요한지 체크
//            modelAndView.addObject("room", room);
            modelAndView.setViewName("chat");
        } else {
            modelAndView.setViewName("room");
        }
        return modelAndView;
    }

    private boolean isRoomExist(int roomNumber) {
        return rooms.stream()
                .anyMatch(room -> room.getNumber() == roomNumber);
    }
}