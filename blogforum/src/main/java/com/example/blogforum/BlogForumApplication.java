package com.example.blogforum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

public class BlogForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogForumApplication.class, args);
	}

}
