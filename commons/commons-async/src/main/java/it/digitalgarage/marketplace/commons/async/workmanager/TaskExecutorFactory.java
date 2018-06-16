package it.digitalgarage.marketplace.commons.async.workmanager;

import org.springframework.core.task.TaskExecutor;

public interface TaskExecutorFactory {

	TaskExecutor getTaskExecutor();

}