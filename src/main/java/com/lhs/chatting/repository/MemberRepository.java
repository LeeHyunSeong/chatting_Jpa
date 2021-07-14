package com.lhs.chatting.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lhs.chatting.model.MemberRoom;
import com.lhs.chatting.model.entity.Member;
import com.lhs.chatting.model.entity.User;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Optional<Member> findByUserIdAndRoomId(Long userId, Long roomId);
    
    List<Member> findAllByUserId(Long userId);
    
    List<MemberRoom> findAllMemberRoomsByUserId(@Param("userId") Long userId);
    
    @Query("SELECT m.user FROM Member m WHERE m.room.id = :roomId")
    List<User> findAllMemberUsersByRoomId(@Param("roomId") Long roomId);
    
    @Transactional
    void deleteByUserIdAndRoomId(Long userId, Long roomId);
    
}