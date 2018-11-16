package com.bootcamp.compliancereportgenerator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.compliancereportgenerator.models.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {

	@EntityGraph(attributePaths = { "attachments" })
	Optional<Template> findById(Long id);

}
