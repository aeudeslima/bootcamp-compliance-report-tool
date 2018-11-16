package com.bootcamp.compliancereportgenerator.controllers.forms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bootcamp.compliancereportgenerator.models.Template;
import com.bootcamp.compliancereportgenerator.models.TemplateAttachment;

import lombok.Data;

@Data
public class TemplateForm {
	
	private Long id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	@Lob
    private String text;
	
	private List<TemplateAttachment> attachments = new ArrayList<>();
	
	private List<MultipartFile> uploadedFiles = new ArrayList<>();
	
	private List<String> attachmentIds = new ArrayList<>();
	
	public TemplateForm() {
	}
	
	public Template toTemplate() {
		Template template = new Template();
		template.setId(id);
		template.setName(name);
		template.setText(text);
		template.setAttachments(attachments);
		uploadedFiles.forEach(file -> {
			TemplateAttachment attachment = new TemplateAttachment();
			attachment.setContentType(file.getContentType());
			attachment.setName(file.getOriginalFilename());
			try {
				attachment.setData(IOUtils.toByteArray(file.getInputStream()));
			} catch (IOException e) {
				throw new RuntimeException("Unable to get uploaded file data", e);
			}
			template.getAttachments().add(attachment);
		});
		return template;
	}
	
	public TemplateForm(Template template) {
		this.id = template.getId();
		this.name = template.getName();
		this.text = template.getText();
		this.attachments = new ArrayList<>(template.getAttachments());
	}
	
}
