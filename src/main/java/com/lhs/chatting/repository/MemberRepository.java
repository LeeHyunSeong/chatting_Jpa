package com.lhs.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lhs.chatting.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{ }