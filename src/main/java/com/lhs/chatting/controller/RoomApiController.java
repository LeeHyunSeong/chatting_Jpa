package com.lhs.chatting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.model.CreateRoomRequest;
import com.lhs.chatting.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomApiController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<Boolean> makeRoom(@RequestParam CreateRoomRequest request) {
        boolean success = roomService.makeRoom(request.getUserIds());
        return ResponseEntity.ok(success);
    }
    
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Boolean> deleteRoom(@PathVariable Long roomId) {
        boolean success = roomService.deleteRoom(roomId);
        return ResponseEntity.ok(success);
    }
}