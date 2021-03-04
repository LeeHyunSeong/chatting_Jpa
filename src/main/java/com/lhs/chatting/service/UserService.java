package com.lhs.chatting.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.Member;
import com.lhs.chatting.entity.Message;
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

	public void registerUser(User user) {
		userRepository.save(user);
	}

	public void changePassword(User user, String password) {
		User targetUser = userRepository.getOne(user.getId());
		targetUser.setPassword(password);
	}

	public void changeNickname(User user, String nickname) {
		User targetUser = userRepository.getOne(user.getId());
		targetUser.setNickname(nickname);
	}

	public void changeProfile(User user, String profile) {
		User targetUser = userRepository.getOne(user.getId());
		targetUser.setProfileImage(profile);
	}

	public void deleteUser(User user) {
		Long targetUserId = user.getId();
		List<Member> members = memberRepository.findAll();
		for (Member member : members) {
			if (member.getUser().getId() == targetUserId) {
				Message message = makeExitMessage(member.getRoom());
				messageRepository.save(message);
				memberRepository.delete(member);
			}
		}
		userRepository.deleteById(targetUserId);
	}

	private Message makeExitMessage(Room room) {
		Message inviteMessage = new Message();
		inviteMessage.setContents("(알수없음)님이 퇴장하였습니다.");
		inviteMessage.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		inviteMessage.setRoom(room);
		inviteMessage.setType("NOTICE");

		return inviteMessage;
	}
}