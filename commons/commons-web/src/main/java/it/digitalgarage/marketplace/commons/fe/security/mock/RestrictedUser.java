package it.digitalgarage.marketplace.commons.fe.security.mock;

public interface RestrictedUser {
	
	public boolean isAuthorized(String username);

}
