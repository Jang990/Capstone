package com.inhatc.spring.capstone.comment.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.spring.capstone.comment.dto.NewCommentDTO;
import com.inhatc.spring.capstone.comment.entity.Comment;
import com.inhatc.spring.capstone.comment.repository.CommentRepository;
import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.content.repository.ContentRepository;
import com.inhatc.spring.capstone.user.entity.Users;
import com.inhatc.spring.capstone.user.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final ContentRepository contentRepository;
	private final UsersRepository userRepository;

	/** 댓글 등록 */
	public void registerComment(String userEmail, Long contentId, NewCommentDTO newComment) {
		Users user = userRepository.findByEmail(userEmail).orElseThrow(EntityNotFoundException::new);
		Content content = contentRepository.findById(contentId).orElseThrow(EntityNotFoundException::new);
		
		Comment createdComment = new Comment(user, content, newComment.getCommentBody());
		commentRepository.save(createdComment);
	}

	/** 댓글 삭제 */
	public void deleteComment(Long commentId) {
		Comment deletedComment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
		commentRepository.delete(deletedComment);
	}

	/** 댓글 수정 */
	public void modifyComment(Long commentId, NewCommentDTO newComment) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
		comment.modifyComment(newComment.getCommentBody());
	}
	
	/** 본인 댓글이 맞는지 확인 */
	private void checkOwnComment(String userEmail, Long commentId) {
		commentRepository.findByContent_IdAndWriter_Email(commentId, userEmail)
			.orElseThrow(EntityNotFoundException::new);
	}
	
	/** user권한에 댓글 삭제 서비스 */
	public void userDeleteService(String userEmail, Long commentId) {
		checkOwnComment(userEmail, commentId);
		deleteComment(commentId);
	}

	/** 댓글 수정 서비스 */
	public void modifyCommentService(Long commentId, String userEmail, NewCommentDTO newComment) {
		checkOwnComment(userEmail, commentId);
		modifyComment(commentId, newComment);
	}
}
