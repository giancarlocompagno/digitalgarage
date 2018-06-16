package it.digitalgarage.marketplace.commons.email;


import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

// TODO: Auto-generated Javadoc
/**
 * The Class MailChannel.
 */
public class MailChannel{
	
	/** The mail sender. */
	private JavaMailSender mailSender;
	
	/** The from. */
	private String from;
	
	
	/**
	 * Sets the mail sender.
	 *
	 * @param mailSender the new mail sender
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	/**
	 * Sets the from.
	 *
	 * @param from the new from
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	
	
	/**
	 * Send message.
	 *
	 * @param mailRecipient the mail recipient
	 * @param mailMessage the mail message
	 */
	public void sendMessage(final MailRecipient mailRecipient,final MailMessage mailMessage) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) {
				try {
					MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
					helper.setSubject(mailMessage.getSubject());
					helper.setTo(mailRecipient.getTo());
					String from = mailRecipient.getFrom();
					if(from == null){
						from = MailChannel.this.from;
					}
					helper.setFrom(from);
					helper.setText(mailMessage.getBody(), mailMessage.isHtml());
					for(MailAttachment attach : mailMessage.getAttachments()){
						ByteArrayResource in = new ByteArrayResource(attach.getContent()){
							@Override
							public String getFilename() {
								return attach.getFileName();								
							}; 
						};
						switch (attach.getType()) {
						case INLINE:
							helper.addInline(attach.getFileName(),in);
							break;
						default:
							helper.addAttachment(attach.getFileName(), in);
							break;
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
        
		mailSender.send(preparator);
	}
	
	


}
