package com.example.blogforum.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name="Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = true)
	private String image;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = true,columnDefinition = "NVARCHAR(255)")
	private String name;
	private String role;
	@Column(nullable = true,columnDefinition = "DATE")
	private LocalDate dob;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "friends",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name="friend_id")
	)
	@JsonIgnore
	private List<User> friends;
	
	@JsonIgnore
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserChat> userchat;
	@JsonManagedReference
	@OneToMany(mappedBy="sender", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Message> massages;
	@JsonManagedReference
	@OneToMany(mappedBy ="user", cascade = CascadeType.ALL, orphanRemoval = true)
	List<UserMessage> received_msg;
	@CreationTimestamp
	@Column(name="created_at",updatable=false)
	private LocalDate created_at;
}
