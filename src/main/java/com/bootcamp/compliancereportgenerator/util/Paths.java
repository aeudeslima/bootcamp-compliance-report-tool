package com.bootcamp.compliancereportgenerator.util;

public class Paths {

	public static final String SSO_LOGIN_BASE = "/ssologin";
	public static final String SSO_LOGIN_PAGE = SSO_LOGIN_BASE + "/google";
	public static final String SSO_LOGOUT_URL = "/ssologout";
	
	public static final String HOME_PAGE = "/home";
	
	public static final String TEMPLATES_BASE = "/templates";
	public static final String TEMPLATES_LIST_PAGE = TEMPLATES_BASE + "/list";
	public static final String TEMPLATES_FORM_PAGE = TEMPLATES_BASE + "/form";
	public static final String TEMPLATES_EDIT_FORM_URL = TEMPLATES_BASE + "/form/{templateId}";
	public static final String TEMPLATES_DELETE_URL = TEMPLATES_BASE + "/{templateId}";
	public static final String TEMPLATES_DOWNLOAD_ATTACHMENT_URL = TEMPLATES_BASE + "/attachment/{attachmentId}";
	
	public static final String REPORTS_BASE = "/reports";
	public static final String REPORTS_LIST_PAGE = REPORTS_BASE + "/list";
	public static final String REPORTS_FORM_PAGE = REPORTS_BASE + "/form";
	public static final String REPORTS_EDIT_FORM_URL = REPORTS_BASE + "/form/{reportId}";
	public static final String REPORTS_DELETE_URL = REPORTS_BASE + "/{reportId}";
	public static final String REPORTS_PREVIEW_URL = REPORTS_BASE + "/preview/{reportId}";
	public static final String REPORTS_PREVIEW_PAGE = REPORTS_BASE + "/preview";
	public static final String REPORTS_SENDMAIL_URL = REPORTS_BASE + "/sendmail/{reportId}";
	public static final String REPORTS_MAILSENT_PAGE = REPORTS_BASE + "/mailsent";

	public static final String SCHEDULE_BASE = "/schedule";
	public static final String SCHEDULE_LIST_PAGE = SCHEDULE_BASE + "/list";
	public static final String SCHEDULE_FORM_PAGE = SCHEDULE_BASE + "/form";
	public static final String SCHEDULE_EDIT_FORM_URL = SCHEDULE_BASE + "/form/{schedulerId}";
	
	public static final String SPREADSHEET_BASE = "/spreadsheets";
	public static final String SPREADSHEET_LIST_PAGE = SPREADSHEET_BASE + "/list";
	public static final String SPREADSHEET_FORM_PAGE = SPREADSHEET_BASE + "/form";
	public static final String SPREADSHEET_EDIT_FORM_URL = SPREADSHEET_BASE + "/form/{spreadsheetId}";
	
	public static final String ERROR_PAGE = "/error";
	
	
}
