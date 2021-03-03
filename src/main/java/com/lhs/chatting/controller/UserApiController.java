package com.lhs.chatting.controller;

import com.lhs.chatting.entity.Friend;
import com.lhs.chatting.entity.User;
import com.lhs.chatting.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {
	private final UserService userService;

	@PostMapping
	public void registerUser(@RequestBody User user) {
		userService.registerUser(user);
	}

	@PostMapping(path = "/password")
	public void changePassword(@RequestBody Map<String, Object> passwordMap) {
		User user = (User) passwordMap.get("user");
		String password = String.valueOf(passwordMap.get("password"));
		userService.changePassword(user, password);
	}

	@PostMapping(path = "/nickname")
	public void changeNickname(@RequestBody Map<String, Object> nicknameMap) {
		User user = (User) nicknameMap.get("user");
		String nickname = String.valueOf(nicknameMap.get("nickname"));
		userService.changeNickname(user, nickname);
	}

	@PostMapping(path = "/profile")
	public void changeProfile(@RequestBody Map<String, Object> profileMap) {
		User user = (User) profileMap.get("user");
		String image = String.valueOf(profileMap.get("image"));
		userService.changeProfile(user, image);
	}

	@PostMapping(path = "/delete")
	public void deleteUser(@RequestBody Map<String, Object> userMap) {
		User user = (User) userMap.get("user");
		userService.deleteUser(user);
	}
}