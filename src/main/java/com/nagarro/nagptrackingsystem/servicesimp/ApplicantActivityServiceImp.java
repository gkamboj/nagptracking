package com.nagarro.nagptrackingsystem.servicesimp;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.nagarro.nagptrackingsystem.constant.ActivityStatus;
import com.nagarro.nagptrackingsystem.constant.Constants;
import com.nagarro.nagptrackingsystem.entity.Applicant;
import com.nagarro.nagptrackingsystem.entity.ApplicantActivity;
import com.nagarro.nagptrackingsystem.entity.User;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;
import com.nagarro.nagptrackingsystem.repositories.ActivityRepository;
import com.nagarro.nagptrackingsystem.repositories.ApplicantActivityRepository;
import com.nagarro.nagptrackingsystem.repositories.ApplicantRepository;
import com.nagarro.nagptrackingsystem.repositories.LevelRepository;
import com.nagarro.nagptrackingsystem.repositories.UserRepository;
import com.nagarro.nagptrackingsystem.services.ApplicantActivityService;
import com.nagarro.nagptrackingsystem.services.ApplicantService;

@Service
public class ApplicantActivityServiceImp implements ApplicantActivityService {

	@Autowired
	ApplicantActivityRepository applicantActivityReository;

	@Autowired
	ApplicantRepository applicantRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ActivityRepository activityRepository;

	@Autowired
	LevelRepository levelRepository;

	@Autowired
	ApplicantService applicantService;

	@Override
	@Transactional
	public List<ApplicantActivity> getApplicantActivities(int applicantId) {
		return applicantActivityReository.findByApplicant(applicantRepository.findById(applicantId).get());
	}

	@Override
	@Transactional
	public ApplicantActivity getApplicantActivityById(int applicantActivityId) {
		return applicantActivityReository.findById(applicantActivityId).get();
	}

	@Override
	@Transactional
	public String deleteApplicantActivity(int applicantActivityId) {
		applicantActivityReository.deleteById(applicantActivityId);
		return Constants.DELETE_SUCCESS;
	}

	@Override
	@Transactional
	public ApplicantActivity addApplicantActivity(ApplicantActivity applicantActivity) throws InvalidDataException {
		ApplicantActivity addedApplicantActivity;
		if (applicantActivity.getDoneDate() != null
				&& applicantActivity.getStartDate().compareTo(applicantActivity.getDoneDate()) > 0) {
			throw new InvalidDataException(Constants.APPLICANT_ACTIVITY_INVALID_START_END_DATE);
		} else if (applicantActivity.getCompletionDate() != null
				&& applicantActivity.getStartDate().compareTo(applicantActivity.getCompletionDate()) > 0) {
			throw new InvalidDataException(Constants.APPLICANT_ACTIVITY_INVALID_START_COMPLETION_DATE);
		} else if (applicantActivity.getCompletionDate() != null && applicantActivity.getDoneDate() != null
				&& applicantActivity.getCompletionDate().compareTo(applicantActivity.getDoneDate()) > 0) {
			throw new InvalidDataException(Constants.APPLICANT_ACTIVITY_INVALID_COMPLETION_END_DATE);
		} else if (applicantActivityReository
				.findByApplicantAndActivity(applicantActivity.getApplicant(), applicantActivity.getActivity())
				.size() < activityRepository.findById(applicantActivity.getActivity().getActivityId()).get()
						.getMaxQualificationTimes()) {
			DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
			Date startDate = new Date();
			dateFormat.format(startDate);
			applicantActivity.setStartDate(startDate);
			applicantActivity.setPoints(getPointsFromPercentage(
					activityRepository.findById(applicantActivity.getActivity().getActivityId()).get().getPoints(),
					applicantActivity.getPercentage()));

			addedApplicantActivity = applicantActivityReository.save(applicantActivity);
			addedApplicantActivity
					.setApplicant(applicantRepository.findById(addedApplicantActivity.getApplicant().getId()).get());
			addedApplicantActivity
					.setAssignor(userRepository.findById(addedApplicantActivity.getAssignor().getUserId()).get());

			applicantService.updateApplicantLevel(applicantActivity.getApplicant().getId());
		} else {
			throw new DataIntegrityViolationException(Constants.APPLICANT_ACTIVITY_LIMIT);
		}
		return addedApplicantActivity;
	}

	@Override
	@Transactional
	public ApplicantActivity editApplicantActivityByAdmin(int id, ActivityStatus status, String description,
			byte[] document, double percentage, User assignor, Date startDate) throws InvalidDataException {
		ApplicantActivity editedApplicantActivity;
		ApplicantActivity applicantActivity = applicantActivityReository.findById(id).get();
//		if (endDate != null && startDate.compareTo(endDate) > 0) {
//			throw new InvalidDataException(Messages.APPLICANT_ACTIVITY_INVALID_START_END_DATE);
//		} else if (completionDate != null && startDate.compareTo(completionDate) > 0) {
//			throw new InvalidDataException(Messages.APPLICANT_ACTIVITY_INVALID_START_COMPLETION_DATE);
//		} else if (completionDate != null && endDate != null && completionDate.compareTo(endDate) > 0) {
//			throw new InvalidDataException(Messages.APPLICANT_ACTIVITY_INVALID_COMPLETION_END_DATE);
//		} else {
		DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date date = new Date();
		dateFormat.format(date);
		Date completionDate = null;
		double points = getPointsFromPercentage(
				activityRepository.findById(applicantActivity.getActivity().getActivityId()).get().getPoints(),
				percentage);
		if (percentage > 100 || percentage < 0) {
			throw new InvalidDataException(Constants.INVALID_PERCENTAGE);
		}
		if (String.valueOf(status).equals("COMPLETED") && applicantActivity.getDoneDate() != null) {
			completionDate = date;
		}
		int affectedRows = applicantActivityReository.updateApplicantActivity(id, String.valueOf(status),
				completionDate, description, document, applicantActivity.getDoneDate(), percentage, points,
				assignor.getUserId(), startDate);
		if (affectedRows == 1) {
			editedApplicantActivity = applicantActivityReository.findById(id).get();
			applicantService.updateApplicantLevel(applicantActivityReository.findById(id).get().getApplicant().getId());
		} else {
			throw new DataIntegrityViolationException(Constants.UPDATE_ERROR);
		}
//		}
		return editedApplicantActivity;
	}

	@Override
	@Transactional
	public ApplicantActivity editApplicantActivityByApplicant(int id, ActivityStatus activityStatus, String description,
			byte[] document) throws InvalidDataException {

		ApplicantActivity editedApplicantActivity;
		ApplicantActivity applicantActivity = applicantActivityReository.findById(id).get();
		DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date date = new Date();
		dateFormat.format(date);
		Date doneDate = null;
		if (String.valueOf(activityStatus).equals("DONE")) {
			doneDate = date;
		}

		int affectedRows = applicantActivityReository.updateApplicantActivity(id,
				String.valueOf(applicantActivity.getActivityStatus()), applicantActivity.getCompletionDate(),
				description, document, doneDate, applicantActivity.getPercentage(), applicantActivity.getPoints(),
				applicantActivity.getAssignor().getUserId(), applicantActivity.getStartDate());
		if (affectedRows == 1) {
			editedApplicantActivity = applicantActivityReository.findById(id).get();
			applicantService.updateApplicantLevel(applicantActivityReository.findById(id).get().getApplicant().getId());
		} else {
			throw new DataIntegrityViolationException(Constants.UPDATE_ERROR);
		}
		return editedApplicantActivity;
	}

	@Override
	public double getPointsFromPercentage(double activityPoints, double percentage) {
		return (new BigDecimal(activityPoints).multiply(new BigDecimal(percentage)).divide(new BigDecimal(100)))
				.doubleValue();
	}

	@Override
	@Transactional
	public List<ApplicantActivity> getApplicantActivityByActivityStatusAndApplicant(ActivityStatus activityStatus,
			Applicant applicant) {
		return applicantActivityReository.findByActivityStatusAndApplicant(activityStatus, applicant);
	}

	@Override
	@Transactional
	public double maxPossiblePoints(ActivityStatus activityStatus, Applicant applicant) {
		double maxPoints = 0;
		for (ApplicantActivity applicantActivity : getApplicantActivityByActivityStatusAndApplicant(activityStatus,
				applicant)) {
			maxPoints += applicantActivity.getActivity().getPoints();
		}
		return maxPoints;
	}
}
