package com.lhs.chatting.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "message")
@Builder
@Getter
public class Message {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "contents", length = 21844, nullable = false)
	private String contents;

	@Column(name = "type", nullable = false)
	private MessageType type;

	@Column(name = "created_time")
	private Timestamp createdTime;

	@ManyToOne(targetEntity = Room.class)
	@Column(name = "room_id")
	private Room room;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;
	
	public static Message of(String contents, MessageType type, Long roomId, Long userId) {
		return Message.builder()
				.contents(contents)
				.type(type)
				.createdTime(new Timestamp(System.currentTimeMillis()))
				.room(Room.builder().id(roomId).build())
				.user(User.builder().id(userId).build())
				.build();
	}
}