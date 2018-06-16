package it.digitalgarage.marketplace.commons.async.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import it.digitalgarage.marketplace.commons.logging.Logger;
import it.digitalgarage.marketplace.commons.logging.LoggerFactory;



public class StopTaskExecutor implements ApplicationListener<ApplicationContextEvent> {
	
	Logger logger = LoggerFactory.getLogger(StopTaskExecutor.class);
	
	private TaskExecutor executor;
	
	public StopTaskExecutor(TaskExecutor executor) {
		this.executor = executor;
	}
	
	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		if( event instanceof ContextClosedEvent || event instanceof ContextStoppedEvent){
			
		logger.info("RICEVUTO EVENTO DI STOP O CLOSE {}", event);
		if(executor instanceof ThreadPoolTaskExecutor){
			logger.info("shutdown executor");
			((ThreadPoolTaskExecutor)executor).shutdown();
		}else if(executor instanceof ConcurrentTaskExecutor){
			
		}
		
		}
		
	}
    
}
