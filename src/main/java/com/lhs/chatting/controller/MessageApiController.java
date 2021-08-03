package com.lhs.chatting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.model.ChatMessages;
import com.lhs.chatting.model.GetMessagesResponse;
import com.lhs.chatting.model.MakeMessageRequest;
import com.lhs.chatting.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageApiController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Boolean> makeMessage(@RequestParam MakeMessageRequest request) {
        boolean success = messageService.makeMessage(request.getRoomId(), request.getUserId(), request.getContents());
        return ResponseEntity.ok(success);
    }
    
    @GetMapping
    public ResponseEntity<GetMessagesResponse> getMessages(@RequestParam Long roomId, @RequestParam int displayedMsgNum) {
        ChatMessages chatMessages = messageService.getMessages(roomId, displayedMsgNum);
        GetMessagesResponse response = GetMessagesResponse.builder()
                .displayedMsgNum(chatMessages.getDisplayedMsgNum())
                .messages(chatMessages.getMessages())
                .build();
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Boolean> deleteMessage(@PathVariable Long messageId) {
        boolean success = messageService.deleteMessage(messageId);
        return ResponseEntity.ok(success);
    }
}
