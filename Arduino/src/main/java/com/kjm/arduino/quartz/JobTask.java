package com.kjm.arduino.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobTask {

	//private  Logger logger = LoggerFactory.getLogger(JobTask.class);
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void jobTask() {
	       
		logger.debug("Job Test.");
		logger.debug("Job Test.");
		logger.debug("Job Test.");
	   
	}
}
 