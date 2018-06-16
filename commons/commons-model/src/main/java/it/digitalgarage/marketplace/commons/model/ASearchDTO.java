package it.digitalgarage.marketplace.commons.model;

public abstract class ASearchDTO<T> extends DTO<T>{
	
	
	
	private Pageable pageable;
	
	
	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}
	
	public Pageable getPageable() {
		return pageable;
	}
	
	
	
	
	
	
	
	

}
