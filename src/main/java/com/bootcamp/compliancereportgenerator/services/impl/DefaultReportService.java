package com.bootcamp.compliancereportgenerator.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp.compliancereportgenerator.models.Report;
import com.bootcamp.compliancereportgenerator.repository.ReportsRepository;
import com.bootcamp.compliancereportgenerator.services.ReportService;

@Service
public class DefaultReportService implements ReportService {

	@Autowired
	private ReportsRepository reportsRepository;

	@Override
	public void save(Report report) {
		reportsRepository.save(report);
	}
	
	@Override
	public List<Report> findAll() {
		return reportsRepository.findAll();
	}
	
	@Override
	public Optional<Report> findById(Long id) {
		return reportsRepository.findById(id);
	}
	
	@Override
	public void deleteById(Long id) {
		reportsRepository.deleteById(id);
	}
	
}
