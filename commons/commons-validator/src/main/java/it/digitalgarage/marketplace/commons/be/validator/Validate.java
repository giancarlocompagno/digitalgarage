package it.digitalgarage.marketplace.commons.be.validator;

@FunctionalInterface
public interface Validate<T> {
	
	public void validate(ValidationInfo<T> v);
	
}
