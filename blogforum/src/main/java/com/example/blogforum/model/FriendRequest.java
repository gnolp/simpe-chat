package com.example.blogforum.model;

import java.time.LocalDate;

import com.example.blogforum.model.Enum.FriendRequestStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "friend_requests")
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
	private User sender;
	@ManyToOne
	@JoinColumn(name = "receiver_id", nullable = false)
	private User receiver;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private FriendRequestStatus status;
	
	private LocalDate created = LocalDate.now();
}
