package com.lhs.chatting.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.model.ChangeUserInfoRequest;
import com.lhs.chatting.model.GetAllMemberResponse;
import com.lhs.chatting.model.RegisterUserRequest;
import com.lhs.chatting.model.entity.Member;
import com.lhs.chatting.model.entity.User;
import com.lhs.chatting.service.MemberService;
import com.lhs.chatting.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final MemberService memberService;

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

    @GetMapping("/user-id")
    public ResponseEntity<Long> getUserIdByEmail(@RequestParam String email) {
        Long userId = userService.getUserIdByEmail(email);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/{userId}/members")
    public ResponseEntity<GetAllMemberResponse> getMembers(@PathVariable Long userId){
        List<Member> members = memberService.getMembers(userId);
        GetAllMemberResponse response = GetAllMemberResponse.builder()
                .members(members)
                .build();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long userId) {
        boolean success = userService.deleteUser(userId);
        return ResponseEntity.ok(success);
    }
}