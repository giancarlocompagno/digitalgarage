package it.digitalgarage.marketplace.commons.fe.security.common;
 
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
 
public class RestAuthenticationAccessDeniedHandler implements AccessDeniedHandler {
 
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException arg2) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
 
    }
}