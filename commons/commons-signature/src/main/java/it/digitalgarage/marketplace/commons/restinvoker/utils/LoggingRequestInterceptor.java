package it.digitalgarage.marketplace.commons.restinvoker.utils;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;

// TODO: Auto-generated Javadoc
/**
 * Utility for logging.
 *
 * @author george georgovassilis
 */
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    /** The Constant requestLog. */
    private static final Log requestLog = LogFactory
	    .getLog("it.eng.rete2i.asset.commons.restinvoker.Request");

    /** The Constant responseLog. */
    private static final Log responseLog = LogFactory
	    .getLog("it.eng.rete2i.asset.commons.restinvoker.Response");

    /* (non-Javadoc)
     * @see org.springframework.http.client.ClientHttpRequestInterceptor#intercept(org.springframework.http.HttpRequest, byte[], org.springframework.http.client.ClientHttpRequestExecution)
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
	    ClientHttpRequestExecution execution) throws IOException {

	ClientHttpResponse response = execution.execute(request, body);

	log(request, body, response);

	return response;
    }

    /**
     * Log.
     *
     * @param request the request
     * @param body the body
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void log(HttpRequest request, byte[] body,
	    ClientHttpResponse response) throws IOException {
	if (requestLog.isDebugEnabled()) {
	    String s = new String(body);
	    requestLog.debug(request.getURI().toString()+" - "+s);
	}
	if (responseLog.isDebugEnabled()) {
	    byte[] b = FileCopyUtils.copyToByteArray(response.getBody());
	    String s = new String(b);
	    responseLog.debug(request.getURI().toString()+" - "+s);
	}
    }
}