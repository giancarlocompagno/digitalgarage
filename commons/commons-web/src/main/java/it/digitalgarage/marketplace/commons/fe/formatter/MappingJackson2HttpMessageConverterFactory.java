package it.digitalgarage.marketplace.commons.fe.formatter;

import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MappingJackson2HttpMessageConverterFactory {
	
		public void resetObjectMapper(List<HttpMessageConverter<?>> list){
			boolean trovato = false;
			for(HttpMessageConverter<?> curr :list){
				if(curr instanceof MappingJackson2HttpMessageConverter){
					((MappingJackson2HttpMessageConverter)curr).setObjectMapper(new MappingJackson2HttpMessageConverterFactory().getObjectMapper());
					trovato = true;
				}
			}
			if(!trovato){
				list.add(new MappingJackson2HttpMessageConverter(getObjectMapper()));
			}
		}

		private ObjectMapper getObjectMapper(){
			Jackson2ObjectMapperBuilder mapper = Jackson2ObjectMapperBuilder.json();
//			mapper.deserializerByType(BigDecimal.class, new BigDecimalJsonDeserializer());
//			mapper.serializerByType(BigDecimal.class, new BigDecimalJsonSerializer());
			return mapper.build();
		}
}

