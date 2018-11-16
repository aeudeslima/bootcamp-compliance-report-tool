package com.bootcamp.compliancereportgenerator.services.impl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp.compliancereportgenerator.models.SheetLine;
import com.bootcamp.compliancereportgenerator.repository.TemplateRepository;
import com.bootcamp.compliancereportgenerator.services.TemplateService;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

@Service
public class DefaultTemplateService implements TemplateService {
	
	private static final String DEFAULT_TEMPLATE_NAME = "default";
	private static final String DEFAULT_VARIABLE_NAME = "lines";
	private static final String DATE_VARIABLE_NAME = "date";
	private static final String DEFAULT_ENCODING = "utf-8";

	@Autowired
	private TemplateRepository templateRepository;
	
	@Override
	public synchronized String processTemplateText(String templateText, Date date, List<SheetLine> sheetLines) 
			throws TemplateException, IOException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
		StringTemplateLoader templateLoader = new StringTemplateLoader();
		templateLoader.putTemplate(DEFAULT_TEMPLATE_NAME, templateText);
		cfg.setTemplateLoader(templateLoader);
		cfg.setDefaultEncoding(DEFAULT_ENCODING);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
		
		Map<String, Object> root = new HashMap<>();
		root.put(DEFAULT_VARIABLE_NAME, sheetLines);
		root.put(DATE_VARIABLE_NAME, date);
		
		Template temp = cfg.getTemplate(DEFAULT_TEMPLATE_NAME);
		
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		BufferedOutputStream out = new BufferedOutputStream(byteOut);
		OutputStreamWriter writer = new OutputStreamWriter(out);
		try {
			temp.process(root, writer);
			writer.flush();
			
			return byteOut.toString();
		} finally {
			writer.close();
		}
	}

	@Override
	public List<com.bootcamp.compliancereportgenerator.models.Template> findAll() {
		return templateRepository.findAll();
	}

	@Override
	public Optional<com.bootcamp.compliancereportgenerator.models.Template> findById(Long id) {
		return templateRepository.findById(id);
	}
	
	@Override
	public void deleteById(Long id) {
		templateRepository.deleteById(id);
	}

	@Override
	public com.bootcamp.compliancereportgenerator.models.Template save(com.bootcamp.compliancereportgenerator.models.Template template) {
		return templateRepository.save(template);
		
	}

}
