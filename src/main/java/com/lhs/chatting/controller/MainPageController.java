package com.lhs.chatting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lhs.chatting.service.MemberService;
import com.lhs.chatting.service.RoomService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainPageController {
    private final RoomService roomService;
    private final MemberService memberService;

    @GetMapping("/chat")
    public String chatPage() {
        return "chat.html";
    }

    @GetMapping("/room")
    public String roomPage() {
        return "room.html";
    }

    @RequestMapping("/chatting")
    public String chatting(@RequestParam("roomId") Long roomId) {
        return roomService.isRoomExist(roomId) ? "chat.html" : "room.html";
    }

    @RequestMapping("/list")
    public String listPage(@RequestParam("memberId") Long memberId) {
        memberService.updateLastEntranceTime(memberId);
        return "room.html";
    }
}