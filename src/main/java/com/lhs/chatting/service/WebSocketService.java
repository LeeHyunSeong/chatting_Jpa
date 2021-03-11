package com.lhs.chatting.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.lhs.chatting.entity.Message;
import com.lhs.chatting.entity.MessageType;
import com.lhs.chatting.entity.Room;
import com.lhs.chatting.repository.MemberRepository;
import com.lhs.chatting.repository.MessageRepository;
import com.lhs.chatting.repository.RoomRepository;
import com.lhs.chatting.repository.RoomSessionRepository;
import com.lhs.chatting.repository.UserRepository;
import com.lhs.chatting.util.JsonUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final RoomSessionRepository roomSessionRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;

    public void sendMessage(WebSocketSession session, Long roomId, Long userId, String contents) {
        List<WebSocketSession> roomSessions = roomSessionRepository.findAllByRoomId(roomId);
        Message message = generateMessage(roomId, userId, contents);
        messageRepository.save(message);
        Room targetRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Can not found Room entity"));
        targetRoom.setLastMsgId(message);
        roomRepository.save(targetRoom);
        for (WebSocketSession roomSession : roomSessions) {
            sendMessage(roomSession, mappingChatMessage(session, message));
        }
    }

    public void connectToRoom(WebSocketSession session) {
        roomSessionRepository.save(session);
    }

    public void closeRoom(WebSocketSession session) {
        roomSessionRepository.delete(session);
    }

    private void sendMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.error("Fail to send message to session ({},{})", session, message);
        }
    }

    private String mappingChatMessage(WebSocketSession session, Message message) {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("type", "message");
        payloadMap.put("messageId", message.getId());
        payloadMap.put("username", message.getUser().getNickname());
        payloadMap.put("contents", message.getContents());
        payloadMap.put("createdtime", message.getCreatedTime());
        return JsonUtils.writeValue(payloadMap);
    }

    private Message generateMessage(Long roomId, Long userId, String contents) {
        Message message = Message.of(roomId, userId, contents, MessageType.MESSAGE);

        return message;
    }
}