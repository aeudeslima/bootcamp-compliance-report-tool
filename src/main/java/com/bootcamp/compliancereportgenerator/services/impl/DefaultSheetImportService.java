package com.bootcamp.compliancereportgenerator.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

	private static final int COLUMN_CHARACTER_OFFSET = 65;
	
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
		
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		return response.getValues().stream()
				.filter(line -> !line.isEmpty())
				.map(line -> { 
					SheetLine sheetLine = new SheetLine();
					sheetLine.setIcId(getLineStr(line, sheetConfig.getIcIdColumn()));
					sheetLine.setIcName(getLineStr(line, sheetConfig.getIcNameColumn()));
					sheetLine.setIcSEM(getLineStr(line, sheetConfig.getIcSEMColumn()));
					sheetLine.setDay(getLineStr(line, sheetConfig.getDayColumn()));
					sheetLine.setWorkTimeCompliant(getLineBool(line, sheetConfig.getWorkTimeColumn()));
					sheetLine.setDeepWorkBlocksCompliant(getLineBool(line, sheetConfig.getDeepWorkBlocksColumn()));
					sheetLine.setDevTimeCompliant(getLineBool(line, sheetConfig.getDevTimeColumn()));
					sheetLine.setDailyCiCCompliant(getLineBool(line, sheetConfig.getDailyCiCColumn()));
					sheetLine.setWsProCompliant(getLineBool(line, sheetConfig.getWsProColumn()));
					return sheetLine;})
				.filter(sheetLine -> sheetLine.getDay().equals(date))
				.collect(Collectors.toList());
	}
	
	private Object getLine(List<Object> line, char column) {
		return line.get(column - COLUMN_CHARACTER_OFFSET);
	}
	
	private String getLineStr(List<Object> line, char column) {
		Object value = getLine(line, column);
		String strValue = null;
		if (value != null) {
			strValue = value.toString();
		}
		return strValue;
	}

	private boolean getLineBool(List<Object> line, char column) {
		String strValue = getLineStr(line, column);
		return strValue != null && strValue.equalsIgnoreCase("Yes");
	}

}
