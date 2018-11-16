package com.bootcamp.compliancereportgenerator;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.GenericFilterBean;

import com.bootcamp.compliancereportgenerator.util.Paths;

public class SsoGoogleAuthenticationFilter extends GenericFilterBean {

	@Value("${security.loginPage}")
	private String loginPage;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		boolean authenticated = false;
		if (httpRequest.isRequestedSessionIdValid()) {
			authenticated = SecurityConfig.getCurrentAccessToken() != null;
		}
//		{scopedTarget.oauth2ClientContext=org.springframework.security.oauth2.client.DefaultOAuth2ClientContext@366b096b, SPRING_SECURITY_CONTEXT=org.springframework.security.core.context.SecurityContextImpl@242808a3: Authentication: org.springframework.security.oauth2.provider.OAuth2Authentication@242808a3: Principal: 110142853871452124986; Credentials: [PROTECTED]; Authenticated: true; Details: remoteAddress=0:0:0:0:0:0:0:1, sessionId=<SESSION>, tokenType=BearertokenValue=<TOKEN>; Granted Authorities: ROLE_USER}
		if (httpRequest.getRequestURI().equals("/")) {
			if (authenticated) {
				httpResponse.sendRedirect(Paths.HOME_PAGE);
			} else {
				httpResponse.sendRedirect(Paths.SSO_LOGIN_PAGE);
			}
		} else if (authenticated && httpRequest.getRequestURI().equals(Paths.SSO_LOGIN_PAGE)) {
			httpResponse.sendRedirect(Paths.HOME_PAGE);
		} else {
			chain.doFilter(request, response);
		}
	}
	
}
