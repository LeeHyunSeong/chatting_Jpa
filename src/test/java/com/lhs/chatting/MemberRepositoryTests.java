package com.lhs.chatting;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lhs.chatting.exception.MemberNotFoundException;
import com.lhs.chatting.model.entity.Member;
import com.lhs.chatting.model.entity.Room;
import com.lhs.chatting.model.entity.User;
import com.lhs.chatting.repository.MemberRepository;
import com.lhs.chatting.repository.RoomRepository;
import com.lhs.chatting.repository.UserRepository;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private MemberRepository memberRepository;

    private static final long TEST_USER_ID = 1L;
    private static final long TEST_ROOM_ID = 1L;
    private static final long TEST_MEMBER_ID = 1L;

    @Test
    public void t1_user_insert() {
        User user = User.builder()
                .username("test1")
                .password("test1_password")
                .email("test1@test.com")
                .nickname("T1")
                .signedTime(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        Assert.assertEquals(TEST_USER_ID, savedUser.getId());
    }
    
    @Test
    public void t2_room_insert() {
        Room room = Room.builder()
                .lastMsg(null)
                .createdTime(LocalDateTime.now())
                .build();

        Room savedRoom = roomRepository.save(room);

        Assert.assertEquals(TEST_ROOM_ID, savedRoom.getId());
    }

    @Test
    public void t3_insert() {
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.builder()
                .roomAlias("test1")
                .user(User.pseudo(TEST_USER_ID))
                .room(Room.pseudo(TEST_ROOM_ID))
                .joinedTime(now)
                .lastEntranceTime(now)
                .build();

        Member savedMember = memberRepository.save(member);
        Assert.assertEquals(TEST_MEMBER_ID, savedMember.getId());
        Assert.assertEquals(TEST_USER_ID, savedMember.getUser().getId());
        Assert.assertEquals(TEST_ROOM_ID, savedMember.getRoom().getId());
        Assert.assertEquals("test1", savedMember.getRoomAlias());
        Assert.assertEquals(now, savedMember.getJoinedTime());
        Assert.assertEquals(now, savedMember.getLastEntranceTime());
    }

    @Test
    public void t4_search_inserted_member() {
        Member member = memberRepository.findById(TEST_MEMBER_ID)
                .orElseThrow(() -> new MemberNotFoundException(TEST_MEMBER_ID));

        Assert.assertEquals(TEST_MEMBER_ID, member.getId());
        Assert.assertEquals(TEST_USER_ID, member.getUser().getId());
        Assert.assertEquals(TEST_ROOM_ID, member.getRoom().getId());
        Assert.assertEquals("test1", member.getRoomAlias());
    }

    @Test
    public void t5_update() {
        Member member = memberRepository.findById(TEST_MEMBER_ID)
                .orElseThrow(() -> new MemberNotFoundException(TEST_MEMBER_ID));
        LocalDateTime now = LocalDateTime.now();
        
        member.setRoomAlias("update_test");
        member.setLastEntranceTime(now);
        Member savedMember = memberRepository.save(member);

        Assert.assertEquals(TEST_MEMBER_ID, savedMember.getId());
        Assert.assertEquals(TEST_USER_ID, savedMember.getUser().getId());
        Assert.assertEquals(TEST_ROOM_ID, savedMember.getRoom().getId());
        Assert.assertEquals("update_test", savedMember.getRoomAlias());
        Assert.assertEquals(now, savedMember.getLastEntranceTime());
    }

    @Test
    public void t6_search_updated_member() {
        Member member = memberRepository.findById(TEST_MEMBER_ID)
                .orElseThrow(() -> new MemberNotFoundException(TEST_MEMBER_ID));

        Assert.assertEquals(TEST_MEMBER_ID, member.getId());
        Assert.assertEquals(TEST_USER_ID, member.getUser().getId());
        Assert.assertEquals(TEST_ROOM_ID, member.getRoom().getId());
        Assert.assertEquals("update_test", member.getRoomAlias());    
    }

    @Test
    public void t7_delete() {
        memberRepository.deleteById(TEST_MEMBER_ID);
    }

    @Test
    public void t7_search_deleted_member() {
        Assert.assertThrows(
                MemberNotFoundException.class,
                () -> memberRepository.findById(TEST_MEMBER_ID)
                        .orElseThrow(() -> new MemberNotFoundException(TEST_MEMBER_ID))
        );
    }

}