package it.digitalgarage.marketplace.commons.async.service;

import java.util.concurrent.CompletableFuture;

public interface AsyncService {
	
	public <T> CompletableFuture<T> execute(Execute<T> execute);
	
	
	@FunctionalInterface
	public interface Execute<T> {

		public T execute();
		
	}


}
