package com.son.facebookclone.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_post")
public class UserPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Lob
	@Column(name = "written_text")
	private String writtenText;

	@Column(name = "created_datetime")
	private LocalDateTime createdDateTime;
	
	@OneToMany(cascade = CascadeType.ALL, 
			   fetch = FetchType.LAZY,
			   mappedBy = "userPost")
	private Set<PostLike> postLikes;
	
	@OneToMany(cascade = CascadeType.ALL, 
			   fetch = FetchType.LAZY,
			   mappedBy = "userPost")
	private List<PostComment> postComments;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, 
				CascadeType.PERSIST, CascadeType.REFRESH},
				fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id")
	private UserProfile userProfile;
}
