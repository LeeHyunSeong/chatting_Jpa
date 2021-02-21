package com.lhs.chatting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoomSessionRepository {
    private final Map<Integer, List<WebSocketSession>> roomSessionsMap = new HashMap<>();

    public List<WebSocketSession> findAllByRoomNumber(int roomNumber) {
        return roomSessionsMap.get(roomNumber);
    }

    public void save(WebSocketSession session) {
        int sessionRoomNumber = getSessionRoomNumber(session);

        if (isRoomSessionExist(sessionRoomNumber)) {
            List<WebSocketSession> sessions = roomSessionsMap.get(sessionRoomNumber);
            sessions.add(session);
        } else {
            List<WebSocketSession> sessions = new ArrayList<>();
            sessions.add(session);
            roomSessionsMap.put(sessionRoomNumber, sessions);
        }
    }

    public void delete(WebSocketSession session) {
        int sessionRoomNumber = getSessionRoomNumber(session);
        if (isRoomSessionExist(sessionRoomNumber)) {
            List<WebSocketSession> sessions = roomSessionsMap.get(sessionRoomNumber);
            // TODO :  session equals method 확인 (같은 session이 삭제되는지 debugging 필요)
            sessions.remove(session);
        }
    }

    private boolean isRoomSessionExist(int roomNumber) {
        return roomSessionsMap.containsKey(roomNumber);
    }

    private int getSessionRoomNumber(WebSocketSession session) {
        String uriStr = String.valueOf(session.getUri());
        int lastPathIndex = uriStr.lastIndexOf("/");
        if (lastPathIndex < 0) {
            throw new RuntimeException("Can not find room number of session");
        }
        return Integer.parseInt(uriStr.substring(lastPathIndex + 1));
    }
}
