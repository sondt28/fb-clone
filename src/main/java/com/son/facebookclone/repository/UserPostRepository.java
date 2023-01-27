package com.son.facebookclone.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.son.facebookclone.model.UserPost;
import com.son.facebookclone.model.UserProfile;

@Repository
public interface UserPostRepository extends JpaRepository<UserPost, Long> {

	List<UserPost> findByUserProfile(UserProfile userProfile, Sort sort);

	@Query("SELECT p "
			+ "FROM UserProfile u1\n"
			+ "JOIN Friendship f ON f.id.profileRequest = u1.id\n"
			+ "JOIN UserProfile u2 ON f.id.profileAccept = u2.id\n"
			+ "JOIN UserPost p ON p.userProfile = u2.id\n"
			+ "WHERE u1.id = :id "
			+ "ORDER BY p.id desc")
	List<UserPost> findAllPostByListFriendOfUserProfileRequest(Long id);
	
	@Query("SELECT p "
			+ "FROM UserProfile u1\n"
			+ "JOIN Friendship f ON f.id.profileAccept = u1.id\n"
			+ "JOIN UserProfile u2 ON f.id.profileRequest = u2.id\n"
			+ "JOIN UserPost p ON p.userProfile = u2.id\n"
			+ "WHERE u1.id = :id "
			+ "ORDER BY p.id desc")
	List<UserPost> findAllPostByListFriendOfUserProfileAccept(Long id);
}
