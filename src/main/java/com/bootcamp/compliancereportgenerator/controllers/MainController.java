package com.bootcamp.compliancereportgenerator.controllers;

import static com.bootcamp.compliancereportgenerator.util.Paths.ERROR_PAGE;
import static com.bootcamp.compliancereportgenerator.util.Paths.HOME_PAGE;
import static com.bootcamp.compliancereportgenerator.util.Paths.SCHEDULE_LIST_PAGE;
import static com.bootcamp.compliancereportgenerator.util.Paths.SSO_LOGIN_PAGE;
import static com.bootcamp.compliancereportgenerator.util.Paths.SSO_LOGOUT_URL;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
    public String indexPage() {
        return SSO_LOGIN_PAGE;
    }
	
	@Value("applicationName")
	private String applicationName;
	
    @GetMapping(HOME_PAGE)
    public String homePage() throws GeneralSecurityException, IOException {
        return SCHEDULE_LIST_PAGE;
    }

    @GetMapping(SSO_LOGIN_PAGE)
    public String ssoLogin() {
        return SSO_LOGIN_PAGE;
    }
    
    @GetMapping(SSO_LOGOUT_URL) 
    public String ssoLogout(HttpServletRequest request) {
    	request.getSession().invalidate();
        return SSO_LOGIN_PAGE;
    }
 
    @GetMapping(ERROR_PAGE) 
    public String errorPage() {
    	return ERROR_PAGE;
    }
    
}
