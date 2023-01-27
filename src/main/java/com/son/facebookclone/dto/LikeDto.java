package com.son.facebookclone.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeDto {

	private long id;
	private long profileId;
	private String surname;
	private String givenName;
}
