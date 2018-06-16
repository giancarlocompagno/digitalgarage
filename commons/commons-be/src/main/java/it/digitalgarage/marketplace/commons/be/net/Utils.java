package it.digitalgarage.marketplace.commons.be.net;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import it.digitalgarage.marketplace.commons.logging.Logger;
import it.digitalgarage.marketplace.commons.logging.LoggerFactory;


public class Utils {
	
	private static Logger LOGGER = LoggerFactory.getLogger(Utils.class);
	
	
	private static final TrustManager[] UNQUESTIONING_TRUST_MANAGER = new TrustManager[]{
			new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers(){
					return null;
				}
				public void checkClientTrusted( X509Certificate[] certs, String authType ){}
				public void checkServerTrusted( X509Certificate[] certs, String authType ){}
			}
	};
	
	
	private static final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
		
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};
	
	
	
	/**
	 * 
	 * 
	 * set JAVA_OPTIONS=%JAVA_OPTIONS% -DUseSunHttpHandler=true
	 * 
	 * 
	 * @param httpURLConnection
	 */
	
	//@SuppressWarnings("restriction")
	public static void disableHttpsCheck(URLConnection httpURLConnection) {
		
		
		LOGGER.info("disableHttpsCheck {} " , httpURLConnection!=null? httpURLConnection.getClass().getName() :" httpURLConnection is null");
		if(HttpsURLConnection.class.isInstance(httpURLConnection)){
			try{
				SSLSocketFactory socketFactory = getTrustedSSLSocketFactory();
				((HttpsURLConnection)httpURLConnection).setSSLSocketFactory(socketFactory);
				((HttpsURLConnection)httpURLConnection).setHostnameVerifier(hostnameVerifier);
			}catch (Exception e) {
				LOGGER.error("disableHttpsCheck" , e);
				throw new RuntimeException(e);
			}
		}else{
			try{
				SSLSocketFactory socketFactory = getTrustedSSLSocketFactory();
				Method method = httpURLConnection.getClass().getMethod("setSSLSocketFactory", new Class[]{SSLSocketFactory.class});
				method.invoke(httpURLConnection, socketFactory);
			}catch (Exception e) {
				LOGGER.error("impossibile trovare o settare sslsocketfactory per la classe " + httpURLConnection.getClass().getName(),e);
			}
			try{
				Method method = httpURLConnection.getClass().getMethod("setHostnameVerifier", new Class[]{HostnameVerifier.class});
				method.invoke(httpURLConnection, hostnameVerifier);
			}catch (Exception e) {
				LOGGER.error("impossibile trovare o settare hostnameVerifier per la classe " + httpURLConnection.getClass().getName(),e);
			}
		}
		
//		if(HttpsURLConnectionOldImpl.class.isInstance(httpURLConnection)){
//			try{
//				SSLSocketFactory socketFactory = getTrustedSSLSocketFactory();
//				((HttpsURLConnectionOldImpl)httpURLConnection).setSSLSocketFactory(socketFactory);
//			}catch (Exception e) {
//				LOGGER.error("disableHttpsCheck" , e);
//				throw new RuntimeException(e);
//			}
//		}
	}


	public static SSLSocketFactory getTrustedSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
		final SSLContext sc = SSLContext.getInstance("SSL");
		sc.init( null, UNQUESTIONING_TRUST_MANAGER, null );
		SSLSocketFactory socketFactory = sc.getSocketFactory();
		return socketFactory;
	}
	
	
	public static void main(String[] args) throws IOException {
		//System.setProperty("java.protocol.handler.pkgs","com.sun.net.ssl.internal.www.protocol");
		//System.setProperty("java.protocol.handler.pkgs","sun.net.www.protocol");
		URL url = new URL("https://sd4-myeni.eni.com");
		System.out.println(url.openConnection());
	}

}
