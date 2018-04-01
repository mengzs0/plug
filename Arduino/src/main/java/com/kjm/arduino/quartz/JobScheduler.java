package com.kjm.arduino.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class JobScheduler extends QuartzJobBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	JobTask jobTask;

	public void setJobTask(JobTask jobTask) {
		this.jobTask = jobTask;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {

		jobTask.jobTask();
	}

}