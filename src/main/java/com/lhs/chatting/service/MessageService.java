package com.lhs.chatting.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.Message;
import com.lhs.chatting.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public List<Message> searchMessageByText(Long roomId, String content) {
        List<Message> targetMessages = messageRepository.findAllByRoomId(roomId);
        List<Message> resultMessages = new ArrayList<>();

        for (Message message : targetMessages)
            if (message.getContents().contains(content))
                resultMessages.add(message);

        return resultMessages;
    }
}
