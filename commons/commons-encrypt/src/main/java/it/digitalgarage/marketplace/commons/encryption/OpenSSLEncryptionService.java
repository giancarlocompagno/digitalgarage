package it.digitalgarage.marketplace.commons.encryption;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64InputStream;




/**
 * 
 * @author RvS
 *
 */
public class OpenSSLEncryptionService implements EncryptionService {
	
	
	private static  final Map<String, Boolean> supportedAlgorithms = new HashMap<String, Boolean>();
	static{
		for (EncryptionAlgorithm alg : EncryptionAlgorithm.values()) {
			supportedAlgorithms.put(alg.getName(), 
						Boolean.valueOf(
								 alg!=EncryptionAlgorithm.AES192 && alg!=EncryptionAlgorithm.AES256
								)
					);
		}
	}
	

	private final String defaultPassword;
	private final EncryptionAlgorithm defaultAlgorithm;
	
	
	
	
	
	/**
	 * 
	 * @param algName a
	 * @param pwd p
	 * @param data d
	 * @param out o
	 * @throws IOException i
	 * @throws GeneralSecurityException g
	 */
	private void encryptInternal(String algName, String pwd, InputStream data,
			OutputStream out)  throws IOException, GeneralSecurityException{
		 
		 checkData(pwd, algName);
		 InputStream enc = OpenSSL.encrypt(algName, pwd.toCharArray(), data, false);
		 copyStream(enc, out);
	}
	
	/**
	 * 
	 * @param algName a
	 * @param pwd p
	 * @param data d
	 * @param out o
	 * @throws IOException i
	 * @throws GeneralSecurityException g
	 */
	private void decryptInternal(String algName, String pwd, InputStream data,
			OutputStream out)  throws IOException, GeneralSecurityException{
		 checkData(pwd, algName);
		 InputStream enc = OpenSSL.decrypt(algName, pwd.toCharArray(), data);
		 copyStream(enc, out);
	}
	
	
	
	/**
	 * Constructor with defaultPassword and defaultAl
	 * @param defaultPassword the default password
	 */
	public OpenSSLEncryptionService(String defaultPassword,EncryptionAlgorithm defaultAlgorithm) {
		this.defaultPassword = defaultPassword;
		this.defaultAlgorithm = defaultAlgorithm;
	}
	
	
	
	
	
	@Override
    public void encrypt(String pwd, InputStream data, OutputStream out) throws IOException, GeneralSecurityException {
		encryptInternal(defaultAlgorithm.getName(),  pwd,  data,  out);
	}
	
	@Override
	public void encrypt(String pwd, File data, File out) throws IOException,
			GeneralSecurityException {
		encrypt(pwd, new FileInputStream(data), new FileOutputStream(out));
	}
	
	@Override
    public void encrypt(String pwd, byte[] data, OutputStream out) throws IOException, GeneralSecurityException {
		 encrypt(pwd, new ByteArrayInputStream(data), out);
	}
	
	
	@Override
    public void decrypt(String pwd, InputStream data, OutputStream out) throws IOException, GeneralSecurityException {
		decryptInternal(defaultAlgorithm.getName(), pwd, data, out);
	}
	
	@Override
	public void decrypt(String pwd, File data, File out) throws IOException,
			GeneralSecurityException {
		decrypt(pwd, new FileInputStream(data), new FileOutputStream(out));
	}
	
	@Override
	public void decrypt(String pwd, byte[] data, OutputStream out) throws IOException, GeneralSecurityException {
		decrypt(pwd, new ByteArrayInputStream(data), out);
	}

	 
	private static final int COPY_BUFFER_SIZE = 1024 * 4;
	private static final int NOREAD = -1;
	private static final int MIN_PWD_LENGTH = 4;
	
	/**
	 * Copy stream
	 * @param input input
	 * @param out output
	 * @throws IOException on error
	 */
	 protected void copyStream(InputStream input, OutputStream out)
	            throws IOException {
	        byte[] buffer = new byte[COPY_BUFFER_SIZE];
	        int n = 0;
	        while (NOREAD != (n = input.read(buffer))) {
	        	out.write(buffer, 0, n);
	        }
	        out.flush();
			out.close();
			input.close();
	    }

	
	
	
	@Override
	public void encrypt(File data, File out) throws IOException,
			GeneralSecurityException {
		encrypt(defaultPassword, data, out);
		
	}

	@Override
	public void encrypt(InputStream data, OutputStream out) throws IOException,
			GeneralSecurityException {
		encrypt(defaultPassword, data, out);
	}


	@Override
	public void encrypt(byte[] data, OutputStream out) throws IOException,
			GeneralSecurityException {
		encrypt(defaultPassword, data, out);
	}


	@Override
	public void decrypt(File data, File out) throws IOException,
			GeneralSecurityException {
		decrypt(defaultPassword, data, out);
	}


	@Override
	public void decrypt(InputStream data, OutputStream out) throws IOException,
			GeneralSecurityException {
		decrypt(defaultPassword, data, out);
	}


	@Override
	public void decrypt(byte[] data, OutputStream out) throws IOException,
			GeneralSecurityException {
		decrypt(defaultPassword, data, out);
	}


	
	
	 /**
	  * 
	  * @param password the password to check
	  * @param algName  the alg name
	  * @throws GeneralSecurityException  if the password is invalid
	  */
	private void checkData(String password, String algName) throws GeneralSecurityException{
		if(password==null||password.length()<MIN_PWD_LENGTH){
			throw new GeneralSecurityException(String.format("Password is too short (must be at least %d chars)", MIN_PWD_LENGTH));
		}
		
		Boolean b = supportedAlgorithms.get(algName);
		if(b==null||!b.booleanValue()){
			throw new GeneralSecurityException(String.format("Unsupported Algorithm [%s]", algName));
		}
	}


	@Override
	public String decrypt(EncryptionAlgorithm algorithm, String pwd,
			String base64Data) throws IOException, GeneralSecurityException {
		
		Base64InputStream data = new Base64InputStream(new ByteArrayInputStream(base64Data.getBytes()));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		decryptInternal(algorithm.getName(), pwd, data, out);
		return new String(out.toByteArray());
	}


	@Override
	public String decrypt(EncryptionAlgorithm algorithm, String base64Data)
			throws IOException, GeneralSecurityException {
		return decrypt(algorithm, defaultPassword, base64Data);
	}


	@Override
	public String decrypt(String base64Data) throws IOException,
			GeneralSecurityException {
		return decrypt(defaultAlgorithm, defaultPassword, base64Data);
	}

	@Override
	public String decrypt(String pwd, String base64Data) throws IOException,
			GeneralSecurityException {
		return  decrypt(defaultAlgorithm, pwd, base64Data);
	}

	@Override
	public String encrypt(EncryptionAlgorithm algorithm, String pwd,
			String plainText) throws IOException, GeneralSecurityException {
		ByteArrayInputStream data = new ByteArrayInputStream(plainText.getBytes("UTF-8"));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		encryptInternal(algorithm.getName(), pwd, data, out);
		return  Base64.getEncoder().encodeToString(out.toByteArray());
	}




	@Override
	public String encrypt(EncryptionAlgorithm algorithm, String plainText)
			throws IOException, GeneralSecurityException {
		return encrypt(algorithm, defaultPassword, plainText);
	}


	@Override
	public String encrypt(String plainText) throws IOException,
			GeneralSecurityException {
		return encrypt(defaultAlgorithm, defaultPassword, plainText);
	}

	@Override
	public String encrypt(String pwd, String plainText) throws IOException,
			GeneralSecurityException {
		return  encrypt(defaultAlgorithm, pwd, plainText);
	}


	@Override
	public boolean isSupported(EncryptionAlgorithm algorithm) {
		return algorithm!=null&&supportedAlgorithms.get(algorithm.getName()).booleanValue();
	}

	final private static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	@Override
	public String byteToHexString(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

	@Override
	public EncryptionAlgorithm getDefaultAlgorithm() {
		return defaultAlgorithm;
	}
	
	@Override
	public String getDefaultPassword() {
		return defaultPassword;
	}


	private static final String DEFAULT_PASSWORD = "uy52Nqe2M6Vvy9335Xa6TQ58xN2rn5dXF9RPgAuUFK87haRy";
	private static final EncryptionService encryptionService;

	static{
		String pwd = null;
		EncryptionAlgorithm alg = null;
		try{
			pwd = System.getProperty("myeni.encryption.key");
		} catch (Throwable e) {
			pwd = null;
		}
		try{
			 alg = EncryptionAlgorithm.fromName( System.getProperty("myeni.encryption.alg") );
		} catch (Throwable e) {
			alg = null;
		}
		
		if(pwd==null||"".equals((pwd=pwd.trim()))){
			pwd = DEFAULT_PASSWORD;
		}
		if(alg==null){
			alg= EncryptionAlgorithm.AES128;
		}
		
		
		encryptionService = new OpenSSLEncryptionService(pwd , alg);
	}

	public static EncryptionService getDefaultInstance() {
		return encryptionService;
	}
	
	

}