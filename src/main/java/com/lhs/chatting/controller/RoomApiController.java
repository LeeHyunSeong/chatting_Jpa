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

import com.lhs.chatting.model.GetRoomListResponse;
import com.lhs.chatting.model.InviteUserRequest;
import com.lhs.chatting.model.MakeRoomRequest;
import com.lhs.chatting.model.RoomInfoResponse;
import com.lhs.chatting.service.MemberService;
import com.lhs.chatting.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomApiController {
    private final RoomService roomService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Boolean> makeRoom(@RequestParam MakeRoomRequest request) {
        boolean success = roomService.makeRoom(request.getUserIds());
        return ResponseEntity.ok(success);
    }
    
    @PostMapping("/{roomId}/user-id")
    public ResponseEntity<Boolean> inviteFriend(@RequestParam InviteUserRequest request) {
        boolean success = memberService.inviteFriend(request.getUserId(), request.getTargetUserId(), request.getRoomId());
        return ResponseEntity.ok(success);
    }
    
    @GetMapping("/room-id/users/{userId}")
    public ResponseEntity<GetRoomListResponse> getRoomList(@PathVariable Long userId){
        List<RoomInfoResponse> members = memberService.getMembers(userId);
        GetRoomListResponse response = GetRoomListResponse.builder()
                .members(members)
                .build();
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{roomId}/members/{memberId}/room-alias")
    public ResponseEntity<Boolean> changeRoomAlias(@PathVariable Long memberId, @RequestParam String roomAlias){
        boolean success = memberService.changeRoomAlias(memberId, roomAlias);
        return ResponseEntity.ok(success);
    }
    
    @PatchMapping("/{roomId}/members/{memberId}/last-entrance-time")
    public ResponseEntity<Boolean> updateLastEntranceTime(@PathVariable Long memberId){
        boolean success = memberService.updateLastEntranceTime(memberId);
        return ResponseEntity.ok(success);
    }
    
    @DeleteMapping("/{roomId}/members/{memberId}")
    public ResponseEntity<Boolean> leaveRoom(@PathVariable Long memberId) {
        boolean success = memberService.leaveRoom(memberId);
        return ResponseEntity.ok(success);
    }
}