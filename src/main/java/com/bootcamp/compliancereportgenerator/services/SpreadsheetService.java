package com.bootcamp.compliancereportgenerator.services;

import java.util.List;
import java.util.Optional;

import com.bootcamp.compliancereportgenerator.models.Spreadsheet;

public interface SpreadsheetService {

	Spreadsheet save(Spreadsheet spreadsheet);

	List<Spreadsheet> findAll();

	Optional<Spreadsheet> findById(Long id);

}
