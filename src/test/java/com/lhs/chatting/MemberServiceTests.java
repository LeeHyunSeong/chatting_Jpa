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

import com.lhs.chatting.model.RegisterUserRequest;
import com.lhs.chatting.model.entity.Member;
import com.lhs.chatting.service.MemberService;
import com.lhs.chatting.service.RoomService;
import com.lhs.chatting.service.UserService;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private MemberService memberService;

    private static final long TEST_USER1_ID = 1L;
    private static final long TEST_USER2_ID = 2L;
    private static final long TEST_USER3_ID = 3L;
    private static final long TEST_ROOM_ID = 1L;
    private static final long TEST_MEMBER1_ID = 1L;
    private static final long TEST_MEMBER2_ID = 2L;
    private static final long TEST_MEMBER3_ID = 3L;

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

        boolean success1 = userService.registerUser(user1Request);
        boolean success2 = userService.registerUser(user2Request);
        boolean success3 = userService.registerUser(user3Request);

        Assert.assertTrue(success1);
        Assert.assertEquals("test1", userService.getUser(TEST_USER1_ID).getUsername());
        Assert.assertTrue(success2);
        Assert.assertEquals("test2", userService.getUser(TEST_USER2_ID).getUsername());
        Assert.assertTrue(success3);
        Assert.assertEquals("test3", userService.getUser(TEST_USER3_ID).getUsername());
    }

    @Test
    public void t2_makeRoom() {
        List<Long> userIds = new ArrayList<>();
        userIds.add(TEST_USER1_ID);
        userIds.add(TEST_USER2_ID);
        
        boolean success = roomService.makeRoom(userIds);
        
        Assert.assertTrue(success);
    }
    
    @Test
    public void t3_inviteFriend() {
        boolean success = memberService.inviteFriend(TEST_USER1_ID, TEST_USER3_ID, TEST_ROOM_ID);
        Assert.assertTrue(success);
    }

    @Test
    public void t4_getMembers() {
        List<Member> members = memberService.getMembers(TEST_USER3_ID);
        Member firstMember = members.stream()
                .findFirst()
                .orElse(null);


        Assert.assertEquals(1, members.size());
        Assert.assertNotNull(firstMember);
        Assert.assertEquals(TEST_MEMBER3_ID, firstMember.getId());
        Assert.assertEquals(TEST_USER3_ID, firstMember.getUser().getId());
        Assert.assertEquals(TEST_ROOM_ID, firstMember.getRoom().getId());
    }
    
    @Test
    public void t5_changeRoomAlias() {
        boolean success = memberService.changeRoomAlias(TEST_MEMBER3_ID, "chatting");
        List<Member> members = memberService.getMembers(TEST_USER3_ID);
        Member firstMember = members.stream()
                .findFirst()
                .orElse(null);
        
        Assert.assertTrue(success);
        Assert.assertEquals("chatting", firstMember.getRoomAlias());
    }
    
    @Test
    public void t6_updateLastEntranceTime() {
        List<Member> members = memberService.getMembers(TEST_USER3_ID);
        Member beforeMember = members.stream()
                .findFirst()
                .orElse(null);
        boolean success = memberService.updateLastEntranceTime(TEST_MEMBER3_ID);
        members = memberService.getMembers(TEST_USER3_ID);
        Member afterMember = members.stream()
                .findFirst()
                .orElse(null);
        
        Assert.assertTrue(success);
        Assert.assertNotEquals(beforeMember.getLastEntranceTime(), afterMember.getLastEntranceTime());
    }

    @Test
    public void t7_leaveRoom() {
        boolean success = memberService.leaveRoom(TEST_MEMBER3_ID);
        Assert.assertTrue(success);
    }
}