package com.bootcamp.compliancereportgenerator.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bootcamp.compliancereportgenerator.models.Report;
import com.bootcamp.compliancereportgenerator.models.SheetLine;
import com.bootcamp.compliancereportgenerator.models.TemplateAttachment;
import com.bootcamp.compliancereportgenerator.services.ReportMailService;
import com.bootcamp.compliancereportgenerator.services.SheetImportService;
import com.bootcamp.compliancereportgenerator.services.TemplateService;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import lombok.extern.java.Log;

@Service
@Log
public class DefaultReportMailService implements ReportMailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private SheetImportService sheetImportService;
	
	@Value("${applicationName}")
	private String applicationName;
	
	public static Message createMessageWithEmail(MimeMessage email) throws IOException, MessagingException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		email.writeTo(baos);
		String encodedEmail = Base64.encodeBase64URLSafeString(baos.toByteArray());
		Message message = new Message();
		message.setRaw(encodedEmail);
		return message;
	}
	
	public void sendMail(String accessToken, Message message) throws GeneralSecurityException, IOException {
		final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
		Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod());
		credential.setAccessToken(accessToken);
		Gmail service = new Gmail.Builder(httpTransport, jacksonFactory, credential)
				.setApplicationName(applicationName)
				.build();
		log.info("Sending mail");
		service.users().messages().send("me", message).execute();
		log.info("Mail sent");
	}
	
	private String[] buildMailList(String mailValue) {
		List<String> resultList = new ArrayList<>();
		if (mailValue.contains(".") || mailValue.contains(";")) {
			String[] mails = mailValue.split("[,;]");
			for (String mail : mails) {
				resultList.add(mail);
			}
		} else {
			resultList.add(mailValue);
		}
		return resultList.toArray(new String[resultList.size()]);
	}
	
	@Override
	public void sendReportMail(String accessToken, Report report, String processedText) throws Exception {
		log.info("Preparing mail to send");
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
		helper.setTo(buildMailList(report.getMailTo()));
		helper.setFrom(report.getMailFrom());
		helper.setSubject(report.getMailSubject());
		if (report.getMailCc() != null && !report.getMailCc().isEmpty()) {
			helper.setCc(buildMailList(report.getMailCc()));
		}
		if (report.getMailBcc() != null && !report.getMailBcc().isEmpty()) {
			helper.setBcc(buildMailList(report.getMailBcc()));
		}
		helper.setText(processedText, true);
		if (report.getTemplate().getAttachments() != null) {
			for (TemplateAttachment attachment : report.getTemplate().getAttachments()) {
				ByteArrayResource resource = new ByteArrayResource(attachment.getData());
				helper.addAttachment(attachment.getName(), resource);
			}
		}
		sendMail(accessToken, createMessageWithEmail(helper.getMimeMessage()));
	}
	
	@Override
	public void sendReportMail(String accessToken, Report report) throws Exception {
		List<SheetLine> sheetDataList = sheetImportService.importComplianceDataSheet(
				accessToken, report.getSpreadsheet());
		String processedText = templateService.processTemplateText(
				report.getTemplate().getText(),
				new Date(),
				sheetDataList);
		sendReportMail(accessToken, report, processedText);
	}

}
