/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.digitalgarage.marketplace.commons.async.workmanager;

import org.springframework.core.task.TaskExecutor;


public class ManagedTaskExecutorFactory implements TaskExecutorFactory {


	private TaskExecutor taskExecutor;

	public ManagedTaskExecutorFactory(TaskExecutor taskExecutor) {
		this.taskExecutor=taskExecutor;
	}

	/* (non-Javadoc)
	 * @see it.eng.eni.myeni.commons.cluster.workmanager.EsexutorServiceFactory#getExecutorService()
	 */
	@Override
	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

}
