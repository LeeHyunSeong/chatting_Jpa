package com.lhs.chatting.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.entity.UserInfoType;
import com.lhs.chatting.model.ChangeUserInformationRequest;
import com.lhs.chatting.model.RegisterUserRequest;
import com.lhs.chatting.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping
    public void registerUser(@RequestBody RegisterUserRequest request) {
        userService.registerUser(request.getUsername(), request.getPassword(), request.getEmail(), request.getNickname());
    }

    @PutMapping("/{userId}")
    public void changeUserInfo(@RequestBody ChangeUserInformationRequest request, @PathVariable Long userId) {
        UserInfoType userInfoType = request.getUserInfoType();
        String contents = request.getContents();
        userService.changeUserInfo(userId, userInfoType, contents);
    }

    @GetMapping
    public Long getUserId(@RequestBody String email) {
        return userService.getUserIdByEmail(email);
    }

    
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}