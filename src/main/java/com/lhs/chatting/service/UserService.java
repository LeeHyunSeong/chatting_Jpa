package com.lhs.chatting.service;

import org.springframework.stereotype.Service;

import com.lhs.chatting.exception.UserNotFoundException;
import com.lhs.chatting.model.ChangeUserInfoRequest;
import com.lhs.chatting.model.RegisterUserRequest;
import com.lhs.chatting.model.entity.User;
import com.lhs.chatting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean registerUser(RegisterUserRequest request) {
        User user = User.of(request);
        userRepository.save(user);
        return true;
    }

    public Long getUserIdByEmail(String email) {
        User targetUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return targetUser.getId();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public boolean changeUserInfo(Long userId, ChangeUserInfoRequest request) {
        User user = getUser(userId);
        user.changeWith(request);
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return true;
    }
}