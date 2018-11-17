package com.bootcamp.compliancereportgenerator.controllers;

import static com.bootcamp.compliancereportgenerator.util.Paths.SPREADSHEET_BASE;
import static com.bootcamp.compliancereportgenerator.util.Paths.SPREADSHEET_EDIT_FORM_URL;
import static com.bootcamp.compliancereportgenerator.util.Paths.SPREADSHEET_FORM_PAGE;
import static com.bootcamp.compliancereportgenerator.util.Paths.SPREADSHEET_LIST_PAGE;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.bootcamp.compliancereportgenerator.controllers.forms.SpreadsheetForm;
import com.bootcamp.compliancereportgenerator.models.Spreadsheet;
import com.bootcamp.compliancereportgenerator.services.SpreadsheetService;

@Controller
public class SpreadsheetsController {

	@Autowired
	private SpreadsheetService spreadsheetService;
	
	@GetMapping({SPREADSHEET_BASE, SPREADSHEET_LIST_PAGE})
	public String list(Model model) {
		model.addAttribute("spreadsheets", spreadsheetService.findAll());
		return SPREADSHEET_LIST_PAGE;
	}
	
	@GetMapping(SPREADSHEET_FORM_PAGE)
	public String formPage(Model model) {
		model.addAttribute("spreadsheetForm", new SpreadsheetForm());
		return SPREADSHEET_FORM_PAGE;
	}
	
	@GetMapping(SPREADSHEET_EDIT_FORM_URL)
	public String editForm(Model model, @PathVariable("spreadsheetId") Long spreadsheetId) {
		Optional<Spreadsheet> spreadsheet = spreadsheetService.findById(spreadsheetId);
		if (spreadsheet.isPresent()) {
			model.addAttribute("spreadsheetForm", new SpreadsheetForm(spreadsheet.get()));
			return SPREADSHEET_FORM_PAGE;
		}
		return SPREADSHEET_LIST_PAGE;
	}
	
	@PostMapping(SPREADSHEET_FORM_PAGE) 
	public String save(@Valid SpreadsheetForm spreadsheetForm, BindingResult errors, Model model) {
		if (errors.hasErrors()) {
			return SPREADSHEET_FORM_PAGE;
		}
		spreadsheetService.save(spreadsheetForm.toSpreadsheet());
		model.addAttribute("spreadsheets", spreadsheetService.findAll());
		return SPREADSHEET_LIST_PAGE;
	}
	
}
