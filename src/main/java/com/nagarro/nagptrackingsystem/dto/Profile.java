package com.nagarro.nagptrackingsystem.dto;

import com.nagarro.nagptrackingsystem.entity.Applicant;

public class Profile {
	private Applicant applicant;
	private double accumulatedPoints;
	private double requiredPoints;

	public Profile(Applicant applicant, double accumulatedPoints, double requiredPoints) {
		super();
		this.applicant = applicant;
		this.accumulatedPoints = accumulatedPoints;
		this.requiredPoints = requiredPoints;
	}

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	public double getAccumulatedPoints() {
		return accumulatedPoints;
	}

	public void setAccumulatedPoints(double accumulatedPoints) {
		this.accumulatedPoints = accumulatedPoints;
	}

	public double getRequiredPoints() {
		return requiredPoints;
	}

	public void setRequiredPoints(double requiredPoints) {
		this.requiredPoints = requiredPoints;
	}

}
