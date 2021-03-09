package com.lhs.chatting.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.entity.Message;
import com.lhs.chatting.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageApiController {
    private final MessageService messageService;

    @PostMapping(path = "{content}/rooms/{roomId}")
    public List<Message> searchMessage(@PathVariable String content, @PathVariable Long roomId) {
        return messageService.searchMessage(content, roomId);
    }
}
