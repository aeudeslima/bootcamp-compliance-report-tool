package com.bootcamp.compliancereportgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.compliancereportgenerator.models.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
