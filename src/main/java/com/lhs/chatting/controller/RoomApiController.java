package com.lhs.chatting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.model.RoomRequest;
import com.lhs.chatting.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomApiController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<Boolean> makeRoom(@RequestParam RoomRequest request) {
        boolean success = roomService.makeRoom(request.getUserIds(), request.getRoomName());
        return ResponseEntity.ok(success);
    }
}