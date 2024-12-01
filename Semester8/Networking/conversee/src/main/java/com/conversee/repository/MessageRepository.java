package com.conversee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conversee.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}

