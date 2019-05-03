package com.nagarro.nagptrackingsystem.services;

import java.util.List;

import com.nagarro.nagptrackingsystem.entity.Comment;

public interface CommentService {

	Comment addComment(Comment comment);

	List<Comment> getCommentsByApplicantActivityId(int applicantActivityId);

	String deleteComment(int id);

	Comment getCommentById(int id);

}
