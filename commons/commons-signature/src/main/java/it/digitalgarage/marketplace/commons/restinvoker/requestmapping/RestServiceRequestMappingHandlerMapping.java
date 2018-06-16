package it.digitalgarage.marketplace.commons.restinvoker.requestmapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import it.digitalgarage.marketplace.commons.restinvoker.factory.SpringRestService;

// TODO: Auto-generated Javadoc
/**
 * The Class RestServiceRequestMappingHandlerMapping.
 */
public class RestServiceRequestMappingHandlerMapping extends RequestMappingHandlerMapping{
	
	
	/**
	 * Test create handler method.
	 *
	 * @param handler the handler
	 * @param method the method
	 * @return the handler method
	 */
	public HandlerMethod testCreateHandlerMethod(Object handler, Method method){
		return createHandlerMethod(handler, method);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.AbstractHandlerMethodMapping#createHandlerMethod(java.lang.Object, java.lang.reflect.Method)
	 */
	@Override
	protected HandlerMethod createHandlerMethod(Object handler, Method method) {
		HandlerMethod handlerMethod;
		if (handler instanceof String) {
			String beanName = (String) handler;
			handlerMethod = new RestServiceHandlerMethod(beanName,getApplicationContext().getAutowireCapableBeanFactory(), method);
		}
		else {
			handlerMethod = new RestServiceHandlerMethod(handler, method);
		}
		return handlerMethod;
	}
	
	
	/**
	 * The Class RestServiceHandlerMethod.
	 */
	public static class RestServiceHandlerMethod extends HandlerMethod{
		
		/*@Override
		protected Method getBridgedMethod() {
			System.out.println("++++++++RestServiceHandlerMethod.getBridgedMethod");
			return this.interfaceMethod!=null ?  this.interfaceMethod :  super.getBridgedMethod();
		}*/

		/** The interface method. */
		private Method interfaceMethod;
		
		
		/**
		 * Instantiates a new rest service handler method.
		 *
		 * @param bean the bean
		 * @param method the method
		 */
		public RestServiceHandlerMethod(Object bean, Method method) {
			super(bean,method);
			changeType();
		}

		/**
		 * Instantiates a new rest service handler method.
		 *
		 * @param bean the bean
		 * @param methodName the method name
		 * @param parameterTypes the parameter types
		 * @throws NoSuchMethodException the no such method exception
		 */
		public RestServiceHandlerMethod(Object bean, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
			super(bean,methodName,parameterTypes);
			changeType();
		}

		/**
		 * Instantiates a new rest service handler method.
		 *
		 * @param beanName the bean name
		 * @param beanFactory the bean factory
		 * @param method the method
		 */
		public RestServiceHandlerMethod(String beanName, BeanFactory beanFactory, Method method) {
			super(beanName,beanFactory,method);
			changeType();
		}


		/**
		 * Change type.
		 */
		private void changeType(){
			for(Class<?> clazz : getMethod().getDeclaringClass().getInterfaces()){
				if(clazz.isAnnotationPresent(SpringRestService.class)){
					try{
						interfaceMethod = clazz.getMethod(getMethod().getName(), getMethod().getParameterTypes());
						break;		
					}catch(NoSuchMethodException e){
						
					}
				}
			}
			MethodParameter[] params = super.getMethodParameters();
			for(int i=0;i<params.length;i++){
				params[i] = new RestServiceMethodParameter(params[i]);
			}
		}
		
		
		

		/**
		 * The Class RestServiceMethodParameter.
		 */
		private class RestServiceMethodParameter extends MethodParameter{
			
			/** The parameter annotations. */
			private volatile Annotation[] parameterAnnotations;

			/**
			 * Instantiates a new rest service method parameter.
			 *
			 * @param methodParameter the method parameter
			 */
			public RestServiceMethodParameter(MethodParameter methodParameter){
				super(methodParameter);
			}

			
			/* (non-Javadoc)
			 * @see org.springframework.core.MethodParameter#getParameterAnnotations()
			 */
			@Override
			public Annotation[] getParameterAnnotations() {
				if (this.parameterAnnotations == null){
						if(RestServiceHandlerMethod.this.interfaceMethod!=null) {
							Annotation[][] annotationArray = RestServiceHandlerMethod.this.interfaceMethod.getParameterAnnotations();
							if (this.getParameterIndex() >= 0 && this.getParameterIndex() < annotationArray.length) {
								this.parameterAnnotations = annotationArray[this.getParameterIndex()];
							}
							else {
								this.parameterAnnotations = new Annotation[0];
							}
						}else{
							this.parameterAnnotations = super.getParameterAnnotations();
						}
				}
				return this.parameterAnnotations;
			}
			
		}

	}

}
