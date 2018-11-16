package com.bootcamp.compliancereportgenerator.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp.compliancereportgenerator.models.TemplateAttachment;
import com.bootcamp.compliancereportgenerator.repository.TemplateAttachmentRepository;
import com.bootcamp.compliancereportgenerator.services.TemplateAttachmentService;

@Service
public class DefaultTemplateAttachmentService implements TemplateAttachmentService {

	@Autowired
	private TemplateAttachmentRepository templateAttachmentRepository;
	
	@Override
	public Optional<TemplateAttachment> findById(Long id) {
		return templateAttachmentRepository.findById(id);
	}

}
