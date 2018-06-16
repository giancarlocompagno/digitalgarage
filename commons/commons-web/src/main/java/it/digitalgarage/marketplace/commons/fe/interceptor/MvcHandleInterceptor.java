package it.digitalgarage.marketplace.commons.fe.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import it.digitalgarage.marketplace.commons.logging.Logger;
import it.digitalgarage.marketplace.commons.logging.LoggerFactory;

public class MvcHandleInterceptor extends HandlerInterceptorAdapter {
	
    private static final Logger log = LoggerFactory.getLogger(MvcHandleInterceptor.class);
    
    @Override
    public void afterCompletion (HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        if (e != null){
            log.error("[ERRORE!]",e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter writer = response.getWriter();
			writer.print("<html><head><title>Oops an error happened!</title></head>");
            writer.print("<body>Something bad happened uh-oh!</body>");
            writer.println("</html>");
            writer.flush();
        }
    }
}
