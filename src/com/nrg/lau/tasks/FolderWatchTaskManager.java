package com.nrg.lau.tasks;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

public class FolderWatchTaskManager {
	
	private static Map <String, List <Object>> futures = 
			new HashMap <String, List <Object>>();
	private static ExecutorService instance = null;
	private static final int POOL = 10; 
	protected static Logger log = Logger.getLogger("FolderWatchTaskManager");
		
	public static ExecutorService getInstance() {		
		if(instance == null) {
			instance = Executors.newFixedThreadPool(POOL);
			log.info("FolderWatchTaskManager getInstance() -> " + "Thread Pool created");
		}
		flushCompleted();
		return instance;
	}
	
	public static Map <String, List <Object>> getFuturesMap() {
		return futures;
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized boolean cancelTask(String uid) {	    
		List<Object> obj = futures.get(uid);		
		Future<Object> future = null;
		boolean success = false;
		if(obj.size() > 0) {
			future = (Future<Object>) obj.get(1);			
			if (future != null) {
				success = (future.isDone() ? true : future.cancel(true));
				if (success) {
					futures.remove(uid);
					log.info("FolderWatchTaskManager cancelTask() -> " + obj.get(0).toString() 
			    			+ " {" + uid + "}");
				}
			}
		}	   
	    return success; 
	}
	
	public static synchronized String getRandomNum() {
		return String.valueOf(java.util.UUID.randomUUID());
	}
	
	@SuppressWarnings("all")
	public static void printStatus() {
		
		log.info("***********************JOB POOL STATUS***********************");
		for (Iterator<Entry<String, List<Object>>> i = futures.entrySet().iterator(); i.hasNext(); )  
		{  
			Map.Entry mapEntry = i.next(); 
			List<Object> list = (List<Object>) mapEntry.getValue();			
			Future<Object> future = (Future<Object>) list.get(1);			
			log.info("Key -> " + mapEntry.getKey() + " Thread Status -> " + future.isDone() + " Thread Description -> " + list.get(0).toString());
		}
		log.info("***********************JOB POOL STATUS***********************");
	}
	
	@SuppressWarnings("all")
	public static void flushCompleted() {		
		
		for (Iterator<Entry<String, List<Object>>> i = futures.entrySet().iterator(); i.hasNext(); )  
		{  
			Map.Entry mapEntry = i.next();  
			List<Object> list = (List<Object>) mapEntry.getValue();
			Future<Object> future = (Future<Object>) list.get(1);
			
		    if (future.isDone())  
		    {  
		    	log.info("Removed completed thread -> " + list.get(0).toString() 
		    			+ " {" + mapEntry.getKey() + "}"); 
		        i.remove(); 
		    }  
		}		
	}
}