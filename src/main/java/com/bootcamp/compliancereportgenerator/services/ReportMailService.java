package com.bootcamp.compliancereportgenerator.services;

import com.bootcamp.compliancereportgenerator.models.Report;

public interface ReportMailService {

	void sendReportMail(String accessToken, Report report, String processedText) throws Exception;

	void sendReportMail(String accessToken, Report report) throws Exception;
	
}
