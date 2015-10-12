package com.nrg.lau.service.batch;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nrg.lau.security.AppContext;

public class DeleteAllArchiveJobDetails extends QuartzJobBean{
	
	private static Logger log = Logger.getLogger(DeleteAllArchiveJobDetails.class);
	private JobLauncher jobLauncher;
	private Job jobDeleteAllBatch;
	
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		
		ApplicationContext ctx 	= 	AppContext.getApplicationContext();
		jobLauncher = (JobLauncher)ctx.getBean("jobLauncher");
		jobDeleteAllBatch = (Job)ctx.getBean("jobDeleteAllBatch");			
		
		try {
			JobParametersBuilder builder = new JobParametersBuilder();
			builder.addString("key", String.valueOf(java.util.UUID.randomUUID()));
			jobLauncher.run(jobDeleteAllBatch, builder.toJobParameters());
		
		} catch (Exception e) {
			log.error(e);
		} 
		
	}

}
