package com.example.blogforum.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
	private final Long userId;
	private final String accessToken;
	private final String refeshToken;
}
