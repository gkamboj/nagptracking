package com.nagarro.nagptrackingsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nagarro.nagptrackingsystem.constant.NAGPStatus;
import com.nagarro.nagptrackingsystem.entity.Applicant;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {

	public List<Applicant> findByNagpStatus(NAGPStatus nagpStatus);

	@Modifying
	@Query(value = "UPDATE applicant SET nagp_status = ?1, level_id = ?2, batch_id = ?3 WHERE applicant_id = ?4", nativeQuery = true)
	public int updateApplicant(String nagpStatus, int levelId, int batchId, int applicantId);
}
