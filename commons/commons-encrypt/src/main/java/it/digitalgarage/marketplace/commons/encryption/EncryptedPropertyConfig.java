package it.digitalgarage.marketplace.commons.encryption;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptedPropertyConfig {

	

	@Bean
	public CustomEditorConfigurer customEditorConfigurer() {

	    Map<Class<?>, Class<? extends PropertyEditor>> customEditors = 
	            new HashMap<Class<?>, Class<? extends PropertyEditor>>(1);
	    customEditors.put(EncryptedProperty.class, EncryptedPropertyEditor.class);

	    CustomEditorConfigurer configurer = new CustomEditorConfigurer();
	    configurer.setCustomEditors(customEditors);

	    return configurer;
	}
	
	
}

