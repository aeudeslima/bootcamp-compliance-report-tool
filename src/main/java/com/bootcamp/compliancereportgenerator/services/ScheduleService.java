package com.bootcamp.compliancereportgenerator.services;

import java.util.List;
import java.util.Optional;

import org.quartz.SchedulerException;

import com.bootcamp.compliancereportgenerator.models.Schedule;

public interface ScheduleService {

	Schedule save(Schedule schedule) throws SchedulerException;

	Optional<Schedule> findById(Long id);

	List<Schedule> findAll();

	void delete(Schedule schedule) throws SchedulerException;

	void deleteById(Long id) throws SchedulerException;
}
