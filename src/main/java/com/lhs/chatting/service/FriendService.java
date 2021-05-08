package com.lhs.chatting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lhs.chatting.exception.FriendNotFoundException;
import com.lhs.chatting.model.entity.Friend;
import com.lhs.chatting.model.type.FriendRelationType;
import com.lhs.chatting.repository.FriendRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository repository;

    public boolean registerFriend(Long userId, Long targetUserId) {
        Friend friend = Friend.of(userId, targetUserId, FriendRelationType.NORMAL);
        repository.save(friend);
        return true;
    }

    public boolean changeRelation(Long friendId, FriendRelationType relationType) {
        Friend exitedFriend = repository.findById(friendId)
                .orElseThrow(() -> new FriendNotFoundException(friendId));
        exitedFriend.setFriendRelationType(relationType);
        repository.save(exitedFriend);
        return true;
    }
    
    public List<Friend> getAllFriends(Long userId, FriendRelationType relationType){
        return repository.findAllByUserIdAndFriendRelationType(userId, relationType);
    }
    
    public boolean deleteFriend(Long friendId) {
        repository.deleteById(friendId);
        return true;
    }
}
