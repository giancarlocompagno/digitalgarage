package it.digitalgarage.marketplace.commons.restinvoker;

import java.lang.reflect.Method;

import it.digitalgarage.marketplace.commons.restinvoker.model.UrlMapping;

// TODO: Auto-generated Javadoc
/**
 * Looks at methods and extracts mappings to REST URLs.
 *
 * @author george georgovassilis
 */
public interface MethodInspector {

    /**
     * Inspect.
     *
     * @param method the method
     * @param args the args
     * @return the url mapping
     */
    UrlMapping inspect(Method method, Object[] args);
}
