package com.example.blogforum.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.blogforum.DTO.ChatDTO;
import com.example.blogforum.model.Chat;
import com.example.blogforum.model.User;


@Repository
public interface ChatRepository extends JpaRepository<Chat,Long>{
	@Query("""
		    SELECT c 
		    FROM Chat c 
		    JOIN UserChat uc ON uc.chat = c 
		    WHERE uc.user.id = :userId 
		""")
	List<Chat> findByUserId(@Param("userId") Long userId);
	Optional<Chat> findById(Long id);
	Page<Chat> findByUser(User user, Pageable pageable);
}
