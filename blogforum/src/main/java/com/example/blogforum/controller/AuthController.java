package com.example.blogforum.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogforum.DTO.ApiResponse;
import com.example.blogforum.DTO.UserDTO;
import com.example.blogforum.model.LoginRequest;
import com.example.blogforum.model.LoginResponse;
import com.example.blogforum.model.RegisterRequest;
import com.example.blogforum.model.User;
import com.example.blogforum.repository.UserRepository;
import com.example.blogforum.security.JwtIssuer;
import com.example.blogforum.security.UserPrincipal;
import com.example.blogforum.service.UserService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@OpenAPIDefinition(
	    info = @Info(title = "User API", version = "1.0", description = "Quản lý người dùng")
	)
public class AuthController {
	private final JwtIssuer jwtIssuer;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody  @Validated LoginRequest request) {
		Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
		if(optionalUser.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(new ApiResponse(false, "Email không đúng!"));
		}
		User user = optionalUser.get();
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(new ApiResponse(false,"Mật khẩu không đúng!"));
	    }
		var refeshToken = jwtIssuer.refeshToken(user.getId(), user.getEmail(), List.of(user.getRole()));
		var token = jwtIssuer.issue(user.getId(), user.getEmail(), List.of(user.getRole()));
		return ResponseEntity.ok(LoginResponse.builder()
				.userId(user.getId())
				.accessToken(token)
				.refeshToken(refeshToken)
				.build());
	}
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(@Validated @RequestBody RegisterRequest request) {
	    if (userRepository.existsByEmail(request.getEmail())) {
	        return ResponseEntity.badRequest().body(new ApiResponse(false, "Email đã được sử dụng!"));
	    }
	    try {
	        User user = new User();
	        user.setEmail(request.getEmail());
	        user.setPassword(passwordEncoder.encode(request.getPassword()));
	        user.setRole("User");
	        userRepository.save(user);
	        return ResponseEntity.ok(new ApiResponse(true, "Đăng ký thành công!"));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ApiResponse(false, "Có lỗi xảy ra, vui lòng thử lại!"));
	    }
	}
	@PutMapping("/update-profile/{id}")
	public ResponseEntity<ApiResponse> changeInfo(@RequestBody UserDTO request,@PathVariable Long id,@AuthenticationPrincipal UserPrincipal userPrincipal){
		return userService.updateProfile(id, request, userPrincipal);
	}
	@GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(authentication);
    }
}
