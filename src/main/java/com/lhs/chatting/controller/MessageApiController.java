package com.lhs.chatting.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.model.GetTargetMessageResponse;
import com.lhs.chatting.model.entity.Message;
import com.lhs.chatting.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageApiController {
    private final MessageService messageService;

    @GetMapping("{content}/rooms/{roomId}")
    public ResponseEntity<String> searchMessageByText(@PathVariable Long roomId, @PathVariable String content) {
        List<Message> messages = messageService.searchMessageByText(roomId, content);
        GetTargetMessageResponse response = GetTargetMessageResponse.builder()
                .messages(messages)
                .build();
        return ResponseEntity.ok(response.toString());
    }
}
