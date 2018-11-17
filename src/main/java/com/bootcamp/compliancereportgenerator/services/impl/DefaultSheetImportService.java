package com.bootcamp.compliancereportgenerator.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bootcamp.compliancereportgenerator.models.SheetConfig;
import com.bootcamp.compliancereportgenerator.models.SheetLine;
import com.bootcamp.compliancereportgenerator.models.Spreadsheet;
import com.bootcamp.compliancereportgenerator.services.SheetImportService;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

@Service
public class DefaultSheetImportService implements SheetImportService {

	@Value("${applicationName}")
	private String applicationName;
	
	@Override
	public List<SheetLine> importComplianceDataSheet(String accessToken, Spreadsheet spreadsheet) throws Exception {
		final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
		Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod());
		credential.setAccessToken(accessToken);
		Sheets service = new Sheets.Builder(httpTransport, jacksonFactory, credential)
				.setApplicationName(applicationName)
				.build();
		SheetConfig sheetConfig = spreadsheet.getSheetConfig();
		ValueRange response = service.spreadsheets().values().get(
				spreadsheet.getSpreadsheetId(), 
				sheetConfig.getRange()).execute();
		
		return response.getValues().stream()
				.filter(line -> !line.isEmpty())
				.map(line -> { 
					SheetLine sheetLine = new SheetLine();
					sheetLine.setColumns(new HashMap<>());
					IntStream.range(0, spreadsheet.getSheetConfig().getColumnNames().size()).forEach(idx -> {
						sheetLine.getColumns().put(
								spreadsheet.getSheetConfig().getColumnNames().get(idx),
								line.get(idx).toString());
					});
					return sheetLine;})
				.collect(Collectors.toList());
	}

}
