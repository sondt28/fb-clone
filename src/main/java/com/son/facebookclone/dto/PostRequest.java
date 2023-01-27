package com.son.facebookclone.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequest {

	private long id;
	
	@NotBlank(message = "{userPost.writtenText.notblank}")
	private String writtenText;
	
	private LocalDateTime createdDateTime;
}
