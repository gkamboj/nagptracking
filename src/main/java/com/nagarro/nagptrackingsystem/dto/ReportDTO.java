package com.nagarro.nagptrackingsystem.dto;

//import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class ReportDTO {

	@CsvBindByPosition(position = 0)
	private int id;

	@CsvBindByPosition(position = 1)
	private String email;

	@CsvBindByPosition(position = 2)
	private String name;

	@CsvBindByPosition(position = 3)
	private double monthPoints;

	@CsvBindByPosition(position = 4)
	private double totalPoints;

	@CsvBindByPosition(position = 5)
	private int noOfActivities;

	@CsvBindByPosition(position = 6)
	private double maxPoints;

	public ReportDTO() {
	}

	public ReportDTO(int id, String email, String name, double monthPoints, double totalPoints, int noOfActivities,
			double maxPoints) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.monthPoints = monthPoints;
		this.totalPoints = totalPoints;
		this.noOfActivities = noOfActivities;
		this.maxPoints = maxPoints;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMonthPoints() {
		return monthPoints;
	}

	public void setMonthPoints(double monthPoints) {
		this.monthPoints = monthPoints;
	}

	public double getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(double totalPoints) {
		this.totalPoints = totalPoints;
	}

	public int getNoOfActivities() {
		return noOfActivities;
	}

	public void setNoOfActivities(int noOfActivities) {
		this.noOfActivities = noOfActivities;
	}

	public double getMaxPoints() {
		return maxPoints;
	}

	public void setMaxPoints(double maxPoints) {
		this.maxPoints = maxPoints;
	}

}
