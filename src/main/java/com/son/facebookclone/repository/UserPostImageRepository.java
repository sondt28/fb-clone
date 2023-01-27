package com.son.facebookclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.son.facebookclone.model.UserPostImage;

@Repository
public interface UserPostImageRepository extends JpaRepository<UserPostImage, Long> {

	@Modifying
	@Query("DELETE FROM UserPost u WHERE u.image.id =?1")
	void deleteImageForPost(long imageId);
}
