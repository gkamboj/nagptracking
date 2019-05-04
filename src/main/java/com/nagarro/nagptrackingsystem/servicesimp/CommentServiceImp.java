package com.nagarro.nagptrackingsystem.servicesimp;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.nagptrackingsystem.constant.Messages;
import com.nagarro.nagptrackingsystem.entity.Comment;
import com.nagarro.nagptrackingsystem.repositories.ApplicantActivityRepository;
import com.nagarro.nagptrackingsystem.repositories.CommentRepository;
import com.nagarro.nagptrackingsystem.repositories.UserRepository;
import com.nagarro.nagptrackingsystem.services.CommentService;

@Service
public class CommentServiceImp implements CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	ApplicantActivityRepository applicantActivityRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public Comment getCommentById(int id) {
		return commentRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public String deleteComment(int id) {
		commentRepository.deleteById(id);
		return Messages.DELETE_SUCCESS;
	}

	@Override
	@Transactional
	public List<Comment> getCommentsByApplicantActivityId(int applicantActivityId) {
		return commentRepository
				.findByApplicantActivity(applicantActivityRepository.findById(applicantActivityId).orElse(null));

	}

	@Override
	@Transactional
	public Comment addComment(Comment comment) {
		Comment addedComment = commentRepository.save(comment);
		addedComment.setOwner(userRepository.findById(addedComment.getOwner().getUserId()).orElse(null));
		return addedComment;
	}

}
