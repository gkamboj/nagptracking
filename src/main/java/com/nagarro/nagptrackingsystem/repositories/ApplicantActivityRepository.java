package com.nagarro.nagptrackingsystem.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nagarro.nagptrackingsystem.entity.Activity;
import com.nagarro.nagptrackingsystem.entity.Applicant;
import com.nagarro.nagptrackingsystem.entity.ApplicantActivity;

@Repository
public interface ApplicantActivityRepository extends JpaRepository<ApplicantActivity, Integer> {

	public List<ApplicantActivity> findByApplicant(Applicant applicant);

	public List<ApplicantActivity> findByApplicantAndActivity(Applicant applicant, Activity activity);

	@Modifying
	@Query(value = "UPDATE applicant_activity SET  status = ?2, completion_date = ?3, description = ?4, document = ?5, end_date = ?6, percentage = ?7, assignor = ?8, start_date = ?9 WHERE applicant_activity_id = ?1", nativeQuery = true)
	public int updateApplicantActivity(int id, String status, Date completionDate, String description, byte[] document,
			Date endDate, double percentage, int assignorId, Date startDate);

}
