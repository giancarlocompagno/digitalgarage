package it.digitalgarage.marketplace.commons.restinvoker.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;

// TODO: Auto-generated Javadoc
/**
 * Implements proxies with cglib.
 *
 * @author george georgovassilis
 */
public class CglibProxyFactory implements ProxyFactory{
    
    /** The base class. */
    protected Class<?> baseClass;
    
    /** The class loader. */
    protected ClassLoader classLoader;
    
    /**
     * Instantiates a new cglib proxy factory.
     *
     * @param classLoader the class loader
     * @param baseClass the base class
     */
    public CglibProxyFactory(ClassLoader classLoader, Class<?> baseClass) {
	this.baseClass = baseClass;
	this.classLoader = classLoader;
    }

    /* (non-Javadoc)
     * @see it.eng.rete2i.asset.commons.restinvoker.utils.ProxyFactory#createProxy(java.lang.ClassLoader, java.lang.Class[], java.lang.reflect.InvocationHandler)
     */
    @Override
    public Object createProxy(final ClassLoader classLoader, final Class<?>[] interfaces,
	    final InvocationHandler callback) {
	Enhancer enhancer = new Enhancer();
	enhancer.setSuperclass(Object.class);
	enhancer.setClassLoader(classLoader);
	enhancer.setCallback(new net.sf.cglib.proxy.InvocationHandler() {
	    
	    @Override
	    public Object invoke(Object proxy, Method method, Object[] args)
		    throws Throwable {
		return callback.invoke(proxy, method, args);
	    }
	});
	enhancer.setInterfaces(interfaces);
	return enhancer.create();
    }

}
