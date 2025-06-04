package com.example.blogforum.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.example.blogforum.model.Enum.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserChat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name="chat_id")
	private Chat chat;
	@Column(nullable = true)
	private String name;
	@CreationTimestamp
	@Column(nullable = false, updatable=false)
	private LocalDate joined_at;
	@Column(name="role")
	@Enumerated(EnumType.STRING)
	private Role role = Role.member;
}
