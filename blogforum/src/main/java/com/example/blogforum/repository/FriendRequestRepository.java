package com.example.blogforum.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.blogforum.model.FriendRequest;
import com.example.blogforum.model.User;
import com.example.blogforum.model.FriendRequestStatus;
@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long> {
	Optional<FriendRequest> findBySenderAndReceiver(User sender, User receiver);

	List<FriendRequest> findByReceiverAndStatus(User user, FriendRequestStatus Status);
}
