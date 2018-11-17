package com.bootcamp.compliancereportgenerator.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp.compliancereportgenerator.models.Spreadsheet;
import com.bootcamp.compliancereportgenerator.repository.SpreadsheetsRepository;
import com.bootcamp.compliancereportgenerator.services.SpreadsheetService;

@Service
public class DefaultSpreadsheetService implements SpreadsheetService {

	@Autowired
	private SpreadsheetsRepository spreadsheetsRepository;
	
	@Override
	public Spreadsheet save(Spreadsheet spreadsheet) {
		if (spreadsheet.getSpreadsheetId() == null || spreadsheet.getSpreadsheetId().isEmpty()) {
			spreadsheet.setSpreadsheetId(extractSpreadsheetId(spreadsheet.getSpreadsheetURL()));
		}
		return spreadsheetsRepository.save(spreadsheet);
	}
	
	@Override
	public List<Spreadsheet> findAll() {
		return spreadsheetsRepository.findAll();
	}
	
	@Override
	public Optional<Spreadsheet> findById(Long id) {
		return spreadsheetsRepository.findById(id);
	}

	private String extractSpreadsheetId(String spreadsheetURL) {
		Pattern pattern = Pattern.compile("\\/spreadsheets\\/d\\/([\\w-]+)");
		Matcher matcher = pattern.matcher(spreadsheetURL);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return "";
		}
	}
}
