package com.bootcamp.compliancereportgenerator.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.bootcamp.compliancereportgenerator.models.SheetLine;
import com.bootcamp.compliancereportgenerator.models.Template;

public interface TemplateService {

	public String processTemplateText(String templateText, Date date, List<SheetLine> sheetLines) throws Exception;
	
	public List<Template> findAll();
	
	public Optional<Template> findById(Long id);
	
	void deleteById(Long id);
	
	Template save(Template template);
}
