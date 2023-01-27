package com.son.facebookclone.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.son.facebookclone.model.PostLike;
import com.son.facebookclone.model.UserPost;
import com.son.facebookclone.model.UserProfile;

@DataJpaTest
public class PostLikeRepositoryTest {

	@Autowired
	private PostLikeRepository postLikeRepository;
	
	private PostLike like;
	
	private PostLike like2;
	
	private UserProfile userProfile;
	private UserProfile userProfile2;
	
	private UserPost userPost;
	
	@BeforeEach
	void beforeEach() {
		
		userProfile = new UserProfile();
		userProfile.setGivenName("Son");
		userProfile.setSurname("Le");
		userProfile.setEmail("son123");
		userProfile.setDateOfBirth(LocalDate.of(2002, 06, 28));
		userProfile2 = new UserProfile();
		userProfile2.setGivenName("Dang");
		userProfile2.setSurname("Le");
		userProfile2.setEmail("dang123");
		userProfile2.setDateOfBirth(LocalDate.of(2002, 06, 28));

		userPost = new UserPost();
		userPost.setUserProfile(userProfile);
		userPost.setWrittenText("abc");
		
		like = new PostLike();
		like.setUserPost(userPost);
		like.setUserProfile(userProfile);
		
		like2 = new PostLike();
		like2.setUserPost(userPost);
		like2.setUserProfile(userProfile2);
		
		postLikeRepository.save(like);
		postLikeRepository.save(like2);
	}
	
	@Test
	void deletePostLike() {
		postLikeRepository.delete(like);
		
		List<PostLike> postLike = postLikeRepository.findAll();
		
		assertEquals(1, postLike.size());
		assertEquals(like2.getId(), postLike.get(0).getId());
		assertEquals(userProfile2.getId(), postLike.get(0).getUserProfile().getId());
		assertEquals(userPost.getId(), postLike.get(0).getUserPost().getId());
	}
}
