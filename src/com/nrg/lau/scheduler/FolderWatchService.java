package com.nrg.lau.scheduler;


import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nrg.lau.beans.LauJobScheduler;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.tasks.FolderWatchTaskLoadAndSplit;
import com.nrg.lau.tasks.FolderWatchTaskManager;

/*
 * config.properties
 * LauJobScheduler.java
 * ExternalDocuments.java
 * GroupInboxService.java
 * dispatcher-servlet.xml
 */

//@Service
public class FolderWatchService {
protected static Logger log = Logger.getLogger("folderWatchService");
	
	@Autowired 
	@Qualifier("folderWatchImpl")
	private FolderWatch folderWatch;
	
	private static final int CRON_VAL = 2;
	
	@Autowired
	private DataSource dataSource;
	
	//@Scheduled(cron="0 0/" + CRON_VAL + " * * * ?")
	public void doSchedule() {  
		
		java.util.Date date = new java.util.Date();
		log.info("Folder Watch Service ->" + new Timestamp(date.getTime()));  

		try {
			
			//fetch all file names from database with status 'P' & 'C'
			//and pass it to folderWatch.fileCreated(filesMap)
			
			List<File> newFiles = folderWatch.fileCreated(); 
			Iterator<File> itr 	= newFiles.iterator();   
			while(itr.hasNext())   
            {
				File file = itr.next();	
				java.sql.Timestamp dt  = CommonDAO.getTimestamp(getJdbcTemplate());
				String JobId = FolderWatchTaskManager.getRandomNum();
				
				try {
					//insert the file details to db with status P
					folderWatch.insertJob(new Object[] {
						JobId , file.getName(), "P", "LOAD_AND_SPLIT", dt, dt
					});
					
					ExecutorService pool = FolderWatchTaskManager.getInstance();
					List<Object> lst = new ArrayList<Object>();
					lst.add(file.getName());
					//call the first thread/task
					lst.add(pool.submit(new FolderWatchTaskLoadAndSplit(folderWatch,JobId,dataSource,file.getName())));
					//add the thread to future java map
					FolderWatchTaskManager.getFuturesMap().put(FolderWatchTaskManager.getRandomNum(), lst);	
				
				} catch (Exception e) {
					log.error(e);
					//Update the Job Status to error
					LauJobScheduler lauJobScheduler =  new LauJobScheduler();			
					lauJobScheduler.setSTATUS("E");
					lauJobScheduler.setJOB_COMMENT(e.toString());
					folderWatch.updateJobStatus(lauJobScheduler);
				}
				
            }
			
		} catch (Exception e) {
			log.error(e);			
		}
	}
	
	private SimpleJdbcTemplate getJdbcTemplate() {
		return new SimpleJdbcTemplate(dataSource);
	}
	
}
