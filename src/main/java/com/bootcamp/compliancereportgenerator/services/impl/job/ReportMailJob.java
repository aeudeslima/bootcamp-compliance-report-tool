package com.bootcamp.compliancereportgenerator.services.impl.job;

import java.util.Optional;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.bootcamp.compliancereportgenerator.models.Report;
import com.bootcamp.compliancereportgenerator.models.Schedule;
import com.bootcamp.compliancereportgenerator.models.Template;
import com.bootcamp.compliancereportgenerator.services.ReportMailService;
import com.bootcamp.compliancereportgenerator.services.ScheduleService;
import com.bootcamp.compliancereportgenerator.services.TemplateService;
import com.bootcamp.compliancereportgenerator.services.impl.DefaultScheduleService;

import lombok.extern.java.Log;

@Log
public class ReportMailJob implements Job {

	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private ReportMailService reportMailService;
	
	@Autowired
	private TemplateService templateService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Running ReportMailJob");
		JobDataMap jobDataMap = context.getTrigger().getJobDataMap();
		Long scheduleId = (Long) jobDataMap.get(DefaultScheduleService.SCHEDULE_ID_PARAM);
		String accessToken = (String) jobDataMap.get(DefaultScheduleService.ACCESS_TOKEN_PARAM);
		Optional<Schedule> scheduleOpt = scheduleService.findById(scheduleId);
		if (!scheduleOpt.isPresent()) {
			log.info("Couldn't find the schedule with the id " + scheduleId);
		} else {
			Schedule schedule = scheduleOpt.get();
			Report report = schedule.getReport();
			Optional<Template> templateWithAttachments = templateService.findById(
					report.getTemplate().getId());
			if (templateWithAttachments.isPresent()) {
				report.setTemplate(templateWithAttachments.get());
				try {
					reportMailService.sendReportMail(accessToken, report);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
