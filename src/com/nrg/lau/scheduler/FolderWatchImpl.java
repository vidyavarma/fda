package com.nrg.lau.scheduler;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;

import com.nrg.lau.beans.LauJobScheduler;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.utils.ReadConfig;

@Component("folderWatchImpl")
public class FolderWatchImpl implements FolderWatch {
	
	@Autowired
	private DataSource dataSource;
	
	protected static Logger logger = Logger.getLogger("folderWatchImpl");	
	
	private SimpleJdbcTemplate getJdbcTemplate() {
		return new SimpleJdbcTemplate(dataSource);
	}
	
	public List<File> fileCreated() throws Exception {
		
		List<File> list 	= new ArrayList<File>();
		try {
		HashMap<String, File> filesMap = new HashMap<String, File>();				
		List<LauJobScheduler> jobSchedulers = getActiveJobs("P");
		for(LauJobScheduler jobScheduler : jobSchedulers) {
			filesMap.put(jobScheduler.getJOB_NAME(), new File(
					ReadConfig.getMessage().getString("folderWatchLocation") + "\\" + jobScheduler.getJOB_NAME()));		
		}		
		File fileLocation 	= new File(ReadConfig.getMessage().getString("folderWatchLocation"));
		File[] listOfFiles 	= fileLocation.listFiles();;
		logger.info("$$$ folder path is :"+ReadConfig.getMessage().getString("folderWatchLocation"));
		logger.debug("Total files available before check -> " + listOfFiles.length);		
		for (File listOfFile : listOfFiles) {			
		   if ((!filesMap.containsKey(listOfFile.getName())) && (!listOfFile.isDirectory())) {		    	
		    	list.add(listOfFile);
		   }
		}
		
		logger.debug("Total files available after check -> " + list.size());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;		
	}	

	public boolean fileDelete(File file) throws Exception {
		return file.delete();
	}
	
	public List<LauJobScheduler> getActiveJobs(String status) throws Exception{
	
		String sql = "SELECT * FROM LAU_JOB_SCHEDULER ";		
		if(status.trim().length() > 0)
			sql += " WHERE STATUS IN ('" + status +"','E')";
		
		//logger.info(sql);
		List<LauJobScheduler> jobScheduler = getJdbcTemplate().query(sql,new BeanPropertyRowMapper<LauJobScheduler>(LauJobScheduler.class));		
		return jobScheduler;
	}
	
	public void insertJob(Object[] params) throws Exception{
		
		String sql = "INSERT INTO LAU_JOB_SCHEDULER (THREAD_ID,JOB_NAME,STATUS,JOB_DESCRIPTION," +
				"START_TIME,UPDATE_TIMESTAMP,UPDATE_USER_ID) VALUES (?,?,?,?,?,?,'" + 
				ReadConfig.getMessage().getString("systemUser") + "')";		
		int rtn = getJdbcTemplate().update(sql, params);		
		logger.info("Insert job status -> " + rtn);
		
	}
	
	public void updateJobStatus(LauJobScheduler lauJobScheduler) throws Exception{
		try {
		
			String sql = "UPDATE LAU_JOB_SCHEDULER SET STATUS=?,END_TIME=?,UPDATE_TIMESTAMP=?,JOB_COMMENT=? " +
					"WHERE JOB_ID= (SELECT JOB_ID FROM LAU_JOB_SCHEDULER WHERE THREAD_ID=?)";	
			java.sql.Timestamp dt  = CommonDAO.getTimestamp(getJdbcTemplate());		
			
			int rtn = getJdbcTemplate().update(sql, new Object[] {
					lauJobScheduler.getSTATUS(), dt, dt, lauJobScheduler.getJOB_COMMENT(), lauJobScheduler.getTHREAD_ID()
			});
			logger.info("Update job status -> " + rtn);
		}catch (Exception e) {
			logger.info(e);
			e.printStackTrace();
		}		
	}
	
	public Object getOutput(String threadId) throws Exception{		
		
		String sql = "SELECT OUTPUT FROM LAU_JOB_SCHEDULER WHERE JOB_ID= (SELECT JOB_ID FROM LAU_JOB_SCHEDULER WHERE THREAD_ID = ?)";
		logger.info("sql -> " + sql);	 
		Object obj = getJdbcTemplate().queryForObject(
			sql, Object.class, threadId);
		
		return obj;

		
	}


}
