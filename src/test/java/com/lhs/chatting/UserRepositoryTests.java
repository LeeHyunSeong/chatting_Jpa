package com.lhs.chatting;

import com.lhs.chatting.exception.UserNotFoundException;
import com.lhs.chatting.model.entity.User;
import com.lhs.chatting.repository.UserRepository;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private static final long TEST_USER_ID = 1L;

    @Test
    public void t1_insert() {
        User user = User.builder()
                .username("eraise")
                .password("5070")
                .email("lhs980118@naver.com")
                .nickname("HS")
                .signedTime(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        Assert.assertEquals(1L, savedUser.getId());
        Assert.assertEquals("eraise", savedUser.getUsername());
        Assert.assertEquals("5070", savedUser.getPassword());
        Assert.assertEquals("lhs980118@naver.com", savedUser.getEmail());
        Assert.assertEquals("HS", savedUser.getNickname());
    }

    @Test
    public void t2_search_inserted_user() {
        User user = userRepository.findById(TEST_USER_ID)
                .orElseThrow(() -> new UserNotFoundException(TEST_USER_ID));

        Assert.assertEquals("eraise", user.getUsername());
        Assert.assertEquals("5070", user.getPassword());
        Assert.assertEquals("lhs980118@naver.com", user.getEmail());
        Assert.assertEquals("HS", user.getNickname());
    }

    @Test
    public void t3_update() {
        User user = userRepository.findById(TEST_USER_ID)
                .orElseThrow(() -> new UserNotFoundException(TEST_USER_ID));

        user.setNickname("HS._l18");
        User savedUser = userRepository.save(user);

        Assert.assertEquals("eraise", savedUser.getUsername());
        Assert.assertEquals("5070", savedUser.getPassword());
        Assert.assertEquals("lhs980118@naver.com", savedUser.getEmail());
        Assert.assertEquals("HS._l18", savedUser.getNickname());
    }


    @Test
    public void t4_search_updated_user() {
        User user = userRepository.findById(TEST_USER_ID)
                .orElseThrow(() -> new UserNotFoundException(TEST_USER_ID));

        Assert.assertEquals("eraise", user.getUsername());
        Assert.assertEquals("5070", user.getPassword());
        Assert.assertEquals("lhs980118@naver.com", user.getEmail());
        Assert.assertEquals("HS._l18", user.getNickname());
    }

    @Test
    public void t5_delete() {
        userRepository.deleteById(TEST_USER_ID);
    }

    @Test
    public void t6_search_deleted_user() {
        Assert.assertThrows(
                UserNotFoundException.class,
                () -> userRepository.findById(TEST_USER_ID)
                        .orElseThrow(() -> new UserNotFoundException(TEST_USER_ID))
        );
    }

}