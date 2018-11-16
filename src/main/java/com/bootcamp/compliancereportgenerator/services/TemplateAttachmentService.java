package com.bootcamp.compliancereportgenerator.services;

import java.util.Optional;

import com.bootcamp.compliancereportgenerator.models.TemplateAttachment;

public interface TemplateAttachmentService {

	Optional<TemplateAttachment> findById(Long id);
}
