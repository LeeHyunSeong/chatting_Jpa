package com.lhs.chatting.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.entity.Friend;
import com.lhs.chatting.entity.User;
import com.lhs.chatting.service.FriendService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendApiController {
	private final FriendService friendService;

	@PostMapping
	public void registerFriend(@RequestBody Map<String, Object> friendMap) {
		User user = (User) friendMap.get("user");
		User friend = (User) friendMap.get("friend");
		friendService.registerFriend(user, friend);
	}

	@PostMapping(path = "/change")
	public void changeRelation(@RequestBody Map<String, Object> friendMap) {
		Friend friend = (Friend) friendMap.get("friend");
		String relation = String.valueOf(friendMap.get("relation"));
		friendService.changeRelation(friend, relation);
	}

	@PostMapping(path = "/delete")
	public void deleteFriend(@RequestBody Map<String, Object> friendMap) {
		Friend friend = (Friend) friendMap.get("friend");
		friendService.deleteFriend(friend);
	}
}
