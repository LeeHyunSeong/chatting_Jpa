package com.lhs.chatting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.Message;
import com.lhs.chatting.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public void createMessage(Message message) {
        messageRepository.save(message);
        // type 별로 세분화하기
    }

    public void searchMessage(String contents) {
        // 내용으로 검색
    }
}
