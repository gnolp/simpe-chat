package com.example.blogforum.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private Long id;
	private String name;
	private String email;
	private String role;
	private String image;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dob;
}
