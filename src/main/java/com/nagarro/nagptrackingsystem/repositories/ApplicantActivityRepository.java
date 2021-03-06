package com.nagarro.nagptrackingsystem.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nagarro.nagptrackingsystem.constant.ActivityStatus;
import com.nagarro.nagptrackingsystem.entity.Activity;
import com.nagarro.nagptrackingsystem.entity.Applicant;
import com.nagarro.nagptrackingsystem.entity.ApplicantActivity;

@Repository
public interface ApplicantActivityRepository extends JpaRepository<ApplicantActivity, Integer> {

	public List<ApplicantActivity> findByApplicant(Applicant applicant);

	public List<ApplicantActivity> findByApplicantAndActivity(Applicant applicant, Activity activity);

	public List<ApplicantActivity> findByActivityStatusAndApplicant(ActivityStatus activityStatus, Applicant applicant);

	@Modifying
	@Query(value = "UPDATE applicant_activity SET  status = ?2, completion_date = ?3, description = ?4, document = ?5, done_date = ?6, percentage = ?7, points = ?8, assignor = ?9, start_date = ?10 WHERE applicant_activity_id = ?1", nativeQuery = true)
	public int updateApplicantActivity(int id, String status, Date completionDate, String description, byte[] document,
			Date doneDate, double percentage, double points, int assignorId, Date startDate);

}
