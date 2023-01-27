package com.son.facebookclone.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileDetailsDto {

	private long id;
	private String givenName;
	private String surname;
	private LocalDate dateOfBirth;
	private List<PostResponse> posts;
}
