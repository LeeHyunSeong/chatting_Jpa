package com.lhs.chatting;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lhs.chatting.model.MultipleInviteMessage;
import com.lhs.chatting.model.RegisterUserRequest;
import com.lhs.chatting.model.entity.Message;
import com.lhs.chatting.service.MessageService;
import com.lhs.chatting.service.RoomService;
import com.lhs.chatting.service.UserService;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class MessageServiceTests {

    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private MessageService messageService;
    
    private static final long TEST_USER1_ID = 1L;
    private static final long TEST_USER2_ID = 2L;
    private static final long TEST_ROOM_ID = 1L;
    private static final long TEST_MESSAGE1_ID = 2L;
    private static final long TEST_MESSAGE2_ID = 3L;
    private static final long TEST_MESSAGE3_ID = 4L;

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
    public void t2_makeRoom() {
        List<Long> userIds = new ArrayList<>();
        userIds.add(TEST_USER1_ID);
        userIds.add(TEST_USER2_ID);
        MultipleInviteMessage message = roomService.makeRoom(TEST_USER1_ID, userIds);
        Assert.assertEquals("T1님, T2님이 초대되었습니다.", message.getContents());
    }

    @Test
    public void t3_makeMessage() {
        String contents1 = "TEST_USER1_CONTENTS1";
        String contents2 = "TEST_USER1_CONTENTS2";
        String contents3 = "TEST_USER2_CONTENTS1";
        
        boolean success1 = messageService.makeMessage(TEST_ROOM_ID, TEST_USER1_ID, contents1);
        boolean success2 = messageService.makeMessage(TEST_ROOM_ID, TEST_USER1_ID, contents2);
        boolean success3 = messageService.makeMessage(TEST_ROOM_ID, TEST_USER2_ID, contents3);
        
        Assert.assertTrue(success1);
        Assert.assertTrue(success2);
        Assert.assertTrue(success3);
    }

    @Test
    public void t4_getMessages() {
        List<Message> responses = messageService.getMessages(TEST_ROOM_ID, 3);
        Message firstResponse = responses.stream()
                .findFirst()
                .orElse(null);
        
        Assert.assertEquals(3, responses.size());
        Assert.assertNotNull(firstResponse);
        Assert.assertEquals(TEST_MESSAGE3_ID, firstResponse.getId());
        Assert.assertEquals("TEST_USER2_CONTENTS1", firstResponse.getContents());
    }
    
    @Test
    public void t5_deleteMessage() {
        boolean success1 = messageService.deleteMessage(TEST_MESSAGE1_ID);
        boolean success2 = messageService.deleteMessage(TEST_MESSAGE2_ID);
        boolean success3 = messageService.deleteMessage(TEST_MESSAGE3_ID);
        
        Assert.assertTrue(success1);
        Assert.assertTrue(success2);
        Assert.assertTrue(success3);
    }

}