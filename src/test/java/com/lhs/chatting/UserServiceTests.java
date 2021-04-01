package com.lhs.chatting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lhs.chatting.entity.UserInfoType;
import com.lhs.chatting.model.ChangeUserInformationRequest;
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
        
        userService.registerUser(request.getUsername(), request.getPassword(), request.getEmail(), request.getNickname());
    }
    
//    @Test
    public void changeUserInfo() {
        UserInfoType userInfoType = UserInfoType.NICKNAME;
        String contents = "LeeHS";
        ChangeUserInformationRequest informationRequest = ChangeUserInformationRequest.builder()
                .userInfoType(userInfoType)
                .contents(contents)
                .build();

        userService.changeUserInfo(15L, informationRequest.getUserInfoType(), informationRequest.getContents());
    }

//    @Test
    public void getUserId() {
        assertEquals(userService.getUserIdByEmail("eldkf4006@naver.com"), 15L);
    }

    @Test   
    public void deleteUser() {
        userService.deleteUser(15L);
    }
}