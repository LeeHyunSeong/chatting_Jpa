package com.lhs.chatting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lhs.chatting.model.Nickname;
import com.lhs.chatting.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String Email);
    
    @Query("SELECT nickname AS nickname FROM User WHERE id = :id")
    Optional<Nickname> findNicknameById(@Param("id") Long id);

}