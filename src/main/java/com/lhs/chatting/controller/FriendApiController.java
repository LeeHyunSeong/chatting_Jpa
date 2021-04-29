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
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.model.ChangeFriendRelationRequest;
import com.lhs.chatting.model.FriendRequest;
import com.lhs.chatting.model.entity.Friend;
import com.lhs.chatting.service.FriendService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendApiController {
    private final FriendService friendService;
    @PostMapping
    public ResponseEntity<Boolean> registerFriend(@RequestBody FriendRequest request) {
        boolean success = friendService.registerFriend(request.getUserId(), request.getTargetUserId());
        return ResponseEntity.ok(success);
    }
    
    @PutMapping("/{friendId}")
    public ResponseEntity<Boolean> changeFriendRelation(@RequestBody ChangeFriendRelationRequest request, @PathVariable Long friendId) {
        boolean success = friendService.changeRelation(friendId, request.getRelationType());
        return ResponseEntity.ok(success);
    }
    
    @GetMapping
    public ResponseEntity<List<Friend>> getAllFriends(@RequestBody Long userId){
        List<Friend> friends = friendService.getAllFriends(userId);
        return ResponseEntity.ok(friends);
    }
    
    @GetMapping("/hide")
    public ResponseEntity<List<Friend>> getHideFriends(@RequestBody Long userId){
        List<Friend> hideFriends = friendService.getHideFriends(userId);
        return ResponseEntity.ok(hideFriends);
    }
    
    @GetMapping("/block")
    public ResponseEntity<List<Friend>> getBlockFriends(@RequestBody Long userId){
        List<Friend> blockFriends = friendService.getBlockFriends(userId);
        return ResponseEntity.ok(blockFriends);
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<Boolean> deleteFriend(@PathVariable Long friendId) {
        boolean success = friendService.deleteFriend(friendId);
        return ResponseEntity.ok(success);
    }
}
