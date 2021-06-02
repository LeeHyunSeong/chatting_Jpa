package com.lhs.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lhs.chatting.model.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    
    void deleteRoomById(Long id);
    
}