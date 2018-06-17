package it.digitalgarage.marketplace.commons.fe.security.mock;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import it.digitalgarage.marketplace.commons.be.security.UserInfo;
import it.digitalgarage.marketplace.commons.logging.Logger;
import it.digitalgarage.marketplace.commons.logging.LoggerFactory;

public class PreAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, UserInfo> {

	private static Logger LOGGER = LoggerFactory.getLogger(PreAuthenticationDetailsSource.class);


	public RestrictedUser restrictedUser;

	public PreAuthenticationDetailsSource() {
		
	}
	
	public void setRestrictedUser(RestrictedUser restrictedUser) {
		this.restrictedUser = restrictedUser;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationDetailsSource#buildDetails(java.lang.Object)
	 */
	@Override
	public UserInfo buildDetails(HttpServletRequest request) {
		//"SMMAIL":"fabio.tornari@eni.com","SMTIPOLOGIAUTENTE":"D","SMGIVENNAME":"FABIO","SMSN":"TORNARI","SMUSERNAME":"EN21347"
		String username = request.getHeader("SMUSERNAME");// request.getHeader("cn");
		if(username!=null && isAuthorized(username)){
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			UserInfo user = new UserInfo(username, null,grantedAuthorities);
			return user;
		}else{

			LOGGER.warn("impossibile autenticare l'utente, uno o piu' campi non sono valorizzati: username: {} ",username);

			throw new PreAuthenticatedCredentialsNotFoundException("Credentials not found!");
		}
	}

	public boolean isAuthorized(String username){
		return restrictedUser==null || restrictedUser.isAuthorized(username);
	}

	/**
		o=Organization
		cn=Common Name
		sn=Surname
		giveName=Name
		ENICodiceFiscale=Codice Fiscale
		EniTelUfficio=Telefono Ufficio presso ENI
		telephoneNumber=Telefono Ufficio presso ENI
		ENIQualifica=Qualifica
		ENIUnita=Unit� d�appartenenza
		ENIDivisione=Divisione
		ENIRuolo=Ruolo
		ENITipologiaUtente=Tipologia Utente
		organizationname
		departmentNumber=Numero Dipartimento
		mail=Indirizzo di Posta Elettronica
		manager
		preferredLanguage=Lingua preferita
		sm_userloginname=Login Name digitato dall�Utente
		ENIMatricola=Matricola ENI dell�utente
		ENIMatricolaNotes=Identificativo ENI dell�utente
		ENIMatricolaSAP=Identificativo SAP dell�utente
		ENIPkMeta=Identificativo PKMeta dell�utente
	 **/
}