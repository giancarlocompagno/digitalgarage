package it.digitalgarage.marketplace.commons.restinvoker.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import it.digitalgarage.marketplace.commons.restinvoker.MethodInspector;

// TODO: Auto-generated Javadoc
/**
 * Exception thrown by the a {@link MethodInspector} implementation when mapping errors are detected
 * on a service method or its arguments.
 * @author george georgovassilis
 *
 */
public class MappingDeclarationException extends RuntimeException{
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The method. */
	protected Method method;
    
    /** The annotation. */
    protected Annotation annotation;
    
    /** The parameter index. */
    protected int parameterIndex;
    
    /**
     * Instantiates a new mapping declaration exception.
     *
     * @param message the message
     * @param method the method
     * @param annotation the annotation
     * @param parameterIndex the parameter index
     */
    public MappingDeclarationException(String message, Method method, Annotation annotation, int parameterIndex) {
	super(message);
	this.method = method;
	this.annotation = annotation;
	this.parameterIndex = parameterIndex;
    }
}
