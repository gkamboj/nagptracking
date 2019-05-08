package com.nagarro.nagptrackingsystem.servicesimp;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.nagarro.nagptrackingsystem.constant.Constants;
import com.nagarro.nagptrackingsystem.entity.Level;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;
import com.nagarro.nagptrackingsystem.repositories.LevelRepository;
import com.nagarro.nagptrackingsystem.services.LevelService;

@Service
public class LevelServiceImp implements LevelService {

	@Autowired
	LevelRepository levelRepository;

	@Override
	@Transactional
	public Level addLevel(Level level) {
		if (levelRepository.findFirstByName(level.getName()) != null) {
			throw new DataIntegrityViolationException(Constants.LEVEL_NAME_EXISTS);
		} else if (levelRepository.findFirstByNumber(level.getNumber()) != null) {
			throw new DataIntegrityViolationException(Constants.LEVEL_NUMBER_EXISTS);
		} else {
			return levelRepository.save(level);
		}

	}

	@Override
	@Transactional
	public List<Level> getLevels() {
		return levelRepository.findAll();
	}

	@Override
	@Transactional
	public Level getLevelById(int id) {
		return levelRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public String deleteLevel(int id) {
		levelRepository.deleteById(id);
		return Constants.DELETE_SUCCESS;
	}

	@Override
	@Transactional
	public Level editLevel(int id, String name, String description, double qualificationPoints)
			throws InvalidDataException {
		Level level;
		if (!(getLevelById(id).getName().equals(name)) && levelRepository.findFirstByName(name) != null) {
			throw new DataIntegrityViolationException(Constants.LEVEL_NAME_EXISTS);
		} else {
			int affectedRows = levelRepository.editLevel(name, description, qualificationPoints, id);
			if (affectedRows == 1) {
				level = levelRepository.findById(id).get();
			} else {
				throw new InvalidDataException(Constants.UPDATE_ERROR);
			}
		}
		return level;
	}

}
