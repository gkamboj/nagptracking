package com.nagarro.nagptrackingsystem.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nagarro.nagptrackingsystem.constant.BatchTechnology;
import com.nagarro.nagptrackingsystem.entity.Batch;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {

	public List<Batch> findByBatchTechnology(BatchTechnology batchTechnology);

	public List<Batch> findByYear(int Year);

	public List<Batch> findByStartDate(Date startDate);

	public Batch findFirstByBatchTechnologyAndStartDate(BatchTechnology batchTechnology, Date startDate);

	@Modifying
	@Query(value = "UPDATE batch SET technology = ?1, description = ?2, start_date = ?3, year = ?4 WHERE batch_id = ?5", nativeQuery = true)
	public int editBatch(String batchTechnology, String description, Date startDate, int year, int id);

}
