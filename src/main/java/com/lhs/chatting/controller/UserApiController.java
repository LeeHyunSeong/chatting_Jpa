package com.lhs.chatting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.model.ChangeUserInfoRequest;
import com.lhs.chatting.model.RegisterUserRequest;
import com.lhs.chatting.model.entity.User;
import com.lhs.chatting.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<Boolean> registerUser(@RequestBody RegisterUserRequest request) {
        boolean success = userService.registerUser(request);
        return ResponseEntity.ok(success);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Boolean> changeUserInfo(@PathVariable Long userId, @RequestBody ChangeUserInfoRequest request) {
        boolean success = userService.changeUserInfo(userId, request);
        return ResponseEntity.ok(success);
    }

    @GetMapping
    public ResponseEntity<Long> getUserIdByEmail(@RequestBody String email) {
        Long userId = userService.getUserIdByEmail(email);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserByUserId(@PathVariable Long userId) {
        User user = userService.getUserByUserId(userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long userId) {
        boolean success = userService.deleteUser(userId);
        return ResponseEntity.ok(success);
    }
}