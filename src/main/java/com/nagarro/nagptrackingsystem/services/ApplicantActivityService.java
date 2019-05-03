package com.nagarro.nagptrackingsystem.services;

import java.util.Date;
import java.util.List;

import com.nagarro.nagptrackingsystem.constant.ActivityStatus;
import com.nagarro.nagptrackingsystem.entity.ApplicantActivity;
import com.nagarro.nagptrackingsystem.entity.User;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;

public interface ApplicantActivityService {

	public List<ApplicantActivity> getApplicantActivities(int applicantId);

	public ApplicantActivity getApplicantActivityById(int applicantActivityId);

	public String deleteApplicantActivity(int applicantActivityId);

	public ApplicantActivity addApplicantActivity(ApplicantActivity applicantActivity) throws InvalidDataException;

	public ApplicantActivity editApplicantActivityByAdmin(int id, ActivityStatus status, Date completionDate,
			String description, byte[] document, Date endDate, double percentage, User assignor, Date startDate)
			throws InvalidDataException;

	public ApplicantActivity editApplicantActivityByApplicant(int id, String description, byte[] document, Date endDate,
			double percentage, User assignor, Date startDate) throws InvalidDataException;

}
