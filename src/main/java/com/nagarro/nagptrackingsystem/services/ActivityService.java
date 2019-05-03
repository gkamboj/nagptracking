package com.nagarro.nagptrackingsystem.services;

import java.util.EnumSet;
import java.util.List;

import com.nagarro.nagptrackingsystem.constant.ActivityStatus;
import com.nagarro.nagptrackingsystem.entity.Activity;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;

public interface ActivityService {

	public Activity addActivity(Activity activity) throws InvalidDataException;

	public List<Activity> getActivities();

	public List<Activity> getActivityByBatch(int batchId);

	public List<Activity> getActivityByLevel(int levelId);

	public String deleteActivity(int id);

	public Activity getActivityById(int id);

	public Activity editActivity(Activity activity) throws InvalidDataException;

	public EnumSet<ActivityStatus> getActivityStatus();

}
