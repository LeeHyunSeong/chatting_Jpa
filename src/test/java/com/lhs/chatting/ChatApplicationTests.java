package com.lhs.chatting;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhs.chatting.controller.UserApiController;
import com.lhs.chatting.model.RegisterUserRequest;
import com.lhs.chatting.repository.UserRepository;
import com.lhs.chatting.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserApiController.class)
class ChatApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private UserRepository userRepository;
    
//    @Test
    public void 유저_저장() throws Exception {
        String email = "eldkf4006@naver.com";
        String password = "@@math2230";
        String username = "Lee-HyunSeong";
        String nickname = "hs._0118";
        RegisterUserRequest userRequest = RegisterUserRequest.builder()
                .email(email)
                .password(password)
                .username(username)
                .nickname(nickname)
                .build();
        String content = objectMapper.writeValueAsString(userRequest);
        mockMvc.perform(post("/api/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
    }
    
    //@Test
    public void 유저_불러오기() throws Exception {
        String name = "Hong-gildong";
        mockMvc.perform(get("/api/users/{userName}", name)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
    }
}
