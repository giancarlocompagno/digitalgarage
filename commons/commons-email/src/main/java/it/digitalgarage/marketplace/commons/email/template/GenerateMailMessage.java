package it.digitalgarage.marketplace.commons.email.template;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import it.digitalgarage.marketplace.commons.email.MailMessage;
import it.digitalgarage.marketplace.commons.email.jaxb.Language;
import it.digitalgarage.marketplace.commons.email.jaxb.Mail;

// TODO: Auto-generated Javadoc
/**
 * The Class GenerateMailMessage.
 */
public class GenerateMailMessage  {
	
	/** The velocity engine. */
	
	/** The mail manager. */
	MailManager mailManager;
	
	/**
	 * Sets the mail manager.
	 *
	 * @param mailManager the new mail manager
	 */
	public void setMailManager(MailManager mailManager) {
		this.mailManager = mailManager;
	}
	
	/**
	 * Generate mail message.
	 *
	 * @param templateName the template name
	 * @param language the language
	 * @param context the context
	 * @return the mail message
	 * @throws Exception the exception
	 */
	public MailMessage generateMailMessage(String templateName,Language language,Map<String,Object> context) throws Exception{
		VelocityEngine velocityEngine = new VelocityEngine();
		Mail mailTemplate = mailManager.get(templateName, language);
		StringWriter subjectWriter = new StringWriter();
		StringWriter contentWriter = new StringWriter();
		VelocityContext velocityContext = new VelocityContext(context);
		velocityEngine.evaluate(velocityContext, subjectWriter, templateName+".subject", mailTemplate.getSubject());
		velocityEngine.evaluate(velocityContext, contentWriter, templateName+".content", mailTemplate.getContent());
		return new MailMessage(subjectWriter.getBuffer().toString(), contentWriter.getBuffer().toString(),mailTemplate.isHtml());
	}
	

	public MailMessage generateMailMessage(String templateName,Map<String,Object> context) throws Exception{
		return generateMailMessage(templateName, Language.IT, context);
	}
}
