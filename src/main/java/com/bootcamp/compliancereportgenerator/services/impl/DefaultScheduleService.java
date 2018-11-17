package com.bootcamp.compliancereportgenerator.services.impl;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp.compliancereportgenerator.SecurityConfig;
import com.bootcamp.compliancereportgenerator.models.Schedule;
import com.bootcamp.compliancereportgenerator.repository.ScheduleRepository;
import com.bootcamp.compliancereportgenerator.services.ScheduleService;
import com.bootcamp.compliancereportgenerator.services.impl.job.ReportMailJob;

@Service
public class DefaultScheduleService implements ScheduleService {

	public static final String SCHEDULE_ID_PARAM = "schedule_id";
	public static final String ACCESS_TOKEN_PARAM = "access_token";

	private static final String DEFAULT_JOB_GROUP = "DefaultJobGroup";
	private static final String JOB_IDENTITY_PATTERN = "Job:%d";
	private static final String TRIGGER_GROUP_PATTERN = "TriggerGroup:%d";
	private static final String TRIGGER_NAME_PATTERN = "Trigger:%d";

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private Scheduler scheduler;
	
	public void schedule(Schedule schedule) throws SchedulerException {
		cancelSchedules(schedule);
		JobDetail jobDetail = newJob(ReportMailJob.class)
				.withIdentity(buildJobKey(schedule))
				.storeDurably()
				.build();
		scheduler.addJob(jobDetail, true);
		for (Trigger trigger : buildTriggers(jobDetail, schedule)) {
			try {
				scheduler.scheduleJob(trigger);
			} catch (SchedulerException e) {
				try {
					cancelSchedules(schedule);
				} catch (SchedulerException e1) {
					throw new RuntimeException("Couldn't cancel imcompleted created jobs", e1);
				}
				throw new RuntimeException("Error creating required Triggers to schedule", e);
			}
		}
	}
	
	public void cancelSchedules(Schedule scheduleConfig) throws SchedulerException {
		scheduler.deleteJob(buildJobKey(scheduleConfig));
	}
	
	@Override
	public Schedule save(Schedule schedule) throws SchedulerException {
		schedule = scheduleRepository.save(schedule);
		schedule(schedule);
		return schedule;
	}
	
	@Override
	public void delete(Schedule schedule) throws SchedulerException {
		cancelSchedules(schedule);
		scheduleRepository.delete(schedule);
	}
	
	@Override
	public Optional<Schedule> findById(Long id) {
		return scheduleRepository.findById(id);
	}
	
	@Override
	public List<Schedule> findAll() {
		return scheduleRepository.findAll();
	}
	
	private JobKey buildJobKey(Schedule scheduleConfig) {
		return new JobKey(
				String.format(JOB_IDENTITY_PATTERN, scheduleConfig.getId()),
				DEFAULT_JOB_GROUP);
	}

	private List<Trigger> buildTriggers(JobDetail jobDetail, Schedule schedule) {
		return schedule.getDaysOfWeek().stream().map(dayOfWeek -> {
			return newTrigger()
				.withIdentity(buildTriggerName(dayOfWeek), buildTriggerGroupName(schedule))
				.withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(
						convertDayOfWeekToDateBuilder(dayOfWeek),
						schedule.getHour(),
						schedule.getMinute()))
				.forJob(jobDetail)
				.usingJobData(SCHEDULE_ID_PARAM, schedule.getId())
				.usingJobData(ACCESS_TOKEN_PARAM, SecurityConfig.getCurrentAccessToken())
				.build();
				
		}).collect(Collectors.toList());
	}
	
	private String buildTriggerGroupName(Schedule scheduleConfig) {
		return String.format(
				TRIGGER_GROUP_PATTERN, 
				scheduleConfig.getId());
	}
	
	private String buildTriggerName(DayOfWeek dayOfWeek) {
		return String.format(
				TRIGGER_NAME_PATTERN, 
				dayOfWeek.getValue());
	}

	private int convertDayOfWeekToDateBuilder(DayOfWeek dayOfWeek) {
		switch(dayOfWeek) {
		case MONDAY: return DateBuilder.MONDAY; 
		case TUESDAY: return DateBuilder.TUESDAY;
		case WEDNESDAY: return DateBuilder.WEDNESDAY;
		case THURSDAY: return DateBuilder.THURSDAY;
		case FRIDAY: return DateBuilder.FRIDAY; 
		case SATURDAY: return DateBuilder.SATURDAY;
		case SUNDAY: return DateBuilder.SUNDAY;
		}
		throw new RuntimeException("No DayOfWeek found for the dayOfWeek param");
	}

	@Override
	public void deleteById(Long id) throws SchedulerException {
		Optional<Schedule> schedule = scheduleRepository.findById(id);
		if (schedule.isPresent()) {
			delete(schedule.get());
		}
	}

}
