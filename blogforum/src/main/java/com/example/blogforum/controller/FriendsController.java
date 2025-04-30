package com.example.blogforum.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogforum.DTO.ApiResponse;
import com.example.blogforum.DTO.FriendRequestDto;
import com.example.blogforum.DTO.UserDTO;
import com.example.blogforum.service.HelperService;
import com.example.blogforum.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendsController {
	private final UserService userService;
	private final HelperService helperService;
	@PostMapping("/send-request")
	public ResponseEntity<ApiResponse> sendFriendRequest(@RequestParam Long fromUserId, @RequestParam Long toUserId){
		return userService.sendRequest(fromUserId, toUserId);
	}
	@GetMapping("/friend-requests/{userId}")
	@PreAuthorize("@helperService.isCurrentUserId(#userId)")
	public ResponseEntity<List<FriendRequestDto>> getFriendRequests(@PathVariable Long userId) {
	    return userService.getFriendRequests(userId);
	}
	@PutMapping("/accept-request/{requestId}")
	@PreAuthorize("@friendRequestService.isReceiver(#requestId)")
	public ResponseEntity<ApiResponse> acceptFriendRequest(@PathVariable Long requestId) {
	    try {
	        userService.acceptFriendRequest(requestId);
	        return ResponseEntity.ok(new ApiResponse(true, "Lời mời kết bạn đã được chấp nhận!"));
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(new ApiResponse(false, "Lời mời đã bị xóa hoặc có lỗi xảy ra!"));
	    }
	}
	@GetMapping("/get-list-friend/{userId}")
	@PreAuthorize("@helperService.isCurrentUserId(#userId)")
	public ResponseEntity<List<UserDTO>> getListFriend(@PathVariable Long userId){
		return ResponseEntity.ok(userService.getListFriend(userId));
	}
}
