package com.lhs.chatting.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "room")
@Builder
@Getter
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "created_time")
	private Timestamp createdTime;

	@Setter
	@Column(name = "last_msg_id")
	private Message lastMsgId;
	
	public static Room of() {
		return Room.builder()
				.createdTime(new Timestamp(System.currentTimeMillis()))
				.lastMsgId(null)
				.build();
	}
}