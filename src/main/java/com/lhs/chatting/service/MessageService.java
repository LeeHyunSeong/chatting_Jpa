package com.lhs.chatting.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lhs.chatting.model.ChatMessage;
import com.lhs.chatting.model.entity.Message;
import com.lhs.chatting.model.type.MessageType;
import com.lhs.chatting.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;

    public Boolean makeMessage(Long roomId, Long userId, String contents) {
        Message message = Message.of(roomId, userId, contents, MessageType.MESSAGE);
        repository.save(message);
        return true;
    }

    public List<ChatMessage> getMessages(Long roomId, int currentNum) {
        Page<ChatMessage> messagePage = repository.findChatMessageByRoomId(roomId, PageRequest.of(currentNum/10, 10));
        List<ChatMessage> messages = messagePage.getContent();
        return messages;
    }

    public Boolean deleteMessage(Long messageId) {
        repository.deleteById(messageId);
        return true;
    }
}
