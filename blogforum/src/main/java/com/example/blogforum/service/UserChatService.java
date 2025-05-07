package com.example.blogforum.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.blogforum.exception.BadRequestException;
import com.example.blogforum.exception.NotFoundException;
import com.example.blogforum.model.Chat;
import com.example.blogforum.model.User;
import com.example.blogforum.model.UserChat;
import com.example.blogforum.repository.ChatRepository;
import com.example.blogforum.repository.UserChatRepository;
import com.example.blogforum.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserChatService {
	private final ChatRepository chatRepository;
	private final UserRepository userRepository;
	private final HelperService helperService;
	private final UserChatRepository userChatRepository;
	public String role(Long chatId) {
		Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NotFoundException("Chat not found"));
		Long currentId = helperService.getCurrentUserId();
		User user = userRepository.findById(currentId).get();
		return userChatRepository.getRole(chat,user);
	}
	public void deleteUserFromChat(Long chatId, Long userId) {
		Chat chat = chatRepository.findById(chatId).get();
		User user = userRepository.findById(userId)
		        .orElseThrow(() -> new NotFoundException("User not found"));
		userChatRepository.deleteByChatAndUser(chat,user);
	}
	public void changeNameInChat(Long chatId, Long userId, String name) {
		Chat chat = chatRepository.findById(chatId).get();
		User user = userRepository.findById(userId).get();
		UserChat uc = userChatRepository.findByChatAndUser(chat,user)
				.orElseThrow(() -> new NotFoundException("User not in this chat"));
		uc.setName(name);
		userChatRepository.save(uc);
	}
	public void addUserFromChat(Long chatId, Long userId) {
		Chat chat = chatRepository.findById(chatId).get();
		User user = userRepository.findById(userId)
		        .orElseThrow(() -> new NotFoundException("User not found"));
		Optional<UserChat> existing = userChatRepository.findByChatAndUser(chat, user);
	    if (existing.isPresent()) {
	        throw new BadRequestException("User is already in the chat");
	    }
		UserChat uc = new UserChat();
		uc.setUser(user);
		uc.setChat(chat);
		
		userChatRepository.save(uc);
	}
}
