package it.digitalgarage.marketplace.commons.email.template;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

import it.digitalgarage.marketplace.commons.email.jaxb.Language;
import it.digitalgarage.marketplace.commons.email.jaxb.Mail;
import it.digitalgarage.marketplace.commons.email.jaxb.Mails;

// TODO: Auto-generated Javadoc
/**
 * The Class MailManager.
 */
public class MailManager implements ResourceLoaderAware {
	
	/** The base path template. */
	private String basePathTemplate = "classpath:/";
	
	/** The enable cache. */
	private boolean enableCache = true;
	
	/**
	 * Sets the base path template.
	 *
	 * @param basePathTemplate the new base path template
	 */
	public void setBasePathTemplate(String basePathTemplate) {
		this.basePathTemplate = basePathTemplate;
	}

	/**
	 * Sets the enable cache.
	 *
	 * @param enableCache the new enable cache
	 */
	public void setEnableCache(boolean enableCache) {
		this.enableCache = enableCache;
	}
	
	/** The resource loader. */
	private ResourceLoader resourceLoader;

	/* (non-Javadoc)
	 * @see org.springframework.context.ResourceLoaderAware#setResourceLoader(org.springframework.core.io.ResourceLoader)
	 */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader=resourceLoader;

	}
	
	/** The unmarshaller. */
	private static Unmarshaller unmarshaller;
	static{
		try{
			unmarshaller = JAXBContext.newInstance(Mails.class).createUnmarshaller();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/** The cache. */
	private static Map<String,InternalCache> cache = new HashMap<>();
	
	
	
	/**
	 * Gets the.
	 *
	 * @param Name the name
	 * @param language the language
	 * @return the mail
	 * @throws Exception the exception
	 */
	public Mail get(String name,Language language) throws Exception{
		InternalCache internalCache = null;
		if(enableCache && cache.containsKey(name)){
			internalCache = cache.get(name);
		}else{
			synchronized (cache) {
				if(enableCache && cache.containsKey(name)){
					internalCache = cache.get(name);
				}
				if(internalCache==null){
					Mails mails = (Mails) unmarshaller.unmarshal(resourceLoader.getResource(basePathTemplate+name).getInputStream());
					internalCache = new InternalCache(mails);
					if(enableCache){
						cache.put(name, internalCache);
					}
				}
			}
		}
		return internalCache.get(language);
	}
	
	public Mail get(String name) throws Exception{
		return this.get(name, Language.IT);
	}
	
	
	/**
	 * The Class InternalCache.
	 */
	private class InternalCache{
		
		/** The default mail. */
		private Mail defaultMail;
		
		/** The all mail. */
		private Map<Language,Mail> allMail = new HashMap<>();
		
		/**
		 * Instantiates a new internal cache.
		 *
		 * @param mails the mails
		 */
		private InternalCache(Mails mails) {
			for(Mail mail : mails.getMail()){
				if(mail.isDefaultTemplate()){
					defaultMail = mail;
				}
				allMail.put(mail.getLanguage(), mail);
			}
		}
		
		/**
		 * Gets the.
		 *
		 * @param language the language
		 * @return the mail
		 */
		public Mail get(Language language){
			Mail mail = allMail.get(language);
			return mail!=null?mail:defaultMail;
		}
		
	}

}
