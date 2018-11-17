package com.bootcamp.compliancereportgenerator.controllers.forms;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.bootcamp.compliancereportgenerator.models.SheetConfig;
import com.bootcamp.compliancereportgenerator.models.Spreadsheet;

import lombok.Data;

@Data
public class SpreadsheetForm {

	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String spreadsheetURL;
	private String spreadsheetId;
	private Long sheetConfigId;
	@NotEmpty
	private String sheetConfigSheetName;
	@NotEmpty
	private String sheetConfigRange;
	
	private List<String> sheetConfigColumnNames = new ArrayList<>();
	
	public SpreadsheetForm() {
	}
	
	public SpreadsheetForm(Spreadsheet spreadsheet) {
		id = spreadsheet.getId();
		spreadsheetURL = spreadsheet.getSpreadsheetURL();
		spreadsheetId = spreadsheet.getSpreadsheetId();
		name = spreadsheet.getName();
		if (spreadsheet.getSheetConfig() != null) {
			SheetConfig config = spreadsheet.getSheetConfig();
			sheetConfigId = config.getId();
			sheetConfigSheetName = config.getSheetName();
			sheetConfigRange = config.getRange();
			if (config.getColumnNames() != null) {
				config.getColumnNames().forEach(col -> {
					sheetConfigColumnNames.add(col);
				});
			}
		}
	}
	
	public Spreadsheet toSpreadsheet() {
		Spreadsheet spreadsheet = new Spreadsheet();
		spreadsheet.setId(id);
		spreadsheet.setSpreadsheetURL(spreadsheetURL);
		spreadsheet.setSpreadsheetId(spreadsheetId);
		spreadsheet.setName(name);
		
		SheetConfig config = new SheetConfig();
		config.setId(sheetConfigId);
		config.setSheetName(sheetConfigSheetName);
		config.setRange(sheetConfigRange);
		config.setColumnNames(sheetConfigColumnNames);
		spreadsheet.setSheetConfig(config);
		return spreadsheet;
	}
}
