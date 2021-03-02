package com.lhs.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lhs.chatting.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{ }