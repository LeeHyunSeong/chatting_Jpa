package com.lhs.chatting.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lhs.chatting.model.HostNickname;
import com.lhs.chatting.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String Email);

    Optional<HostNickname> findNicknameById(@Param("userId") Long userId);

}