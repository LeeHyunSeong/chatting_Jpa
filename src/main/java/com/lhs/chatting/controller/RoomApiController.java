package com.lhs.chatting.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.model.CreateMessage;
import com.lhs.chatting.model.GetRoomsResponse;
import com.lhs.chatting.model.InviteMessage;
import com.lhs.chatting.model.InviteUserRequest;
import com.lhs.chatting.model.LeaveMessage;
import com.lhs.chatting.model.MakeRoomRequest;
import com.lhs.chatting.model.MemberRoom;
import com.lhs.chatting.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomApiController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<CreateMessage> makeRoom(@RequestParam MakeRoomRequest request) {
        CreateMessage message = roomService.makeRoom(request.getUserIds());
        return ResponseEntity.ok(message);
    }
    
    @PostMapping("/{roomId}")
    public ResponseEntity<InviteMessage> inviteFriend(@PathVariable Long roomId, @RequestParam InviteUserRequest request) {
        InviteMessage message = roomService.inviteFriend(request.getUserId(), roomId, request.getTargetUserId());
        return ResponseEntity.ok(message);
    }
    
    @GetMapping
    public ResponseEntity<GetRoomsResponse> getRooms(@RequestParam Long userId){
        List<MemberRoom> rooms = roomService.getRooms(userId);
        GetRoomsResponse response = GetRoomsResponse.builder()
                .members(rooms)
                .build();
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{roomId}/users/{userId}/room-alias")
    public ResponseEntity<Boolean> changeRoomAlias(@PathVariable Long roomId, @PathVariable Long userId, @RequestParam String roomAlias){
        boolean success = roomService.changeRoomAlias(userId, roomId, roomAlias);
        return ResponseEntity.ok(success);
    }
    
    @PatchMapping("/{roomId}/users/{userId}/last-entrance-time")
    public ResponseEntity<Boolean> updateLastEntranceTime(@PathVariable Long roomId, @PathVariable Long userId){
        boolean success = roomService.updateLastEntranceTime(userId, roomId);
        return ResponseEntity.ok(success);
    }
    
    @DeleteMapping("/{roomId}/users/{userId}")
    public ResponseEntity<LeaveMessage> leaveRoom(@PathVariable Long roomId, @PathVariable Long userId) {
        LeaveMessage message = roomService.leaveRoom(userId, roomId);
        return ResponseEntity.ok(message);
    }
}