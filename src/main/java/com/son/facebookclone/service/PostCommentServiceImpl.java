package com.son.facebookclone.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.son.facebookclone.dto.CommentDto;
import com.son.facebookclone.exceptions.PostCommentNotFoundException;
import com.son.facebookclone.exceptions.UserPostNotFoundException;
import com.son.facebookclone.mapper.PostCommentMapper;
import com.son.facebookclone.model.PostComment;
import com.son.facebookclone.model.UserPost;
import com.son.facebookclone.repository.PostCommentRepository;
import com.son.facebookclone.repository.UserPostRepository;

@Service
public class PostCommentServiceImpl implements PostCommentService {
	
	@Autowired
	private UserPostRepository userPostRepository;
	
	@Autowired
	private PostCommentRepository postCommentRepository;
	
	@Autowired
	private AuthService authService;
	
	@Override
	public void save(Long postId, CommentDto dto) {
		UserPost userPost = userPostRepository.findById(postId)
				.orElseThrow(() -> new UserPostNotFoundException("Post not found."));
		
		dto.setCreatedDateTime(LocalDateTime.now());
		
		PostComment postComment = PostCommentMapper.INSTANCE
				.commentDtoToPostComment(dto, authService.getCurrentUserProfile(), userPost);
		
		postCommentRepository.save(postComment);
	}

	@Override
	public void delete(long id) {
		PostComment postComment = postCommentRepository.findById(id)
				.orElseThrow(() -> new PostCommentNotFoundException("Post comment not found."));
		postCommentRepository.delete(postComment);
	}
}
