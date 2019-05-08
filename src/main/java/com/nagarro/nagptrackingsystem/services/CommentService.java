package com.nagarro.nagptrackingsystem.services;

import java.text.ParseException;
import java.util.List;

import com.nagarro.nagptrackingsystem.entity.Comment;

public interface CommentService {

	Comment addComment(Comment comment) throws ParseException;

	List<Comment> getCommentsByApplicantActivityId(int applicantActivityId);

	String deleteComment(int id);

	Comment getCommentById(int id);

}
