package com.example.blogforum.DTO;

import java.util.List;

import com.example.blogforum.model.Enum.Status;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
    private Long id;
    private String name;
    private String image;
    private List<UserDTO> users;
    private List<MessageDTO> messages;
    private Status status;
    private int unreadCount;
}
