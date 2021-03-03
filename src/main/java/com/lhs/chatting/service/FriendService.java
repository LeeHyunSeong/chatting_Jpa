package com.lhs.chatting.service;

import org.springframework.stereotype.Service;

import com.lhs.chatting.entity.Friend;
import com.lhs.chatting.entity.User;
import com.lhs.chatting.repository.FriendRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {
	private final FriendRepository repository;

	public void registerFriend(User selfUser, User friendUser) {
		Friend friend = new Friend(selfUser, friendUser);
		repository.save(friend);
	}

	public void changeRelation(Friend friend, String relation) {
		Friend exisiting = repository.getOne(friend.getId());
		exisiting.setRelationType(relation);
	}

	public void deleteFriend(Friend friend) {
		repository.deleteById(friend.getId());
	}
}
