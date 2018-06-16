package it.digitalgarage.marketplace.commons.encryption;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;




/**
 * @author RvS
 *
 */
public interface EncryptionService {

	public static enum EncryptionAlgorithm {
		
		 DES(1,"des"), 
		 DES3(2,"des3"), 
		 DES_EDE3_CBC(3,"des-ede3-cbc"), 
		 AES128(4,"aes128"), 
		 AES192(5,"aes192"), 
		 AES256(6,"aes256"), 
		 AES256_CBC(7,"aes-256-cbc"), 
		 RC2(8,"rc2"), 
		 RC4(9,"rc4"), 
		 BF(10,"bf")
		 ;
		 
		private final int id;
		private final String name;
		 
		private EncryptionAlgorithm(int id, String name){
			this.id=id;
			this.name=name;
		}
		
		
		@Override
		public String toString() {
			return name;
		}
		 
		public String getName() {
			return name;
		}
		
		public int getId() {
			return id;
		}
		 
		public static EncryptionAlgorithm fromId(int id){
			for(EncryptionAlgorithm a : EncryptionAlgorithm.values()){
				if(a.id == id){
					return a;
				}
			}
			return null;
		}


		public static EncryptionAlgorithm fromName(String alg) {
			if(alg!=null){
				for (EncryptionAlgorithm a : values()){
					if(a.name.equals(alg)){
						return a;
					}
				}
			}
			return  null;
		}
	}
	
	
	/**
	 * Encrypts data
	 * <br>
	 * to decrypt: <b>openssl des3 -d -k {password} -in data.p7m.enc -out data.p7m</b>
	 * @param pwd password
	 * @param data {@link File} to encrypt
	 * @param out  {@link File} to write encrypted data into
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 */
	public void encrypt(String pwd, File data, File out) throws IOException, GeneralSecurityException ;
	
	/**
	 * Encrypts data
	 * <br>
	 * to decrypt: <b>openssl des3 -d -k {password} -in data.p7m.enc -out data.p7m</b>
	 * @param pwd password
	 * @param data data to encrypt
	 * @param out {@link OutputStream} to write encrypted data into
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 */
	public void encrypt(String pwd, InputStream data, OutputStream out) throws IOException, GeneralSecurityException ;
	/**
	 * Encrypts data
	 * <br>
	 * to decrypt: <b>openssl des3 -d -k {password} -in data.p7m.enc -out data.p7m</b>
	 * @param pwd password
	 * @param data data to encrypt
	 * @param out {@link OutputStream} to write encrypted data into
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 */
    public void encrypt(String pwd, byte[] data, OutputStream out) throws IOException, GeneralSecurityException ;
    
    /**
	 * Decrypts data
	 * @param pwd password
	 * @param data {@link File} to decrypt
	 * @param out  {@link File} to write decrypted data into
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 */
    public void decrypt(String pwd, File data, File out) throws IOException, GeneralSecurityException;
    
    /**
	 * Decrypts data
	 * @param pwd password
	 * @param data data to decrypt
	 * @param out {@link OutputStream} to write decrypted data into
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 */
    public void decrypt(String pwd, InputStream data, OutputStream out) throws IOException, GeneralSecurityException;
    
    /**
	 * Decrypts data
	 * @param pwd password
	 * @param data data to decrypt
	 * @param out {@link OutputStream} to write decrypted data into
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 */
	public void decrypt(String pwd, byte[] data, OutputStream out) throws IOException, GeneralSecurityException ;
	
	
	
	/**
	 * decrypts a String
	 * @param algorithm the algorithm
	 * @param pwd the password
	 * @param base64Data the data to decrypt
	 * @return the plain text String
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 * 
	 */
	public String decrypt(EncryptionAlgorithm algorithm, String pwd, String base64Data) throws IOException, GeneralSecurityException ;
	
	/**
	 * decrypts a String using default password
	 * @param algorithm the algorithm
	 * @param base64Data the data to decrypt
	 * @return the plain text String
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 * 
	 */
	public String decrypt(EncryptionAlgorithm algorithm, String base64Data) throws IOException, GeneralSecurityException ;
	
	/**
	 * decrypts a String using default password and default algorithm
	 * @param base64Data the data to decrypt
	 * @return the plain text String
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 * 
	 */
	public String decrypt(String base64Data) throws IOException, GeneralSecurityException ;
	

	/**
	 * decrypts a String using default algorithm and provided password
	 * @param pwd the password
	 * @param base64Data the string to encrypt
	 * @return the plain text String
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 * 
	 */
	public String decrypt(String pwd, String base64Data) throws IOException, GeneralSecurityException ;
	
	/**
	 * encrypts a String
	 * @param algorithm the algorithm
	 * @param pwd the password
	 * @param plainText the string to encrypt
	 * @return the base64 encoded encrypted string
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 * 
	 */
	public String encrypt(EncryptionAlgorithm algorithm, String pwd, String plainText) throws IOException, GeneralSecurityException ;
	
	/**
	 * encrypts a String using default password
	 * @param algorithm the algorithm
	 * @param plainText the string to encrypt
	 * @return the base64 encoded encrypted string
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 * 
	 */
	public String encrypt(EncryptionAlgorithm algorithm, String plainText) throws IOException, GeneralSecurityException ;
	
	/**
	 * encrypts a String using default password and default algorithm
	 * @param plainText the string to encrypt
	 * @return the base64 encoded encrypted string
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 * 
	 */
	public String encrypt(String plainText) throws IOException, GeneralSecurityException ;
	
	
	
	/**
	 * encrypts a String using default algorithm and provided password
	 * @param pwd the password
	 * @param plainText the string to encrypt
	 * @return the base64 encoded encrypted string
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 * 
	 */
	public String encrypt(String pwd, String plainText) throws IOException, GeneralSecurityException ;
	
	/**
	 * Encrypts data using the default password
	 * <br>
	 * to decrypt: <b>openssl des3 -d -k {password} -in data.p7m.enc -out data.p7m</b>
	 * @param data {@link File} to encrypt
	 * @param out  {@link File} to write encrypted data into
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 */
	public void encrypt( File data, File out) throws IOException, GeneralSecurityException ;
	
	/**
	 * Encrypts data using the default password
	 * <br>
	 * to decrypt: <b>openssl des3 -d -k {password} -in data.p7m.enc -out data.p7m</b>
	 * @param data data to encrypt
	 * @param out {@link OutputStream} to write encrypted data into
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 */
	public void encrypt( InputStream data, OutputStream out) throws IOException, GeneralSecurityException ;
	/**
	 * Encrypts data using the default password
	 * <br>
	 * to decrypt: <b>openssl des3 -d -k {password} -in data.p7m.enc -out data.p7m</b>
	 * @param data data to encrypt
	 * @param out {@link OutputStream} to write encrypted data into
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 */
    public void encrypt( byte[] data, OutputStream out) throws IOException, GeneralSecurityException ;
    
    /**
	 * Decrypts data using the default password
	 * @param data {@link File} to decrypt
	 * @param out  {@link File} to write decrypted data into
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 */
    public void decrypt( File data, File out) throws IOException, GeneralSecurityException;
    
    /**
	 * Decrypts data using the default password
	 * @param data data to decrypt
	 * @param out {@link OutputStream} to write decrypted data into
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 */
    public void decrypt( InputStream data, OutputStream out) throws IOException, GeneralSecurityException;
    
    /**
	 * Decrypts data using the default password
	 * @param data data to decrypt
	 * @param out {@link OutputStream} to write decrypted data into
	 * @throws IOException on error
	 * @throws GeneralSecurityException on error
	 */
	public void decrypt( byte[] data, OutputStream out) throws IOException, GeneralSecurityException ;
	
	

	/**
	 * 
	 * @param algorithm the alg
	 * @return true if supported, false otherwise
	 */
	public boolean isSupported(EncryptionAlgorithm algorithm);
	
	/**
	 * 
	 * @param bytes bytes
	 * @return string representation of the bytes
	 */
	public String byteToHexString(byte[] bytes);
	
	
	EncryptionAlgorithm getDefaultAlgorithm();

	String getDefaultPassword();
}