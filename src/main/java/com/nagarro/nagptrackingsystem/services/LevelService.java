package com.nagarro.nagptrackingsystem.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nagarro.nagptrackingsystem.entity.Level;
import com.nagarro.nagptrackingsystem.exceptions.InvalidDataException;

@Service
public interface LevelService {

	Level addLevel(Level level);

	List<Level> getLevels();

	Level getLevelById(int id);

	String deleteLevel(int id);

	Level editLevel(int id, String name, String description, double qualificationPoints) throws InvalidDataException;

}
