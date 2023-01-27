package com.son.facebookclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.son.facebookclone.model.PostLike;
import com.son.facebookclone.model.UserPost;
import com.son.facebookclone.model.UserProfile;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
	
	Optional<PostLike> findByUserProfileAndUserPost(UserProfile userProfile, UserPost userPost);

	@Query("SELECT COUNT(l) FROM PostLike l WHERE userPost.id = ?1")
	Integer totalLikeOfPost(Long id);
}
