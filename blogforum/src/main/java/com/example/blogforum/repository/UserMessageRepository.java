package com.example.blogforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blogforum.model.UserMessage;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessage,Long> {

}
