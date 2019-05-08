package com.nagarro.nagptrackingsystem.services;

import java.util.Date;
import java.util.List;

import com.nagarro.nagptrackingsystem.constant.ActivityStatus;
import com.nagarro.nagptrackingsystem.entity.Applicant;
import com.nagarro.nagptrackingsystem.entity.ApplicantActivity;
import com.nagarro.nagptrackingsystem.entity.User;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;

public interface ApplicantActivityService {

	List<ApplicantActivity> getApplicantActivities(int applicantId);

	ApplicantActivity getApplicantActivityById(int applicantActivityId);

	String deleteApplicantActivity(int applicantActivityId);

	ApplicantActivity addApplicantActivity(ApplicantActivity applicantActivity) throws InvalidDataException;

	ApplicantActivity editApplicantActivityByAdmin(int id, ActivityStatus status, String description, byte[] document,
			double percentage, User assignor, Date startDate) throws InvalidDataException;

	ApplicantActivity editApplicantActivityByApplicant(int id, ActivityStatus status, String description,
			byte[] document, User assignor) throws InvalidDataException;

	double getPointsFromPercentage(double activityPoints, double percentage);

	List<ApplicantActivity> getApplicantActivityByActivityStatusAndApplicant(ActivityStatus activityStatus,
			Applicant applicant);

	double maxPossiblePoints(ActivityStatus activityStatus, Applicant applicant);

}
