package it.digitalgarage.marketplace.commons.encryption;

import java.io.Serializable;

public class EncryptedProperty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6077947677561567353L;
	private final String enc;
	private String dec;
	
	public EncryptedProperty(String enc,boolean decryptInMemory) {
		this.enc = enc;
		this.dec = decryptInMemory ? decrypt() : null;
	}
	
	public EncryptedProperty(String enc){
		this(enc,false);
	}
	
	private String decrypt() {
		try {
			return OpenSSLEncryptionService.getDefaultInstance().decrypt(enc);
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	public String toString() {
		return this.dec!=null?this.dec:decrypt();
	}
}
