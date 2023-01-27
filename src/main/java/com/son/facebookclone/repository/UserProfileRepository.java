package com.son.facebookclone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.son.facebookclone.model.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

	Optional<UserProfile> findByEmail(String email);

	@Query("SELECT u2 "
			+ "FROM UserProfile u1 "
			+ "JOIN Friendship f ON f.id.profileRequest = u1.id "
			+ "JOIN UserProfile u2 ON f.id.profileAccept = u2.id "
			+ "WHERE u1.id = :id")
	List<UserProfile> findByOwnUserProfileRequest(Long id);
	
	@Query("SELECT u2 "
			+ "FROM UserProfile u1 "
			+ "JOIN Friendship f ON f.id.profileAccept = u1.id "
			+ "JOIN UserProfile u2 ON f.id.profileRequest = u2.id "
			+ "WHERE u1.id = :id")
	List<UserProfile> findByOwnUserProfileAccept(Long id);
	
	@Query("SELECT u FROM UserProfile u WHERE u.surname LIKE %?1% "
									+ "OR u.givenName LIKE %?1% "
									+ "OR u.email LIKE %?1% "
									+ "OR u.id LIKE %?1%")
	List<UserProfile> search(String keyword);
}
