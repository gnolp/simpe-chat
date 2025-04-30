package com.example.blogforum.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.blogforum.model.User;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
	@Query("SELECT u.friends FROM User u WHERE u.id = :id")
	List<User> findAllFriendsById(Long id);
	Optional<User> findById(Long id);
}
