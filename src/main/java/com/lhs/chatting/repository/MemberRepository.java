package com.lhs.chatting.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lhs.chatting.model.MemberRoom;
import com.lhs.chatting.model.MemberUser;
import com.lhs.chatting.model.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Optional<Member> findByUserIdAndRoomId(Long userId, Long roomId);
    
    List<Member> findAllByUserId(Long userId);
    
    List<MemberRoom> findAllMemberRoomsByUserId(@Param("userId") Long userId);

    List<MemberUser> findAllMemberUsersByRoomId(@Param("roomId") Long roomId);
    
    @Transactional
    void deleteByUserIdAndRoomId(Long userId, Long roomId);
    
}