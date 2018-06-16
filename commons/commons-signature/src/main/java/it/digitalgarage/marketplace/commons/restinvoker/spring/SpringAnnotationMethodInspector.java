package it.digitalgarage.marketplace.commons.restinvoker.spring;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import it.digitalgarage.marketplace.commons.restinvoker.model.BasicAuthentication;
import it.digitalgarage.marketplace.commons.restinvoker.model.Header;
import it.digitalgarage.marketplace.commons.restinvoker.model.MappingDeclarationException;
import it.digitalgarage.marketplace.commons.restinvoker.model.MethodParameterDescriptor;
import it.digitalgarage.marketplace.commons.restinvoker.model.UrlMapping;
import it.digitalgarage.marketplace.commons.restinvoker.model.MethodParameterDescriptor.Type;
import it.digitalgarage.marketplace.commons.restinvoker.spring.multipart.MultipartFileResource;
import it.digitalgarage.marketplace.commons.restinvoker.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * Looks at methods and extracts {@link RequestParam}, {@link PathVariable},
 * {@link RequestBody} and {@link RequestMapping} annotations.
 *
 * @author george georgovassilis
 */
public class SpringAnnotationMethodInspector extends BaseAnnotationMethodInspector{

    /* (non-Javadoc)
     * @see it.eng.rete2i.asset.commons.restinvoker.MethodInspector#inspect(java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public UrlMapping inspect(Method method, Object[] args) {
	UrlMapping urlMapping = new UrlMapping();
	
	RequestMapping rm = AnnotationUtils.findAnnotation(method,RequestMapping.class);
	if (rm == null)
	    return null;
	if (!Utils.hasValue(rm.value()))
	    throw new MappingDeclarationException("Path missing from @RequestMapping on "+method.toGenericString(), method, rm, -1);
	urlMapping.setUrl(resolveExpression(rm.value()[0]));
	urlMapping.setHeaders(rm.headers());
	urlMapping.setConsumes(rm.consumes());
	urlMapping.setProduces(rm.produces());
	
	BasicAuthentication ba = AnnotationUtils.findAnnotation(method,BasicAuthentication.class);
	if(ba!=null){
		String[] headers = urlMapping.getHeaders();
		String[] newHeaders = new String[headers.length+1];
		String auth = ba.username() + ":" + ba.password();
        byte[] encodedAuth = Base64Utils.encode( auth.getBytes(Charset.forName("US-ASCII")) );
		String authHeader = "Authorization=+Basic " + new String( encodedAuth );
		System.arraycopy(headers, 0, newHeaders, 0, headers.length);
		newHeaders[newHeaders.length-1] = authHeader; 
		urlMapping.setHeaders(newHeaders);
	}
	
	if (Utils.hasValue(rm.method())) {
	    if (rm.method().length!=1)
		throw new MappingDeclarationException("Multiple HTTP methods on @RequestMapping on "+method.toGenericString(), method, rm, -1);
	    urlMapping.setHttpMethod(HttpMethod.valueOf(rm.method()[0].name()));
	}
	Annotation[][] parameterAnnotations = method.getParameterAnnotations();
	if (parameterAnnotations.length != method.getParameterTypes().length)
	    throw new MappingDeclarationException(
		    String.format(
			    "Annotation mismatch: method has %d parameters but %d have been annotated on %s",
			    parameterAnnotations.length,
			    method.getParameterTypes().length,
			    method.toString()), method, rm, -1);
	int i = 0;
	for (Annotation[] annotations : parameterAnnotations) {
	    Object value = args[i];
	    i++;
	    String parameterName = "";
	    Type parameterType = null;
	    boolean parameterFound = false;
	    boolean parameterNameRequired = false;
	    for (Annotation annotation : annotations) {
		if (PathVariable.class.isAssignableFrom(annotation
			.annotationType())) {
		    PathVariable pv = (PathVariable) annotation;
		    parameterName = pv.value();
		    parameterType = Type.pathVariable;
		    parameterFound = true;
		    parameterNameRequired = true;
		}
		if (RequestParam.class.isAssignableFrom(annotation
			.annotationType())) {
		    RequestParam pv = (RequestParam) annotation;
		    parameterName = pv.value();
		    urlMapping.addDescriptor(new MethodParameterDescriptor(
			    Type.httpParameter, parameterName, value, method, i));
		    parameterFound = true;
		    parameterNameRequired = true;
		}
		if (Header.class.isAssignableFrom(annotation
			.annotationType())) {
		    Header h = (Header) annotation;
		    parameterName = h.value();
		    parameterType = Type.httpHeader;
		    parameterFound = true;
		    parameterNameRequired = true;
		}
		if (RequestHeader.class.isAssignableFrom(annotation
				.annotationType())) {
			RequestHeader h = (RequestHeader) annotation;
			    parameterName = h.value();
			    parameterType = Type.httpHeader;
			    parameterFound = true;
			    parameterNameRequired = true;
			}
		if (RequestBody.class.isAssignableFrom(annotation
			.annotationType())) {
		    parameterType = Type.requestBody;
		    parameterFound = true;
		}
		if (RequestPart.class.isAssignableFrom(annotation
			.annotationType())) {
			RequestPart h = (RequestPart) annotation;
		    parameterType = Type.requestPart;
		    parameterFound = true;
		    parameterName = h.value();
		    if(value instanceof MultipartFile){
		    	value = new MultipartFileResource((MultipartFile)value);
		    }
		}
		if (CookieValue.class.isAssignableFrom(annotation
			.annotationType())) {
		    parameterType = Type.cookie;
		    parameterFound = true;
		    CookieValue cv = (CookieValue) annotation;
		    parameterName = cv.value();
		    parameterNameRequired = true;
		}
	    }
	    if (!parameterFound)
		throw new MappingDeclarationException(
			String.format(
				"Couldn't find mapping annotation on parameter %d of method %s",
				i, method.toGenericString()), method, null, i);
	    if (parameterType != null) {
		if (parameterNameRequired && !Utils.hasValue(parameterName))
		    throw new MappingDeclarationException(String.format("No name specified for parameter %d on method %s", i, method.toGenericString()), method, null, i);
		urlMapping.addDescriptor(new MethodParameterDescriptor(parameterType, parameterName, value, method, i));
	    }
	}
	return urlMapping;
    }
}
