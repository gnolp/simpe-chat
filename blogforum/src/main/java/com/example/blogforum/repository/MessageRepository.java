package com.example.blogforum.repository;

import org.springframework.stereotype.Repository;

import com.example.blogforum.model.Message;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
	@Query(value = """
	        SELECT TOP 1 *
	        FROM message
	        WHERE chat_id = :chatId
	        ORDER BY send_at DESC
	    """, nativeQuery = true)
///đang sử dụng mssql nên dùng top1
	Message findLatestMessageByChatId(@Param("chatId") Long chatId);
	Page<Message> findByChatIdOrderBySendAtDesc(Long chatId, Pageable pageable);
	@Query("""
		    SELECT COUNT(msg)
		    FROM Message msg
		    JOIN msg.chat c
		    JOIN msg.UserMessage um
		    WHERE um.user.id = :userId AND c.id = :chatId AND um.status = false
		    """)
	int countUnreadMsgByChatId(@Param("chatId") Long chatId, @Param("userId") Long userId);
}
