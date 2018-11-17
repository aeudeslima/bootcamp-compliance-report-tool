package com.bootcamp.compliancereportgenerator.controllers.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bootcamp.compliancereportgenerator.models.Report;
import com.bootcamp.compliancereportgenerator.models.Spreadsheet;
import com.bootcamp.compliancereportgenerator.models.Template;

import lombok.Data;

@Data
public class ReportForm {

	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String mailSubject;
	@NotEmpty
	private String mailFrom;
	@NotEmpty
	private String mailTo;
	private String mailCc;
	private String mailBcc;
	@NotNull
	private Long spreadsheetId;
	@NotNull
	private Long templateId;
	
	public ReportForm() {
	}
	
	public ReportForm(Report report) {
		id = report.getId();
		name = report.getName();
		mailSubject = report.getMailSubject();
		mailFrom = report.getMailFrom();
		mailTo = report.getMailTo();
		mailCc = report.getMailCc();
		mailBcc = report.getMailBcc();
		if (report.getSpreadsheet() != null) {
			spreadsheetId = report.getSpreadsheet().getId();
		}
		if (report.getTemplate() != null) {
			templateId = report.getTemplate().getId();
		}
	}
	
	public Report toReport() {
		Report report = new Report();
		report.setId(id);
		report.setName(name);
		report.setMailSubject(mailSubject);
		report.setMailFrom(mailFrom);
		report.setMailTo(mailTo);
		report.setMailCc(mailCc);
		report.setMailBcc(mailBcc);
		
		Spreadsheet spreadsheet = new Spreadsheet();
		spreadsheet.setId(spreadsheetId);
		report.setSpreadsheet(spreadsheet);
		
		Template template = new Template();
		template.setId(templateId);
		report.setTemplate(template);
		
		return report;
	}
	
}
