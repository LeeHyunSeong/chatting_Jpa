package com.lhs.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lhs.chatting.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{ }