package it.digitalgarage.marketplace.commons.encryption;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import it.digitalgarage.marketplace.commons.encryption.EncryptionService.EncryptionAlgorithm;


/**
 * 
 * @author RvS
 * 
 * <br><br>
 * Main Encrypt/Decrypt class (See also {@link EncryptionService} )
 * <br><br>
 * <i>Call with no arguments to <b>ENCRYPT</b> the string from stdin with default password and default algorithm</i>
 * <br><br>
 * Supported arguments:<br>
 * -d 				decrypt mode<br>
 * -k {password} 	Encrypt/Decrypt using the supplied password<br>
 * -a {algorithm}	Encrypt/Decrypt using the supplied algorithm<br> 
 * 
 */
public class StringEncrypterMain {
	
	private static final String OPENSSL_COMMAND = "echo '%s' | openssl enc -%s -%s -a -salt -k '%s'";
	
	private static class Argz{
		final String str;
		final String pwd;
		final EncryptionAlgorithm alg;
		final boolean encrypt;
		
		final boolean hidePassword;
		
		public Argz(String str, String pwd, EncryptionAlgorithm alg, boolean encrypt,boolean hidePassword) {
			super();
			this.str = str;
			this.pwd = pwd;
			this.alg = alg;
			this.encrypt = encrypt;
			this.hidePassword = hidePassword;
		}

		@Override
		public String toString() {
			return String.format("Argz [str=%s, pwd=%s, alg=%s]", str,hidePassword?"{password}":pwd,alg);
		}
		
		
		public String getOpenSslEncCmd(){
			return String.format(OPENSSL_COMMAND, str,"e",alg.getName(),hidePassword?"{password}":pwd);
		}
		
		public String getOpenSslDecCmd(String encStr){
			return String.format(OPENSSL_COMMAND, encStr,"d",alg.getName(),hidePassword?"{password}":pwd);
		}
	}
	
	public static void main(String[] args) throws Exception{
		Argz a = parseArgs(args);
		String ret = null;
		if(a.encrypt){
			ret = OpenSSLEncryptionService.getDefaultInstance().encrypt(a.alg,a.pwd,a.str);
		}else{
			ret = OpenSSLEncryptionService.getDefaultInstance().decrypt(a.alg,a.pwd,a.str);
		}
		
		System.out.println(String.format("%s%nResult--> [%s]%nopenssl cmd (encrypt)--> [%s]%nopenssl cmd (decrypt)--> [%s]", 
				a,
				ret,
				a.getOpenSslEncCmd(),
				a.getOpenSslDecCmd(ret)
				));
	}

	private static Argz parseArgs(String[] args) throws Exception {
		String str = null;
		String pwd = null;
		EncryptionAlgorithm alg=null;
		boolean encrypt = true;
		final EncryptionService e = OpenSSLEncryptionService.getDefaultInstance();
		for (int ii=0;ii<args.length;ii++) {
			if("-d".equals(args[ii])){
				encrypt=false;
				continue;
			}
			
			if("-k".equals(args[ii])){
				ii++;
				if(args.length>ii){
					pwd = args[ii];
				} else{
					throw new Exception ("arg -k should be followed by a password!");
				}
				continue;
			}
			
			if("-a".equals(args[ii])){
				ii++;
				if(args.length>ii){
					alg = EncryptionAlgorithm.fromName(args[ii]);
					if(alg == null){
						throw new Exception (String.format("Unknown algorithm [%s]",args[ii]));
					}
					
					if(!e.isSupported(alg)){
						throw new Exception (String.format("Unsupported algorithm [%s]",args[ii]));
					}
				} else{
					throw new Exception ("arg -a should be followed by an algorithm!");
				}
				continue;
			}
			
			if(str==null){
				str=args[ii].trim();
				continue;
			} 
			
			throw new Exception (String.format("Unexpected parameter [%s]",args[ii]));
		}
		
		if(str == null || "".equals(str)){
			System.out.println(String.format("Please enter the String to %s",encrypt?"Encrypt":"Decrypt"));
			BufferedReader br = null;
			try{
				br = new BufferedReader(new InputStreamReader(System.in));
				str = br.readLine().trim();
			}finally {
				if(br!=null){
					try{
						br.close();
					}catch (Exception ex) {
					}
				}
			}
		}
		
		if(str == null || "".equals(str)){
			throw new Exception(String.format("String to %s not specified",encrypt?"Encrypt":"Decrypt"));
			
		}
		
		return new Argz(str, pwd==null?e.getDefaultPassword():pwd, alg==null?e.getDefaultAlgorithm():alg, encrypt,pwd==null);
	}

}