package com.nagarro.nagptrackingsystem.servicesimp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.nagptrackingsystem.constant.Constants;
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
		return Constants.DELETE_SUCCESS;
	}

	@Override
	@Transactional
	public List<Comment> getCommentsByApplicantActivityId(int applicantActivityId) {
		return commentRepository
				.findByApplicantActivity(applicantActivityRepository.findById(applicantActivityId).orElse(null));

	}

	@Override
	@Transactional
	public Comment addComment(Comment comment) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		comment.setTimestamp(sdf.parse(sdf.format(new Date())));
		Comment addedComment = commentRepository.save(comment);
		addedComment.setOwner(userRepository.findById(addedComment.getOwner().getUserId()).orElse(null));
		return addedComment;
	}

}
