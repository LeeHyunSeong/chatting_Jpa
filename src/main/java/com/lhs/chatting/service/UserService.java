package com.lhs.chatting.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.User;
import com.lhs.chatting.entity.UserInfoType;
import com.lhs.chatting.exception.NotExistedUserException;
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

    public Long getUserIdByEmail(String email) {
        User targetUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotExistedUserException());;
        
        return targetUser.getId();
    }
    
    public User getUserByUserId(Long userId) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotExistedUserException());
        return targetUser;
    }
    
    public void changeUserInfo(Long userId, UserInfoType userInfoType, String contents) {
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(targetUser -> {
            switch(userInfoType) {
                case NICKNAME :
                    targetUser.setNickname(contents);
                    break;
                case PASSWORD :
                    targetUser.setPassword(contents);
                    break;
                case PROFILEIMAGE :
                    targetUser.setProfileImage(contents);
                    break;
                default :
            }
            userRepository.save(targetUser);
        });
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}