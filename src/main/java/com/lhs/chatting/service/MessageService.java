package com.lhs.chatting.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lhs.chatting.model.entity.Message;
import com.lhs.chatting.model.type.MessageType;
import com.lhs.chatting.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;
    
    private static final Integer DEFAULT_MSG_COUNT_OF_PAGE = 10;

    public Boolean makeMessage(Long roomId, Long userId, String contents) {
        Message message = Message.chatMessageOf(roomId, userId, contents);
        repository.save(message);
        return true;
    }

    public List<Message> getMessages(Long roomId, int displayedMsgNum) {
        Page<Message> messagePage = repository.findByRoomIdAndMsgTypeOrderByCreatedTimeDesc(roomId, MessageType.MESSAGE, PageRequest.of(displayedMsgNum/DEFAULT_MSG_COUNT_OF_PAGE, DEFAULT_MSG_COUNT_OF_PAGE));
        List<Message> messages = messagePage.getContent();

        return messages;
    }

    public Boolean deleteMessage(Long messageId) {
        repository.deleteById(messageId);
        return true;
    }
}