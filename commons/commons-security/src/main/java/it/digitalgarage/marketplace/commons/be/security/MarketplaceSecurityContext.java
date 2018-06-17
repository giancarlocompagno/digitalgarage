package it.digitalgarage.marketplace.commons.be.security;

import java.sql.Timestamp;
import java.util.Set;

import org.keycloak.KeycloakPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class MarketplaceSecurityContext {
	
	
	public static String getUsername(){
		UserInfo userInfo = getUserInfo();
		if(userInfo!=null){
			return userInfo.getUsername();
		}else{
			return null;
		}
	}
	
	public static Timestamp getDate(){
		UserInfo userInfo = getUserInfo();
		if(userInfo!=null && userInfo.getDate()!=null){
			return userInfo.getDate();
		}else{
			return new Timestamp(System.currentTimeMillis());
		}
	}
	
	public static void setDate(Timestamp date){
		UserInfo userInfo = getUserInfo();
		if(userInfo!=null){
			userInfo.setDate(date);
		}
	}
	
	public static String[] getRoles(){
		if(SecurityContextHolder.getContext()!=null && SecurityContextHolder.getContext().getAuthentication()!=null){
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof KeycloakPrincipal) {
				KeycloakPrincipal p = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Set<String> s = p.getKeycloakSecurityContext().getToken().getRealmAccess().getRoles();
				return s!=null ? s.toArray(new String[]{}) : new String[]{};
			}
		}
		return new String[]{};
	}
	
	public static UserInfo getUserInfo(){
		if(SecurityContextHolder.getContext()!=null && SecurityContextHolder.getContext().getAuthentication()!=null){
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof KeycloakPrincipal) {
				KeycloakPrincipal p = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				return new UserInfo(p.getName(), p.getKeycloakSecurityContext().getTokenString(), null);
			}
		}
		return null;
//		return (UserInfo)(SecurityContextHolder.getContext()!=null && SecurityContextHolder.getContext().getAuthentication()!=null?SecurityContextHolder.getContext().getAuthentication().getPrincipal():null);
	}

	public static String getAccessToken(){
		UserInfo userInfo = getUserInfo();
		if(userInfo!=null){
			return userInfo.getToken();
		}else{
			return null;
		}
	}
	

}

