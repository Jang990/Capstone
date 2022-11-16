package com.inhatc.spring.capstone.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.spring.capstone.user.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
