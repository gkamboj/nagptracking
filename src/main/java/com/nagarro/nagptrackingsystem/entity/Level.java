package com.nagarro.nagptrackingsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "level")
public class Level {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "level_id")
	public int levelId;
	
	@Column(name = "name")
	public String name;
	
	@Column(name = "number")
	public int number;
	
	@Column(name = "description")
	public String description;
	
	@Column(name = "qualification_points")
	public double qualificationPoints;
	
	public Level() {
		
	}

	public Level(String name, int number, String description, double qualificationPoints) {
		this.name = name;
		this.number = number;
		this.description = description;
		this.qualificationPoints = qualificationPoints;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getQualificationPoints() {
		return qualificationPoints;
	}

	public void setQualificationPoints(double qualificationPoints) {
		this.qualificationPoints = qualificationPoints;
	}
	
}
