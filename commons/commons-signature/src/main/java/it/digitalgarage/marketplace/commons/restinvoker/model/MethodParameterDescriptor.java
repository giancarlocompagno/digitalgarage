package it.digitalgarage.marketplace.commons.restinvoker.model;

import java.lang.reflect.Method;

// TODO: Auto-generated Javadoc
/**
 * Models the relation of a method parameter with an url (fragment) such as part in a path or parameter name.
 *
 * @author george georgovassilis
 */
public class MethodParameterDescriptor {

    /**
     * The Enum Type.
     */
    public enum Type {
	
	/** The http parameter. */
	httpParameter, 
 /** The path variable. */
 pathVariable, 
 /** The request body. */
 requestBody, 
 /** The request part. */
 requestPart, 
 /** The cookie. */
 cookie, 
 /** The http header. */
 httpHeader
    };

    /** The type. */
    protected Type type = Type.httpParameter;
    
    /** The name. */
    protected String name;
    
    /** The value. */
    protected Object value;
    
    /** The method. */
    protected Method method;
    
    /** The parameter ordinal. */
    protected int parameterOrdinal;

    /**
     * Instantiates a new method parameter descriptor.
     */
    public MethodParameterDescriptor(){
    }
    
    /**
     * Instantiates a new method parameter descriptor.
     *
     * @param type the type
     * @param name the name
     * @param value the value
     * @param method the method
     * @param parameterOrdinal the parameter ordinal
     */
    public MethodParameterDescriptor(Type type, String name, Object value, Method method, int parameterOrdinal){
	setType(type);
	setName(name);
	setValue(value);
	this.method = method;
	this.parameterOrdinal = parameterOrdinal;
    }

    /**
     * Gets the method.
     *
     * @return the method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Sets the method.
     *
     * @param method the new method
     */
    public void setMethod(Method method) {
        this.method = method;
    }

    /**
     * Gets the parameter ordinal.
     *
     * @return the parameter ordinal
     */
    public int getParameterOrdinal() {
        return parameterOrdinal;
    }

    /**
     * Sets the parameter ordinal.
     *
     * @param parameterOrdinal the new parameter ordinal
     */
    public void setParameterOrdinal(int parameterOrdinal) {
        this.parameterOrdinal = parameterOrdinal;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public Type getType() {
	return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(Type type) {
	this.type = type;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Object getValue() {
	return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(Object value) {
	this.value = value;
    }

}
