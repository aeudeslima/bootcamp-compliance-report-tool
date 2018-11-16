package com.bootcamp.compliancereportgenerator.services;

import java.util.List;

import com.bootcamp.compliancereportgenerator.models.SheetLine;
import com.bootcamp.compliancereportgenerator.models.Spreadsheet;

public interface SheetImportService {

	List<SheetLine> importComplianceDataSheet(String accessToken, Spreadsheet spreadsheet) throws Exception;

}
