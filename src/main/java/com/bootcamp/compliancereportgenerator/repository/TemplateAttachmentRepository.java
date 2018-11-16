package com.bootcamp.compliancereportgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.compliancereportgenerator.models.TemplateAttachment;

public interface TemplateAttachmentRepository extends JpaRepository<TemplateAttachment, Long>{

}
