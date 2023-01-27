package com.son.facebookclone.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
	
	private long id;
	private UserProfileDto userProfile;
	private String commentText;
}
