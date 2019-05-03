package com.nagarro.nagptrackingsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.nagarro.nagptrackingsystem.constant.NAGPStatus;

@Entity
@Table(name = "applicant")
public class Applicant {

	@Id
	@Column(name = "applicant_id")
	public int applicantId;

	@OneToOne
	@JoinColumn(name = "applicant_id")
	public User applicant;

	@ManyToOne
	@JoinColumn(name = "batch_id")
	public Batch batch;

	@ManyToOne
	@JoinColumn(name = "level_id")
	public Level level;

	@Column(name = "nagp_status")
	@Enumerated(EnumType.STRING)
	public NAGPStatus nagpStatus;

	public Applicant() {
	}

	public Applicant(User applicant, Batch batch, Level level, NAGPStatus nagpStatus) {
		this.applicant = applicant;
		this.batch = batch;
		this.level = level;
		this.nagpStatus = nagpStatus;
	}

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public NAGPStatus getNagpStatus() {
		return nagpStatus;
	}

	public void setNagpStatus(NAGPStatus nagpStatus) {
		this.nagpStatus = nagpStatus;
	}

	public int getId() {
		return applicantId;
	}

	public void setId(int applicantId) {
		this.applicantId = applicantId;
	}

}
