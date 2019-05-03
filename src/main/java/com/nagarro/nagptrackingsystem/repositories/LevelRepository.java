package com.nagarro.nagptrackingsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nagarro.nagptrackingsystem.entity.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {

	public Level findFirstByNumber(int number);

	public List<Level> findByName(String name);

	public Level findFirstByName(String name);

	public Level findFirstByOrderByNumberDesc();

	@Modifying
	@Query(value = "UPDATE level SET name = ?1, description = ?2, qualification_points = ?3 WHERE level_id = ?4", nativeQuery = true)
	public int editLevel(String name, String description, double qualificationPoints, int levelId);
}
