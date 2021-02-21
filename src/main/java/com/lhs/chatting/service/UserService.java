package com.lhs.chatting.service;

import com.lhs.chatting.model.User;
import com.lhs.chatting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public void registerUser(User user) {
        repository.save(user);
    }
}
