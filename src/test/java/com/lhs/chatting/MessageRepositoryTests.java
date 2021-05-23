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

import com.lhs.chatting.exception.FriendNotFoundException;
import com.lhs.chatting.exception.MessageNotFoundException;
import com.lhs.chatting.model.entity.Friend;
import com.lhs.chatting.model.entity.Message;
import com.lhs.chatting.model.entity.Room;
import com.lhs.chatting.model.entity.User;
import com.lhs.chatting.model.type.FriendRelationType;
import com.lhs.chatting.model.type.MessageType;
import com.lhs.chatting.repository.MessageRepository;
import com.lhs.chatting.repository.RoomRepository;
import com.lhs.chatting.repository.UserRepository;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class MessageRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private MessageRepository messageRepository;

    private static final long TEST_USER_ID = 1L;
    private static final long TEST_ROOM_ID = 1L;
    private static final long TEST_MESSAGE_ID = 1L;

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
        Message message = Message.builder()
                .room(Room.pseudo(TEST_ROOM_ID))
                .user(User.pseudo(TEST_USER_ID))
                .contents("test")
                .msgType(MessageType.MESSAGE)
                .createdTime(LocalDateTime.now())
                .build();

        Message savedMessage = messageRepository.save(message);
        Assert.assertEquals(TEST_MESSAGE_ID, savedMessage.getId());
        Assert.assertEquals(TEST_ROOM_ID, savedMessage.getRoom().getId());
        Assert.assertEquals(TEST_USER_ID, savedMessage.getUser().getId());
        Assert.assertEquals(MessageType.MESSAGE, savedMessage.getMsgType());
    }

    @Test
    public void t4_search_inserted_message() {
        Message message = messageRepository.findById(TEST_MESSAGE_ID)
                .orElseThrow(() -> new MessageNotFoundException(TEST_MESSAGE_ID));

        Assert.assertEquals(TEST_MESSAGE_ID, message.getId());
        Assert.assertEquals(TEST_ROOM_ID, message.getRoom().getId());
        Assert.assertEquals(TEST_USER_ID, message.getUser().getId());
        Assert.assertEquals(MessageType.MESSAGE, message.getMsgType());
    }

    @Test
    public void t5_delete() {
        messageRepository.deleteById(TEST_MESSAGE_ID);
    }

    @Test
    public void t6_search_deleted_message() {
        Assert.assertThrows(
                MessageNotFoundException.class,
                () -> messageRepository.findById(TEST_MESSAGE_ID)
                        .orElseThrow(() -> new MessageNotFoundException(TEST_MESSAGE_ID))
        );
    }

}