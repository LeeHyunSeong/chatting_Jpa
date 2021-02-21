package com.lhs.chatting.service;

import com.lhs.chatting.model.User;
import com.lhs.chatting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository repository;

    public void registerUser(WebSocketSession session, String userName) {
        User user = User.builder()
                .sessionId(session.getId())
                .name(userName)
                .build();
        repository.save(user);
    }

    public User getUser(WebSocketSession session) {
        return repository.findBySessionId(session.getId());
    }
}
