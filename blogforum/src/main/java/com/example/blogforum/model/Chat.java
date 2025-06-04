package com.example.blogforum.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.example.blogforum.model.Enum.ChatType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = true)
	private String image;
	@Column(name="name", columnDefinition = "NVARCHAR(255)", nullable = true)
	private String name;
	@OneToMany(mappedBy="chat", cascade=CascadeType.ALL, orphanRemoval =true)
	private List<UserChat> userchat;
	@JsonManagedReference
	@OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
	@OrderBy("sendAt DESC")
	private List<Message> messages;
	@CreationTimestamp
	@Column(name="created_at",updatable=false)
	private LocalDate created_at;
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE")
	private ChatType type;
	@JsonManagedReference
	@OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ImageChat> imgs;
}
