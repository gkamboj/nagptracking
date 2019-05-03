package com.nagarro.nagptrackingsystem.services;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import com.nagarro.nagptrackingsystem.constant.BatchTechnology;
import com.nagarro.nagptrackingsystem.entity.Batch;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;

public interface BatchService {

	Batch addBatch(Batch batch) throws InvalidDataException;

	List<Batch> getBatches();

	EnumSet<BatchTechnology> getBatchTechnologies();

	Batch getBatchById(int id);

	String deleteBatch(int id);

	Batch editBtach(int id, BatchTechnology batchTechnology, String description, Date startDate, int year)
			throws InvalidDataException;

}
