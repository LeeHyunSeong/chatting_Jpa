package com.lhs.chatting.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.User;
import com.lhs.chatting.entity.UserInfoType;
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
        Optional<User> optionalUser = userRepository.findByEmail(email);
        Long targetUserId;
        if(optionalUser.isPresent()) {
            targetUserId = optionalUser.get().getId();
        }
        else {
            targetUserId = -1L;
        }
        
        return targetUserId;
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