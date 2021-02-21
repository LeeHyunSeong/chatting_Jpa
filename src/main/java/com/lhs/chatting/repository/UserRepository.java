package com.lhs.chatting.repository;

import com.lhs.chatting.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<String, User> userMap = new HashMap<>();

    public User save(User user) {
        userMap.put(user.getSessionId(), user);
        return user;
    }

    public User findBySessionId(String sessionId) {
        return userMap.get(sessionId);
    }

    public void deleteBySessionId(String sessionId) {
        userMap.remove(sessionId);
    }
}
