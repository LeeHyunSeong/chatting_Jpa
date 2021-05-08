package com.lhs.chatting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lhs.chatting.model.ChangeUserInfoRequest;
import com.lhs.chatting.model.RegisterUserRequest;
import com.lhs.chatting.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserService userService;

//    @Test
    public void registerUser() {
        String email = "eldkf4006@naver.com";
        String password = "@@math2230";
        String username = "LeeHS";
        String nickname = "hs._0118";
        RegisterUserRequest request = RegisterUserRequest.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();

        userService.registerUser(request);
    }

//    @Test
    public void changeUserInfo() {
        String nickname = "LeeHS";
        ChangeUserInfoRequest request = ChangeUserInfoRequest.builder()
                .nickname(nickname)
                .build();

        userService.changeUserInfo(44L, request);
    }

//    @Test
    public void getUserId() {
        assertEquals(userService.getUserIdByEmail("eldkf4006@naver.com"), 44L);
    }

//    @Test
    public void getUser() {
        assertEquals(userService.getUserByUserId(44L).getPassword(), "@@math2230");
    }

//    @Test
    public void deleteUser() {
        userService.deleteUser(44L);
    }
}