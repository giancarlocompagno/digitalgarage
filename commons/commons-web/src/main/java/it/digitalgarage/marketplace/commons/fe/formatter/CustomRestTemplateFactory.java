package it.digitalgarage.marketplace.commons.fe.formatter;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import it.digitalgarage.marketplace.commons.be.security.MarketplaceSecurityContext;
import it.digitalgarage.marketplace.commons.restinvoker.factory.RestTemplateFactory;

public class CustomRestTemplateFactory implements RestTemplateFactory{
	
	
	public RestTemplate create(){
		RestTemplate restTemplate = new RestTemplate();

		new MappingJackson2HttpMessageConverterFactory().resetObjectMapper(restTemplate.getMessageConverters());
		
		restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
			
			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
				if(MarketplaceSecurityContext.getUsername()!=null){
					request.getHeaders().set("Content-Type", "application/json");
					request.getHeaders().set("Authorization","Bearer "+MarketplaceSecurityContext.getAccessToken());
				}
	            return execution.execute(request, body);
			}
		});
		
		return restTemplate;
	}
	
	/*
	public static void setReturnType(Object o,Class<?> parametrized, Class<?>... parameterClasses){
		if(o instanceof Proxy){
			InvocationHandler ie = ((Proxy)o).getInvocationHandler(o);
			if(ie instanceof BaseRestInvokerProxyFactoryBean){
				BaseRestInvokerProxyFactoryBean b = (BaseRestInvokerProxyFactoryBean)ie;
				RestTemplate r = b.getRestTemplate();
				ObjectMapper objectMapper = mappingJackson2HttpMessageConverterFactory.getObjectMapper(r);
				objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
			}
		}
	}*/

}
