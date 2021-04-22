package com.lhs.chatting.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lhs.chatting.exception.NotExistedUserException;
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
                .orElseThrow(() -> new NotExistedUserException(HttpStatus.NOT_FOUND, "Not found user"));;

        return targetUser.getId();
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotExistedUserException(HttpStatus.NOT_FOUND, "Not found user"));
    }

    // 유저의 여러 정보를 바꾸지 못하는 비효율적인 코드 (한 번에 하나의 정보만 변경이 가능하다.)
    public boolean changeUserInfo(Long userId, ChangeUserInfoRequest request) {
        User user = getUserByUserId(userId);
        user.changeWith(request);
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return true;
    }
}