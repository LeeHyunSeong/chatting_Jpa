package com.lhs.chatting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.model.FriendRequest;
import com.lhs.chatting.service.FriendService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendApiController {
    private final FriendService friendService;
    @PostMapping
    public ResponseEntity<Boolean> registerFriend(@RequestBody FriendRequest request) {
        boolean success = friendService.registerFriend(request);
        return ResponseEntity.ok(success);
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<Boolean> deleteFriend(@PathVariable Long friendId) {
        boolean success = friendService.deleteFriend(friendId);
        return ResponseEntity.ok(success);
    }
}
