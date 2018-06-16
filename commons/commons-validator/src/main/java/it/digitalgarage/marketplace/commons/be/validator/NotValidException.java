package it.digitalgarage.marketplace.commons.be.validator;

import org.springframework.validation.Errors;

public class NotValidException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Errors errors;
	
	public NotValidException(Errors errors) {
		super("Errore di validazione "+errors);
		this.errors = errors;
	}
	
	
	public Errors getErrors() {
		return errors;
	}

}
