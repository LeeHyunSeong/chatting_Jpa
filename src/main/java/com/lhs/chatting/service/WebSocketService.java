package com.lhs.chatting.service;

import com.lhs.chatting.repository.RoomSessionRepository;
import com.lhs.chatting.repository.UserRepository;
import com.lhs.chatting.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final RoomSessionRepository roomSessionRepository;

    public void sendMessage(int roomNumber, String contents) {
        List<WebSocketSession> roomSessions = roomSessionRepository.findAllByRoomNumber(roomNumber);
        for (WebSocketSession roomSession : roomSessions) {
            sendMessage(roomSession, contents);
        }
    }

    public void connectToRoom(WebSocketSession session) {
        roomSessionRepository.save(session);
        sendMessage(session, generateConnectMessage(session));
    }

    public void removeRoomSession(WebSocketSession session) {
        roomSessionRepository.delete(session);
    }

    private void sendMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.error("Fail to send message to session ({},{})", session, message);
        }
    }

    private String generateConnectMessage(WebSocketSession session) {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("type", "session");
        payloadMap.put("sessionId", session.getId());
        return JsonUtils.writeValue(payloadMap);
    }

    private String generateChatMessage(WebSocketSession session, String contents) {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("type", "message");
        payloadMap.put("sessionId", session.getId());
        payloadMap.put("contents", contents);
        return JsonUtils.writeValue(payloadMap);
    }
}
