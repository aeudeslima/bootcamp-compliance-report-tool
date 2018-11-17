package com.bootcamp.compliancereportgenerator.models;

import java.util.List;

import javax.persistence.ElementCollection;
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
	
	@ElementCollection
	private List<String> columnNames;
}
