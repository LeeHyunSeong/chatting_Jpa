package com.lhs.chatting.controller;

import java.util.Map;

import com.lhs.chatting.model.ChangeFriendRelationRequest;
import com.lhs.chatting.model.FriendRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lhs.chatting.entity.Friend;
import com.lhs.chatting.service.FriendService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendApiController {
    private final FriendService friendService;

    @PostMapping
    public void registerFriend(@RequestBody FriendRequest request) {
        friendService.registerFriend(request.getUserId(), request.getFriendId());
    }

    @PutMapping("/{friendId}")
    public ResponseEntity<Object> changeFriendRelation(@RequestBody ChangeFriendRelationRequest request, @PathVariable Long friendId) {
        friendService.changeRelation(request.getUserId(), friendId, request.getRelationType());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{friendId}/users/{userId}")
    public void deleteFriend(@PathVariable Long friendId, @PathVariable Long userId) {
        friendService.deleteFriend(userId, friendId);
    }
}
