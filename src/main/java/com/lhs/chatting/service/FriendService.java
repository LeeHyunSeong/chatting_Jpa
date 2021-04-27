package com.lhs.chatting.service;

import org.springframework.stereotype.Service;

import com.lhs.chatting.model.FriendRequest;
import com.lhs.chatting.model.entity.Friend;
import com.lhs.chatting.model.type.FriendRelationType;
import com.lhs.chatting.repository.FriendRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository repository;

    public boolean registerFriend(FriendRequest request) {
        Friend friend = Friend.of(request.getUserId(), request.getTargetUserId(), FriendRelationType.NORMAL);
        repository.save(friend);
        return true;
    }

    public boolean deleteFriend(Long friendId) {
        repository.deleteById(friendId);
        return true;
    }
}
