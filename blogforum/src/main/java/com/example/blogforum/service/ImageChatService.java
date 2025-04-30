package com.example.blogforum.service;

import org.springframework.stereotype.Service;

import com.example.blogforum.model.Chat;
import com.example.blogforum.model.ImageChat;
import com.example.blogforum.repository.ChatRepository;
import com.example.blogforum.repository.ImageChatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageChatService {
	private final ImageChatRepository imgChatRepository;
	private final ChatRepository chatRepository;
	public void save(String url, Long chatId) {
		Chat a = chatRepository.findById(chatId).get();
		ImageChat imgc = new ImageChat();
		imgc.setChat(a);
		imgc.setUrl(url);
		imgChatRepository.save(imgc);
	}
}
