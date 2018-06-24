package it.example.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

public class MySecurityContext {

	public static String getEmail(){
		DefaultOidcUser defaultOidcUser = (DefaultOidcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return defaultOidcUser.getEmail();
	}
	
}
