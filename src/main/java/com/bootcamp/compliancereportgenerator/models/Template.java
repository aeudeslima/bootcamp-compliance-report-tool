package com.bootcamp.compliancereportgenerator.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
public class Template {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	@Lob
    private String text;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<TemplateAttachment> attachments;
}
