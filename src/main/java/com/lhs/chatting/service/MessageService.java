package com.lhs.chatting.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lhs.chatting.model.entity.Message;
import com.lhs.chatting.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public List<Message> searchMessageByText(Long roomId, String content) {
        List<Message> messages = messageRepository.findAllByRoomIdAndContent(roomId, content);
        return messages;
    }
}
