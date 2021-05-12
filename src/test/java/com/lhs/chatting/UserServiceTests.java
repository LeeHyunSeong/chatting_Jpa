package com.lhs.chatting;

import com.lhs.chatting.model.ChangeUserInfoRequest;
import com.lhs.chatting.model.RegisterUserRequest;
import com.lhs.chatting.model.entity.User;
import com.lhs.chatting.service.UserService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    private static final long TEST_USER_ID = 1L;

    @Test
    public void t1_registerUser() {
        String username = "LeeHS";
        String password = "@@math2230";
        String email = "eldkf4006@naver.com";
        String nickname = "hs._0118";
        RegisterUserRequest request = RegisterUserRequest.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();

        boolean success = userService.registerUser(request);
        Assert.assertTrue(success);
    }

    @Test
    public void t2_changeUserInfo() {
        String nickname = "LeeHS";
        ChangeUserInfoRequest request = ChangeUserInfoRequest.builder()
                .nickname(nickname)
                .build();

        boolean success = userService.changeUserInfo(TEST_USER_ID, request);
        Assert.assertTrue(success);
    }

    @Test
    public void t3_getUserId() {
        long userId = userService.getUserIdByEmail("eldkf4006@naver.com");
        Assert.assertEquals(TEST_USER_ID, userId);
    }

    @Test
    public void t4_getUser() {
        User user = userService.getUser(TEST_USER_ID);

        Assert.assertEquals("LeeHS", user.getUsername());
        Assert.assertEquals("@@math2230", user.getPassword());
        Assert.assertEquals("eldkf4006@naver.com", user.getEmail());
        Assert.assertEquals("LeeHS", user.getNickname());
    }

    @Test
    public void t5_deleteUser() {
        boolean success = userService.deleteUser(TEST_USER_ID);
        Assert.assertTrue(success);
    }
}