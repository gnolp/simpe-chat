package com.example.blogforum.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name= "sender_id")
	@JsonBackReference
	private User sender;
	@JsonIgnore
	@OneToMany(mappedBy="msg",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserMessage> UserMessage;
	@ManyToOne
	@JoinColumn(name="chat_id")
	@JsonBackReference
	private Chat chat;
	@Column(name="content", columnDefinition = "NVARCHAR(MAX)")
	private String content;
	@CreationTimestamp // Tự động set giá trị ngày giờ khi tạo bản ghi
    @Column(nullable = false, updatable = false)
    private LocalDateTime sendAt;
	@Enumerated(EnumType.STRING)
	private MessageType type;
}
