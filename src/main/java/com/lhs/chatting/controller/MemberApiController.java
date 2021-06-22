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

import com.lhs.chatting.model.GetAllMemberResponse;
import com.lhs.chatting.model.InviteUserRequest;
import com.lhs.chatting.model.entity.Member;
import com.lhs.chatting.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Boolean> inviteFriend(@RequestParam InviteUserRequest request) {
        boolean success = memberService.inviteFriend(request.getUserId(), request.getTargetUserId(), request.getRoomId());
        return ResponseEntity.ok(success);
    }

    @GetMapping
    public ResponseEntity<GetAllMemberResponse> getMembers(@RequestParam Long userId){
        List<Member> members = memberService.getMembers(userId);
        GetAllMemberResponse response = GetAllMemberResponse.builder()
                .members(members)
                .build();
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{memberId}/room-alias")
    public ResponseEntity<Boolean> changeRoomAlias(@PathVariable Long memberId, @RequestParam String roomAlias){
        boolean success = memberService.changeRoomAlias(memberId, roomAlias);
        return ResponseEntity.ok(success);
    }
    
    @PatchMapping("/{memberId}")
    public ResponseEntity<Boolean> updateLastEntranceTime(@PathVariable Long memberId){
        boolean success = memberService.updateLastEntranceTime(memberId);
        return ResponseEntity.ok(success);
    }
    
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Boolean> leaveRoom(@PathVariable Long memberId) {
        boolean success = memberService.leaveRoom(memberId);
        return ResponseEntity.ok(success);
    }
}
