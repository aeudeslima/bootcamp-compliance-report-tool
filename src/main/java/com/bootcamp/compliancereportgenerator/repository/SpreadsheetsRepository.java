package com.bootcamp.compliancereportgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.compliancereportgenerator.models.Spreadsheet;

public interface SpreadsheetsRepository extends JpaRepository<Spreadsheet, Long> {

}
