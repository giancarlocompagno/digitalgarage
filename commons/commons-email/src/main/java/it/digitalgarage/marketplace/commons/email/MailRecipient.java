package it.digitalgarage.marketplace.commons.email;

// TODO: Auto-generated Javadoc
/**
 * The Class MailRecipient.
 */
public class MailRecipient{
	
	/** The from. */
	private String from;
	
	/** The to. */
	private String[] to;
	
	/**
	 * Instantiates a new mail recipient.
	 *
	 * @param from the from
	 * @param to the to
	 */
	public MailRecipient(String from,String[] to) {
		this.from = from;
		this.to = to;
	}
	
	/**
	 * Instantiates a new mail recipient.
	 *
	 * @param to the to
	 */
	public MailRecipient(String[] to) {
		this.to = to;
	}
	
	/**
	 * Gets the from.
	 *
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	
	/**
	 * Gets the to.
	 *
	 * @return the to
	 */
	public String[] getTo() {
		return to;
	}
	
}
