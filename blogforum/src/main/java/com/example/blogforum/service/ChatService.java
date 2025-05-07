package com.example.blogforum.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogforum.DTO.ApiResponse;
import com.example.blogforum.DTO.ChatDTO;
import com.example.blogforum.DTO.MessageDTO;
import com.example.blogforum.DTO.UserDTO;
import com.example.blogforum.exception.NotFoundException;
import com.example.blogforum.model.Chat;
import com.example.blogforum.model.ChatType;
import com.example.blogforum.model.Message;
import com.example.blogforum.model.Role;
import com.example.blogforum.model.User;
import com.example.blogforum.model.UserChat;
import com.example.blogforum.model.UserMessage;
import com.example.blogforum.repository.ChatRepository;
import com.example.blogforum.repository.MessageRepository;
import com.example.blogforum.repository.UserChatRepository;
import com.example.blogforum.repository.UserMessageRepository;
import com.example.blogforum.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
	private final ChatRepository chatRepository;
	private final UserChatRepository userChatRepository;
	private final UserRepository userRepository;
	private final MessageService messageService;
	private final UserMessageRepository userMsgRepository;
	private final SimpMessagingTemplate messagingTemplate;
	private final HelperService helperService;
	public List<ChatDTO> getChatsByUserId(Long userId, int page) {
		Pageable pageable = PageRequest.of(page,8);
		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));
		Page<Chat> chats = chatRepository.findByUser(user,pageable);
        return chats.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
	public List<Chat> getChatsByUserIdNoDTO(Long userId){
		List<Chat> chats = chatRepository.findByUserId(userId);
		return chats;
	}
    private ChatDTO convertToDTO(Chat chat) {
    	Long chat_id = chat.getId();
    	
    	List<User> users = userChatRepository.findAllUserByChatId(chat_id);
    	if(chat.getType() == ChatType.Private) {
    		Long a = helperService.getCurrentUserId();
    		for(User u:users) {
    			if(u.getId()!=a) {
    				chat.setName(u.getName());
    			}
    		}
    		System.out.println(chat.getName());
    	} else {
    		if(chat.getName()==null) {
    			StringBuilder name = new StringBuilder();
    			for(int i = 0; i <users.size(); i++) {
    				name.append(users.get(i).getName()).append(" ");
    				if(i>=2) {
    					name.append(users.size()-i);
    					break;
    				}
    			}
    			chat.setName(name.toString());
    		}
    	}
    	List<UserDTO> userDTOs = users.stream()
                .map(user -> UserDTO.builder()
                		.id(user.getId())
                		.image(user.getImage())
                		.name(user.getName())     
	                    .email(user.getEmail())    
	                    .role(user.getRole())
	                    .dob(user.getDob())
	                    .build()
                		)
                .collect(Collectors.toList());

        List<MessageDTO> messageDTOs = new ArrayList<>();
        messageDTOs.add(messageService.getNewestMessage(chat.getId()));
        
        return ChatDTO.builder()
        		.id(chat.getId())
        		.name(chat.getName())
        		.image(chat.getImage())
        		.users(userDTOs)
        		.messages(messageDTOs)
        		.build();
    }
    public void findOrCreateChat(Long senderId,Long receiverId) {
    	Optional<Long> optionalChatId = userChatRepository.findChatIdBySenderIdAndReceiverId(senderId,receiverId);
    	System.out.println("Sender ID: " + senderId);
    	System.out.println("Receiver ID: " + receiverId);
    	if(optionalChatId.isPresent()) {
    		return;
    	}
    	createChatPrivate(List.of(senderId,receiverId));
    }
    private void createChatPrivate(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            throw new IllegalArgumentException("Cần ít nhất một người dùng");
        }

        // Tạo Chat mới
        Chat chat = new Chat();
        chat.setType(ChatType.Private);
        chat = chatRepository.save(chat);

        // Lấy danh sách User từ userIds
        List<User> users = userRepository.findAllById(userIds);

        if (users.size() != userIds.size()) {
            throw new IllegalArgumentException("Có userId không tồn tại");
        }

        // Thêm User vào Chat
        for (User user : users) {
            UserChat userChat = new UserChat();
            userChat.setUser(user);
            userChat.setChat(chat);
            userChatRepository.save(userChat);
        }
    }
	public ResponseEntity<?> send(MessageDTO message, Long chatId) {
		System.out.println(message.getContent());
		Optional<Chat> a = chatRepository.findById(chatId);
		if(a.isEmpty()) {
			return ResponseEntity.badRequest().body(new ApiResponse(false,"Không tồn tại đoạn chat!"));
		}
		List<User> users = userChatRepository.findAllUserByChatId(chatId);
		boolean isIn = false;
		for(User s:users) {
			if(s.getId()== message.getSender().getId()) {
				isIn = true;
			}
		}
		if(!isIn) {
			return ResponseEntity.badRequest().body(new ApiResponse(false,"Ban khong thuoc doan chat!"));
		}
		Chat chat = a.get();
		Message msg = messageService.save(message, chat);
		// gui tin nhan cho moi nguoi trong doan chat.
		for(User u:users) {
			if(u.getId()!= message.getSender().getId()) {
				UserMessage um = new UserMessage();
				um.setMsg(msg);
				um.setUser(u);
				userMsgRepository.save(um);
			}
			messagingTemplate.convertAndSendToUser(
		            u.getId().toString(), // ID người nhận
		            "/queue/chat-" + chatId, // Kênh WebSocket cho cuộc trò chuyện này
		            message
		        );
		}
		return ResponseEntity.ok(new ApiResponse(true, "Gửi tin nhắn thành công"));
	}
	@Transactional
	public ResponseEntity<?> createGroupChat(Long createrId, List<Long> memberId){
		Chat chat = new Chat();
		chat.setType(ChatType.Group);
		chatRepository.save(chat);
		User admin = userRepository.findById(createrId).get();
		UserChat adminUser = new UserChat();
		adminUser.setRole(Role.Admin);
		adminUser.setUser(admin);
		adminUser.setChat(chat);
		userChatRepository.save(adminUser);
		List<User> members = userRepository.findAllById(memberId);
		if (members.size() != memberId.size()) {
            throw new IllegalArgumentException("Có userId không tồn tại");
        }
		for(User m: members) {
			UserChat userChat = new UserChat();
            userChat.setUser(m);
            userChat.setChat(chat);
            userChatRepository.save(userChat);
		}
		return ResponseEntity.ok(new ApiResponse(true,"Tạo cuộc trò chuyện thành công!"));
	}
	public boolean isIn(Long chatId) {
		Long currentId = helperService.getCurrentUserId();
		Chat chat = chatRepository.findById(chatId).get();
		User user = userRepository.findById(currentId).get();
		return userChatRepository.UserInChat(chat, user);
	}
	public void changeImage(Long chatId, String url) {
	    Chat chat = chatRepository.findById(chatId)
	                 .orElseThrow(() -> new NotFoundException("Chat not found"));
	    chat.setImage(url);
	    chatRepository.save(chat);
	}
}
