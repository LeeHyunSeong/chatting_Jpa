package com.lhs.chatting.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lhs.chatting.util.JsonUtils;

@Component
public class SocketHandler extends TextWebSocketHandler {

    private final Map<Integer, List<WebSocketSession>> roomSessionsMap = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String messagePayload = message.getPayload();
        Map<String, String> payloadMap = JsonUtils.readValue(messagePayload, new TypeReference<Map<String, String>>() {
        }).orElseThrow(() -> new RuntimeException("Can not parse json to room"));

        int roomNumber = Integer.parseInt(payloadMap.get("roomNumber"));

        JSONObject roomSessionJsonObject = new JSONObject();
    	for(Map.Entry<String, String> entry : payloadMap.entrySet())
    		roomSessionJsonObject.put(entry.getKey(), payloadMap.get(entry.getKey()));
    	
        List<WebSocketSession> roomSessions = roomSessionsMap.get(roomNumber);
        for (WebSocketSession roomSession : roomSessions) {
        	roomSession.sendMessage(new TextMessage(roomSessionJsonObject.toString()));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        int sessionRoomNumber = getSessionRoomNumber(session);
        if (isRoomSessionExist(sessionRoomNumber)) {
            List<WebSocketSession> sessions = roomSessionsMap.get(sessionRoomNumber);
            sessions.add(session);
        } else {
            List<WebSocketSession> sessions = new ArrayList<>();
            sessions.add(session);
            roomSessionsMap.put(sessionRoomNumber, sessions);
        }

        session.sendMessage(new TextMessage(getSessionIdMessagePayload(session)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        roomSessionsMap.values()
                .forEach(
                        // TODO :  session equals method 확인 (같은 session이 삭제되는지 debugging 필요)
                        sessions -> sessions.remove(session)
                );
        super.afterConnectionClosed(session, status);
    }

    private int getSessionRoomNumber(WebSocketSession session) {
        String uriStr = String.valueOf(session.getUri());
        int lastPathIndex = uriStr.lastIndexOf("/");
        if (lastPathIndex < 0) {
            throw new RuntimeException("Can not find room number of session");
        }
        return Integer.parseInt(uriStr.substring(lastPathIndex + 1));
    }

    private boolean isRoomSessionExist(int roomNumber) {
        return roomSessionsMap.containsKey(roomNumber);
    }

    private String getSessionIdMessagePayload(WebSocketSession session) {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("type", "getId");
        payloadMap.put("sessionId", session.getId());
        return JsonUtils.writeValue(payloadMap);
    }
}