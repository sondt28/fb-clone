package com.son.facebookclone.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
	
	private long id;
	@NotBlank(message = "{postComment.commentText.notblank}")
	private String commentText;
	private LocalDateTime createdDateTime;
}
