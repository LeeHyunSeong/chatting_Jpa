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

import com.lhs.chatting.model.CreateMessage;
import com.lhs.chatting.model.InviteMessage;
import com.lhs.chatting.model.LeaveMessage;
import com.lhs.chatting.model.MemberRoom;
import com.lhs.chatting.model.RegisterUserRequest;
import com.lhs.chatting.service.RoomService;
import com.lhs.chatting.service.UserService;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class RoomServiceTests {

    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;

    private static final long TEST_USER1_ID = 1L;
    private static final long TEST_USER2_ID = 2L;
    private static final long TEST_USER3_ID = 3L;
    private static final long TEST_USER4_ID = 4L;
    private static final long TEST_ROOM_ID = 1L;

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
        RegisterUserRequest user3Request = RegisterUserRequest.builder()
                .username("test3")
                .password("test3_password")
                .email("test3@test.com")
                .nickname("T3")
                .build();
        RegisterUserRequest user4Request = RegisterUserRequest.builder()
                .username("test4")
                .password("test4_password")
                .email("test4@test.com")
                .nickname("T4")
                .build();

        boolean success1 = userService.registerUser(user1Request);
        boolean success2 = userService.registerUser(user2Request);
        boolean success3 = userService.registerUser(user3Request);
        boolean success4 = userService.registerUser(user4Request);

        Assert.assertTrue(success1);
        Assert.assertTrue(success2);
        Assert.assertTrue(success3);
        Assert.assertTrue(success4);

        Assert.assertEquals("test1", userService.getUser(TEST_USER1_ID).getUsername());
        Assert.assertEquals("test2", userService.getUser(TEST_USER2_ID).getUsername());
        Assert.assertEquals("test3", userService.getUser(TEST_USER3_ID).getUsername());
        Assert.assertEquals("test4", userService.getUser(TEST_USER4_ID).getUsername());
    }

    @Test
    public void t2_makeRoom() {
        List<Long> userIds = new ArrayList<>();
        userIds.add(TEST_USER2_ID);
        userIds.add(TEST_USER3_ID);
        CreateMessage message = roomService.makeRoom(userIds);
        Assert.assertEquals("T2님, T3님이 초대되었습니다.", message.getContents());
    }

    @Test
    public void t3_inviteFriend() {
        InviteMessage message = roomService.inviteFriend(TEST_USER2_ID, TEST_ROOM_ID, TEST_USER4_ID);
        Assert.assertEquals("T4님이 초대되었습니다.", message.getContents());
    }

    @Test
    public void t4_getRooms() {
        List<MemberRoom> responses = roomService.getRooms(TEST_USER4_ID);
        MemberRoom firstResponse = responses.stream()
                .findFirst()
                .orElse(null);


        Assert.assertEquals(1, responses.size());
        Assert.assertNotNull(firstResponse);
        Assert.assertEquals(TEST_ROOM_ID, firstResponse.getRoomId());
    }
    
    @Test
    public void t5_changeRoomAlias() {
        boolean success = roomService.changeRoomAlias(TEST_USER4_ID, TEST_ROOM_ID, "chatting");
        List<MemberRoom> responses = roomService.getRooms(TEST_USER4_ID);
        MemberRoom firstResponse = responses.stream()
                .findFirst()
                .orElse(null);
        
        Assert.assertTrue(success);
        Assert.assertEquals("chatting", firstResponse.getRoomAlias());
    }
    
    @Test
    public void t6_updateLastEntranceTime() {
        List<MemberRoom> responses = roomService.getRooms(TEST_USER4_ID);
        MemberRoom beforeResponse = responses.stream()
                .findFirst()
                .orElse(null);
        boolean success = roomService.updateLastEntranceTime(TEST_USER4_ID, TEST_ROOM_ID);
        responses = roomService.getRooms(TEST_USER4_ID);
        MemberRoom afterResponse = responses.stream()
                .findFirst()
                .orElse(null);
        
        Assert.assertTrue(success);
        Assert.assertNotEquals(beforeResponse.getLastEntranceTime(), afterResponse.getLastEntranceTime());
    }

    @Test
    public void t7_leaveRoom() {
        LeaveMessage message = roomService.leaveRoom(TEST_USER4_ID, TEST_ROOM_ID);
        Assert.assertEquals("T4님이 나갔습니다.", message.getContents());
    }    
}