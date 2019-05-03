package com.nagarro.nagptrackingsystem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nagarro.nagptrackingsystem.constant.BatchTechnology;

@Entity
@Table(name = "batch")
public class Batch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batch_id")
	public int batchId;

	@Column(name = "technology")
	@Enumerated(EnumType.STRING)
	public BatchTechnology batchTechnology;

	@Column(name = "year")
	public int year;

	@Column(name = "description")
	public String description;

	@Temporal(value = TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Column(name = "start_date")
	public Date startDate;

	@Column(name = "batch_name", insertable = false, updatable = false)
	public String batchName;

	public Batch() {
	}

	public Batch(BatchTechnology batchTechnology, int year, String description, Date startDate) {
		this.batchTechnology = batchTechnology;
		this.year = year;
		this.description = description;
		this.startDate = startDate;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public BatchTechnology getBatchTechnology() {
		return batchTechnology;
	}

	public void setBatchTechnology(BatchTechnology batchTechnology) {
		this.batchTechnology = batchTechnology;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getBatchName() {
		return batchName;
	}

}
