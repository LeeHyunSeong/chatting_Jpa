package com.lhs.chatting.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.Member;
import com.lhs.chatting.entity.Message;
import com.lhs.chatting.entity.MessageNoticeType;
import com.lhs.chatting.entity.Room;
import com.lhs.chatting.entity.User;
import com.lhs.chatting.repository.MemberRepository;
import com.lhs.chatting.repository.MessageRepository;
import com.lhs.chatting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MessageRepository messageRepository;

	public void registerUser(String email, String password, String username, String nickname) {
		User user = User.of(email, password, username, nickname);
		userRepository.save(user);
	}

	public void changePassword(Long userId, String password) {
		User targetUser = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Can not found User entity"));
		targetUser.setPassword(password);
		userRepository.save(targetUser);
	}

	public void changeNickname(Long userId, String nickname) {
		User targetUser = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Can not found User entity"));
		targetUser.setNickname(nickname);
		userRepository.save(targetUser);
	}

	public void changeProfile(Long userId, String profile) {
		User targetUser = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Can not found User entity"));
		targetUser.setProfileImage(profile);
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
		Message inviteMessage = Message.of(contents, MessageNoticeType.NOTICE, roomId, null);

		return inviteMessage;
	}
}