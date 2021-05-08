package com.lhs.chatting;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lhs.chatting.model.entity.Friend;
import com.lhs.chatting.model.type.FriendRelationType;
import com.lhs.chatting.service.FriendService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendServiceTests {

    @Autowired
    FriendService friendService;

//    @Test
    public void registerFriend() {
        friendService.registerFriend(45L, 46L);
    }

//    @Test
    public void changeRelation() {
        friendService.changeRelation(48L, FriendRelationType.BLOCK);
    }

//    @Test
    public void getAllFriends() {
        List<Friend> friends = friendService.getAllFriends(45L,FriendRelationType.BLOCK);
        for(Friend i : friends) {
            System.out.println(i.getFriendRelationType());
        }
    }

//    @Test
    public void deleteUser() {
        friendService.deleteFriend(48L);
    }
}