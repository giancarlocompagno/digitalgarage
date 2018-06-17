package it.digitalgarage.marketplace.offertaasta.be.signature.dto;

public class ResponseObject<T> {
	
	T entity;
	
	public ResponseObject(T entity) {
		this.entity = entity;
	}
	
	public T getEntity() {
		return entity;
	}

}
