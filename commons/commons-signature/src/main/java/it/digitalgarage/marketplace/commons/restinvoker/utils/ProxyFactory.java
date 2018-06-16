package it.digitalgarage.marketplace.commons.restinvoker.utils;

import java.lang.reflect.InvocationHandler;

// TODO: Auto-generated Javadoc
/**
 * Interface for implementations that can create proxies. This is necessary because some use cases
 * might not work well with the default java dynamic proxies but might require opaque proxies (i.e. cglib).
 * @author george georgovassilis
 *
 */
public interface ProxyFactory {
    
    /**
     * Creates a proxy where each method invocation is passed to 'callback'.
     *
     * @param classLoader the class loader
     * @param interfaces the interfaces
     * @param callback the callback
     * @return the object
     */
    Object createProxy(ClassLoader classLoader, Class<?>[] interfaces, InvocationHandler callback);

}
