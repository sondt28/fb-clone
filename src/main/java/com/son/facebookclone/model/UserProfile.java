package com.son.facebookclone.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_profile")
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;
	
	@Column(name = "given_name")
	private String givenName;
	
	@Column(name = "surname")
	private String surname;
	
	@OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, 
				CascadeType.PERSIST, CascadeType.REFRESH},
				fetch = FetchType.LAZY,
				mappedBy = "userProfile")
	private List<UserPost> posts;
	
	@OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, 
						CascadeType.PERSIST, CascadeType.REFRESH}, 
			   fetch = FetchType.LAZY,
			   mappedBy = "userProfile")
	private Set<PostLike> postLikes;
	
	@OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, 
						CascadeType.PERSIST, CascadeType.REFRESH}, 
			   fetch = FetchType.LAZY,
			   mappedBy = "userProfile")
	private List<PostComment> postComments;
}
