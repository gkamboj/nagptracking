package com.nagarro.nagptrackingsystem.servicesimp;

import java.util.EnumSet;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.nagarro.nagptrackingsystem.constant.Message;
import com.nagarro.nagptrackingsystem.constant.UserType;
import com.nagarro.nagptrackingsystem.entity.User;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;
import com.nagarro.nagptrackingsystem.repositories.UserRepository;
import com.nagarro.nagptrackingsystem.services.UserService;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userRepository;

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
			throw new NoSuchElementException(Message.ADMIN_ID_NOT_EXISTS);
		} else {
			return admin;
		}
	}

	@Override
	@Transactional
	public User adminByEmail(String email) {
		User admin = userRepository.findFirstByEmail(email);
		if (admin == null || admin.getUserType() != UserType.admin) {
			throw new DataIntegrityViolationException(Message.ADMIN_EMAIL_NOT_EXISTS);
		} else {
			return admin;
		}

	}

	@Override
	@Transactional
	public User addAdmin(User user) throws InvalidDataException {
		User addedUser;
		if (!(user.getContactNo().matches("^[6-9]\\d{9}$"))) {
			throw new InvalidDataException(Message.CONTACT_INVALID);
		} else if (!(userRepository.findFirstByEmail(user.getEmail()) == null)) {
			throw new DataIntegrityViolationException(Message.ADMIN_EMAIL_EXISTS);
		} else if (!(userRepository.findFirstByContactNo(user.getContactNo()) == null)) {
			throw new DataIntegrityViolationException(Message.ADMIN_CONTACT_EXISTS);
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
			throw new DataIntegrityViolationException(Message.ADMIN_INVALID_EMAIL);
		} else {
			if (BCrypt.checkpw(password, user.getPassword())) {
				return user;
			} else {
				throw new DataIntegrityViolationException(Message.INVALID_PASSWORD);
			}
		}
	}

	@Override
	@Transactional
	public User applicantLogin(String email, String password) {
		User user = userRepository.findFirstByEmail(email);
		if ((user != null && user.getUserType() == UserType.admin) || user == null) {
			throw new DataIntegrityViolationException(Message.ADMIN_INVALID_EMAIL);
		} else {
			if (BCrypt.checkpw(password, user.getPassword())) {
				return user;
			} else {
				throw new DataIntegrityViolationException(Message.INVALID_PASSWORD);
			}
		}
	}

	@Override
	@Transactional
	public String deleteUser(int id) {
		userRepository.deleteById(id);
		return Message.DELETE_SUCCESS;
	}

	@Override
	@Transactional
	public User updateAdmin(int id, String password, String name, String contactNo, UserType userType)
			throws InvalidDataException {
		User admin;
		if (!(contactNo.matches("^[6-9]\\d{9}$"))) {
			throw new InvalidDataException(Message.CONTACT_INVALID);
		} else if (userRepository.findFirstByContactNo(contactNo) != null
				&& !(userRepository.findById(id).orElse(null).getContactNo().equals(contactNo))) {
			if (userRepository.findById(id).orElse(null).getUserType() == UserType.admin) {
				throw new DataIntegrityViolationException(Message.ADMIN_CONTACT_EXISTS);
			} else {
				throw new DataIntegrityViolationException(Message.USER_CONTACT_EXISTS);
			}
		} else {
			String dePassword = BCrypt.hashpw(password, BCrypt.gensalt());
			int affectedRows = userRepository.updateUser(dePassword, name, contactNo, String.valueOf(userType), id);
			if (affectedRows == 1) {
				admin = userRepository.findById(id).get();
			} else {
				throw new InvalidDataException(Message.UPDATE_ERROR);
			}
		}
		return admin;
	}

	@Override
	public EnumSet<UserType> getUserTypes() {
		return EnumSet.allOf(UserType.class);
	}

}
