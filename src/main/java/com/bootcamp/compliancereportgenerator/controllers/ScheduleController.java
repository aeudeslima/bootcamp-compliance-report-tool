package com.bootcamp.compliancereportgenerator.controllers;

import static com.bootcamp.compliancereportgenerator.util.Paths.SCHEDULE_BASE;
import static com.bootcamp.compliancereportgenerator.util.Paths.SCHEDULE_EDIT_FORM_URL;
import static com.bootcamp.compliancereportgenerator.util.Paths.SCHEDULE_FORM_PAGE;
import static com.bootcamp.compliancereportgenerator.util.Paths.SCHEDULE_LIST_PAGE;
import static com.bootcamp.compliancereportgenerator.util.Paths.SCHEDULE_DELETE_URL;

import java.util.Optional;

import javax.validation.Valid;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.bootcamp.compliancereportgenerator.controllers.forms.ScheduleForm;
import com.bootcamp.compliancereportgenerator.models.Schedule;
import com.bootcamp.compliancereportgenerator.repository.ReportsRepository;
import com.bootcamp.compliancereportgenerator.services.ScheduleService;

@Controller
public class ScheduleController {

	@Autowired
	private ReportsRepository reportsRepository;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@GetMapping({SCHEDULE_BASE, SCHEDULE_LIST_PAGE})
	public String list(Model model) {
		model.addAttribute("schedules", scheduleService.findAll());
		return SCHEDULE_LIST_PAGE;
	}
	
	@GetMapping({SCHEDULE_EDIT_FORM_URL})
	public String formEditPage(Model model, @PathVariable("schedulerId") Long id) {
		Optional<Schedule> schedule = scheduleService.findById(id);
		if (schedule.isPresent()) {
			model.addAttribute("scheduleForm", new ScheduleForm(schedule.get()));
			model.addAttribute("reports", reportsRepository.findAll());
			return SCHEDULE_FORM_PAGE;
		} else {
			return SCHEDULE_LIST_PAGE;
		}
	}
	
	@GetMapping(SCHEDULE_FORM_PAGE)
	public String formPage(Model model) {
		model.addAttribute("scheduleForm", new ScheduleForm());
		fillFormLists(model);
		return SCHEDULE_FORM_PAGE;
	}
	
	@PostMapping(SCHEDULE_FORM_PAGE)
	public String create(@Valid ScheduleForm scheduleForm, BindingResult errors, Model model) throws SchedulerException {
		if (errors.hasErrors()) {
			fillFormLists(model);
			return SCHEDULE_FORM_PAGE;
		}
		scheduleService.save(scheduleForm.toSchedule());
		model.addAttribute("schedules", scheduleService.findAll());
		return SCHEDULE_LIST_PAGE;
	}
	
	@DeleteMapping(SCHEDULE_DELETE_URL)
	public String delete(Model model, @PathVariable("scheduleId") Long id) throws SchedulerException {
		scheduleService.deleteById(id);
		return "redirect:/" + SCHEDULE_LIST_PAGE;
	}

	private void fillFormLists(Model model) {
		model.addAttribute("reports", reportsRepository.findAll());
	}
}
