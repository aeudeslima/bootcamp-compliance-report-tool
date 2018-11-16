package com.bootcamp.compliancereportgenerator.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Report {
	@Id
	@GeneratedValue
    private Long id;
	@NotEmpty
    private String name;
	@NotEmpty
    private String mailSubject;
	@NotEmpty
    private String mailFrom;
	@NotEmpty
    private String mailTo;
    private String mailCc;
    private String mailBcc;
    
    @ManyToOne(cascade= {CascadeType.ALL})
    private Spreadsheet spreadsheet;
    
    @ManyToOne
    @NotNull
    private Template template;
    
}
