package it.digitalgarage.marketplace.commons.fe.filter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import it.digitalgarage.marketplace.commons.logging.LoggerFactory;

public class LogbackSubystemIdContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	String SUBSYSTEM_ID = servletContextEvent.getServletContext().getContextPath();
    	if(SUBSYSTEM_ID!=null){
    		SUBSYSTEM_ID = SUBSYSTEM_ID.replaceAll("/", "");
    		LoggerFactory.register(SUBSYSTEM_ID);
    	}
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
