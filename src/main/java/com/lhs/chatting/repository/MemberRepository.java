package com.lhs.chatting.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lhs.chatting.model.MemberRoom;
import com.lhs.chatting.model.RoomAlias;
import com.lhs.chatting.model.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Optional<Member> findByUserIdAndRoomId(Long userId, Long roomId);
    
    List<Member> findAllByUserId(Long userId);
    
    Optional<RoomAlias> findRoomAliasByUserIdAndRoomId(@Param("userId") Long userId, @Param("roomId") Long roomId);
    
    List<MemberRoom> findAllMemberRoomsByUserId(@Param("userId") Long userId);

    @Transactional
    void deleteByUserIdAndRoomId(Long userId, Long roomId);
    
}