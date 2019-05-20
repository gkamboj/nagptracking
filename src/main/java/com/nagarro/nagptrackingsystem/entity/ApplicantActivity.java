package com.nagarro.nagptrackingsystem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nagarro.nagptrackingsystem.constant.ActivityStatus;

@Entity
@Table(name = "applicant_activity")
public class ApplicantActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "applicant_activity_id")
	public int applicantActivityId;

	@ManyToOne(targetEntity = Applicant.class)
	@JoinColumn(name = "applicant_id", referencedColumnName = "applicant_id")
	public Applicant applicant;

	@ManyToOne(targetEntity = Activity.class)
	@JoinColumn(name = "activity_id", referencedColumnName = "activity_id")
	public Activity activity;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "assignor", referencedColumnName = "user_id")
	public User assignor;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	public ActivityStatus activityStatus;

	@Column(name = "percentage")
	public double percentage;

	@Column(name = "points")
	public double points;

	@Column(name = "description")
	public String description;

	@Column(name = "document")
	public byte[] document;

	@Temporal(value = TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "start_date")
	public Date startDate;

	@Temporal(value = TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "done_date")
	public Date doneDate;

	@Temporal(value = TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "completion_date")
	public Date completionDate;

	public ApplicantActivity() {
	}

	public ApplicantActivity(Applicant applicant, Activity activity, User assignor, ActivityStatus activityStatus,
			double percentage, double points, String description, byte[] document, Date startDate, Date doneDate,
			Date completionDate) {
		this.applicant = applicant;
		this.activity = activity;
		this.assignor = assignor;
		this.activityStatus = activityStatus;
		this.percentage = percentage;
		this.points = points;
		this.description = description;
		this.document = document;
		this.startDate = startDate;
		this.doneDate = doneDate;
		this.completionDate = completionDate;
	}

	public int getApplicantActivityId() {
		return applicantActivityId;
	}

	public void setApplicantActivityId(int applicantActivityId) {
		this.applicantActivityId = applicantActivityId;
	}

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public User getAssignor() {
		return assignor;
	}

	public void setAssignor(User assignor) {
		this.assignor = assignor;
	}

	public ActivityStatus getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(ActivityStatus activityStatus) {
		this.activityStatus = activityStatus;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
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

	public Date getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public byte[] getDocument() {
		return document;
	}

	public void setDocument(byte[] document) {
		this.document = document;
	}

}
