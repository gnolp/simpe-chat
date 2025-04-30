package com.example.blogforum.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDto {
    private Long requestId;
    private Long senderId;
    private String senderName;
}
