package com.lhs.chatting.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping(path = "/search")
    public void searchMessage(@RequestBody Map<String, Object> textMap) {
        String contents = String.valueOf(textMap.get("text"));
        messageService.searchMessage(contents);
    }
}
