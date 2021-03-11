package com.lhs.chatting.service;

import com.lhs.chatting.entity.FriendRelationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.Friend;
import com.lhs.chatting.repository.FriendRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository repository;

    public void registerFriend(Long userId, Long friendId) {
        Friend friend = Friend.of(userId, friendId, FriendRelationType.NORMAL);
        repository.save(friend);
    }

    public void changeRelation(Long userId, Long friendId, FriendRelationType relationType) {
        Friend exitedFriend = repository.findByUserIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new RuntimeException("Can not found friend entity"));
        exitedFriend.setRelationType(relationType);
        repository.save(exitedFriend);
    }

    public void deleteFriend(Long userId, Long friendId) {
        Friend exitedFriend = repository.findByUserIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new RuntimeException("Can not found friend entity"));
        repository.deleteById(exitedFriend.getId());
    }
}
