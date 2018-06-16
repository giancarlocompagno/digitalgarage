package it.digitalgarage.marketplace.commons.email;

// TODO: Auto-generated Javadoc
/**
 * The Class MailAttachment.
 */
public class MailAttachment {
	
	
	public enum TYPE{ATTACHMENT,INLINE}
	
	/**
	 * Instantiates a new mail attachment.
	 *
	 * @param fileName the file name
	 * @param content the content
	 */
	public MailAttachment(String fileName,byte[] content,TYPE type) {
		super();
		this.fileName = fileName;
		this.content = content;
		this.type = type;
	}
	
//	public MailAttachment(String fileName,byte[] content) {
//		this(fileName, content, TYPE.ATTACHMENT);
//	}
	
	
	/** The type. */
	private TYPE type;
	
	/** The file name. */
	private String fileName; 
	
	/** The content. */
	private byte[] content;
	
	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}
	
	public TYPE getType() {
		return type;
	}
}
