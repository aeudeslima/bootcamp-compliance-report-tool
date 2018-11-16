package com.bootcamp.compliancereportgenerator.models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Data
@Entity
public class TemplateAttachment {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String contentType;
	
	@Basic(fetch=FetchType.LAZY)
	@Lob
	private byte[] data;
}
