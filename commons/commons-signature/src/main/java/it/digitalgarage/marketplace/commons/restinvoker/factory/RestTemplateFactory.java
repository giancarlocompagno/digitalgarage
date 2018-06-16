package it.digitalgarage.marketplace.commons.restinvoker.factory;

import org.springframework.web.client.RestTemplate;

public interface RestTemplateFactory {
	
	public RestTemplate create();

}
