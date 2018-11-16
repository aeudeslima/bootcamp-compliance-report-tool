package com.bootcamp.compliancereportgenerator.models;

import java.time.DayOfWeek;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Schedule {

	@Id
	@GeneratedValue
	private Long id;
	
	private boolean activated;
	
	private String name;
	
	private Integer hour;
	
	private Integer minute;
	
	@ManyToOne
	private Report report;
	
	@ElementCollection
	private Set<DayOfWeek> daysOfWeek;
	
	
}
