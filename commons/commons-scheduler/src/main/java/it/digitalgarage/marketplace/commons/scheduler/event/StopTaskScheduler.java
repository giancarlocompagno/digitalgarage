package it.digitalgarage.marketplace.commons.scheduler.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.commonj.TimerManagerTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import it.digitalgarage.marketplace.commons.logging.Logger;
import it.digitalgarage.marketplace.commons.logging.LoggerFactory;


public class StopTaskScheduler implements ApplicationListener<ApplicationContextEvent> {

	Logger logger = LoggerFactory.getLogger(StopTaskScheduler.class);

	private TaskScheduler scheduler;

	public StopTaskScheduler(TimerManagerTaskScheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		if( event instanceof ContextClosedEvent || event instanceof ContextStoppedEvent){

			logger.info("RICEVUTO EVENTO DI STOP O CLOSE {}", event);
			if(scheduler instanceof TimerManagerTaskScheduler){
				logger.info("stop scheduler");
				((TimerManagerTaskScheduler)scheduler).stop();
			}else if(scheduler instanceof ThreadPoolTaskScheduler){
				logger.info("shutdown scheduler");
				((ThreadPoolTaskScheduler)scheduler).shutdown();
			}
		}
	}

}
