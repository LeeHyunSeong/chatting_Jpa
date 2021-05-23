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

import com.lhs.chatting.exception.MessageNotFoundException;
import com.lhs.chatting.exception.RoomNotFoundException;
import com.lhs.chatting.model.entity.Message;
import com.lhs.chatting.model.entity.Room;
import com.lhs.chatting.model.entity.User;
import com.lhs.chatting.model.type.MessageType;
import com.lhs.chatting.repository.MessageRepository;
import com.lhs.chatting.repository.RoomRepository;
import com.lhs.chatting.repository.UserRepository;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class RoomRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private RoomRepository roomRepository;

    private static final long TEST_MESSAGE_ID = 1L;
    private static final long TEST_ROOM_ID = 1L;
    private static final long TEST_USER_ID = 1L;

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
    public void t2_insert() {
        LocalDateTime now = LocalDateTime.now();
        Room room = Room.builder()
                .lastMsg(null)
                .createdTime(now)
                .build();

        Room savedRoom = roomRepository.save(room);
        Assert.assertEquals(TEST_ROOM_ID, savedRoom.getId());
        Assert.assertEquals(now, savedRoom.getCreatedTime());
    }

    @Test
    public void t3_search_inserted_room() {
        Room room = roomRepository.findById(TEST_ROOM_ID)
                .orElseThrow(() -> new RoomNotFoundException(TEST_ROOM_ID));

        Assert.assertEquals(TEST_ROOM_ID, room.getId());
    }

    @Test
    public void t4_message_insert() {
        Message message = Message.builder()
                .room(Room.pseudo(TEST_ROOM_ID))
                .user(User.pseudo(TEST_USER_ID))
                .contents("test_message")
                .msgType(MessageType.MESSAGE)
                .createdTime(LocalDateTime.now())
                .build();

        Message savedMessage = messageRepository.save(message);
        Assert.assertEquals(TEST_MESSAGE_ID, savedMessage.getId());
        Assert.assertEquals(TEST_ROOM_ID, savedMessage.getUser().getId());
        Assert.assertEquals(TEST_USER_ID, savedMessage.getUser().getId());
        Assert.assertEquals("test_message", savedMessage.getContents());
        Assert.assertEquals(MessageType.MESSAGE, savedMessage.getMsgType());
    }
    
    @Test
    public void t5_update() {
        Room room = roomRepository.findById(TEST_ROOM_ID)
                .orElseThrow(() -> new RoomNotFoundException(TEST_ROOM_ID));
        room.setLastMsg(Message.pseudo(TEST_MESSAGE_ID));
        
        Room savedRoom = roomRepository.save(room);

        Assert.assertEquals(TEST_ROOM_ID, savedRoom.getId());
        Assert.assertEquals(TEST_MESSAGE_ID, savedRoom.getLastMsg().getId());
    }

    @Test
    public void t6_search_updated_room() {
        Room room = roomRepository.findById(TEST_ROOM_ID)
                .orElseThrow(() -> new RoomNotFoundException(TEST_ROOM_ID));

        Assert.assertEquals(TEST_ROOM_ID, room.getId());
        Assert.assertEquals(TEST_MESSAGE_ID, room.getLastMsg().getId());
    }
    
    @Test
    public void t7_delete() {
        roomRepository.deleteById(TEST_ROOM_ID);
    }

    @Test
    public void t8_search_deleted_room() {
        Assert.assertThrows(
                RoomNotFoundException.class,
                () -> roomRepository.findById(TEST_ROOM_ID)
                        .orElseThrow(() -> new RoomNotFoundException(TEST_ROOM_ID))
        );
    }

}