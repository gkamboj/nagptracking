package com.nagarro.nagptrackingsystem.servicesimp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nagarro.nagptrackingsystem.constant.ActivityStatus;
import com.nagarro.nagptrackingsystem.constant.Constants;
import com.nagarro.nagptrackingsystem.constant.UserType;
import com.nagarro.nagptrackingsystem.dto.ReportDTO;
import com.nagarro.nagptrackingsystem.entity.Applicant;
import com.nagarro.nagptrackingsystem.entity.Batch;
import com.nagarro.nagptrackingsystem.entity.User;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;
import com.nagarro.nagptrackingsystem.repositories.ApplicantRepository;
import com.nagarro.nagptrackingsystem.repositories.UserRepository;
import com.nagarro.nagptrackingsystem.services.ApplicantActivityService;
import com.nagarro.nagptrackingsystem.services.ApplicantService;
import com.nagarro.nagptrackingsystem.services.BatchService;
import com.nagarro.nagptrackingsystem.services.UserService;
import com.opencsv.CSVWriter;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ApplicantRepository applicantRepository;

	@Autowired
	BatchService batchService;

	@Autowired
	ApplicantActivityService applicantActivityService;

	@Autowired
	ApplicantService applicantService;

	@Override
	@Transactional
	public List<User> findAdmins() {
		return userRepository.findByUserType(UserType.admin);
	}

	@Override
	@Transactional
	public User adminById(int id) {
		User admin = userRepository.findById(id).orElse(null);
		if (admin == null || (admin != null && admin.getUserType() != UserType.admin)) {
			throw new NoSuchElementException(Constants.ADMIN_ID_NOT_EXISTS);
		} else {
			return admin;
		}
	}

	@Override
	@Transactional
	public User adminByEmail(String email) {
		User admin = userRepository.findFirstByEmail(email);
		if (admin == null || admin.getUserType() != UserType.admin) {
			throw new DataIntegrityViolationException(Constants.ADMIN_EMAIL_NOT_EXISTS);
		} else {
			return admin;
		}

	}

	@Override
	@Transactional
	public User addAdmin(User user) throws InvalidDataException {
		User addedUser;
		if (!(Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
				.matcher(user.getEmail()).find())) {
			throw new InvalidDataException(Constants.EMAIL_INVALID);
		} else if (!(user.getContactNo().matches("^[6-9]\\d{9}$"))) {
			throw new InvalidDataException(Constants.CONTACT_INVALID);
		} else if (!(userRepository.findFirstByEmail(user.getEmail()) == null)) {
			throw new DataIntegrityViolationException(Constants.ADMIN_EMAIL_EXISTS);
		} else if (!(userRepository.findFirstByContactNo(user.getContactNo()) == null)) {
			throw new DataIntegrityViolationException(Constants.ADMIN_CONTACT_EXISTS);
		} else {
			addedUser = userRepository.save(user);
		}
		return addedUser;
	}

	@Override
	@Transactional
	public User adminLogin(String email, String password) {
		User user = userRepository.findFirstByEmail(email);
		if ((user != null && user.getUserType() == UserType.applicant) || user == null) {
			throw new DataIntegrityViolationException(Constants.ADMIN_INVALID_EMAIL);
		} else {
			if (BCrypt.checkpw(password, user.getPassword())) {
				return user;
			} else {
				throw new DataIntegrityViolationException(Constants.INVALID_PASSWORD);
			}
		}
	}

	@Override
	@Transactional
	public User applicantLogin(String email, String password) {
		User user = userRepository.findFirstByEmail(email);
		if ((user != null && user.getUserType() == UserType.admin) || user == null) {
			throw new DataIntegrityViolationException(Constants.APPLICANT_INVALID_EMAIL);
		} else {
			if (BCrypt.checkpw(password, user.getPassword())) {
				return user;
			} else {
				throw new DataIntegrityViolationException(Constants.INVALID_PASSWORD);
			}
		}
	}

	@Override
	@Transactional
	public String deleteUser(int id) {
		userRepository.deleteById(id);
		return Constants.DELETE_SUCCESS;
	}

	@Override
	@Transactional
	public User updateAdmin(int id, String password, String name, String contactNo, UserType userType)
			throws InvalidDataException {
		User admin;
		if (!(contactNo.matches("^[6-9]\\d{9}$"))) {
			throw new InvalidDataException(Constants.CONTACT_INVALID);
		} else if (userRepository.findFirstByContactNo(contactNo) != null
				&& !(userRepository.findById(id).orElse(null).getContactNo().equals(contactNo))) {
			if (userRepository.findById(id).orElse(null).getUserType() == UserType.admin) {
				throw new DataIntegrityViolationException(Constants.ADMIN_CONTACT_EXISTS);
			} else {
				throw new DataIntegrityViolationException(Constants.USER_CONTACT_EXISTS);
			}
		} else {
			String dePassword = BCrypt.hashpw(password, Constants.BCRYPT_SALT);
			int affectedRows = userRepository.updateUser(dePassword, name, contactNo, String.valueOf(userType), id);
			if (affectedRows == 1) {
				admin = userRepository.findById(id).get();
			} else {
				throw new InvalidDataException(Constants.UPDATE_ERROR);
			}
		}
		return admin;
//		User admin = userRepository.findById(id).get();
//		admin.setPassword(password);
//		admin.setContactNo(contactNo);
//		userRepository.save(admin);
//		return admin;
	}

	@Override
	public EnumSet<UserType> getUserTypes() {
		return EnumSet.allOf(UserType.class);
	}

	@Override
	public String getAdminEmails() {
		List<User> admins = findAdmins();
		String adminEmails = "";
		for (int i = 0; i < admins.size(); i++) {
			adminEmails += admins.get(i).getEmail() + ",";
		}
		return adminEmails.substring(0, adminEmails.length() - 1);
	}

	@Override
	public List<ReportDTO> getBatchReport(int batchId) {
		List<ReportDTO> reportList = new ArrayList<ReportDTO>();
		Calendar currCal = Calendar.getInstance();
		currCal.setTime(new Date());
		currCal.add(Calendar.MONTH, -1);
		int prevMonth = currCal.get(Calendar.MONTH);
		for (Applicant applicant : applicantRepository.findByBatch(batchService.getBatchById(batchId))) {
			ReportDTO reportObj = new ReportDTO();
			reportObj.setEmail(applicant.getApplicant().getEmail());
			reportObj.setId(applicant.getId());
			reportObj.setName(applicant.getApplicant().getName());
			reportObj.setMaxPoints(applicantActivityService.maxPossiblePoints(ActivityStatus.PLANNED, applicant)
					+ applicantActivityService.maxPossiblePoints(ActivityStatus.IN_PROGRESS, applicant));
			reportObj.setMonthPoints(applicantService.getAccumulatedPointsByMonth(applicant.getId(), prevMonth));
			reportObj.setNoOfActivities(applicantActivityService
					.getApplicantActivityByActivityStatusAndApplicant(ActivityStatus.IN_PROGRESS, applicant).size());
			reportObj.setTotalPoints(applicantService.getAccumulatedPoints(applicant.getId()));
			reportList.add(reportObj);
		}
		return reportList;
	}

	@Override
	public void createCsv() throws IOException {
		Calendar currCal = Calendar.getInstance();
		currCal.setTime(new Date());
		currCal.add(Calendar.MONTH, -1);
		int prevMonth = currCal.get(Calendar.MONTH);
		String prevMonthName = new DateFormatSymbols().getMonths()[prevMonth];
		try {
			for (Batch batch : batchService.getBatches()) {
				File file = new File(Constants.REPORT_ATTACHMENT_PATH + prevMonthName + "-"
						+ String.valueOf(currCal.get(Calendar.YEAR)) + "_" + batch.getBatchName() + ".csv");
				file.setReadable(true);
				file.setWritable(true);
				FileWriter fw = new FileWriter(file);
				List<ReportDTO> report = getBatchReport(batch.getBatchId());
				CSVWriter csvWriter = new CSVWriter(fw);
				csvWriter.writeNext(Constants.REPORT_HEADER);
				for (ReportDTO reportObj : report) {
					csvWriter.writeNext(new String[] { String.valueOf(reportObj.getId()), reportObj.getName(),
							reportObj.getEmail(), String.valueOf(reportObj.getMonthPoints()),
							String.valueOf(reportObj.getTotalPoints()), String.valueOf(reportObj.getNoOfActivities()),
							String.valueOf(reportObj.getMaxPoints()) });
				}
				csvWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cretaeSummaryZip() throws IOException {
		Calendar currCal = Calendar.getInstance();
		currCal.setTime(new Date());
		currCal.add(Calendar.MONTH, -1);
		int prevMonth = currCal.get(Calendar.MONTH);
		String prevMonthName = new DateFormatSymbols().getMonths()[prevMonth];

		try {
			byte[] buffer = new byte[1024];
			File zipFile = new File(Constants.REPORT_ATTACHMENT_PATH + prevMonthName + "-"
					+ String.valueOf(currCal.get(Calendar.YEAR)) + ".zip");
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
			for (Batch batch : batchService.getBatches()) {
				File file = new File(Constants.REPORT_ATTACHMENT_PATH + prevMonthName + "-"
						+ String.valueOf(currCal.get(Calendar.YEAR)) + "_" + batch.getBatchName() + ".csv");
				FileInputStream fis = new FileInputStream(file);
				zos.putNextEntry(new ZipEntry(file.getName()));
				int length;
				while ((length = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, length);
				}
				zos.closeEntry();
				fis.close();
			}
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getApplicantReport(int applicantId) {
		Applicant applicant = applicantService.getApplicantById(applicantId);
		Calendar currCal = Calendar.getInstance();
		currCal.setTime(new Date());
		currCal.add(Calendar.MONTH, -1);
		int prevMonth = currCal.get(Calendar.MONTH);
		return "Applicant Id: " + applicant.getId() + "\r\nName: " + applicant.getApplicant().getName() + "\r\nEmail: "
				+ applicant.getApplicant().getEmail() + "\r\nPoints accumulated in month: "
				+ applicantService.getAccumulatedPointsByMonth(applicant.getId(), prevMonth) + "\r\nTotal points: "
				+ applicantService.getAccumulatedPoints(applicant.getId()) + "\r\nNumber of activities in progress: "
				+ applicantActivityService
						.getApplicantActivityByActivityStatusAndApplicant(ActivityStatus.IN_PROGRESS, applicant).size()
				+ "\r\nMax points that can be accumulated: "
				+ String.valueOf(applicantActivityService.maxPossiblePoints(ActivityStatus.PLANNED, applicant)
						+ applicantActivityService.maxPossiblePoints(ActivityStatus.IN_PROGRESS, applicant));
	}

	@Override
	public void sendRegistrationEmail(int applicantId) {
		Applicant applicant = applicantRepository.findById(applicantId).get();

		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(Constants.AUTH_EMAIL, Constants.AUTH_PASSWORD);
				}
			});
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Constants.AUTH_EMAIL, false));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(applicant.getApplicant().getEmail()));

			message.setSubject(Constants.EMAIL_SUBJECT_APPLICANT_REGISTER);
			message.setContent(" email", "text/html");
			message.setSentDate(new Date());

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(
					Constants.EMAIL_BODY_APPLICANT_REGISTER + "\nEmail: " + applicant.getApplicant().getEmail() + "\n"
							+ "Password: " + applicant.getApplicant().getPassword(),
					"text/plain");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			if (Constants.APPLICANT_REGISTER_ATTACHMENT_FILE_PATH != ""
					&& Constants.APPLICANT_REGISTER_ATTACHMENT_FILE_PATH != null) {
				MimeBodyPart attachPart = new MimeBodyPart();
				attachPart.attachFile(Constants.APPLICANT_REGISTER_ATTACHMENT_FILE_PATH);
				multipart.addBodyPart(attachPart);
			}
			message.setContent(multipart);
			Transport.send(message);
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Scheduled(cron = "0 0 13 1 1/1 ?")
	public void sendApplicantMonthlyReportEmail() {

		try {
			for (Applicant applicant : applicantService.getAllApplicants()) {
				Calendar currCal = Calendar.getInstance();
				currCal.setTime(new Date());
				currCal.add(Calendar.MONTH, -1);
				int prevMonth = currCal.get(Calendar.MONTH);
				String prevMonthName = new DateFormatSymbols().getMonths()[prevMonth];
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");
				Session session = Session.getInstance(props, new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(Constants.AUTH_EMAIL, Constants.AUTH_PASSWORD);
					}
				});
				Message message = new MimeMessage(session);

//				message.setFrom(new InternetAddress("", false));
//				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(""));

				message.setFrom(new InternetAddress(Constants.AUTH_EMAIL, false));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(applicant.getApplicant().getEmail()));
				message.setSubject(Constants.EMAIL_SUBJECT_APPLICANT_MONTHLY_REPORT + prevMonthName + ", "
						+ currCal.get(Calendar.YEAR));
				message.setContent(" email", "text/html");
				message.setSentDate(new Date());
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(getApplicantReport(applicant.getId()), "text/plain");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);
				Transport.send(message);
			}

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	// @Scheduled(cron = "0 0/1 * 1/1 * ?"): To test, cron to send mail every minute
	@Override
	@Scheduled(cron = "0 0 13 1 1/1 ?")
	public void sendBatchSummaryEmail() {
		try {
			createCsv();
			cretaeSummaryZip();

			Calendar currCal = Calendar.getInstance();
			currCal.setTime(new Date());
			currCal.add(Calendar.MONTH, -1);
			int prevMonth = currCal.get(Calendar.MONTH);
			String prevMonthName = new DateFormatSymbols().getMonths()[prevMonth];

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(Constants.AUTH_EMAIL, Constants.AUTH_PASSWORD);
				}
			});
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(Constants.AUTH_EMAIL, false));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getAdminEmails()));
			message.setSubject(
					Constants.EMAIL_SUBJECT_ADMIN_MONTHLY_SUMMARY + prevMonthName + ", " + currCal.get(Calendar.YEAR));
			message.setContent(" email", "text/html");
			message.setSentDate(new Date());

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(Constants.EMAIL_BODY_ADMIN_NAGP_SUMMARY, "text/plain");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			MimeBodyPart attachPart = new MimeBodyPart();
			attachPart.attachFile(Constants.REPORT_ATTACHMENT_PATH + prevMonthName + "-"
					+ String.valueOf(currCal.get(Calendar.YEAR)) + ".zip");
			multipart.addBodyPart(attachPart);

			message.setContent(multipart);
			Transport.send(message);
		} catch (IOException | MessagingException e) {
			e.printStackTrace();
		}
	}
}
