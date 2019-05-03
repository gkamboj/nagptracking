package com.nagarro.nagptrackingsystem.servicesimp;

import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.nagarro.nagptrackingsystem.constant.BatchTechnology;
import com.nagarro.nagptrackingsystem.constant.Message;
import com.nagarro.nagptrackingsystem.entity.Batch;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;
import com.nagarro.nagptrackingsystem.repositories.BatchRepository;
import com.nagarro.nagptrackingsystem.services.BatchService;

@Service
public class BatchServiceImp implements BatchService {

	@Autowired
	BatchRepository batchRepository;

	@Override
	@Transactional
	public Batch addBatch(Batch batch) throws InvalidDataException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(batch.getStartDate());
		if (batch.getYear() != calendar.get(Calendar.YEAR)) {
			throw new InvalidDataException(Message.BATCH_INVALID_START_DATE);
		} else if (batchRepository.findFirstByBatchTechnologyAndStartDate(batch.getBatchTechnology(),
				batch.getStartDate()) != null) {
			throw new DataIntegrityViolationException(Message.BATCH_EXISTS);
		} else {
			return batchRepository.save(batch);
		}
	}

	@Override
	@Transactional
	public List<Batch> getBatches() {
		return batchRepository.findAll();

	}

	@Override
	public EnumSet<BatchTechnology> getBatchTechnologies() {
		return EnumSet.allOf(BatchTechnology.class);
	}

	@Override
	@Transactional
	public Batch getBatchById(int id) {
		return batchRepository.findById(id).get();
	}

	@Override
	@Transactional
	public String deleteBatch(int id) {
		batchRepository.deleteById(id);
		return Message.DELETE_SUCCESS;
	}

	@Override
	@Transactional
	public Batch editBtach(int id, BatchTechnology batchTechnology, String description, Date startDate, int year)
			throws InvalidDataException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		Batch currBatch = batchRepository.findById(id).get();
		Batch batch = batchRepository.findFirstByBatchTechnologyAndStartDate(batchTechnology, startDate);
		if (year != calendar.get(Calendar.YEAR)) {
			throw new InvalidDataException(Message.BATCH_INVALID_START_DATE);
		} else if (batch != null && batch != currBatch) {
			throw new DataIntegrityViolationException(Message.BATCH_EXISTS);
		} else {
			int affectedRows = batchRepository.editBatch(String.valueOf(batchTechnology), description, startDate, year,
					id);
			if (affectedRows == 1) {
				return batchRepository.findById(id).get();
			} else {
				throw new InvalidDataException(Message.UPDATE_ERROR);
			}
		}
	}

}
