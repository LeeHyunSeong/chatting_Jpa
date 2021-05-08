package com.lhs.chatting;

import com.lhs.chatting.model.RegisterUserRequest;
import com.lhs.chatting.model.entity.Friend;
import com.lhs.chatting.model.type.FriendRelationType;
import com.lhs.chatting.service.FriendService;
import com.lhs.chatting.service.UserService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class FriendServiceTests {

    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;

    private static final long TEST_USER1_ID = 1L;
    private static final long TEST_USER2_ID = 2L;
    private static final long TEST_FRIEND_ID = 3L;

    @Test
    public void t1_registerUsers() {
        RegisterUserRequest user1Request = RegisterUserRequest.builder()
                .username("test1")
                .password("test1_password")
                .email("test1@test.com")
                .nickname("T1")
                .build();
        RegisterUserRequest user2Request = RegisterUserRequest.builder()
                .username("test2")
                .password("test2_password")
                .email("test2@test.com")
                .nickname("T2")
                .build();

        boolean success1 = userService.registerUser(user1Request);
        boolean success2 = userService.registerUser(user2Request);

        Assert.assertTrue(success1);
        Assert.assertTrue(success2);
        Assert.assertEquals("test1", userService.getUser(TEST_USER1_ID).getUsername());
        Assert.assertEquals("test2", userService.getUser(TEST_USER2_ID).getUsername());
    }

    @Test
    public void t2_registerFriend() {
        boolean success = friendService.registerFriend(TEST_USER1_ID, TEST_USER2_ID);
        Assert.assertTrue(success);
    }

    @Test
    public void t3_changeRelation() {
        boolean success = friendService.changeRelation(TEST_FRIEND_ID, FriendRelationType.BLOCK);
        Assert.assertTrue(success);
    }

    @Test
    public void t4_getAllFriends() {
        List<Friend> friends = friendService.getAllFriends(TEST_USER1_ID, FriendRelationType.BLOCK);
        Friend firstFriend = friends.stream()
                .findFirst()
                .orElse(null);


        Assert.assertEquals(1, friends.size());
        Assert.assertNotNull(firstFriend);
        Assert.assertEquals(TEST_FRIEND_ID, firstFriend.getId());
        Assert.assertEquals(TEST_USER1_ID, firstFriend.getUser().getId());
        Assert.assertEquals(TEST_USER2_ID, firstFriend.getTargetUser().getId());
        Assert.assertEquals(FriendRelationType.BLOCK, firstFriend.getRelationType());
    }

    @Test
    public void t5_deleteUser() {
        boolean success = friendService.deleteFriend(TEST_FRIEND_ID);
        Assert.assertTrue(success);
    }

}