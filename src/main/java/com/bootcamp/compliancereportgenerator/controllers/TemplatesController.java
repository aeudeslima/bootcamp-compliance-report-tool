package com.bootcamp.compliancereportgenerator.controllers;

import static com.bootcamp.compliancereportgenerator.util.Paths.TEMPLATES_BASE;
import static com.bootcamp.compliancereportgenerator.util.Paths.TEMPLATES_DELETE_URL;
import static com.bootcamp.compliancereportgenerator.util.Paths.TEMPLATES_DOWNLOAD_ATTACHMENT_URL;
import static com.bootcamp.compliancereportgenerator.util.Paths.TEMPLATES_EDIT_FORM_URL;
import static com.bootcamp.compliancereportgenerator.util.Paths.TEMPLATES_FORM_PAGE;
import static com.bootcamp.compliancereportgenerator.util.Paths.TEMPLATES_LIST_PAGE;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.bootcamp.compliancereportgenerator.controllers.forms.TemplateForm;
import com.bootcamp.compliancereportgenerator.models.Template;
import com.bootcamp.compliancereportgenerator.models.TemplateAttachment;
import com.bootcamp.compliancereportgenerator.services.TemplateAttachmentService;
import com.bootcamp.compliancereportgenerator.services.TemplateService;

@Controller
public class TemplatesController {
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private TemplateAttachmentService templateAttachmentService;
	
	@GetMapping({TEMPLATES_BASE, TEMPLATES_LIST_PAGE})
	public String list(Model model) {
		List<Template> templateList = templateService.findAll();
		model.addAttribute("templates", templateList);
		return TEMPLATES_LIST_PAGE;
	}
	
	@GetMapping({TEMPLATES_EDIT_FORM_URL})
	public String formEditPage(Model model, @PathVariable("templateId") Long id) {
		Optional<Template> template = templateService.findById(id);
		if (template.isPresent()) {
			model.addAttribute("templateForm", new TemplateForm(template.get()));
			return TEMPLATES_FORM_PAGE;
		} else {
			return TEMPLATES_LIST_PAGE;
		}
	}

	@GetMapping(TEMPLATES_FORM_PAGE)
	public String formPage(Template template, Model model) {
		TemplateForm templateForm = new TemplateForm();
		model.addAttribute("templateForm", templateForm);
		return TEMPLATES_FORM_PAGE;
	}
	
	@GetMapping(TEMPLATES_DOWNLOAD_ATTACHMENT_URL)
	public void downloadAttachment(HttpServletResponse response, @PathVariable Long attachmentId) {
		Optional<TemplateAttachment> attachmentOpt = templateAttachmentService.findById(attachmentId);
		attachmentOpt.ifPresent(attachment -> {
			response.setContentType(attachment.getContentType());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getName() + "\"");
			response.setContentLength(attachment.getData().length);
			
			try {
				IOUtils.writeChunked(attachment.getData(), response.getOutputStream());
			} catch (IOException e) {
				throw new RuntimeException("Could not write file data to output", e);
			}
		});
	}
	
	@PostMapping(TEMPLATES_FORM_PAGE)
	public String create(@Valid TemplateForm templateForm, BindingResult errors) {
		if (errors.hasErrors()) {
			return TEMPLATES_FORM_PAGE;
		} else {
			Template template = templateForm.toTemplate();
			templateForm.getAttachmentIds().forEach(attachmentId -> {
				Optional<TemplateAttachment> attachmentOpt = templateAttachmentService.findById(
						Long.parseLong(attachmentId));
				if (attachmentOpt.isPresent()) {
					template.getAttachments().add(attachmentOpt.get());
				}
			});
			templateService.save(template);
			return "redirect:/" + TEMPLATES_LIST_PAGE;
		}
	}
	
	@DeleteMapping(TEMPLATES_DELETE_URL)
	public String delete(Model model, @PathVariable("templateId") Long id) {
		templateService.deleteById(id);
		return "redirect:/" + TEMPLATES_LIST_PAGE;
	}
			
}
