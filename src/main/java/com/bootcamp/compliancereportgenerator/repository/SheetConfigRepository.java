package com.bootcamp.compliancereportgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.compliancereportgenerator.models.SheetConfig;

public interface SheetConfigRepository extends JpaRepository<SheetConfig, Long> {

	final Long DEFAULT_SHEET_CONFIG_ID = -1L;
	
}
