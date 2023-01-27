package com.son.facebookclone.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.son.facebookclone.model.Friendship;
import com.son.facebookclone.model.FriendshipPK;
import com.son.facebookclone.model.UserProfile;

@DataJpaTest
public class FriendshipRepositoryTest {

	@Autowired
	private FriendshipRepository repository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	private FriendshipPK friendshipPK;
	
	private UserProfile userProfile1;
	
	private UserProfile userProfile2;
	
	@BeforeEach
	void beforeEach() {
		userProfile1 = new UserProfile();
		userProfile1.setGivenName("Le");
		userProfile1.setSurname("Son");
		userProfile1.setEmail("son@123");
		userProfile1.setPassword("123");
		userProfileRepository.save(userProfile1);
		
		userProfile2 = new UserProfile();
		userProfile2.setGivenName("Le");
		userProfile2.setSurname("Dang");
		userProfile2.setEmail("dang@123");
		userProfile2.setPassword("123");
		userProfileRepository.save(userProfile2);
		
		friendshipPK = new FriendshipPK();
		friendshipPK.setProfileRequest(userProfile1);
		friendshipPK.setProfileAccept(userProfile2);
	}
	
	@Test
	void saveFriendship() {
		Friendship friendship = new Friendship(friendshipPK);
		
		Friendship actual = repository.save(friendship);
		
		assertNotNull(actual);
		assertEquals(userProfile1.getId(), actual.getId().getProfileRequest().getId());
		assertEquals(userProfile2.getId(), actual.getId().getProfileAccept().getId());
	}
}
