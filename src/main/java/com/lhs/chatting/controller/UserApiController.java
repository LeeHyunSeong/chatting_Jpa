package com.lhs.chatting.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.entity.Member;
import com.lhs.chatting.model.ChangeUserInformationRequest;
import com.lhs.chatting.model.RegisterUserRequest;
import com.lhs.chatting.service.RoomService;
import com.lhs.chatting.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final RoomService roomService;

    @PostMapping
    public void registerUser(@RequestBody RegisterUserRequest request) {
        userService.registerUser(request.getUsername(), request.getPassword(), request.getEmail(), request.getNickname());
    }

    @PutMapping("/{userId}")
    public void changeUserInfo(@RequestBody ChangeUserInformationRequest request, @PathVariable Long userId) {
        String password = request.getPassword();
        String nickname = request.getNickname();
        String profileImage = request.getProfileImage();
        userService.changeUserInfo(userId, nickname, password, profileImage);
    }
    
    @GetMapping("/{userId}/Rooms")
    public List<Member> getRooms(@PathVariable Long userId) {
        return roomService.getRooms(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}