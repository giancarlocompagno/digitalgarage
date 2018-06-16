package it.digitalgarage.marketplace.commons.restinvoker.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

// TODO: Auto-generated Javadoc
/**
 * Generates standard dynamic java proxies.
 *
 * @author george georgovassilis
 */
public class DynamicJavaProxyFactory implements ProxyFactory{

    /* (non-Javadoc)
     * @see it.eng.rete2i.asset.commons.restinvoker.utils.ProxyFactory#createProxy(java.lang.ClassLoader, java.lang.Class[], java.lang.reflect.InvocationHandler)
     */
    @Override
    public Object createProxy(ClassLoader classLoader, Class<?>[] interfaces,
	    InvocationHandler callback) {
	return Proxy.newProxyInstance(classLoader, interfaces, callback);
    }

}
