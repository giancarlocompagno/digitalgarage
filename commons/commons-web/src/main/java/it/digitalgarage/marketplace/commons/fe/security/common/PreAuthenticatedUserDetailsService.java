package it.digitalgarage.marketplace.commons.fe.security.common;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import it.digitalgarage.marketplace.commons.be.security.UserInfo;


// TODO: Auto-generated Javadoc
/**
 * The Class PreAuthenticatedUserDetailsService.
 */
public class PreAuthenticatedUserDetailsService implements
		AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.AuthenticationUserDetailsService#loadUserDetails(org.springframework.security.core.Authentication)
	 */
	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		UserInfo userAuth = (UserInfo) token.getDetails();
		return userAuth;
	}
}
