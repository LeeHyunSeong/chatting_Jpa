package com.lhs.chatting.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.entity.Member;
import com.lhs.chatting.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {
	MemberService memberService;
	
	@PostMapping(path = "/alias")
    public void changeRoomAlias(@RequestBody Map<String, Object> aliasMap) {
    	Member member = (Member)aliasMap.get("member");
    	String alias = String.valueOf(aliasMap.get("alias"));
        memberService.changeRoomAlias(member, alias);
    }
	
	@PostMapping(path = "/setting")
    public void changeSetting(@RequestBody Map<String, Object> settingMap) {
    	Member member = (Member)settingMap.get("member");
    	String setting = String.valueOf(settingMap.get("setting"));
        memberService.changeSetting(member, setting);
    }
	
	@PostMapping(path = "/leave")
    public void leaveRoom(@RequestBody Map<String, Object> leaveMap) {
    	Member member = (Member)leaveMap.get("member");
        memberService.leaveRoom(member);
    }
}
