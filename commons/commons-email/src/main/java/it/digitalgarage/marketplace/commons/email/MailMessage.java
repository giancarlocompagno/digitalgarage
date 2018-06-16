package it.digitalgarage.marketplace.commons.email;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.digitalgarage.marketplace.commons.email.MailAttachment.TYPE;

// TODO: Auto-generated Javadoc
/**
 * The Class MailMessage.
 */
public class MailMessage{
	
	/** The subject. */
	private String subject;
	
	/** The body. */
	private String body;
	
	
	private boolean html = true; 
	
	/** The attachments. */
	private List<MailAttachment> attachments = new ArrayList<>();
	
	/**
	 * Instantiates a new mail message.
	 *
	 * @param subject the subject
	 * @param body the body
	 */
	public MailMessage(String subject,String body,boolean html) {
		this.subject=subject;
		this.body=body;
		this.html = html;
	}
	
	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	
	public boolean isHtml() {
		return html;
	}
	
	/**
	 * Gets the attachments.
	 *
	 * @return the attachments
	 */
	public List<MailAttachment> getAttachments(){
		return Collections.unmodifiableList(attachments);
	}
	
	/**
	 * Adds the.
	 *
	 * @param fileName the file name
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean addAttachment(String fileName,byte[] content){
		return this.attachments.add(new MailAttachment(fileName, content,TYPE.ATTACHMENT));
	}
	
	public boolean addInline(String fileName,byte[] content){
		return this.attachments.add(new MailAttachment(fileName, content,TYPE.INLINE));
	}
	
	public boolean add(MailAttachment attachment){
		return this.attachments.add(attachment);
	}
}
