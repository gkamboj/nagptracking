package com.nagarro.nagptrackingsystem.servicesimp;

import java.util.EnumSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.nagarro.nagptrackingsystem.constant.ActivityStatus;
import com.nagarro.nagptrackingsystem.constant.Messages;
import com.nagarro.nagptrackingsystem.entity.Activity;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;
import com.nagarro.nagptrackingsystem.repositories.ActivityRepository;
import com.nagarro.nagptrackingsystem.repositories.BatchRepository;
import com.nagarro.nagptrackingsystem.repositories.LevelRepository;
import com.nagarro.nagptrackingsystem.services.ActivityService;

@Service
public class ActivityServiceImp implements ActivityService {

	@Autowired
	ActivityRepository activityRepository;

	@Autowired
	BatchRepository batchRepository;

	@Autowired
	LevelRepository levelRepository;

	@Override
	@Transactional
	public Activity addActivity(Activity activity) throws InvalidDataException {
		Activity checkActivity = activityRepository.findFirstByBatchAndLevelAndName(
				batchRepository.findById(activity.getBatch().getBatchId()).get(),
				levelRepository.findById(activity.getLevel().getLevelId()).get(), activity.getName());
		if (checkActivity != null) {
			throw new DataIntegrityViolationException(Messages.ACTIVITY_EXISTS);
		} else if (activity.getPoints() > levelRepository.findById(activity.getLevel().getLevelId()).get()
				.getQualificationPoints()) {
			throw new InvalidDataException(Messages.ACTIVITY_PONTS_INVALID);
		} else {
			Activity addedActivity = activityRepository.save(activity);
			addedActivity.setBatch(batchRepository.findById(addedActivity.getBatch().getBatchId()).get());
			addedActivity.setLevel(levelRepository.findById(addedActivity.getLevel().getLevelId()).get());
			return addedActivity;
		}
	}

	@Override
	@Transactional
	public List<Activity> getActivities() {
		return activityRepository.findAll();

	}

	@Override
	@Transactional
	public List<Activity> getActivityByBatch(int batchId) {
		return activityRepository.findByBatch(batchRepository.findById(batchId).get());

	}

	@Override
	public EnumSet<ActivityStatus> getActivityStatus() {
		return EnumSet.allOf(ActivityStatus.class);
	}

	@Override
	@Transactional
	public List<Activity> getActivityByLevel(int levelId) {
		return activityRepository.findByLevel(levelRepository.findById(levelId).get());
	}

	@Override
	@Transactional
	public String deleteActivity(int id) {
		activityRepository.deleteById(id);
		return Messages.DELETE_SUCCESS;
	}

	@Override
	@Transactional
	public Activity getActivityById(int id) {
		return activityRepository.findById(id).get();
	}

	@Override
	@Transactional
	public Activity editActivity(int id, Activity activity) throws InvalidDataException {
		Activity editedActivity;
		if (activity.getPoints() > levelRepository.findById(activity.getLevel().getLevelId()).get()
				.getQualificationPoints()) {
			throw new InvalidDataException(Messages.ACTIVITY_PONTS_INVALID);
		} else {
			int affectedRows = activityRepository.editActivity(activity.getName(), activity.getDescription(),
					activity.getMaxQualificationTimes(), id, activity.getPoints());
			if (affectedRows == 1) {
				editedActivity = activityRepository.findById(id).get();
			} else {
				throw new InvalidDataException(Messages.UPDATE_ERROR);
			}
		}
		return editedActivity;
	}

}
