package com.bootcamp.compliancereportgenerator.models;

import java.util.Map;

import lombok.Data;

@Data
public class SheetLine {

	private Map<String, Object> columns;
}