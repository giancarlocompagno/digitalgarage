package it.digitalgarage.marketplace.commons.be.validator;

public class ValidatorUtils {
	
	
	@SafeVarargs
	public static <I,O> O validate(I target,BL</*I,*/O> bl,Validate<I>... vs) {
		ValidationInfo<I> vi = new ValidationInfo<>(target);
		for(Validate<I> v : vs){
			v.validate(vi);
		}
		if(vi.getErrors().hasErrors()){
			throw new NotValidException(vi.getErrors());
		}


		return bl.execute(/*target*/);
	}

	
	
	@SafeVarargs
	public static <I> Validate<I> and(final Validate<I>... vs){
		return new Validate<I>() {
			
			@Override
			public void validate(ValidationInfo<I> vi) {
				int err = vi.getErrors().getErrorCount();
				for(Validate<I> v : vs){
					v.validate(vi);
					if(err != vi.getErrors().getErrorCount()){
						break;
					}
				}
			}
		};
		
	}
	
	@SafeVarargs
	public static <I> Validate<I> or(final Validate<I>... vs){
		return new Validate<I>() {
			
			@Override
			public void validate(ValidationInfo<I> vi) {
				
				for(Validate<I> v : vs){
					v.validate(vi);
				}
			}
		};
		
	}
	
	
}
