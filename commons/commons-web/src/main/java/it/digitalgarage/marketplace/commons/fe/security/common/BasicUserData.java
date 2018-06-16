package it.digitalgarage.marketplace.commons.fe.security.common;
 
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
 
/**
 * This class is provide the user details which is needed for authentication
 * 
 * @author malalanayake
 * 
 */
public class BasicUserData implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Collection<? extends GrantedAuthority> list = null;
    String userName = null;
    boolean status = false;
 
    public BasicUserData() {
        list = new ArrayList<GrantedAuthority>();
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.list;
    }
 
    public void setAuthorities(Collection<? extends GrantedAuthority> roles) {
        this.list = roles;
    }
 
    public void setAuthentication(boolean status) {
        this.status = status;
    }
 
    @Override
    public String getPassword() {
        return null;
    }
 
 
    @Override
    public String getUsername() {
        return this.userName;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
 
}