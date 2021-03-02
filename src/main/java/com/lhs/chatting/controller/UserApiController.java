package com.lhs.chatting.controller;

import com.lhs.chatting.entity.User;
import com.lhs.chatting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping
    public void registerUser(@RequestBody User user) {
        userService.registerUser(user);
    }
}
