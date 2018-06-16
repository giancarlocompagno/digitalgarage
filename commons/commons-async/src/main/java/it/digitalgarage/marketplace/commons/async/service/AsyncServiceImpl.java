package it.digitalgarage.marketplace.commons.async.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService {

	@Override
	@Async
	public <T> CompletableFuture<T> execute(Execute<T> execute) {
		return CompletableFuture.completedFuture(execute.execute());
	}

}
