package com.bootcamp.compliancereportgenerator.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp.compliancereportgenerator.models.Report;
import com.bootcamp.compliancereportgenerator.models.SheetConfig;
import com.bootcamp.compliancereportgenerator.models.Spreadsheet;
import com.bootcamp.compliancereportgenerator.repository.ReportsRepository;
import com.bootcamp.compliancereportgenerator.services.ReportService;

@Service
public class DefaultReportService implements ReportService {

	@Autowired
	private ReportsRepository reportsRepository;

	@Override
	public void save(Report report) {
		Spreadsheet spreadsheet = report.getSpreadsheet();
		if (spreadsheet.getSheetConfig() == null) {
			spreadsheet.setSheetConfig(createDefaultSheetConfig());
		}
		if (spreadsheet.getSpreadsheetId() == null || spreadsheet.getSpreadsheetId().isEmpty()) {
			spreadsheet.setSpreadsheetId(extractSpreadsheetId(spreadsheet.getSpreadsheetURL()));
		}
		reportsRepository.save(report);
	}
	
	private SheetConfig createDefaultSheetConfig() {
		SheetConfig sheetConfig = new SheetConfig();
		sheetConfig.setSheetName("Index");
		sheetConfig.setRange("A2:I");
		sheetConfig.setIcIdColumn('A');
		sheetConfig.setIcNameColumn('B');
		sheetConfig.setIcSEMColumn('C');
		sheetConfig.setDayColumn('D');
		sheetConfig.setWorkTimeColumn('E');
		sheetConfig.setDeepWorkBlocksColumn('F');
		sheetConfig.setDevTimeColumn('G');
		sheetConfig.setDailyCiCColumn('H');
		sheetConfig.setWsProColumn('I');
		return sheetConfig;
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
