package com.bootcamp.compliancereportgenerator;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ComplianceReportGeneratorApplication {

	@Autowired
	private Scheduler scheduler;
	
	@EventListener(ApplicationReadyEvent.class)
	public void onStart() throws SchedulerException {
		scheduler.start();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ComplianceReportGeneratorApplication.class, args);
	}
}
