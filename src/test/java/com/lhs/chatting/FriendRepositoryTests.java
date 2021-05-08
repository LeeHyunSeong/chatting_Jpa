package com.lhs.chatting;

import java.time.LocalDateTime;

import com.lhs.chatting.exception.UserNotFoundException;
import com.lhs.chatting.repository.UserRepository;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lhs.chatting.exception.FriendNotFoundException;
import com.lhs.chatting.model.entity.Friend;
import com.lhs.chatting.model.entity.User;
import com.lhs.chatting.model.type.FriendRelationType;
import com.lhs.chatting.repository.FriendRepository;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class FriendRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRepository friendRepository;

    private static final long TEST_USER1_ID = 1L;
    private static final long TEST_USER2_ID = 2L;
    private static final long TEST_FRIEND_ID = 3L;

    @Test
    public void t1_users_insert() {
        User user1 = User.builder()
                .username("test1")
                .password("test1_password")
                .email("test1@test.com")
                .nickname("T1")
                .signedTime(LocalDateTime.now())
                .build();
        User user2 = User.builder()
                .username("test2")
                .password("test2_password")
                .email("test2@test.com")
                .nickname("T2")
                .signedTime(LocalDateTime.now())
                .build();

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);

        Assert.assertEquals(TEST_USER1_ID, savedUser1.getId());
        Assert.assertEquals(TEST_USER2_ID, savedUser2.getId());
    }

    @Test
    public void t2_insert() {
        Friend friend = Friend.builder()
                .user(User.builder().id(TEST_USER1_ID).build())
                .targetUser(User.builder().id(TEST_USER2_ID).build())
                .relationType(FriendRelationType.NORMAL)
                .createdTime(LocalDateTime.now())
                .build();

        Friend savedFriend = friendRepository.save(friend);
        Assert.assertEquals(TEST_FRIEND_ID, savedFriend.getId());
        Assert.assertEquals(TEST_USER1_ID, savedFriend.getUser().getId());
        Assert.assertEquals(TEST_USER2_ID, savedFriend.getTargetUser().getId());
        Assert.assertEquals(FriendRelationType.NORMAL, savedFriend.getRelationType());
    }

    @Test
    public void t3_search_inserted_friend() {
        Friend friend = friendRepository.findById(TEST_FRIEND_ID)
                .orElseThrow(() -> new FriendNotFoundException(TEST_FRIEND_ID));

        Assert.assertEquals(TEST_FRIEND_ID, friend.getId());
        Assert.assertEquals(TEST_USER1_ID, friend.getUser().getId());
        Assert.assertEquals(TEST_USER2_ID, friend.getTargetUser().getId());
        Assert.assertEquals(FriendRelationType.NORMAL, friend.getRelationType());
    }

    @Test
    public void t4_update() {
        Friend friend = friendRepository.findById(TEST_FRIEND_ID)
                .orElseThrow(() -> new FriendNotFoundException(TEST_FRIEND_ID));

        friend.setRelationType(FriendRelationType.BLOCK);
        Friend savedFriend = friendRepository.save(friend);

        Assert.assertEquals(TEST_FRIEND_ID, savedFriend.getId());
        Assert.assertEquals(TEST_USER1_ID, savedFriend.getUser().getId());
        Assert.assertEquals(TEST_USER2_ID, savedFriend.getTargetUser().getId());
        Assert.assertEquals(FriendRelationType.BLOCK, savedFriend.getRelationType());
    }

    @Test
    public void t5_search_updated_friend() {
        Friend friend = friendRepository.findById(TEST_FRIEND_ID)
                .orElseThrow(() -> new FriendNotFoundException(TEST_FRIEND_ID));

        Assert.assertEquals(TEST_FRIEND_ID, friend.getId());
        Assert.assertEquals(TEST_USER1_ID, friend.getUser().getId());
        Assert.assertEquals(TEST_USER2_ID, friend.getTargetUser().getId());
        Assert.assertEquals(FriendRelationType.BLOCK, friend.getRelationType());
    }

    @Test
    public void t6_delete() {
        friendRepository.deleteById(TEST_FRIEND_ID);
    }

    @Test
    public void t7_search_deleted_friend() {
        Assert.assertThrows(
                FriendNotFoundException.class,
                () -> friendRepository.findById(TEST_FRIEND_ID)
                        .orElseThrow(() -> new FriendNotFoundException(TEST_FRIEND_ID))
        );
    }

}