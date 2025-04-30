package com.example.blogforum.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blogforum.model.FriendRequest;
import com.example.blogforum.repository.FriendRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendRequestService {
	private final FriendRequestRepository friendRequestRepository;
	private final HelperService helperService;

    public boolean isReceiver(Long requestId) {
        Optional<FriendRequest> requestOpt = friendRequestRepository.findById(requestId);
        if (requestOpt.isEmpty()) return false;
        
        FriendRequest request = requestOpt.get();
        
        Long currentUserId = helperService.getCurrentUserId();
        System.out.println(request.getReceiver());
        return request.getReceiver().getId().equals(currentUserId);
    }
}
