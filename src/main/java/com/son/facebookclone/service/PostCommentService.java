package com.son.facebookclone.service;

import com.son.facebookclone.dto.CommentDto;

public interface PostCommentService {
	void save(Long postId, CommentDto dto);
	void delete(long id);
}
