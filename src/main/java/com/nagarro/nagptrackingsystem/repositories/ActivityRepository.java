package com.nagarro.nagptrackingsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nagarro.nagptrackingsystem.entity.Activity;
import com.nagarro.nagptrackingsystem.entity.Batch;
import com.nagarro.nagptrackingsystem.entity.Level;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	public List<Activity> findByLevel(Level level);

	public List<Activity> findByBatch(Batch batch);

	public Activity findFirstByBatchAndLevelAndName(Batch batch, Level level, String name);

	@Modifying
	@Query(value = "UPDATE query SET name = ?1, description = ?2, max_qual_times = ?3 WHERE activity_id = ?4", nativeQuery = true)
	public int editActivity(String name, String description, int maxQualtimes, int activityId);
}
