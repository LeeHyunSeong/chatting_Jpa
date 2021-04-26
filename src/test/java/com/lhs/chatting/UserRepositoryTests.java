package com.lhs.chatting;

import java.util.Optional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lhs.chatting.model.entity.User;
import com.lhs.chatting.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    //    @Test
    public void insert() {
        User user = User.builder()
                .username("LHS")
                .password("@@math2230")
                .email("eldkf4006@naver.com")
                .nickname("Lee")
                .build();
        userRepository.save(user);
    }

    //    @Test
    public void search() {
        Optional<User> user = userRepository.findById(6L);
        user.ifPresent(selectedUser -> System.out.println(selectedUser.getUsername()));
    }

    //    @Test
    public void delete() {
        userRepository.deleteById(6L);
    }

    //    @Test
    public void update() {
        Optional<User> user = userRepository.findById(8L);
        user.ifPresent(selectedUser -> {
            selectedUser.setNickname("HS._l18");
            userRepository.save(selectedUser);
        });
    }
}