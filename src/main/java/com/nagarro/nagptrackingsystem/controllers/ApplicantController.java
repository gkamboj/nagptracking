package com.nagarro.nagptrackingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagptrackingsystem.dto.LoginDetail;
import com.nagarro.nagptrackingsystem.dto.Response;
import com.nagarro.nagptrackingsystem.entity.Applicant;
import com.nagarro.nagptrackingsystem.entity.ApplicantActivity;
import com.nagarro.nagptrackingsystem.entity.Comment;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;
import com.nagarro.nagptrackingsystem.services.ApplicantActivityService;
import com.nagarro.nagptrackingsystem.services.ApplicantService;
import com.nagarro.nagptrackingsystem.services.CommentService;
import com.nagarro.nagptrackingsystem.services.UserService;

@RestController
@RequestMapping("/applicant")
public class ApplicantController {

	@Autowired
	ApplicantService applicantService;

	@Autowired
	ApplicantActivityService applicantActivityService;

	@Autowired
	UserService userService;

	@Autowired
	CommentService commentService;

	@PutMapping("/{id}")
	public ResponseEntity<Response> editApplicantByApplicant(@PathVariable("id") int id,
			@RequestBody Applicant applicant)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(
					applicantService.editApplicantByApplicant(id, applicant.getApplicant().getPassword(),
							applicant.getApplicant().getName(), applicant.getApplicant().getContactNo()),
					"true");
			status = 200;
		} catch (InvalidDataException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getApplicantById(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(applicantService.getApplicantById(id), "true");
		return ResponseEntity.status(200).body(response);
	}

	@GetMapping("/{id}/profile")
	public ResponseEntity<Response> getApplicantProfile(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(applicantService.getProfile(id), "true");
		return ResponseEntity.status(200).body(response);
	}

	@PutMapping("/applicant_activity/{id}")
	public ResponseEntity<Response> editApplicantActivity_Applicant(@PathVariable("id") int id,
			@RequestBody ApplicantActivity applicantActivity)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(applicantActivityService.editApplicantActivityByApplicant(id,
					applicantActivity.getDescription(), applicantActivity.getDocument(), applicantActivity.getEndDate(),
					applicantActivity.getPercentage(), applicantActivity.getAssignor(),
					applicantActivity.getStartDate()), "true");
			status = 200;
		} catch (InvalidDataException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@GetMapping("/{id}/applicant_activity")
	public ResponseEntity<Response> getApplicantActivitiesByApplicantId(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(applicantActivityService.getApplicantActivities(id), "true");
		return ResponseEntity.status(200).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> adminLogin(@RequestBody LoginDetail loginDetails)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(userService.applicantLogin(loginDetails.getEmail(), loginDetails.getPassword()),
					"true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	// COMMENTS
	@GetMapping("/comments/{id}")
	public ResponseEntity<Response> getCommentByCommentId(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(commentService.getCommentById(id), "true");
		return ResponseEntity.status(200).body(response);
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<Response> deleteComment(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(commentService.deleteComment(id), "true");
		return ResponseEntity.status(200).body(response);
	}

	@PutMapping("/comments/{id}")
	public Response editComment(@PathVariable("id") int id, @RequestBody Comment comment) {
		return null;
	}

	@PostMapping("/comments")
	public ResponseEntity<Response> addComment(@RequestBody Comment comment)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(commentService.addComment(comment), "true");
		return ResponseEntity.status(200).body(response);
	}

}
