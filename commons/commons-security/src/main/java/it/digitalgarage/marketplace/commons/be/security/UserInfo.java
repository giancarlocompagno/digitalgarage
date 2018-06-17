package it.digitalgarage.marketplace.commons.be.security;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// TODO: Auto-generated Javadoc
/**
 * The Class UserAuth.
 */
public class UserInfo implements Serializable, UserDetails, CredentialsContainer {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9019341603348974803L;
	
	/** The granted authorities. */
	private Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
	
	private String username;
	private Timestamp date;
	private String token;
	/**
	 * Instantiates a new user auth.
	 *
	 * @param username the username
	 * @param dtg the dtg
	 * @param grantedAuthorities the grantedAuthorities
	 */
	public UserInfo(String username,String token,Collection<GrantedAuthority> grantedAuthorities) {
		this.username=username;
		this.grantedAuthorities = grantedAuthorities;
		this.token = token;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.CredentialsContainer#eraseCredentials()
	 */
	@Override
	public void eraseCredentials() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}
	
	/**
	 * Adds the authority.
	 *
	 * @param grantedAuthority the granted authority
	 */
	public void addAuthority(GrantedAuthority grantedAuthority) {
		this.grantedAuthorities.add(grantedAuthority);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@Override
	public String getPassword() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		return this.username;
	}
	
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public Timestamp getDate() {
		return date;
	}
	
	public String getToken() {
		return token;
	}
}
