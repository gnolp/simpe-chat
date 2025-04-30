package com.example.blogforum.DTO;

import java.util.List;

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
}
