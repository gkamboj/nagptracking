package com.nagarro.nagptrackingsystem.services;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import com.nagarro.nagptrackingsystem.constant.UserType;
import com.nagarro.nagptrackingsystem.dto.ReportDTO;
import com.nagarro.nagptrackingsystem.entity.User;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;

@Service
public interface UserService {

	User adminLogin(String email, String password);

	User addAdmin(User user) throws InvalidDataException;

	List<User> findAdmins();

	User adminById(int id);

	User adminByEmail(String email);

	User updateAdmin(int id, String password, String name, String contactNo, UserType userType)
			throws InvalidDataException;

	String deleteUser(int id);

	EnumSet<UserType> getUserTypes();

	User applicantLogin(String email, String password);

	void sendRegistrationEmail(int applicantId) throws MessagingException, IOException;

	List<ReportDTO> getBatchReport(int batchId);

	void createCsv() throws IOException;

	String getApplicantReport(int applicantId);

	void sendApplicantMonthlyReportEmail();

	String getAdminEmails();

	void sendBatchSummaryEmail();

	void cretaeSummaryZip() throws IOException;

}
