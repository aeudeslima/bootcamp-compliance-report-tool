package com.bootcamp.compliancereportgenerator.controllers;

import static com.bootcamp.compliancereportgenerator.util.Paths.REPORTS_BASE;
import static com.bootcamp.compliancereportgenerator.util.Paths.REPORTS_DELETE_URL;
import static com.bootcamp.compliancereportgenerator.util.Paths.REPORTS_EDIT_FORM_URL;
import static com.bootcamp.compliancereportgenerator.util.Paths.REPORTS_FORM_PAGE;
import static com.bootcamp.compliancereportgenerator.util.Paths.REPORTS_LIST_PAGE;
import static com.bootcamp.compliancereportgenerator.util.Paths.REPORTS_PREVIEW_PAGE;
import static com.bootcamp.compliancereportgenerator.util.Paths.REPORTS_PREVIEW_URL;
import static com.bootcamp.compliancereportgenerator.util.Paths.REPORTS_SENDMAIL_URL;
import static com.bootcamp.compliancereportgenerator.util.Paths.REPORTS_MAILSENT_PAGE;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.bootcamp.compliancereportgenerator.SecurityConfig;
import com.bootcamp.compliancereportgenerator.controllers.forms.ReportForm;
import com.bootcamp.compliancereportgenerator.models.Report;
import com.bootcamp.compliancereportgenerator.models.SheetLine;
import com.bootcamp.compliancereportgenerator.services.ReportMailService;
import com.bootcamp.compliancereportgenerator.services.ReportService;
import com.bootcamp.compliancereportgenerator.services.SheetImportService;
import com.bootcamp.compliancereportgenerator.services.TemplateService;

@Controller
public class ReportsController {
	
	@Autowired
	private ReportService reportsService;
	
	@Autowired
	private TemplateService templateService;

	@Autowired
	private SheetImportService sheetImportService;

	@Autowired
	private ReportMailService reportMailService;
	
	@GetMapping({REPORTS_BASE, REPORTS_LIST_PAGE})
	public String list(Model model) {
		List<Report> reportList = reportsService.findAll();
		model.addAttribute("reports", reportList);
		return REPORTS_LIST_PAGE;
	}
	
	@GetMapping({REPORTS_EDIT_FORM_URL})
	public String formEditPage(Model model, @PathVariable("reportId") Long id) {
		Optional<Report> report = reportsService.findById(id);
		if (report.isPresent()) {
			model.addAttribute("templates", templateService.findAll());
			model.addAttribute("reportForm", new ReportForm(report.get()));
			return REPORTS_FORM_PAGE;
		} else {
			return REPORTS_LIST_PAGE;
		}
	}

	@GetMapping(REPORTS_FORM_PAGE)
	public String formPage(Report report, Model model) {
		ReportForm reportForm = new ReportForm();
		model.addAttribute("templates", templateService.findAll());
		model.addAttribute("reportForm", reportForm);
		return REPORTS_FORM_PAGE;
	}
	
	@PostMapping(REPORTS_FORM_PAGE)
	public String create(@Valid ReportForm reportForm, BindingResult errors, Model model) {
		if (errors.hasErrors()) {
			return REPORTS_FORM_PAGE;
		} else {
			reportsService.save(reportForm.toReport());
			return "redirect:" + REPORTS_LIST_PAGE;
		}
	}
	
	@GetMapping(REPORTS_PREVIEW_URL)
	public String preview(@PathVariable("reportId") Long id, Model model) throws Exception {
		Optional<Report> report = reportsService.findById(id);
		if (report.isPresent()) {
			List<SheetLine> sheetData = sheetImportService.importComplianceDataSheet(
					SecurityConfig.getCurrentAccessToken(),
					report.get().getSpreadsheet());
			String templateText = report.get().getTemplate().getText();
			model.addAttribute("previewText", templateService.processTemplateText(
					templateText, 
					new Date(),
					sheetData));
			return REPORTS_PREVIEW_PAGE;
		} else {
			return REPORTS_LIST_PAGE;
		}
	}
	
	@DeleteMapping(REPORTS_DELETE_URL)
	public String delete(@PathVariable("reportId") Long id) {
		reportsService.deleteById(id);
		return "redirect:" + REPORTS_LIST_PAGE;
	}
	
	@GetMapping(REPORTS_SENDMAIL_URL)
	public String sendMail(Model model, @PathVariable("reportId") Long id) throws Exception {
		Optional<Report> report = reportsService.findById(id);
		if (report.isPresent()) {
			reportMailService.sendReportMail(SecurityConfig.getCurrentAccessToken(), report.get());
		}
		model.addAttribute("reports", reportsService.findAll());
		return REPORTS_MAILSENT_PAGE;
	}
	
	@GetMapping(REPORTS_MAILSENT_PAGE)	
	public String mailSent() {
		return REPORTS_MAILSENT_PAGE;
	}
}
