package com.nagarro.nagptrackingsystem.constant;

import org.mindrot.jbcrypt.BCrypt;

public class Constants {

	public static final String ACTIVITY_EXISTS = "Another activity with this combination of name, batch and level already exists";
	public static final String ACTIVITY_PONTS_INVALID = "Activity points can't be greater than level qualification points.";
	public static final String ADMIN_CONTACT_EXISTS = "Another user with this contact number already exists.";
	public static final String ADMIN_EMAIL_EXISTS = "Another user with this email already exists.";
	public static final String ADMIN_EMAIL_NOT_EXISTS = "Admin with this email doesn't exists.";
	public static final String ADMIN_ID_NOT_EXISTS = "Admin with this id doesn't exists.";
	public static final String ADMIN_INVALID_EMAIL = "No admin is registered with this email. Please login using registered email or create new account to use the application";
	public static final String APPLICANT_ACTIVITY_LIMIT = "Can't apply for this activity. Maximum qualififcation attempts limit reached.";
	public static final String APPLICANT_ACTIVITY_INVALID_START_END_DATE = "Start date must be before end date";
	public static final String APPLICANT_ACTIVITY_INVALID_START_COMPLETION_DATE = "Start date must be before completion date";
	public static final String APPLICANT_ACTIVITY_INVALID_COMPLETION_END_DATE = "Completion date must be before or same as end date";
	public static final String APPLICANT_CONTACT_EXISTS = "Another user with this contcat number already exists";
	public static final String APPLICANT_EMAIL_EXISTS = "Another user with this email already exists.";
	public static final String APPLICANT_INVALID_EMAIL = "No applicant is registered with this email. Please login using registered email or contact admin to create new account.";
	public static final String APPLICANT_REGISTER_ATTACHMENT_FILE_PATH = "";
	public static final String AUTH_EMAIL = "";
	public static final String AUTH_PASSWORD = "";
	public static final String BATCH_EXISTS = "Another batch with the selected technology and start-date already exists";
	public static final String BATCH_INVALID_START_DATE = "Year of batch's starting date must be same as batch year!!";
	public static final String BCRYPT_SALT = BCrypt.gensalt();
	public static final String CONTACT_INVALID = "Contact number must be of 10 digits and start with 6, 7, 8 or 9";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DELETE_SUCCESS = "Deleted successfully!!";
	public static final String EMAIL_INVALID = "Invalid email id";
	public static final String EMAIL_APPLICANT_REGISTER_SUCCESS = "Email with registration details sent successfully";
	public static final String EMAIL_APPLICANT_REPORT_SUCCESS = "Email sent to each applicant with personalised report of last month";
	public static final String EMAIL_BATCH_SUMMARY_SUCCESS = "Email sent to all admins with last month's summaries of each batch";
	public static final String EMAIL_BODY_APPLICANT_REGISTER = "You have been registered for the NAGP. Your login details are as following: \n";
	public static final String EMAIL_BODY_ADMIN_NAGP_SUMMARY = "PFAs for batchwise summary of NAGP for the last month.";
	public static final String EMAIL_SUBJECT_APPLICANT_REGISTER = "NAGP Registration";
	public static final String EMAIL_SUBJECT_APPLICANT_MONTHLY_REPORT = "NAGP Performance Report for ";
	public static final String EMAIL_SUBJECT_ADMIN_MONTHLY_SUMMARY = "NAGP summary for ";
	public static final String ERROR_EXCEPTION = "Some error occured: ";
	public static final String INVALID_PASSWORD = "Incorrect password";
	public static final String INVALID_PERCENTAGE = "Percentage score must be between 0 and 100 only!!";
	public static final String LEVEL_NAME_EXISTS = "Level with this name already exists";
	public static final String LEVEL_NUMBER_EXISTS = "Level with this number already exists";
	public static final String REPORT_ATTACHMENT_PATH = System.getProperty("user.home") + "/Downloads/";
	public static final String[] REPORT_HEADER = { "Id", "Name", "Email", "Month Points", "Total Points",
			"No. of Activities", "Max Points" };
	public static final String UPDATE_ERROR = "Can't update now. Some error occured while updating. Please try after sometime";
	public static final String USER_CONTACT_EXISTS = "Another user with this contact number already exists.";

}
