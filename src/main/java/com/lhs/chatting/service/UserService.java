package com.lhs.chatting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.User;
import com.lhs.chatting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void registerUser(String username, String password, String email, String nickname) {
        User user = User.of(username, password, email, nickname);
        userRepository.save(user);
    }

    public void changeUserInfo(Long userId, String nickname, String password, String profileImage) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Can not found User entity"));
        if (nickname != null)
            targetUser.setNickname(nickname);
        if (password != null)
            targetUser.setPassword(password);
        if (profileImage != null)
            targetUser.setProfileImage(profileImage);
        userRepository.save(targetUser);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
    
    public User getUserByUserName(String userName) {
        List<User> users = userRepository.findAll();
        User targetUser = null;
        for (User user : users) {
            if (user.getUsername() == userName) {
                targetUser = user;
                break;
            }
        }
        return targetUser;
    }

}