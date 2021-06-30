package com.lhs.chatting.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lhs.chatting.model.RoomInfoResponse;
import com.lhs.chatting.model.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Optional<Member> findByUserIdAndRoomId(Long userId, Long roomId);
    
    List<Member> findAllByUserId(Long userId);
    
    @Query(value = "SELECT room_alias FROM MEMBER WHERE user_id = :userId AND room_id = :roomId", nativeQuery = true)
    Optional<String> findRoomAliasByUserIdAndRoomId(@Param("userId") Long userId, @Param("roomId") Long roomId);
    
    @Query(value = "SELECT room_id, room_alias, last_entrance_time FROM MEMBER WHERE user_id = ?", nativeQuery = true)
    List<Object[]> findAllRoomResponseByUserId(Long userId);
}