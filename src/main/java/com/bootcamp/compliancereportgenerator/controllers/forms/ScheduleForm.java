package com.bootcamp.compliancereportgenerator.controllers.forms;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bootcamp.compliancereportgenerator.models.Report;
import com.bootcamp.compliancereportgenerator.models.Schedule;

import lombok.Data;

@Data
public class ScheduleForm {

	private Long id;
	@NotEmpty
	private String name;
	@NotNull
	private Long reportId;
	@NotEmpty
	private List<String> weekDays = new ArrayList<>();
	@NotEmpty
	private String time;
	
	public ScheduleForm() {
	}
	
	public ScheduleForm(Schedule schedule) {
		id = schedule.getId();
		name = schedule.getName();
		if (schedule.getReport() != null) {
			reportId = schedule.getReport().getId();
		}
		if (schedule.getDaysOfWeek() != null) {
			weekDays = schedule.getDaysOfWeek().stream()
					.map(dayOfWeek -> String.valueOf(dayOfWeek.getValue()))
					.collect(Collectors.toList());
		}
		time = String.format("%02d:%02d", schedule.getHour(), schedule.getMinute());
	}
	
	public Schedule toSchedule() {
		Schedule schedule = new Schedule();
		schedule.setId(id);
		schedule.setName(name);
		
		Report report = new Report();
		report.setId(reportId);
		schedule.setReport(report);
		
		Pattern pattern = Pattern.compile("(\\d+):(\\d+)");
		Matcher matcher = pattern.matcher(time);
		if (matcher.find()) {
			schedule.setHour(Integer.parseInt(matcher.group(1)));
			schedule.setMinute(Integer.parseInt(matcher.group(2)));
		}

		schedule.setDaysOfWeek(weekDays.stream()
				.map(weekDay -> DayOfWeek.of(Integer.parseInt(weekDay)))
				.collect(Collectors.toSet()));
	
		return schedule;
	}
	
}
