package com.example.blogforum.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.blogforum.repository.ChatRepository;
import com.example.blogforum.repository.MessageRepository;
import com.example.blogforum.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import com.example.blogforum.DTO.ApiResponse;
import com.example.blogforum.DTO.MessageDTO;
import com.example.blogforum.DTO.UserDTO;
import com.example.blogforum.model.Chat;
import com.example.blogforum.model.Message;
import com.example.blogforum.model.MessageType;
import com.example.blogforum.model.User;
@Service
@RequiredArgsConstructor
public class MessageService {
	private final MessageRepository messageRepository;
	private final ChatRepository chatRepository;
	private final HelperService helperService;
	private final UserRepository userRepository;
	public Message save(MessageDTO message, Chat chat) {
		Message msg = new Message();
		msg.setChat(chat);
		msg.setContent(message.getContent());
		msg.setType(message.getType());
		User sender = userRepository.findById(message.getSender().getId()).get();
		msg.setSender(sender);
		messageRepository.save(msg);
		return msg;
	}
	
	public MessageDTO getNewestMessage(Long chatId) {
	    Message a = messageRepository.findLatestMessageByChatId(chatId);
	    if(a==null) return null;
	    User sender = a.getSender();
	    UserDTO senderDTO = UserDTO.builder()
	        .id(sender.getId())
	        .image(sender.getImage())
	        .name(sender.getName())
	        .email(sender.getEmail())
	        .role(sender.getRole())
	        .dob(sender.getDob())
	        .build();
	    
	    return MessageDTO.builder()
	        .Content(a.getContent())
	        .sender(senderDTO)
	        .sendAt(a.getSendAt())
	        .type(a.getType())
	        .build();
	}
	public ResponseEntity<List<MessageDTO>> getMessByChatId(Long chatId, int page) {
	    Pageable pageable = PageRequest.of(page, 25); // mỗi trang 25 tin nhắn
	    Page<Message> messagePage = messageRepository.findByChatIdOrderBySendAtDesc(chatId, pageable);

	    List<MessageDTO> dtoList = messagePage.getContent().stream()
	            .map(this::convertDTO)
	            .toList();
	    return ResponseEntity.ok(dtoList);
	}
	private MessageDTO convertDTO(Message message) {
		User sender = message.getSender();
	    UserDTO senderDTO = UserDTO.builder()
	        .id(sender.getId())
	        .image(sender.getImage())
	        .name(sender.getName())
	        .email(sender.getEmail())
	        .role(sender.getRole())
	        .dob(sender.getDob())
	        .build();
	    return MessageDTO.builder()
	    		.Content(message.getContent())
	    		.sendAt(message.getSendAt())
	    		.sender(senderDTO)
	    		.type(message.getType())
	    		.build();
	}
	public MessageDTO convertDTO(Map<String, String> message, Long userID) {
		Optional a = userRepository.findById(userID);
		if(a.isEmpty()) return null;
		User sender = (User) a.get();
		System.out.println(sender.getName());
		MessageDTO msg = new MessageDTO();
		boolean check = true;
		UserDTO senderDTO = UserDTO.builder()
		        .id(sender.getId())
		        .image(sender.getImage())
		        .name(sender.getName())
		        .email(sender.getEmail())
		        .role(sender.getRole())
		        .dob(sender.getDob())
		        .build();
		msg.setSender(senderDTO);
		if(message.containsKey("content")) {
			msg.setContent(message.get("content"));
		}
		else {
			check = false;
		}
		if(message.containsKey("type")) {
			String typeString = message.get("type");
			MessageType type = MessageType.valueOf(typeString);
			msg.setType(type);
		}
		if(check) return msg;
		return null;
	}
}
