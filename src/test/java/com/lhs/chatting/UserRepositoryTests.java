package com.lhs.chatting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lhs.chatting.entity.User;
import com.lhs.chatting.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    //@Test
    public void insert() {
//        User user = User.of("Lee", "@@math2230", "eldkf4006@naver.com", "Lee");
//        Long userId = user.getId();
//        userRepository.save(user);
    }

    @Test
    public void delete() {
        userRepository.deleteAll();
    }
}