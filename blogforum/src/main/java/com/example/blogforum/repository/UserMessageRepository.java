package com.example.blogforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.blogforum.model.UserMessage;

import jakarta.transaction.Transactional;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessage,Long> {
	@Modifying
	@Transactional
	@Query("""
	    UPDATE UserMessage um
	    SET um.status = true
	    WHERE um.user.id = :userId AND um.msg.chat.id = :chatId
	    """)
	void markAsSeen(@Param("userId") Long userId, @Param("chatId") Long chatId);
}
