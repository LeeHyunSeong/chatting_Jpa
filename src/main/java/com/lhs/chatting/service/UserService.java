package com.lhs.chatting.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.Member;
import com.lhs.chatting.entity.Message;
import com.lhs.chatting.entity.MessageType;
import com.lhs.chatting.entity.Room;
import com.lhs.chatting.entity.User;
import com.lhs.chatting.repository.MemberRepository;
import com.lhs.chatting.repository.MessageRepository;
import com.lhs.chatting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;

    public void registerUser(String username, String password, String email, String nickname) {
        User user = User.of(username, password, email, nickname);
        userRepository.save(user);
    }

    public void changeUserInfo(Long userId, String nickname, String password, String profileImage) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Can not found User entity"));
        if (nickname != null)
            targetUser.setNickname(nickname);
        if (password != null)
            targetUser.setPassword(password);
        if (profileImage != null)
            targetUser.setProfileImage(profileImage);
        userRepository.save(targetUser);
    }

    public void deleteUser(Long userId) {
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            if (member.getUser().getId() == userId) {
                Message message = makeExitMessage(member.getRoom().getId());
                messageRepository.save(message);
                memberRepository.delete(member);
            }
        }
        userRepository.deleteById(userId);
    }

    private Message makeExitMessage(Long roomId) {
        String contents = "(알수없음)님이 퇴장하였습니다.";
        Message inviteMessage = Message.of(roomId, null, contents, MessageType.NOTICE);

        return inviteMessage;
    }
}