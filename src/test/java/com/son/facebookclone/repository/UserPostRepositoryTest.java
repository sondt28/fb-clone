package com.son.facebookclone.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.son.facebookclone.model.Friendship;
import com.son.facebookclone.model.FriendshipPK;
import com.son.facebookclone.model.UserPost;
import com.son.facebookclone.model.UserProfile;

@DataJpaTest
public class UserPostRepositoryTest {

	@Autowired
	private UserPostRepository userPostRepository;
	
	@Autowired
	private FriendshipRepository friendshipRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	private UserProfile userProfile;
	
	private UserProfile userProfile2;
	
	private FriendshipPK friendshipPK;
	
	private Friendship friendship;
	
	private UserPost post1;
	
	private UserPost post2;
	
	private UserPost post3;
	
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
		userProfileRepository.save(userProfile2);
		userProfileRepository.save(userProfile);
		
		friendshipPK = new FriendshipPK();
		friendshipPK.setProfileAccept(userProfile2);
		friendshipPK.setProfileRequest(userProfile);
		friendship = new Friendship();
		friendship.setId(friendshipPK);
		friendshipRepository.save(friendship);
		
		post1 = new UserPost();
		post1.setUserProfile(userProfile2);
		post1.setWrittenText("abc");
		post2 = new UserPost();
		post2.setUserProfile(userProfile2);
		post2.setWrittenText("vsdv");
		post3 = new UserPost();
		post3.setUserProfile(userProfile2);
		post3.setWrittenText("vsdv");
		userPostRepository.save(post1);
		userPostRepository.save(post2);
		userPostRepository.save(post3);
	}
	
	@Test
	void findAllPostOfListFriends() {
		long id = userProfile.getId();
		
		List<UserPost> friendRequest = userPostRepository
								.findAllPostByListFriendOfUserProfileRequest(id);
		
		List<UserPost> friendAccept = userPostRepository
								.findAllPostByListFriendOfUserProfileAccept(id);

		List<UserPost> actual = new ArrayList<>();
		
		actual.addAll(friendAccept);
		actual.addAll(friendRequest);
		
		assertEquals(3, actual.size());
	}
}
