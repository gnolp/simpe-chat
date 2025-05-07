package com.example.blogforum.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.blogforum.model.Chat;
import com.example.blogforum.model.User;
import com.example.blogforum.model.UserChat;

@Repository
public interface UserChatRepository extends JpaRepository<UserChat, Long> {
    @Query("select uc.user from UserChat uc where uc.chat.id = :chat_id")
    List<User> findAllUserByChatId(@Param("chat_id") Long chat_id);
    @Query("""
    	    SELECT c.id
    	    FROM Chat c
    	    JOIN UserChat uc1 ON uc1.chat = c AND uc1.user.id = :senderId
    	    JOIN UserChat uc2 ON uc2.chat = c AND uc2.user.id = :receiverId
    	""")
	Optional<Long> findChatIdBySenderIdAndReceiverId(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);
    @Query("""
    	    SELECT CASE WHEN COUNT(uc) > 0 THEN true ELSE false END
    	    FROM UserChat uc
    	    WHERE uc.chat = :chat AND uc.user = :user
    	    """)
    	boolean UserInChat(@Param("chat") Chat chat, @Param("user") User user);
    @Query("""
    		SELECT uc.role
    		FROM UserChat uc WHERE uc.chat = :chat AND uc.user = :user
    		""")
	String getRole(@Param("chat")Chat chat,@Param("user") User user);
	void deleteByChatAndUser(Chat chat, User user);
	Optional<UserChat> findByChatAndUser(Chat chat, User user);

}