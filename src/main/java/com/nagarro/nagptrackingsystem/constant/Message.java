package com.nagarro.nagptrackingsystem.constant;

public class Message {

	public static String ACTIVITY_EXISTS = "Another activity with this combination of name, batch and level already exists";
	public static String ACTIVITY_PONTS_INVALID = "Activity points can't be greater than level qualification points.";
	public static String ADMIN_CONTACT_EXISTS = "Another admin with this contact number already exists.";
	public static String ADMIN_EMAIL_EXISTS = "Another admin with this email already exists.";
	public static String ADMIN_EMAIL_NOT_EXISTS = "Admin with this email doesn't exists.";
	public static String ADMIN_ID_NOT_EXISTS = "Admin with this id doesn't exists.";
	public static String ADMIN_INVALID_EMAIL = "No admin is registered with this email. Please login using registered email or create new account to use the application";
	public static String APPLICANT_ACTIVITY_LIMIT = "Can't apply for this activity. Maximum qualififcation attempts exceeded";
	public static String APPLICANT_ACTIVITY_INVALID_START_END_DATE = "Start data must be before end date";
	public static String APPLICANT_ACTIVITY_INVALID_START_COMPLETION_DATE = "Start data must be before completion date";
	public static String APPLICANT_ACTIVITY_INVALID_COMPLETION_END_DATE = "Completion date must be before or same as end date";
	public static String APPLICANT_CONTACT_EXISTS = "Another applicant with this contcat number already exists";
	public static String APPLICANT_EMAIL_EXISTS = "Another applicant with this email already exists.";
	public static String BATCH_EXISTS = "Another batch with the selected technology and start-date already exists";
	public static String BATCH_INVALID_START_DATE = "Year of batch's starting date must be same as batch year!!";
	public static String CONTACT_INVALID = "Contact number must be of 10 digits and start with 6, 7, 8 or 9";
	public static String DELETE_SUCCESS = "Deleted successfully!!";
	public static String ERROR_EXCEPTION = "Some error occured: ";
	public static String INVALID_PASSWORD = "Incorrect password";
	public static String LEVEL_NAME_EXISTS = "Level with this name already exists";
	public static String LEVEL_NUMBER_EXISTS = "Level with this name already exists";
	public static String UPDATE_ERROR = "Can't update now. Some error occured while updating. Please tr after sometime";
	public static String USER_CONTACT_EXISTS = "Another user with this contact number already exists.";

}
