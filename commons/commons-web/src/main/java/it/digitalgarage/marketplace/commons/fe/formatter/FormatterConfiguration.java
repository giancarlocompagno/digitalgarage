package it.digitalgarage.marketplace.commons.fe.formatter;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class FormatterConfiguration extends WebMvcConfigurationSupport {

    private static final Pattern bigDecimal = Pattern.compile("^[0-9]+(\\.[0-9]+){0,1}$");


    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    	
    	
    	new MappingJackson2HttpMessageConverterFactory().resetObjectMapper(converters);
    }



	
    
    
    
}
