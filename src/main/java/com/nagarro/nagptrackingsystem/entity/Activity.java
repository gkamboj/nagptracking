package com.nagarro.nagptrackingsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activity")
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "activity_id")
	public int activityId;

	@ManyToOne
	@JoinColumn(name = "level_id", nullable = false, updatable = false)
	public Level level;

	@ManyToOne
	@JoinColumn(name = "batch_id")
	public Batch batch;

	@Column(name = "name")
	public String name;

	@Column(name = "description")
	public String description;

	@Column(name = "points")
	public double points;

	@Column(name = "max_qual_times")
	public int maxQualificationTimes;

	public Activity() {
	}

	public Activity(Level level, Batch batch, String name, String description, double points,
			int maxQualificationTimes) {
		this.level = level;
		this.batch = batch;
		this.name = name;
		this.description = description;
		this.points = points;
		this.maxQualificationTimes = maxQualificationTimes;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public int getMaxQualificationTimes() {
		return maxQualificationTimes;
	}

	public void setMaxQualificationTimes(int maxQualificationTimes) {
		this.maxQualificationTimes = maxQualificationTimes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
