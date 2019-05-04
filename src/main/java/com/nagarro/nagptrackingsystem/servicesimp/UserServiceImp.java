package com.nagarro.nagptrackingsystem.servicesimp;

import java.io.IOException;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.nagarro.nagptrackingsystem.constant.Messages;
import com.nagarro.nagptrackingsystem.constant.UserType;
import com.nagarro.nagptrackingsystem.entity.Applicant;
import com.nagarro.nagptrackingsystem.entity.User;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;
import com.nagarro.nagptrackingsystem.repositories.ApplicantRepository;
import com.nagarro.nagptrackingsystem.repositories.UserRepository;
import com.nagarro.nagptrackingsystem.services.UserService;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ApplicantRepository applicantRepository;

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
			throw new NoSuchElementException(Messages.ADMIN_ID_NOT_EXISTS);
		} else {
			return admin;
		}
	}

	@Override
	@Transactional
	public User adminByEmail(String email) {
		User admin = userRepository.findFirstByEmail(email);
		if (admin == null || admin.getUserType() != UserType.admin) {
			throw new DataIntegrityViolationException(Messages.ADMIN_EMAIL_NOT_EXISTS);
		} else {
			return admin;
		}

	}

	@Override
	@Transactional
	public User addAdmin(User user) throws InvalidDataException {
		User addedUser;
		if (!(user.getContactNo().matches("^[6-9]\\d{9}$"))) {
			throw new InvalidDataException(Messages.CONTACT_INVALID);
		} else if (!(userRepository.findFirstByEmail(user.getEmail()) == null)) {
			throw new DataIntegrityViolationException(Messages.ADMIN_EMAIL_EXISTS);
		} else if (!(userRepository.findFirstByContactNo(user.getContactNo()) == null)) {
			throw new DataIntegrityViolationException(Messages.ADMIN_CONTACT_EXISTS);
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
			throw new DataIntegrityViolationException(Messages.ADMIN_INVALID_EMAIL);
		} else {
			if (BCrypt.checkpw(password, user.getPassword())) {
				return user;
			} else {
				throw new DataIntegrityViolationException(Messages.INVALID_PASSWORD);
			}
		}
	}

	@Override
	@Transactional
	public User applicantLogin(String email, String password) {
		User user = userRepository.findFirstByEmail(email);
		if ((user != null && user.getUserType() == UserType.admin) || user == null) {
			throw new DataIntegrityViolationException(Messages.ADMIN_INVALID_EMAIL);
		} else {
			if (BCrypt.checkpw(password, user.getPassword())) {
				return user;
			} else {
				throw new DataIntegrityViolationException(Messages.INVALID_PASSWORD);
			}
		}
	}

	@Override
	@Transactional
	public String deleteUser(int id) {
		userRepository.deleteById(id);
		return Messages.DELETE_SUCCESS;
	}

	@Override
	@Transactional
	public User updateAdmin(int id, String password, String name, String contactNo, UserType userType)
			throws InvalidDataException {
		User admin;
		if (!(contactNo.matches("^[6-9]\\d{9}$"))) {
			throw new InvalidDataException(Messages.CONTACT_INVALID);
		} else if (userRepository.findFirstByContactNo(contactNo) != null
				&& !(userRepository.findById(id).orElse(null).getContactNo().equals(contactNo))) {
			if (userRepository.findById(id).orElse(null).getUserType() == UserType.admin) {
				throw new DataIntegrityViolationException(Messages.ADMIN_CONTACT_EXISTS);
			} else {
				throw new DataIntegrityViolationException(Messages.USER_CONTACT_EXISTS);
			}
		} else {
			String dePassword = BCrypt.hashpw(password, BCrypt.gensalt());
			int affectedRows = userRepository.updateUser(dePassword, name, contactNo, String.valueOf(userType), id);
			if (affectedRows == 1) {
				admin = userRepository.findById(id).get();
			} else {
				throw new InvalidDataException(Messages.UPDATE_ERROR);
			}
		}
		return admin;
	}

	@Override
	public EnumSet<UserType> getUserTypes() {
		return EnumSet.allOf(UserType.class);
	}

	@Override
	public void sendRegistrationEmail(int id) throws AddressException, MessagingException, IOException {
		Applicant applicant = applicantRepository.findById(id).get();
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Messages.AUTH_EMAIL, Messages.AUTH_PASSWORD);
			}
		});
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(Messages.AUTH_EMAIL, false));

		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(applicant.getApplicant().getEmail()));
		message.setSubject("You are Registered");
		message.setContent(" email", "text/html");
		message.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(Messages.REGISTER_SUCESS + "\nEmail: " + applicant.getApplicant().getEmail() + "\n"
				+ "Password: " + applicant.getApplicant().getPassword(), "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		MimeBodyPart attachPart = new MimeBodyPart();

		attachPart.attachFile(Messages.ATTACHMENT_FILE_PATH);
		multipart.addBodyPart(attachPart);
		message.setContent(multipart);
		Transport.send(message);
	}

}
