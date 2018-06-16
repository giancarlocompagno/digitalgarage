package it.digitalgarage.marketplace.commons.restinvoker.spring.multipart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.AbstractResource;
import org.springframework.web.multipart.MultipartFile;

// TODO: Auto-generated Javadoc
/**
 * The Class MultipartFileResource.
 */
public class MultipartFileResource extends AbstractResource implements MultipartFile{
	
	/** The multipart file. */
	MultipartFile multipartFile;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#getName()
	 */
	public String getName() {
		return multipartFile.getName();
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#getOriginalFilename()
	 */
	public String getOriginalFilename() {
		return multipartFile.getOriginalFilename();
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#getContentType()
	 */
	public String getContentType() {
		return multipartFile.getContentType();
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#isEmpty()
	 */
	public boolean isEmpty() {
		return multipartFile.isEmpty();
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#getSize()
	 */
	public long getSize() {
		return multipartFile.getSize();
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#getBytes()
	 */
	public byte[] getBytes() throws IOException {
		return multipartFile.getBytes();
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#transferTo(java.io.File)
	 */
	public void transferTo(File dest) throws IOException, IllegalStateException {
		multipartFile.transferTo(dest);
	}

	/**
	 * Instantiates a new multipart file resource.
	 *
	 * @param multipartFile the multipart file
	 */
	public MultipartFileResource(MultipartFile multipartFile){
		this.multipartFile = multipartFile;
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.io.Resource#getDescription()
	 */
	@Override
	public String getDescription() {
		return multipartFile.getName();
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.core.io.AbstractResource#getFilename()
	 */
	@Override
	public String getFilename() {
		return multipartFile.getName();
	}
	

	/* (non-Javadoc)
	 * @see org.springframework.core.io.InputStreamSource#getInputStream()
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		return multipartFile.getInputStream();
	}

}
