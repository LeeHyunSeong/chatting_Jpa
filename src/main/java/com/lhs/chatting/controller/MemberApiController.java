package com.lhs.chatting.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.entity.Member;
import com.lhs.chatting.model.ChangeRoomSettingRequest;
import com.lhs.chatting.model.InviteUserRequest;
import com.lhs.chatting.model.MemberRequest;
import com.lhs.chatting.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/{userId}")
    public void inviteFriend(@RequestBody InviteUserRequest request, @PathVariable Long userId) {
        memberService.inviteFriend(userId, request.getRoomId(), request.getRoomAlias());
    }

    @PutMapping("/{memberId}")
    public void changeRoomSetting(@RequestBody ChangeRoomSettingRequest request, @PathVariable Long memberId) {
        String roomAlias = request.getRoomAlias();
        String roomSetting = request.getSetting();
        memberService.changeRoomSetting(memberId, roomAlias, roomSetting);
    }

    @DeleteMapping("/{memberId}")
    public void leaveRoom(@RequestBody MemberRequest request) {
        memberService.leaveRoom(request.getMemberId());
    }
}
