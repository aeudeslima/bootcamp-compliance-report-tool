package com.bootcamp.compliancereportgenerator.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class SheetConfig {
	@Id
	@GeneratedValue
	private Long id;
	private String sheetName;
	private String range;
	private Character icIdColumn;
	private Character icNameColumn;
	private Character icSEMColumn;
	private Character dayColumn;
	private Character workTimeColumn;
	private Character deepWorkBlocksColumn;
	private Character devTimeColumn;
	private Character dailyCiCColumn;
	private Character wsProColumn;
}
