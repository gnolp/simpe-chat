package com.example.blogforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blogforum.model.ImageChat;

@Repository
public interface ImageChatRepository extends JpaRepository<ImageChat, Long>{

}
