package com.bootcamp.compliancereportgenerator.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class SheetLine {
	@Id
	@GeneratedValue
	private Long id;
	
    private String icId;
    private String icName;
    private String icSEM;
    private String day;
    private Boolean workTimeCompliant;
    private Boolean deepWorkBlocksCompliant;
    private Boolean devTimeCompliant;
    private Boolean dailyCiCCompliant;
    private Boolean wsProCompliant;
}