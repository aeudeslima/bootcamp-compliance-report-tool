package com.bootcamp.compliancereportgenerator.services;

import java.util.List;
import java.util.Optional;

import com.bootcamp.compliancereportgenerator.models.Report;

public interface ReportService {
	
	void save(Report report);
	List<Report> findAll();
	Optional<Report> findById(Long id);
	void deleteById(Long id);
	
}
