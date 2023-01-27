package com.son.facebookclone.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.son.facebookclone.model.Friendship;
import com.son.facebookclone.model.FriendshipPK;
import com.son.facebookclone.model.UserProfile;
import com.son.facebookclone.repository.FriendshipRepository;
import com.son.facebookclone.repository.UserProfileRepository;

@ExtendWith(MockitoExtension.class)
public class FriendshipServiceTest {

	@Mock
	private AuthService authService;
	
	@Mock
	private FriendshipRepository repository;
	
	@Mock
	private UserProfileRepository userProfileRepository;
	
	@InjectMocks
	private FriendshipServiceImpl service;
	
	private UserProfile userProfile;
	
	private UserProfile userProfile2;
	
	private FriendshipPK friendshipPK;
	
	private Friendship friendship;
	
	@BeforeEach
	void beforeEach() {
		userProfile = new UserProfile();
		userProfile.setGivenName("Le");
		userProfile.setSurname("Son");
		userProfile.setEmail("son@123");
		userProfile.setPassword("123");
		
		userProfile2 = new UserProfile();
		userProfile2.setGivenName("Le");
		userProfile2.setSurname("Dang");
		userProfile2.setEmail("dang@123");
		userProfile2.setPassword("123");
		
		friendshipPK = new FriendshipPK();
		friendshipPK.setProfileAccept(userProfile2);
		friendshipPK.setProfileRequest(userProfile);
	
		friendship = new Friendship();
		friendship.setId(friendshipPK);
	}
	
	@Test
	@Disabled
	void shouldSaveFriendship() {
		
		when(authService.getCurrentUserProfile()).thenReturn(userProfile);
		when(userProfileRepository.findById(any(Long.class))).thenReturn(Optional.of(userProfile2));
		
		when(repository.save(any(Friendship.class))).thenReturn(friendship);
	
		service.saveFriendship(userProfile2.getId());
		
		verify(repository).save(any(Friendship.class));
		verify(authService).getCurrentUserProfile();
		verify(userProfileRepository).findById(any(Long.class));
	}
}
