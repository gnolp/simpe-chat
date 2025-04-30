package com.example.blogforum.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties {
	// tạo secretKey khi bắt đầu chạy dự án để phục vụ cho jwt.
	private String secretKey;
	private String secretKeyRefesh;
}
