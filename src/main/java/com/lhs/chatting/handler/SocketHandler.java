package com.lhs.chatting.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lhs.chatting.service.WebSocketService;
import com.lhs.chatting.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SocketHandler extends TextWebSocketHandler {
    private final WebSocketService webSocketService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        Map<String, String> payloadMap = JsonUtils.readValue(message.getPayload(), new TypeReference<Map<String, String>>() {
        }).orElseThrow(() -> new RuntimeException("Fail to parse json to map"));
        int roomNumber = Integer.parseInt(payloadMap.get("roomNumber"));
        String contents = payloadMap.get("payload");

        webSocketService.sendMessage(roomNumber, contents);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        webSocketService.connectToRoom(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketService.removeRoomSession(session);
        super.afterConnectionClosed(session, status);
    }
}