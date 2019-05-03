package com.nagarro.nagptrackingsystem.servicesimp;

import java.util.EnumSet;
import java.util.List;

import javax.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nagarro.nagptrackingsystem.constant.Message;
import com.nagarro.nagptrackingsystem.constant.NAGPStatus;
import com.nagarro.nagptrackingsystem.constant.UserType;
import com.nagarro.nagptrackingsystem.dto.Profile;
import com.nagarro.nagptrackingsystem.entity.Applicant;
import com.nagarro.nagptrackingsystem.entity.ApplicantActivity;
import com.nagarro.nagptrackingsystem.entity.Batch;
import com.nagarro.nagptrackingsystem.entity.Level;
import com.nagarro.nagptrackingsystem.entity.User;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;
import com.nagarro.nagptrackingsystem.repositories.ApplicantActivityRepository;
import com.nagarro.nagptrackingsystem.repositories.ApplicantRepository;
import com.nagarro.nagptrackingsystem.repositories.BatchRepository;
import com.nagarro.nagptrackingsystem.repositories.LevelRepository;
import com.nagarro.nagptrackingsystem.repositories.UserRepository;
import com.nagarro.nagptrackingsystem.services.ApplicantService;

@Service
public class ApplicantServiceImp implements ApplicantService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ApplicantRepository applicantRepository;

	@Autowired
	BatchRepository batchRepository;

	@Autowired
	LevelRepository levelRepository;

	@Autowired
	ApplicantActivityRepository applicantActivityRespository;

	@Override
	// @Transactional
	public Applicant addApplicant(Applicant applicant) {
		if (!(userRepository.findFirstByEmail(applicant.getApplicant().getEmail()) == null)) {
			throw new DataIntegrityViolationException(Message.APPLICANT_EMAIL_EXISTS);
		} else if (!(userRepository.findFirstByContactNo(applicant.getApplicant().getContactNo()) == null)) {
			throw new DataIntegrityViolationException(Message.APPLICANT_CONTACT_EXISTS);
		} else {
			User user = userRepository.save(applicant.getApplicant());
			applicant.setId(user.getUserId());
			applicant.setApplicant(user);
			Applicant addedApplicant = applicantRepository.save(applicant);
			addedApplicant.setBatch(batchRepository.findById(addedApplicant.getBatch().getBatchId()).get());
			addedApplicant.setLevel(levelRepository.findById(addedApplicant.getLevel().getLevelId()).get());
			return addedApplicant;
		}
	}

	@Override
	@Transactional
	public Applicant editApplicantByApplicant(int id, String password, String name, String contactNo)
			throws InvalidDataException {
		Applicant applicant;
		if (userRepository.findFirstByContactNo(contactNo) != null
				&& !(userRepository.findById(id).get().getContactNo().equals(contactNo))) {
			if (userRepository.findById(id).get().getUserType() == UserType.admin) {
				throw new DataIntegrityViolationException(Message.APPLICANT_CONTACT_EXISTS);
			} else {
				throw new DataIntegrityViolationException(Message.USER_CONTACT_EXISTS);
			}
		} else {
			String dePassword = BCrypt.hashpw(password, BCrypt.gensalt());
			int affectedRows = userRepository.updateUser(dePassword, name, contactNo, "applicant", id);
			if (affectedRows == 1) {
				applicant = applicantRepository.findById(id).get();
			} else {
				throw new InvalidDataException(Message.UPDATE_ERROR);
			}
		}
		return applicant;
	}

	@Override
	@Transactional
	public Applicant editApplicantByAdmin(int id, UserType userType, NAGPStatus nagpStatus, Level level, Batch batch)
			throws InvalidDataException {
		int affectedRows = applicantRepository.updateApplicant(String.valueOf(userType), String.valueOf(nagpStatus),
				level.getLevelId(), batch.getBatchId(), id);
		if (affectedRows == 1) {
			return applicantRepository.findById(id).get();
		} else {
			throw new InvalidDataException(Message.UPDATE_ERROR);
		}

	}

	@Override
	@Transactional
	public List<Applicant> getAllApplicants() {
		return applicantRepository.findAll();
	}

	@Override
	@Transactional
	public List<Applicant> getApplicantsPaginated(int pageNo, int pageSize) {
		return applicantRepository.findAll(PageRequest.of(pageNo - 1, pageSize)).getContent();

	}

	@Override
	public EnumSet<NAGPStatus> getNagpStatus() {
		return EnumSet.allOf(NAGPStatus.class);

	}

	@Override
	@Transactional
	public Applicant getApplicantById(int id) {
		return applicantRepository.findById(id).get();

	}

	@Override
	@Transactional
	public String deleteApplicant(int id) {
		applicantRepository.deleteById(id);
		return Message.DELETE_SUCCESS;

	}

	@Override
	@Transactional
	public Profile getProfile(int id) {
		Applicant applicant = applicantRepository.findById(id).get();
		double accumulatedPoints = getAccumulatedPoints(id);
		double requiredPoints = applicant.getLevel().getQualificationPoints();
		return new Profile(applicant, accumulatedPoints, requiredPoints);
	}

	@Override
	public void updateApplicantLevel(int applicantId) {
		Applicant applicant = applicantRepository.findById(applicantId).get();
		int number = applicant.getLevel().getNumber();
		if (number < levelRepository.findFirstByOrderByNumberDesc().getNumber()
				&& getAccumulatedPoints(applicant.getId()) > levelRepository.findFirstByNumber(number)
						.getQualificationPoints()) {
			applicant.setLevel(levelRepository.findFirstByNumber(applicant.getLevel().getNumber() + 1));
		}
	}

	@Override
	public double getAccumulatedPoints(int id) {
		Applicant applicant = applicantRepository.findById(id).get();
		List<ApplicantActivity> applicantList = applicantActivityRespository.findByApplicant(applicant);
		double accumulatedPoints = 0;
		for (ApplicantActivity applicantActivity : applicantList) {
			if (applicantActivity.getActivity().getLevel().getLevelId() == applicant.getLevel().getLevelId()) {
				accumulatedPoints += applicantActivity.getPoints();
			}
		}
		return accumulatedPoints;
	}
}
