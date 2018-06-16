package it.digitalgarage.marketplace.commons.restinvoker.spring;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringValueResolver;

import it.digitalgarage.marketplace.commons.restinvoker.MethodInspector;

// TODO: Auto-generated Javadoc
/**
 * Base class for annotation method inspectors.
 *
 * @author George Georgovassilis
 */
public abstract class BaseAnnotationMethodInspector implements MethodInspector, EmbeddedValueResolverAware{

    /** The value resolver. */
    protected StringValueResolver valueResolver;

    /* (non-Javadoc)
     * @see org.springframework.context.EmbeddedValueResolverAware#setEmbeddedValueResolver(org.springframework.util.StringValueResolver)
     */
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
	this.valueResolver = resolver;
    }
    
    /**
     * Will replace property placeholders with their values, e.g. ${serverUrl}/customer with http://example.com/js/customer if
     * serverUrl is a property that the application context resolves to http://example.com.js
     *
     * @param expression the expression
     * @return the string
     */
    protected String resolveExpression(String expression){
	return valueResolver.resolveStringValue(expression);
    }
}
