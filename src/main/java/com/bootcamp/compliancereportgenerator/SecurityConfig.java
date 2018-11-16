package com.bootcamp.compliancereportgenerator;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bootcamp.compliancereportgenerator.util.Paths;

@Configuration
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("security.loginPage")
	private String loginPage;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf()
        		.disable()
        	.headers()
        		.frameOptions()
        			.disable()
        	.and()
	        	.authorizeRequests()
	                .mvcMatchers(Paths.SSO_LOGIN_BASE + "/**", Paths.SSO_LOGOUT_URL, Paths.ERROR_PAGE)
	                	.permitAll()
	                .antMatchers("/h2/**", "/", "/css/**", "/img/**", "/js/**")
	                	.permitAll()
	                .anyRequest()
	                	.authenticated()
	                .and()
	                	.addFilterBefore(ssoGoogleFilter(), BasicAuthenticationFilter.class);
        		
    }

	private Filter ssoGoogleFilter() {
		return new SsoGoogleAuthenticationFilter();
	}
    
	public static String getCurrentAccessToken() {
		try {
			Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
	    	OAuth2AuthenticationDetails oauth2Details = (OAuth2AuthenticationDetails) details;
	    	return oauth2Details.getTokenValue();
		} catch (Exception e) {
			return null;
		}
	}
    
}
