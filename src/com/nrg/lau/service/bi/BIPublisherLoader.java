package com.nrg.lau.service.bi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nrg.lau.utils.ReadConfig;

public class BIPublisherLoader {
	
	private static Logger log	= Logger.getLogger(BIPublisherLoader.class);
	private static final Map<String, String> BI_IMPLEMENTATION_CLASS = 
	    Collections.unmodifiableMap(new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{ 
	        put("WEB-SERVICE", "com.nrg.lau.service.bi.BIPublisherWebServiceImpl");
	    }});
	
	public static BIPublisher getBIPublisher() {
		
		BIPublisher bi = null;
		String className = "";
		
		try {
			//BI_OPTION from config file will decide which class to be loaded
			//for BI Publisher
			className = ReadConfig.getValue("BI_OPTION");
			if(className != null){
				Class<?> obj = Class.forName(BI_IMPLEMENTATION_CLASS.get(className));
				bi = (BIPublisher)obj.newInstance();
			}else {
				log.error("BI Publisher config not available, loading default implementation");
				Class<?> obj = Class.forName(BI_IMPLEMENTATION_CLASS.get("WEB-SERVICE"));
				bi = (BIPublisher)obj.newInstance();
			}
		
		}catch (Exception e) {
			log.error(e);
		}
		
		return bi;
	}

}
