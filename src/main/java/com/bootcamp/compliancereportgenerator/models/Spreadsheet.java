package com.bootcamp.compliancereportgenerator.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
public class Spreadsheet {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty
	private String spreadsheetURL;
	
	private String spreadsheetId;
	
	@ManyToOne(cascade=CascadeType.ALL)
    private SheetConfig sheetConfig;
}
