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
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "member")
public class Member {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "room_alias", length = 45, nullable = false)
	private String roomAlias;

	@Column(name = "setting_meta", length = 45, nullable = false)
	private String settingMeta;

	@Column(name = "joined_time")
	private Timestamp joinedTime;

	@Column(name = "last_entrance_time")
	private Timestamp lastEntranceTime;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(targetEntity = Room.class)
	@JoinColumn(name = "room_id")
	private Room room;

	public Member(String roomAlias, User user, Room room) {
		this.roomAlias = roomAlias;
		settingMeta = null;
		joinedTime = new Timestamp(System.currentTimeMillis());
		lastEntranceTime = new Timestamp(System.currentTimeMillis());
		this.user = user;
		this.room = room;
	}
}