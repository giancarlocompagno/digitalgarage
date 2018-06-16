package it.digitalgarage.marketplace.commons.restinvoker.factory;

import java.lang.annotation.Annotation;
import java.util.List;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringValueResolver;

import it.digitalgarage.marketplace.commons.restinvoker.factory.util.AnnotationUtils;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Rest objects.
 */

public class RestFactory implements BeanFactoryPostProcessor,EmbeddedValueResolverAware {
	 
	/** The resolver. */
	StringValueResolver resolver;
	
	/* (non-Javadoc)
	 * @see org.springframework.context.EmbeddedValueResolverAware#setEmbeddedValueResolver(org.springframework.util.StringValueResolver)
	 */
	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.resolver = resolver;
	}
	
	
	RestTemplateFactory restTemplateFactory;
	
	public void setRestTemplateFactory(RestTemplateFactory restTemplateFactory) {
		this.restTemplateFactory = restTemplateFactory;
	}
	
	
	/** The base package. */
	private String basePackage = "it";
	
	/**
	 * Sets the base package.
	 *
	 * @param basePackage the new base package
	 */
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
	

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		createBeanProxy(beanFactory,SpringRestService.class);
		createBeanProxy(beanFactory,JaxrsRestService.class);
	}

	/**
	 * Creates a new Rest object.
	 *
	 * @param beanFactory the bean factory
	 * @param annotation the annotation
	 */
	private void createBeanProxy(ConfigurableListableBeanFactory beanFactory,Class<? extends Annotation> annotation) {
		List<Class<Object>> classes;
		try {
			classes = AnnotationUtils.findAnnotatedClasses(basePackage, annotation);
		} catch (Exception e) {
			throw new BeanInstantiationException(annotation, e.getMessage(), e);
		}
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
		for (Class<Object> classType : classes) {
			Annotation typeService = classType.getAnnotation(annotation);	
			GenericBeanDefinition beanDef = new GenericBeanDefinition();
			beanDef.setBeanClass(getQueryServiceFactory(classType, typeService));
			ConstructorArgumentValues cav = new ConstructorArgumentValues();
			cav.addIndexedArgumentValue(0, classType);
			cav.addIndexedArgumentValue(1, baseUri(classType,typeService));
			if(restTemplateFactory!=null){
				cav.addIndexedArgumentValue(2,restTemplateFactory);
			}
			beanDef.setConstructorArgumentValues(cav);
			registry.registerBeanDefinition(classType.getName() + "Proxy", beanDef);
		}
	}

	/**
	 * Base uri.
	 *
	 * @param c the c
	 * @param typeService the type service
	 * @return the string
	 */
	private String baseUri(Class<Object> c,Annotation typeService){
		String baseUri = null;
		if(typeService instanceof SpringRestService){
			baseUri = ((SpringRestService)typeService).baseUri();  
		}else if(typeService instanceof JaxrsRestService){
			baseUri = ((JaxrsRestService)typeService).baseUri();
		}
		if(baseUri!=null && !baseUri.isEmpty()){
			baseUri = resolver.resolveStringValue(baseUri);
			System.out.println("----------------------------");
			System.out.println("----------------------------");
			System.out.println("---------------------------->"+baseUri);
			System.out.println("----------------------------");
			System.out.println("----------------------------");
			return baseUri;
		}else{
			throw new IllegalStateException("Impossibile individuare una baseUri per l'interface :"+c);
		}
	}

	/**
	 * Gets the query service factory.
	 *
	 * @param c the c
	 * @param typeService the type service
	 * @return the query service factory
	 */
	private static Class<? extends FactoryBean<?>> getQueryServiceFactory(Class<Object> c,Annotation typeService){
		if(typeService instanceof SpringRestService){
			return it.digitalgarage.marketplace.commons.restinvoker.spring.SpringRestInvokerProxyFactoryBean.class;  
		}else if(typeService instanceof JaxrsRestService){
			return it.digitalgarage.marketplace.commons.restinvoker.jaxrs.JaxRsInvokerProxyFactoryBean.class;
		}
		throw new IllegalStateException("Impossibile individuare una classe per l'interface :"+c);
	}
}

