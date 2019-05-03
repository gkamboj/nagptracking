package com.nagarro.nagptrackingsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.nagptrackingsystem.entity.ApplicantActivity;
import com.nagarro.nagptrackingsystem.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	public List<Comment> findByApplicantActivity(ApplicantActivity applicantActivity);

}
