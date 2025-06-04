package com.example.blogforum.DTO;

import java.time.LocalDateTime;

import com.example.blogforum.model.Enum.MessageType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {
	private String Content;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private LocalDateTime sendAt;
	private UserDTO sender;
	private MessageType type;
}
