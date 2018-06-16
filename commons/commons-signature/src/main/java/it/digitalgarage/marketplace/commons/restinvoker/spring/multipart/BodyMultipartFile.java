package it.digitalgarage.marketplace.commons.restinvoker.spring.multipart;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

// TODO: Auto-generated Javadoc
/**
 * The Class BodyMultipartFile.
 */
public class BodyMultipartFile implements MultipartFile, Serializable {

		/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8266480215561893203L;
		
		/** The body. */
		private byte[] body;
		
		/** The file name. */
		private String fileName;

		/**
		 * Instantiates a new body multipart file.
		 *
		 * @param body the body
		 * @param fileName the file name
		 */
		public BodyMultipartFile(byte[] body,String fileName) {
			this.body = body;
			this.fileName=fileName;
		}

		/* (non-Javadoc)
		 * @see org.springframework.web.multipart.MultipartFile#getName()
		 */
		@Override
		public String getName() {
			return this.fileName;
		}

		/* (non-Javadoc)
		 * @see org.springframework.web.multipart.MultipartFile#getOriginalFilename()
		 */
		@Override
		public String getOriginalFilename() {
			return this.fileName;
		}

		/* (non-Javadoc)
		 * @see org.springframework.web.multipart.MultipartFile#getContentType()
		 */
		@Override
		public String getContentType() {
			return "application/octet-stream";
		}

		/* (non-Javadoc)
		 * @see org.springframework.web.multipart.MultipartFile#isEmpty()
		 */
		@Override
		public boolean isEmpty() {
			return (this.body.length == 0);
		}

		/* (non-Javadoc)
		 * @see org.springframework.web.multipart.MultipartFile#getSize()
		 */
		@Override
		public long getSize() {
			return this.body.length;
		}

		/* (non-Javadoc)
		 * @see org.springframework.web.multipart.MultipartFile#getBytes()
		 */
		@Override
		public byte[] getBytes() throws IOException {
			return this.body;
		}

		/* (non-Javadoc)
		 * @see org.springframework.web.multipart.MultipartFile#getInputStream()
		 */
		@Override
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(body);
		}

		/* (non-Javadoc)
		 * @see org.springframework.web.multipart.MultipartFile#transferTo(java.io.File)
		 */
		@Override
		public void transferTo(File dest) throws IOException, IllegalStateException {
			StreamUtils.copy(this.body, new FileOutputStream(dest));
		}
	}