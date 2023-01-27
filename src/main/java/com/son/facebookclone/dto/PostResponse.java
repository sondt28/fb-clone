package com.son.facebookclone.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {

	private long id;
	private String givenName;
	private String surname;
	private String photo;
	private String writtenText;
	private LocalDateTime createdDateTime;
	private int totalLike;
	private int totalComment;
	private List<CommentResponseDto> postComments;
	
}
