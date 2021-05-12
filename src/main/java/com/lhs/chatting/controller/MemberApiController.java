package com.lhs.chatting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.model.ChangeRoomAliasRequest;
import com.lhs.chatting.model.InviteUserRequest;
import com.lhs.chatting.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Boolean> inviteFriend(@RequestParam InviteUserRequest request) {
        boolean success = memberService.inviteFriend(request.getUserId(), request.getRoomId());
        return ResponseEntity.ok(success);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Boolean> leaveRoom(@PathVariable Long memberId) {
        boolean success = memberService.leaveRoom(memberId);
        return ResponseEntity.ok(success);
    }
}
