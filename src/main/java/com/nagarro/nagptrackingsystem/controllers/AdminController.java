package com.nagarro.nagptrackingsystem.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagptrackingsystem.constant.Constants;
import com.nagarro.nagptrackingsystem.dto.LoginDetail;
import com.nagarro.nagptrackingsystem.dto.Response;
import com.nagarro.nagptrackingsystem.entity.Activity;
import com.nagarro.nagptrackingsystem.entity.Applicant;
import com.nagarro.nagptrackingsystem.entity.ApplicantActivity;
import com.nagarro.nagptrackingsystem.entity.Batch;
import com.nagarro.nagptrackingsystem.entity.Comment;
import com.nagarro.nagptrackingsystem.entity.Level;
import com.nagarro.nagptrackingsystem.entity.User;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;
import com.nagarro.nagptrackingsystem.services.ActivityService;
import com.nagarro.nagptrackingsystem.services.ApplicantActivityService;
import com.nagarro.nagptrackingsystem.services.ApplicantService;
import com.nagarro.nagptrackingsystem.services.BatchService;
import com.nagarro.nagptrackingsystem.services.CommentService;
import com.nagarro.nagptrackingsystem.services.LevelService;
import com.nagarro.nagptrackingsystem.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService adminService;

	@Autowired
	BatchService batchService;

	@Autowired
	LevelService levelService;

	@Autowired
	ActivityService activityService;

	@Autowired
	ApplicantService applicantService;

	@Autowired
	CommentService commentService;

	@Autowired
	ApplicantActivityService applicantActivityService;

	// ADMIN
	@GetMapping
	public ResponseEntity<Response> getAdmins()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(adminService.findAdmins(), "true");
		return ResponseEntity.status(200).body(response);
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> findAdminbyId(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(adminService.adminById(id), "true");
			status = 200;
		} catch (NoSuchElementException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@PostMapping
	public ResponseEntity<Response> addAdmin(@RequestBody User user)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(adminService.addAdmin(user), "true");
			status = 200;
		} catch (DataIntegrityViolationException | InvalidDataException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> adminLogin(@RequestBody LoginDetail loginDetails)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(adminService.adminLogin(loginDetails.getEmail(), loginDetails.getPassword()),
					"true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteUser(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(adminService.deleteUser(id), "true");
		return ResponseEntity.status(200).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response> editAdmin(@PathVariable("id") int id, @RequestBody User user)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(adminService.updateAdmin(id, user.getPassword(), user.getName(),
					user.getContactNo(), user.getUserType()), "true");
			status = 200;
		} catch (DataIntegrityViolationException | InvalidDataException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	// LEVELS
	@GetMapping("/level")
	public ResponseEntity<Response> getLevels()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(levelService.getLevels(), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@PostMapping("/level")
	public ResponseEntity<Response> addLevel(@RequestBody Level level)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(levelService.addLevel(level), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@GetMapping("/level/{id}")
	public ResponseEntity<Response> getLevelById(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(levelService.getLevelById(id), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@DeleteMapping("/level/{id}")
	public ResponseEntity<Response> deleteLevel(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(levelService.deleteLevel(id), "true");
		return ResponseEntity.status(200).body(response);
	}

	@PutMapping("/level/{id}")
	public ResponseEntity<Response> editLevel(@PathVariable("id") int id, @RequestBody Level level)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(
					levelService.editLevel(id, level.getName(), level.getDescription(), level.getQualificationPoints()),
					"true");
			status = 200;
		} catch (DataIntegrityViolationException | InvalidDataException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	// BATCH
	@GetMapping("/batch")
	public ResponseEntity<Response> getBatches()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(batchService.getBatches(), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@PostMapping("/batch")
	public ResponseEntity<Response> addBatch(@RequestBody Batch batch)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(batchService.addBatch(batch), "true");
			status = 200;
		} catch (DataIntegrityViolationException | InvalidDataException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@GetMapping("/batch/{id}")
	public ResponseEntity<Response> getBatchById(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(batchService.getBatchById(id), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@DeleteMapping("/batch/{id}")
	public ResponseEntity<Response> deleteBatch(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(batchService.deleteBatch(id), "true");
		return ResponseEntity.status(200).body(response);
	}

	@PutMapping("/batch/{id}")
	public ResponseEntity<Response> editBatch(@PathVariable("id") int id, @RequestBody Batch batch)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(batchService.editBtach(id, batch.getBatchTechnology(), batch.getDescription(),
					batch.getStartDate(), batch.getYear()), "true");
			status = 200;
		} catch (DataIntegrityViolationException | InvalidDataException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	// ACTIVITY
	@PostMapping("/activity")
	public ResponseEntity<Response> addActivity(@RequestBody Activity activity)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(activityService.addActivity(activity), "true");
			status = 200;
		} catch (DataIntegrityViolationException | InvalidDataException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@GetMapping("/activity")
	public ResponseEntity<Response> getActivities()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(activityService.getActivities(), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@GetMapping("/activity/{id}")
	public ResponseEntity<Response> getActivityById(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(activityService.getActivityById(id), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@GetMapping("/activity/level/{levelId}")
	public ResponseEntity<Response> getActivitiesByLevel(@PathVariable("levelId") int levelId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(activityService.getActivityByLevel(levelId), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@GetMapping("/activity/batch/{batchId}")
	public ResponseEntity<Response> getActivitiesByBatch(@PathVariable("batchId") int batchId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(activityService.getActivityByBatch(batchId), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@DeleteMapping("/activity/{id}")
	public ResponseEntity<Response> deleteActivity(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(activityService.deleteActivity(id), "true");
		return ResponseEntity.status(200).body(response);
	}

	@PutMapping("/activity/{id}")
	public ResponseEntity<Response> editActivity(@PathVariable("id") int id, @RequestBody Activity activity)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(activityService.editActivity(id, activity), "true");
			status = 200;
		} catch (DataIntegrityViolationException | InvalidDataException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	// APPLICANT
	@PostMapping("/applicant")
	public ResponseEntity<Response> addApplicant(@RequestBody Applicant applicant) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, AddressException, MessagingException, IOException {
		Response response;
		int status;
		try {
			response = new Response(applicantService.addApplicant(applicant), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@GetMapping("/applicant")
	public ResponseEntity<Response> getAllApplicants(@RequestParam(required = false) Optional<Integer> pageNo,
			@RequestParam(required = false) Optional<Integer> pageSize)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			status = 200;
			if (pageNo.isPresent() && pageSize.isPresent()) {
				response = new Response(applicantService.getApplicantsPaginated(pageNo.get(), pageSize.get()), "true");
			} else {
				response = new Response(applicantService.getAllApplicants(), "true");
			}
		} catch (DataIntegrityViolationException ex) {
			status = 409;
			response = new Response(ex.getMessage(), "false");
		}
		return ResponseEntity.status(status).body(response);

	}

	@PutMapping("/applicant/{id}")
	public ResponseEntity<Response> editApplicantByAdmin(@PathVariable("id") int id, @RequestBody Applicant applicant)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(applicantService.editApplicantByAdmin(id, applicant.getApplicant().getUserType(),
					applicant.getNagpStatus(), applicant.getLevel(), applicant.getBatch()), "true");
			status = 200;
		} catch (DataIntegrityViolationException | InvalidDataException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@GetMapping("/applicant/{id}")
	public ResponseEntity<Response> getApplicantById(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(applicantService.getApplicantById(id), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@DeleteMapping("/applicant/{id}")
	public ResponseEntity<Response> deleteApplicant(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(applicantService.deleteApplicant(id), "true");
		return ResponseEntity.status(200).body(response);
	}

	@GetMapping("/applicant/{id}/applicant_activity")
	public ResponseEntity<Response> getApplicantActivitiesByApplicantId(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(applicantActivityService.getApplicantActivities(id), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	// APPLICANT ACTIVITY
	@PostMapping("/applicant_activity")
	public ResponseEntity<Response> addApplicantActivity(@RequestBody ApplicantActivity applicantActivity)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(applicantActivityService.addApplicantActivity(applicantActivity), "true");
			status = 200;
		} catch (DataIntegrityViolationException | InvalidDataException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@PutMapping("/applicant_activity/{id}")
	public ResponseEntity<Response> editApplicantActivity_Admin(@PathVariable("id") int id,
			@RequestBody ApplicantActivity applicantActivity)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(applicantActivityService.editApplicantActivityByAdmin(id,
					applicantActivity.getActivityStatus(), applicantActivity.getDescription(),
					applicantActivity.getDocument(), applicantActivity.getPercentage(), applicantActivity.getAssignor(),
					applicantActivity.getStartDate()), "true");
			status = 200;
		} catch (DataIntegrityViolationException | InvalidDataException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@DeleteMapping("/applicant_activity/{id}")
	public ResponseEntity<Response> deleteApplicantActivity(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(applicantActivityService.deleteApplicantActivity(id), "true");
		return ResponseEntity.status(200).body(response);
	}

	@GetMapping("/applicant_activity/{id}")
	public ResponseEntity<Response> getApplicantActivityByApplicantActivityId(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(applicantActivityService.getApplicantActivityById(id), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@GetMapping("/applicant_activity/{id}/comments")
	public ResponseEntity<Response> getApplicantActivityComments(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			response = new Response(commentService.getCommentsByApplicantActivityId(id), "true");
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
		Response response;
		int status;
		try {
			response = new Response(commentService.getCommentById(id), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<Response> deleteComment(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response = new Response(commentService.deleteComment(id), "true");
		return ResponseEntity.status(200).body(response);
	}

	@PutMapping("/comments/{id}")
	public ResponseEntity<Response> editComment(@PathVariable("id") int id, @RequestBody Comment comment)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return null;
	}

	@PostMapping("/comments")
	public ResponseEntity<Response> addComment(@RequestBody Comment comment)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, ParseException {
		Response response;
		int status;
		try {
			response = new Response(commentService.addComment(comment), "true");
			status = 200;
		} catch (DataIntegrityViolationException ex) {
			response = new Response(ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	// EMAIL
	@PostMapping("/email/registration/{id}")
	public ResponseEntity<Response> sendRegistrationEmail(@PathVariable("id") int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			adminService.sendRegistrationEmail(id);
			response = new Response(Constants.EMAIL_APPLICANT_REGISTER_SUCCESS, "true");
			status = 200;
		} catch (Exception ex) {
			response = new Response(Constants.ERROR_EXCEPTION + ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@PostMapping("email/summary")
	public ResponseEntity<Response> sendMontlySummary()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			adminService.sendBatchSummaryEmail();
			response = new Response(Constants.EMAIL_BATCH_SUMMARY_SUCCESS, "true");
			status = 200;
		} catch (Exception ex) {
			response = new Response(Constants.ERROR_EXCEPTION + ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

	@PostMapping("email/applicant_report")
	public ResponseEntity<Response> sendApplicantReport()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Response response;
		int status;
		try {
			adminService.sendBatchSummaryEmail();
			response = new Response(Constants.EMAIL_APPLICANT_REPORT_SUCCESS, "true");
			status = 200;
		} catch (Exception ex) {
			response = new Response(Constants.ERROR_EXCEPTION + ex.getMessage(), "false");
			status = 409;
		}
		return ResponseEntity.status(status).body(response);
	}

}
