package it.digitalgarage.marketplace.commons.fe.security.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;


// TODO: Auto-generated Javadoc
/**
 * The Class PreAuthenticatedProcessingFilter.
 *
 * @author catalano
 */
public class PreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {
	
	/**
	 * Instantiates a new pre authenticated processing filter.
	 *
	 * @param registrazioneBE the registrazione BE
	 * @param roles the roles
	 */
	public PreAuthenticatedProcessingFilter() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter#getPreAuthenticatedPrincipal(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return "N/A";
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter#getPreAuthenticatedCredentials(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "N/A";
	}

	


	
	
}
