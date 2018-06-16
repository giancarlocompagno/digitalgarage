package it.digitalgarage.marketplace.commons.restinvoker.requestmapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
// TODO: Auto-generated Javadoc
/**
 * The Class WebConfig.
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport{
	
	
//	private long maxUploadSize = 10*1024*1024;
//	
//	public void setMaxUploadSize(long maxUploadSize) {
//		this.maxUploadSize = maxUploadSize;
//	}
//
//	
//	
//	@Bean
//	public MultipartResolver multipartResolver(){
//		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//		commonsMultipartResolver.setMaxUploadSize(maxUploadSize);
//		return commonsMultipartResolver;
//	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#requestMappingHandlerMapping()
	 */
	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RestServiceRequestMappingHandlerMapping handlerMapping = new RestServiceRequestMappingHandlerMapping();
		handlerMapping.setOrder(0);
		handlerMapping.setInterceptors(getInterceptors());
		handlerMapping.setContentNegotiationManager(mvcContentNegotiationManager());

		PathMatchConfigurer configurer = getPathMatchConfigurer();
		if (configurer.isUseSuffixPatternMatch() != null) {
			handlerMapping.setUseSuffixPatternMatch(configurer.isUseSuffixPatternMatch());
		}
		if (configurer.isUseRegisteredSuffixPatternMatch() != null) {
			handlerMapping.setUseRegisteredSuffixPatternMatch(configurer.isUseRegisteredSuffixPatternMatch());
		}
		if (configurer.isUseTrailingSlashMatch() != null) {
			handlerMapping.setUseTrailingSlashMatch(configurer.isUseTrailingSlashMatch());
		}
		if (configurer.getPathMatcher() != null) {
			handlerMapping.setPathMatcher(configurer.getPathMatcher());
		}
		if (configurer.getUrlPathHelper() != null) {
			handlerMapping.setUrlPathHelper(configurer.getUrlPathHelper());
		}
		return handlerMapping;
	}
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/*.js/**").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/*.css/**").addResourceLocations("classpath:/META-INF/resources/");
	}
	
	
}