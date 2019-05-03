package com.nagarro.nagptrackingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagptrackingsystem.dto.Response;
import com.nagarro.nagptrackingsystem.services.ActivityService;
import com.nagarro.nagptrackingsystem.services.ApplicantService;
import com.nagarro.nagptrackingsystem.services.BatchService;
import com.nagarro.nagptrackingsystem.services.UserService;

@RestController
@RequestMapping("/constants")
public class ConstantsController {

	@Autowired
	BatchService batchService;

	@Autowired
	ActivityService activityService;

	@Autowired
	ApplicantService applicantService;

	@Autowired
	UserService userService;

	@GetMapping("/batch_technologies")
	public ResponseEntity<Response> getTechnologies()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(batchService.getBatchTechnologies(), "true");
		return ResponseEntity.status(200).body(response);
	}

	@GetMapping("/user_types")
	public ResponseEntity<Response> getUserTypes()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(userService.getUserTypes(), "true");
		return ResponseEntity.status(200).body(response);
	}

	@GetMapping("/activity_status")
	public ResponseEntity<Response> getActivityStatuses()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(activityService.getActivityStatus(), "true");
		return ResponseEntity.status(200).body(response);
	}

	@GetMapping("/nagp_status")
	public ResponseEntity<Response> getNAGPStatus()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(applicantService.getNagpStatus(), "true");
		return ResponseEntity.status(200).body(response);
	}

}