package com.lhs.chatting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoomSessionRepository {
    private final Map<Long, List<WebSocketSession>> roomSessionsMap = new HashMap<>();

    public List<WebSocketSession> findAllByRoomId(Long roomId) {
        return roomSessionsMap.get(roomId);
    }

    public void save(WebSocketSession session) {
        Long sessionRoomId = getSessionRoomId(session);

        if (isRoomSessionExist(sessionRoomId)) {
            List<WebSocketSession> sessions = roomSessionsMap.get(sessionRoomId);
            sessions.add(session);
        } else {
            List<WebSocketSession> sessions = new ArrayList<>();
            sessions.add(session);
            roomSessionsMap.put(sessionRoomId, sessions);
        }
    }

    public void delete(WebSocketSession session) {
        Long sessionRoomId = getSessionRoomId(session);
        if (isRoomSessionExist(sessionRoomId)) {
            List<WebSocketSession> sessions = roomSessionsMap.get(sessionRoomId);
            sessions.remove(session);
        }
    }

    private boolean isRoomSessionExist(Long roomId) {
        return roomSessionsMap.containsKey(roomId);
    }

    private Long getSessionRoomId(WebSocketSession session) {
        String uriStr = String.valueOf(session.getUri());
        int lastPathIndex = uriStr.lastIndexOf("/");
        if (lastPathIndex < 0) {
            throw new RuntimeException("Can not find room number of session");
        }
        return Long.parseLong(uriStr.substring(lastPathIndex + 1));
    }
}