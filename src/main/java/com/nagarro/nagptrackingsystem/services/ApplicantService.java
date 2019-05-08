package com.nagarro.nagptrackingsystem.services;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.nagarro.nagptrackingsystem.constant.NAGPStatus;
import com.nagarro.nagptrackingsystem.constant.UserType;
import com.nagarro.nagptrackingsystem.dto.Profile;
import com.nagarro.nagptrackingsystem.entity.Applicant;
import com.nagarro.nagptrackingsystem.entity.Batch;
import com.nagarro.nagptrackingsystem.entity.Level;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;

public interface ApplicantService {

	Applicant addApplicant(Applicant applicant) throws AddressException, MessagingException, IOException;

	public Applicant editApplicantByApplicant(int id, String password, String name, String contactNo)
			throws InvalidDataException;

	Applicant editApplicantByAdmin(int id, UserType userType, NAGPStatus nagpStatus, Level level, Batch batch)
			throws InvalidDataException;

	List<Applicant> getAllApplicants();

	List<Applicant> getApplicantsPaginated(int pageNo, int pageSize);

	EnumSet<NAGPStatus> getNagpStatus();

	Applicant getApplicantById(int id);

	String deleteApplicant(int id);

	Profile getProfile(int id);

	void updateApplicantLevel(int applicantId);

	double getAccumulatedPoints(int id);

	double getAccumulatedPointsByMonth(int id, int month);
}
