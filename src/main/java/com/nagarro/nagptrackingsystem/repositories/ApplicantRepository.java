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
	@Query(value = "UPDATE user u, applicant a INNER JOIN a on a.applicant_id = u.applicant_id SET u.type = ?1, a.nagp_status = ?2, a.level_id = ?3, u.batch_id = ?4 WHERE u.user_id = ?5", nativeQuery = true)
	public int updateApplicant(String type, String nagpStatus, int levelId, int batchId, int applicantId);
}
