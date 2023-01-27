package com.son.facebookclone.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.son.facebookclone.model.Friendship;
import com.son.facebookclone.model.FriendshipPK;
import com.son.facebookclone.model.UserProfile;

@DataJpaTest
public class UserProfileRepositoryTest {

	@Autowired
	private UserProfileRepository repository;
	
	@Autowired
	private FriendshipRepository friendshipRepository;
	
	@Mock
	private PasswordEncoder encoder;
	
	private UserProfile userProfile;
	
	private UserProfile userProfile2;
	
	private FriendshipPK friendshipPK;
	
	private Friendship friendship;
	
	private String encode;
	
	@BeforeEach
	void beforeEach() {
		encode = encoder.encode("1");
		
		userProfile = new UserProfile();
		userProfile.setGivenName("Son");
		userProfile.setSurname("Le");
		userProfile.setEmail("son123");
		userProfile.setDateOfBirth(LocalDate.of(2002, 06, 28));
		userProfile.setPassword(encode);
		
		userProfile2 = new UserProfile();
		userProfile2.setGivenName("Dang");
		userProfile2.setSurname("Le");
		userProfile2.setEmail("dang123");
		userProfile2.setDateOfBirth(LocalDate.of(2002, 06, 28));
		userProfile2.setPassword(encode);
		
		friendshipPK = new FriendshipPK();
		friendshipPK.setProfileAccept(userProfile2);
		friendshipPK.setProfileRequest(userProfile);
		
		friendship = new Friendship();
		friendship.setId(friendshipPK);
	}
	
	@Test
	void shouldSaveUserProfile() {
		
		UserProfile actual = repository.save(userProfile);
	
		assertEquals("Son", actual.getGivenName());
		assertEquals("Le", actual.getSurname());
		assertEquals("son123", actual.getEmail());
		assertEquals(LocalDate.of(2002, 06, 28), actual.getDateOfBirth());
		assertEquals(encode, actual.getPassword());
	}
	
	@Test
	void shouldReturnAllFriendsOfProfileRequest() {
		repository.save(userProfile);
		repository.save(userProfile2);
		friendshipRepository.save(friendship);
		
		List<UserProfile> profiles = repository
				.findByOwnUserProfileRequest(userProfile.getId()); 
		
		assertNotNull(profiles);
		assertEquals(1, profiles.size());
	}
	
	@Test
	void shouldReturnAllFriendsOfProfileAccept() {
		repository.save(userProfile);
		repository.save(userProfile2);
		friendshipRepository.save(friendship);
		
		List<UserProfile> profiles = repository
				.findByOwnUserProfileAccept(userProfile2.getId()); 
		
		assertNotNull(profiles);
		assertEquals(1, profiles.size());
	}
	
	@Test
	void shouldReturnAllResultOfSearch() {
		repository.save(userProfile);
		List<UserProfile> profiles = repository.search("1");
		
		assertEquals(1, profiles.size());
	}
}
