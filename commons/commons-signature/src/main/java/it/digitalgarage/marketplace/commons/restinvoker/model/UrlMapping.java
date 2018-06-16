package it.digitalgarage.marketplace.commons.restinvoker.model;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;

import it.digitalgarage.marketplace.commons.restinvoker.model.MethodParameterDescriptor.Type;

// TODO: Auto-generated Javadoc
/**
 * A collection of {@link MethodParameterDescriptor}s which describe the mapping of a method to a REST URL.
 *
 * @author george georgovassilis
 */
public class UrlMapping {

    /** The http method. */
    protected HttpMethod httpMethod = HttpMethod.GET;
    
    /** The parameters. */
    protected List<MethodParameterDescriptor> parameters = new ArrayList<>();
    
    /** The headers. */
    protected String[] headers = new String[0];
    
    /** The consumes. */
    protected String[] consumes = new String[0];
    
    /** The produces. */
    protected String[] produces = new String[0];
    
    /** The cookies. */
    protected String[] cookies = new String[0];
    
    /**
     * Gets the cookies.
     *
     * @return the cookies
     */
    public String[] getCookies() {
        return cookies;
    }

    /**
     * Sets the cookies.
     *
     * @param cookies the new cookies
     */
    public void setCookies(String[] cookies) {
        this.cookies = cookies;
    }

    /**
     * Gets the consumes.
     *
     * @return the consumes
     */
    public String[] getConsumes() {
        return consumes;
    }

    /**
     * Sets the consumes.
     *
     * @param consumes the new consumes
     */
    public void setConsumes(String[] consumes) {
        this.consumes = consumes;
    }

    /**
     * Gets the produces.
     *
     * @return the produces
     */
    public String[] getProduces() {
        return produces;
    }

    /**
     * Sets the produces.
     *
     * @param produces the new produces
     */
    public void setProduces(String[] produces) {
        this.produces = produces;
    }

    /** The url. */
    protected String url;
    
    /**
     * Gets the headers.
     *
     * @return the headers
     */
    public String[] getHeaders() {
        return headers;
    }

    /**
     * Sets the headers.
     *
     * @param headers the new headers
     */
    public void setHeaders(String[] headers) {
        this.headers = headers;
    }
    
    /**
     * Gets the url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     *
     * @param url the new url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the http method.
     *
     * @return the http method
     */
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    /**
     * Sets the http method.
     *
     * @param httpMethod the new http method
     */
    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    /**
     * Gets the parameters.
     *
     * @return the parameters
     */
    public List<MethodParameterDescriptor> getParameters() {
	return parameters;
    }

    /**
     * Sets the parameters.
     *
     * @param parameters the new parameters
     */
    public void setParameters(List<MethodParameterDescriptor> parameters) {
	this.parameters = parameters;
    }


    /**
     * Adds the descriptor.
     *
     * @param descriptor the descriptor
     */
    public void addDescriptor(MethodParameterDescriptor descriptor) {
	parameters.add(descriptor);
    }
    
    /**
     * Checks for request body.
     *
     * @param parameter the parameter
     * @return true, if successful
     */
    public boolean hasRequestBody(String parameter){
	for (MethodParameterDescriptor descriptor:parameters)
	    if (parameter.equals(descriptor.getName()) && (descriptor.getType().equals(Type.requestBody)||descriptor.getType().equals(Type.requestPart)))
		return true;
	return false;
    }
}
