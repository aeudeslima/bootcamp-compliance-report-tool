package com.bootcamp.compliancereportgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.compliancereportgenerator.models.Report;

public interface ReportsRepository extends JpaRepository<Report, Long> {

}
