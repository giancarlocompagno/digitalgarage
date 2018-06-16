package it.digitalgarage.marketplace.commons.encryption;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64InputStream;

/**
 * OpenSSL encrypt/decrypt java implementation
 * <br>
 * Example command to decrypt using the default params:
 *  <b>openssl des3 -d -k {password} -in data.p7m.enc -out data.p7m</b>
 *  
 * @author RvS
 */
public class OpenSSL {
	
	
	
	public static class DualInputStream extends InputStream {
	    private boolean headDone;
	    private InputStream head;
	    private InputStream tail;

	    public DualInputStream(InputStream head, InputStream tail) {
	        this.head = head != null ? head : tail;
	        this.tail = tail != null ? tail : head;
	    }

	    public int read() throws IOException {
	        int c;
	        if (headDone) {
	            c = tail.read();
	        } else {
	            c = head.read();
	            if (c == -1) {
	                headDone = true;
	                c = tail.read();
	            }
	        }
	        return c;
	    }

	    public int available() throws IOException {
	        return tail.available() + head.available();
	    }

	    public void close() throws IOException {
	        try {
	            head.close();
	        }
	        finally {
	            if (head != tail) {
	                tail.close();
	            }
	        }
	    }

	    public int read(byte b[], int off, int len) throws IOException {
	        int c;
	        if (headDone) {
	            c = tail.read(b, off, len);
	        } else {
	            c = head.read(b, off, len);
	            if (c == -1) {
	                headDone = true;
	                c = tail.read(b, off, len);
	            }
	        }
	        return c;
	    }

	}

	public final static int SIZE_KEY = 0;
    public final static int LAST_READ_KEY = 1;
    
    private static class DerivedKey {
        public final byte[] key;
        public final byte[] iv;

        DerivedKey(byte[] key, byte[] iv) {
            this.key = key;
            this.iv = iv;
        }

    }
    
    private static class CipherInfo {
        public final String javaCipher;
        public final String blockMode;
        public final int keySize;
        public final int ivSize;
        public final boolean des2;

        public CipherInfo(String javaCipher, String blockMode, int keySize,
                          int ivSize, boolean des2) {
            this.javaCipher = javaCipher;
            this.blockMode = blockMode;
            this.keySize = keySize;
            this.ivSize = ivSize;
            this.des2 = des2;
        }

        public String toString() {
            return javaCipher + "/" + blockMode + " " + keySize + "bit  des2=" + des2;
        }
    }
    
    
   
    
    
    /**
     * Decrypts data using a password and an OpenSSL compatible cipher
     * name.
     *
     * @param cipher OpenSSL compatible cipher to use
     *                  <ul><li>des, des3, des-ede3-cbc
     *                  <li>aes128, aes192 (currently unsupported), aes256 (currently unsupported), aes-256-cbc
     *                  <li>rc2, rc4, bf</ul>
     * @param pwd password
     * @param encrypted byte array to decrypt.
     * @return decrypted bytes
     * @throws IOException on error with encrypted bytes
     * @throws GeneralSecurityException on error decrypting
     */
    public static byte[] decrypt(String cipher, char[] pwd, byte[] encrypted)
        throws IOException, GeneralSecurityException {
        ByteArrayInputStream in = new ByteArrayInputStream(encrypted);
        InputStream decrypted = decrypt(cipher, pwd, in);
        return streamToBytes(decrypted);
    }

    /**
     * Decrypts data using a password and an OpenSSL compatible cipher
     * name.
     *
     * @param cipher OpenSSL compatible cipher to use
     *                  <ul><li>des, des3, des-ede3-cbc
     *                  <li>aes128, aes192 (currently unsupported), aes256 (currently unsupported), aes-256-cbc
     *                  <li>rc2, rc4, bf</ul>
     * @param pwd password
     * @param encrypted byte array to decrypt.
     * @return decrypted bytes
     * @throws IOException on error with encrypted bytes
     * @throws GeneralSecurityException on error decrypting
     */
    public static InputStream decrypt(String cipher, char[] pwd,
                                      InputStream encrypted)
        throws IOException, GeneralSecurityException {
        CipherInfo cipherInfo = convertCipher(cipher);
        boolean salted = false;

        byte[] saltLine = streamToBytes(encrypted, 16);
        if (saltLine.length <= 0) {
            throw new IOException("encrypted InputStream is empty");
        }
        String firstEightBytes = "";
        if (saltLine.length >= 8) {
            firstEightBytes = new String(saltLine, 0, 8);
        }
        if ("SALTED__".equalsIgnoreCase(firstEightBytes)) {
            salted = true;
        } 

        byte[] salt = null;
        if (salted) {
            salt = new byte[8];
            System.arraycopy(saltLine, 8, salt, 0, 8);
        } else {
            // Encrypted data wasn't salted. put back the "saltLine"
            InputStream head = new ByteArrayInputStream(saltLine);
            encrypted = new DualInputStream(head, encrypted);
        }

        int keySize = cipherInfo.keySize;
        int ivSize = cipherInfo.ivSize;
        boolean des2 = cipherInfo.des2;
        DerivedKey dk = deriveKey(pwd, salt, keySize, ivSize, des2);
        Cipher c = generateCipher(
            cipherInfo.javaCipher, cipherInfo.blockMode, dk, des2, null, true
        );

        return new CipherInputStream(encrypted, c);
    }

  
    /**
     * Encrypts data using a password and an OpenSSL compatible cipher
     * name.
     *
     * @param cipher OpenSSL compatible cipher to use
     *                  <ul><li>des, des3, des-ede3-cbc
     *                  <li>aes128, aes192, aes256, aes-256-cbc
     *                  <li>rc2, rc4, bf</ul>
     * @param pwd password
     * @param encrypted byte array to decrypt.
     * @return decrypted bytes
     * @throws IOException on error with encrypted bytes
     * @throws GeneralSecurityException on error encrypting
     */
    public static byte[] encrypt(String cipher, char[] pwd, byte[] data)
        throws IOException, GeneralSecurityException {
        return encrypt(cipher, pwd, data, false);
    }

    /**
     * Encrypts data using a password and an OpenSSL compatible cipher
     * name.
     *
     * @param cipher OpenSSL compatible cipher to use
     *                  <ul><li>des, des3, des-ede3-cbc
     *                  <li>aes128, aes192 (currently unsupported), aes256 (currently unsupported), aes-256-cbc
     *                  <li>rc2, rc4, bf</ul>
     * @param pwd password
     * @param encrypted byte array to decrypt.
     * @return decrypted bytes
     * @throws IOException on error with encrypted bytes
     * @throws GeneralSecurityException on error encrypting
     */
    public static InputStream encrypt(String cipher, char[] pwd,
                                      InputStream data)
        throws IOException, GeneralSecurityException {
        return encrypt(cipher, pwd, data, false);
    }


    /**
     * Encrypts data using a password and an OpenSSL compatible cipher
     * name.
     *
     * @param cipher OpenSSL compatible cipher to use
     *                  <ul><li>des, des3, des-ede3-cbc
     *                  <li>aes128, aes192 (currently unsupported), aes256 (currently unsupported), aes-256-cbc
     *                  <li>rc2, rc4, bf</ul>
     * @param pwd password
     * @param encrypted byte array to decrypt.
     * @return decrypted bytes
     * @throws IOException on error with encrypted bytes
     * @throws GeneralSecurityException on error encrypting
     */
    public static byte[] encrypt(String cipher, char[] pwd, byte[] data,
                                 boolean toBase64)
        throws IOException, GeneralSecurityException {
        return encrypt(cipher, pwd, data, toBase64, true);
    }

    /**
     * Encrypts data using a password and an OpenSSL compatible cipher
     * name.
     *
     * @param cipher OpenSSL compatible cipher to use
     *                  <ul><li>des, des3, des-ede3-cbc
     *                  <li>aes128, aes192 (currently unsupported), aes256 (currently unsupported), aes-256-cbc
     *                  <li>rc2, rc4, bf</ul>
     * @param pwd password
     * @param encrypted byte array to decrypt.
     * @return decrypted bytes
     * @throws IOException on error with encrypted bytes
     * @throws GeneralSecurityException on error encrypting
     */
    public static InputStream encrypt(String cipher, char[] pwd,
                                      InputStream data, boolean toBase64)
        throws IOException, GeneralSecurityException {
        return encrypt(cipher, pwd, data, toBase64, true);
    }

    /**
     * Encrypts data using a password and an OpenSSL compatible cipher
     * name.
     *
     * @param cipher OpenSSL compatible cipher to use
     *                  <ul><li>des, des3, des-ede3-cbc
     *                  <li>aes128, aes192 (currently unsupported), aes256 (currently unsupported), aes-256-cbc
     *                  <li>rc2, rc4, bf</ul>
     * @param pwd password
     * @param encrypted byte array to decrypt.
     * @return decrypted bytes
     * @throws IOException on error with encrypted bytes
     * @throws GeneralSecurityException on error encrypting
     */
    public static byte[] encrypt(String cipher, char[] pwd, byte[] data,
                                 boolean toBase64, boolean useSalt)
        throws IOException, GeneralSecurityException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        InputStream encrypted = encrypt(cipher, pwd, in, toBase64, useSalt);
        return streamToBytes(encrypted);
    }

    /**
     * Encrypts data using a password and an OpenSSL compatible cipher
     * name.
     *
     * @param cipher OpenSSL compatible cipher to use
     *                  <ul><li>des, des3, des-ede3-cbc
     *                  <li>aes128, aes192 (currently unsupported), aes256 (currently unsupported), aes-256-cbc
     *                  <li>rc2, rc4, bf</ul>
     * @param pwd password
     * @param encrypted byte array to decrypt.
     * @return decrypted bytes
     * @throws IOException on error with encrypted bytes
     * @throws GeneralSecurityException on error encrypting
     */
    public static InputStream encrypt(String cipher, char[] pwd,
                                      InputStream data, boolean toBase64,
                                      boolean useSalt)
        throws IOException, GeneralSecurityException {
        CipherInfo cipherInfo = convertCipher(cipher);
        byte[] salt = null;
        if (useSalt) {
            SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
            salt = new byte[8];
            rand.nextBytes(salt);
        }

        int keySize = cipherInfo.keySize;
        int ivSize = cipherInfo.ivSize;
        boolean des2 = cipherInfo.des2;
        DerivedKey dk = deriveKey(pwd, salt, keySize, ivSize, des2);
        Cipher c = generateCipher(
            cipherInfo.javaCipher, cipherInfo.blockMode, dk, des2, null, false
        );

        InputStream cipherStream = new CipherInputStream(data, c);

        if (useSalt) {
            byte[] saltLine = new byte[16];
            byte[] salted = "Salted__".getBytes();
            System.arraycopy(salted, 0, saltLine, 0, salted.length);
            System.arraycopy(salt, 0, saltLine, salted.length, salt.length);
            InputStream head = new ByteArrayInputStream(saltLine);
            cipherStream = new DualInputStream(head, cipherStream);
        }
        if (toBase64) {
            cipherStream = new Base64InputStream(cipherStream, true);
        }
        return cipherStream;
    }


    private static DerivedKey deriveKey(char[] password, byte[] salt,
                                       int keySize, int ivSize, boolean des2)
        throws NoSuchAlgorithmException {
        if (des2) {
            keySize = 128;
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] pwdAsBytes = new byte[password.length];
        for (int i = 0; i < password.length; i++) {
            pwdAsBytes[i] = (byte) password[i];
        }

        md.reset();
        byte[] keyAndIv = new byte[(keySize / 8) + (ivSize / 8)];
        if (salt == null || salt.length == 0) {
            salt = null;
        }
        byte[] result;
        int currentPos = 0;
        while (currentPos < keyAndIv.length) {
            md.update(pwdAsBytes);
            if (salt != null) {
                // Only the first 8 bytes are salt
                md.update(salt, 0, 8);
            }
            result = md.digest();
            int stillNeed = keyAndIv.length - currentPos;

            if (result.length > stillNeed) {
                byte[] b = new byte[stillNeed];
                System.arraycopy(result, 0, b, 0, b.length);
                result = b;
            }
            System.arraycopy(result, 0, keyAndIv, currentPos, result.length);
            currentPos += result.length;
            if (currentPos < keyAndIv.length) {
                // Next round starts with a hash of the hash.
                md.reset();
                md.update(result);
            }
        }
        if (des2) {
            keySize = 192;
            byte[] buf = new byte[keyAndIv.length + 8];
            // Make space where 3rd key needs to go (16th - 24th bytes):
            System.arraycopy(keyAndIv, 0, buf, 0, 16);
            if (ivSize > 0) {
                System.arraycopy(keyAndIv, 16, buf, 24, keyAndIv.length - 16);
            }
            keyAndIv = buf;
            // copy first 8 bytes into last 8 bytes to create 2DES key.
            System.arraycopy(keyAndIv, 0, keyAndIv, 16, 8);
        }
        if (ivSize == 0) {
            // if ivSize == 0, then "keyAndIv" array is all key.

            // Must be "Traditional SSLeay Format" encrypted private key in
            // PEM.  The "salt" in its entirety (not just first 8 bytes) will
            // be re-used later as the IV (initialization vector).
            return new DerivedKey(keyAndIv, salt);
        } else {
            byte[] key = new byte[keySize / 8];
            byte[] iv = new byte[ivSize / 8];
            System.arraycopy(keyAndIv, 0, key, 0, key.length);
            System.arraycopy(keyAndIv, key.length, iv, 0, iv.length);
            return new DerivedKey(key, iv);
        }
    }


    

    /**
     * Converts OpenSSL names into a Java naming.
     *
     * @param openSSLCipher OpenSSL cipher name
     * @return CipherInfo object with the Java cipher information.
     */
    private static CipherInfo convertCipher(String openSSLCipher) {
        openSSLCipher = openSSLCipher.trim();
        if (openSSLCipher.charAt(0) == '-') {
            openSSLCipher = openSSLCipher.substring(1);
        }
        String javaCipher = openSSLCipher.toUpperCase();
        String blockMode = "CBC";
        int keySize = -1;
        int ivSize = 64;
        boolean des2 = false;


        StringTokenizer st = new StringTokenizer(openSSLCipher, "-");
        if (st.hasMoreTokens()) {
            javaCipher = st.nextToken().toUpperCase();
            if (st.hasMoreTokens()) {

            	String tok = st.nextToken();
                if (st.hasMoreTokens()) {
                    try {
                        keySize = Integer.parseInt(tok);
                    }
                    catch (NumberFormatException nfe) {
                        
                        String upper = tok.toUpperCase();
                        if (upper.startsWith("EDE3")) {
                            javaCipher = "DESede";
                        } else if (upper.startsWith("EDE")) {
                            javaCipher = "DESede";
                            des2 = true;
                        }
                    }
                    blockMode = st.nextToken().toUpperCase();
                } else {
                    try {
                        keySize = Integer.parseInt(tok);
                    }
                    catch (NumberFormatException nfe) {

                    	blockMode = tok.toUpperCase();
                        if (blockMode.startsWith("EDE3")) {
                            javaCipher = "DESede";
                            blockMode = "ECB";
                        } else if (blockMode.startsWith("EDE")) {
                            javaCipher = "DESede";
                            blockMode = "ECB";
                            des2 = true;
                        }
                    }
                }
            }
        }
        if (javaCipher.startsWith("BF")) {
            javaCipher = "Blowfish";
        } else if (javaCipher.startsWith("TWOFISH")) {
            javaCipher = "Twofish";
            ivSize = 128;
        } else if (javaCipher.startsWith("IDEA")) {
            javaCipher = "IDEA";
        } else if (javaCipher.startsWith("CAST6")) {
            javaCipher = "CAST6";
            ivSize = 128;
        } else if (javaCipher.startsWith("CAST")) {
            javaCipher = "CAST5";
        } else if (javaCipher.startsWith("GOST")) {
            keySize = 256;
        } else if (javaCipher.startsWith("DESX")) {
            javaCipher = "DESX";
        } else if ("DES3".equals(javaCipher)) {
            javaCipher = "DESede";
        } else if ("DES2".equals(javaCipher)) {
            javaCipher = "DESede";
            des2 = true;
        } else if (javaCipher.startsWith("RIJNDAEL")) {
            javaCipher = "Rijndael";
            ivSize = 128;
        } else if (javaCipher.startsWith("SEED")) {
            javaCipher = "SEED";
            ivSize = 128;
        } else if (javaCipher.startsWith("SERPENT")) {
            javaCipher = "Serpent";
            ivSize = 128;
        } else if (javaCipher.startsWith("Skipjack")) {
            javaCipher = "Skipjack";
            ivSize = 128;
        } else if (javaCipher.startsWith("RC6")) {
            javaCipher = "RC6";
            ivSize = 128;
        } else if (javaCipher.startsWith("TEA")) {
            javaCipher = "TEA";
        } else if (javaCipher.startsWith("XTEA")) {
            javaCipher = "XTEA";
        } else if (javaCipher.startsWith("AES")) {
            if (javaCipher.startsWith("AES128")) {
                keySize = 128;
            } else if (javaCipher.startsWith("AES192")) {
                keySize = 192;
            } else if (javaCipher.startsWith("AES256")) {
                keySize = 256;
            }
            javaCipher = "AES";
            ivSize = 128;
        } else if (javaCipher.startsWith("CAMELLIA")) {
            if (javaCipher.startsWith("CAMELLIA128")) {
                keySize = 128;
            } else if (javaCipher.startsWith("CAMELLIA192")) {
                keySize = 192;
            } else if (javaCipher.startsWith("CAMELLIA256")) {
                keySize = 256;
            }
            javaCipher = "CAMELLIA";
            ivSize = 128;
        }
        if (keySize == -1) {
            if (javaCipher.startsWith("DESede")) {
                keySize = 192;
            } else if (javaCipher.startsWith("DES")) {
                keySize = 64;
            } else {
                // RC2, RC4, RC5 and Blowfish ?
                keySize = 128;
            }
        }
        return new CipherInfo(javaCipher, blockMode, keySize, ivSize, des2);
    }


    
	private static byte[] streamToBytes(final InputStream in, int maxLength)
			throws IOException {
		byte[] buf = new byte[maxLength];
		int[] status = fill(buf, 0, in);
		int size = status[SIZE_KEY];
		if (buf.length != size) {
			byte[] smallerBuf = new byte[size];
			System.arraycopy(buf, 0, smallerBuf, 0, size);
			buf = smallerBuf;
		}
		return buf;
	}

	private static byte[] streamToBytes(final InputStream in) throws IOException {
		byte[] buf = new byte[4096];
		try {
			int[] status = fill(buf, 0, in);
			int size = status[SIZE_KEY];
			int lastRead = status[LAST_READ_KEY];
			while (lastRead != -1) {
				buf = resizeArray(buf);
				status = fill(buf, size, in);
				size = status[SIZE_KEY];
				lastRead = status[LAST_READ_KEY];
			}
			if (buf.length != size) {
				byte[] smallerBuf = new byte[size];
				System.arraycopy(buf, 0, smallerBuf, 0, size);
				buf = smallerBuf;
			}
		} finally {
			in.close();
		}
		return buf;
	}

	

	private static int[] fill(final byte[] buf, final int offset,
			final InputStream in) throws IOException {
		int read = in.read(buf, offset, buf.length - offset);
		int lastRead = read;
		if (read == -1) {
			read = 0;
		}
		while (lastRead != -1 && read + offset < buf.length) {
			lastRead = in.read(buf, offset + read, buf.length - read - offset);
			if (lastRead != -1) {
				read += lastRead;
			}
		}
		return new int[] { offset + read, lastRead };
	}

	

	private static byte[] resizeArray(final byte[] bytes) {
		byte[] biggerBytes = new byte[bytes.length * 2];
		System.arraycopy(bytes, 0, biggerBytes, 0, bytes.length);
		return biggerBytes;
	}
	
	
	private static Cipher generateCipher(String cipher, String mode,
			final DerivedKey dk, final boolean des2, final byte[] iv,
			final boolean decryptMode) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {
		if (des2 && dk.key.length >= 24) {
			// copy first 8 bytes into last 8 bytes to create 2DES key.
			System.arraycopy(dk.key, 0, dk.key, 16, 8);
		}

		final int keySize = dk.key.length * 8;
		cipher = cipher.trim();
		String cipherUpper = cipher.toUpperCase();
		mode = mode.trim().toUpperCase();
		// Is the cipher even available?
		Cipher.getInstance(cipher);
		String padding = "PKCS5Padding";
		if (mode.startsWith("CFB") || mode.startsWith("OFB")) {
			padding = "NoPadding";
		}

		String transformation = cipher + "/" + mode + "/" + padding;
		if (cipherUpper.startsWith("RC4")) {
			// RC4 does not take mode or padding.
			transformation = cipher;
		}

		SecretKey secret = new SecretKeySpec(dk.key, cipher);
		IvParameterSpec ivParams;
		if (iv != null) {
			ivParams = new IvParameterSpec(iv);
		} else {
			ivParams = dk.iv != null ? new IvParameterSpec(dk.iv) : null;
		}

		Cipher c = Cipher.getInstance(transformation);
		int cipherMode = Cipher.ENCRYPT_MODE;
		if (decryptMode) {
			cipherMode = Cipher.DECRYPT_MODE;
		}

		// RC2 requires special params to inform engine of keysize.
		if (cipherUpper.startsWith("RC2")) {
			RC2ParameterSpec rcParams;
			if (mode.startsWith("ECB") || ivParams == null) {
				// ECB doesn't take an IV.
				rcParams = new RC2ParameterSpec(keySize);
			} else {
				rcParams = new RC2ParameterSpec(keySize, ivParams.getIV());
			}
			c.init(cipherMode, secret, rcParams);
		} else if (cipherUpper.startsWith("RC5")) {
			RC5ParameterSpec rcParams;
			if (mode.startsWith("ECB") || ivParams == null) {
				// ECB doesn't take an IV.
				rcParams = new RC5ParameterSpec(16, 12, 32);
			} else {
				rcParams = new RC5ParameterSpec(16, 12, 32, ivParams.getIV());
			}
			c.init(cipherMode, secret, rcParams);
		} else if (mode.startsWith("ECB") || cipherUpper.startsWith("RC4")) {
			// RC4 doesn't require any params.
			// Any cipher using ECB does not require an IV.
			c.init(cipherMode, secret);
		} else {
			// DES, DESede, AES, BlowFish require IVParams (when in CBC, CFB,
			// or OFB mode). (In ECB mode they don't require IVParams).
			c.init(cipherMode, secret, ivParams);
		}
		return c;
	}
	
	

}