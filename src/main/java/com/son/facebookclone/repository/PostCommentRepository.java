package com.son.facebookclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.son.facebookclone.model.PostComment;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
	
	@Query("SELECT COUNT(c) FROM PostComment c WHERE userPost.id = ?1")
	Integer totalCommentOfPost(long id);
}
