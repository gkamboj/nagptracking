package com.nagarro.nagptrackingsystem.servicesimp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.nagarro.nagptrackingsystem.constant.ActivityStatus;
import com.nagarro.nagptrackingsystem.constant.Messages;
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
		return Messages.DELETE_SUCCESS;
	}

	@Override
	@Transactional
	public ApplicantActivity addApplicantActivity(ApplicantActivity applicantActivity) throws InvalidDataException {
		ApplicantActivity addedApplicantActivity;
		if (applicantActivity.getEndDate() != null
				&& applicantActivity.getStartDate().compareTo(applicantActivity.getEndDate()) > 0) {
			throw new InvalidDataException(Messages.APPLICANT_ACTIVITY_INVALID_START_END_DATE);
		} else if (applicantActivity.getCompletionDate() != null
				&& applicantActivity.getStartDate().compareTo(applicantActivity.getCompletionDate()) > 0) {
			throw new InvalidDataException(Messages.APPLICANT_ACTIVITY_INVALID_START_COMPLETION_DATE);
		} else if (applicantActivity.getCompletionDate() != null && applicantActivity.getEndDate() != null
				&& applicantActivity.getCompletionDate().compareTo(applicantActivity.getEndDate()) > 0) {
			throw new InvalidDataException(Messages.APPLICANT_ACTIVITY_INVALID_COMPLETION_END_DATE);
		} else if (applicantActivityReository
				.findByApplicantAndActivity(applicantActivity.getApplicant(), applicantActivity.getActivity())
				.size() < activityRepository.findById(applicantActivity.getActivity().getActivityId()).get()
						.getMaxQualificationTimes()) {
			applicantActivity.setPoints((new BigDecimal(
					activityRepository.findById(applicantActivity.getActivity().getActivityId()).get().getPoints())
							.multiply(new BigDecimal(applicantActivity.getPercentage())).divide(new BigDecimal(100)))
									.doubleValue());
//			applicantActivity.setActivityStatus(
//					ActivityStatus.valueOf(String.valueOf(applicantActivity.getActivityStatus()).toUpperCase()));
			addedApplicantActivity = applicantActivityReository.save(applicantActivity);
			addedApplicantActivity
					.setApplicant(applicantRepository.findById(addedApplicantActivity.getApplicant().getId()).get());
			addedApplicantActivity
					.setAssignor(userRepository.findById(addedApplicantActivity.getAssignor().getUserId()).get());

			applicantService.updateApplicantLevel(applicantActivity.getApplicant().getId());
		} else {
			throw new DataIntegrityViolationException(Messages.APPLICANT_ACTIVITY_LIMIT);
		}
		return addedApplicantActivity;
	}

	@Override
	@Transactional
	public ApplicantActivity editApplicantActivityByAdmin(int id, ActivityStatus status, Date completionDate,
			String description, byte[] document, Date endDate, double percentage, User assignor, Date startDate)
			throws InvalidDataException {
		ApplicantActivity editedApplicantActivity;
		if (endDate != null && startDate.compareTo(endDate) > 0) {
			throw new InvalidDataException(Messages.APPLICANT_ACTIVITY_INVALID_START_END_DATE);
		} else if (completionDate != null && startDate.compareTo(completionDate) > 0) {
			throw new InvalidDataException(Messages.APPLICANT_ACTIVITY_INVALID_START_COMPLETION_DATE);
		} else if (completionDate != null && endDate != null && completionDate.compareTo(endDate) > 0) {
			throw new InvalidDataException(Messages.APPLICANT_ACTIVITY_INVALID_COMPLETION_END_DATE);
		} else {
			int affectedRows = applicantActivityReository.updateApplicantActivity(id, String.valueOf(status),
					completionDate, description, document, endDate, percentage, assignor.getUserId(), startDate);
			if (affectedRows == 1) {
				editedApplicantActivity = applicantActivityReository.findById(id).get();
				applicantService
						.updateApplicantLevel(applicantActivityReository.findById(id).get().getApplicant().getId());
			} else {
				throw new DataIntegrityViolationException(Messages.UPDATE_ERROR);
			}
		}
		return editedApplicantActivity;
	}

	@Override
	@Transactional
	public ApplicantActivity editApplicantActivityByApplicant(int id, String description, byte[] document, Date endDate,
			double percentage, User assignor, Date startDate) throws InvalidDataException {

		ApplicantActivity editedApplicantActivity;
		ApplicantActivity applicantActivity = applicantActivityReository.findById(id).get();
		if (endDate != null && startDate.compareTo(endDate) > 0) {
			throw new InvalidDataException(Messages.APPLICANT_ACTIVITY_INVALID_START_END_DATE);
		} else if (applicantActivity.getCompletionDate() != null
				&& startDate.compareTo(applicantActivity.getCompletionDate()) > 0) {
			throw new InvalidDataException(Messages.APPLICANT_ACTIVITY_INVALID_START_COMPLETION_DATE);
		} else if (applicantActivity.getCompletionDate() != null && endDate != null
				&& applicantActivity.getCompletionDate().compareTo(endDate) > 0) {
			throw new InvalidDataException(Messages.APPLICANT_ACTIVITY_INVALID_COMPLETION_END_DATE);
		} else {
			int affectedRows = applicantActivityReository.updateApplicantActivity(id,
					String.valueOf(applicantActivity.getActivityStatus()), applicantActivity.getCompletionDate(),
					description, document, endDate, percentage, assignor.getUserId(), startDate);
			if (affectedRows == 1) {
				editedApplicantActivity = applicantActivityReository.findById(id).get();
				applicantService
						.updateApplicantLevel(applicantActivityReository.findById(id).get().getApplicant().getId());
			} else {
				throw new DataIntegrityViolationException(Messages.UPDATE_ERROR);
			}
		}
		return editedApplicantActivity;
	}

}
