package it.digitalgarage.marketplace.commons.be.message;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;


// TODO: Auto-generated Javadoc
/**
 * The Class MessageUtils.
 */
public class MessageUtils {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(MessageUtils.class);
	
	/**
	 * Message.
	 *
	 * @param messageSource the message source
	 * @param lingua the lingua
	 * @param code the code
	 * @param defaultMessage the default message
	 * @param args the args
	 * @return the string
	 */
	public static String message(MessageSource messageSource, String lingua,String code,String defaultMessage,Object... args){
			try{
				Locale locale = getLocale(lingua);
				return messageSource.getMessage(code, args, locale);
			}catch (NoSuchMessageException e) {
				logger.warn(e.getMessage());
				return defaultMessage;
			}
		}

	public static Locale getLocale(String lingua) {
		Locale locale = Locale.ITALY;
		if(lingua!=null){
			String[] token = lingua.split("_");
			if(token.length==2){
				locale = new Locale(token[0],token[1]);
			}else if(token.length == 1){
				locale = new Locale(token[0]);
			}else{
				locale = new Locale(lingua);
			}
		}
		return locale;
	}
		
}

