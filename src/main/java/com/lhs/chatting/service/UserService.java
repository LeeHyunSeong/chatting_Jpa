package com.lhs.chatting.service;

import com.lhs.chatting.entity.User;
import com.lhs.chatting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository repository;

	public void registerUser(User user) {
		repository.save(user);
	}

	public void changePassword(User user, String password) {
		User targetUser = repository.getOne(user.getId());
		targetUser.setPassword(password);
	}

	public void changeNickname(User user, String nickname) {
		User targetUser = repository.getOne(user.getId());
		targetUser.setNickname(nickname);
	}

	public void changeProfile(User user, String profile) {
		User targetUser = repository.getOne(user.getId());
		targetUser.setProfileImage(profile);
	}

	public void deleteUser(User user) {
		Long targetUserId = user.getId();
		User targetUser = repository.getOne(targetUserId);
		targetUser.setEmail(null);
		targetUser.setUsername(null);
		targetUser.setPassword(null);
		targetUser.setProfileImage(null);
		targetUser.setNickname("(알수없음)");
		// 채팅방 삭제 후 나가기 메시지
		// 메시지 작성자 (알수없음)으로 바꾸기
	}
}