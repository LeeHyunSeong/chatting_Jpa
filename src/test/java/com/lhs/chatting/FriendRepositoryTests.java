package com.lhs.chatting;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lhs.chatting.exception.FriendNotFoundException;
import com.lhs.chatting.model.entity.Friend;
import com.lhs.chatting.model.entity.User;
import com.lhs.chatting.model.type.FriendRelationType;
import com.lhs.chatting.repository.FriendRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendRepositoryTests {

    @Autowired
    FriendRepository friendRepository;

//    @Test
    public void insert() {
        Friend friend = Friend.builder()
                .user(User.builder().id(45L).build())
                .targetUser(User.builder().id(46L).build())
                .friendRelationType(FriendRelationType.NORMAL)
                .createdTime(LocalDateTime.now())
                .build();
        friendRepository.save(friend);
    }

//    @Test
    public void search() {
        Friend friend = friendRepository.findById(47L)
                .orElseThrow(() -> new FriendNotFoundException(47L));
        System.out.println(friend.getFriendRelationType());
    }

//    @Test
    public void delete() {
        friendRepository.deleteById(47L);
    }

//    @Test
    public void update() {
        Friend friend = friendRepository.findById(47L)
                .orElseThrow(() -> new FriendNotFoundException(47L));
        friend.setFriendRelationType(FriendRelationType.BLOCK);
        friendRepository.save(friend);
    }
}