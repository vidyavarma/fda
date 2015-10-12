package com.nrg.lau.service.docmgt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

import com.nrg.lau.utils.ReadConfig;

public class DocMgtLoader {
	
	private static Logger log	= Logger.getLogger(DocMgtLoader.class);
		
	private final Map<String, String> DOC_IMPLEMENTATION_CLASS = 
	    Collections.unmodifiableMap(new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{ 
	        put("DB", "com.nrg.lau.service.docmgt.db.DefaultDocMgtServiceImpl");
	        put("SHAREPOINT", "com.nrg.lau.service.docmgt.sp.SPDocMgtServiceImpl");
	        put("DUAL", "com.nrg.lau.service.docmgt.dual.DualDocMgtServiceImpl");
	    }});
	
	public static DocMgtService<?> getDocMgtService() {
		return new DocMgtLoader().getDocMgtService(null);
	}
	
	public DocMgtService<?> getDocMgtService(String className) {
		
		DocMgtService<?> service = null;		
			
		//Check ENABLE_EXTERNAL_DOC_MANAGEMENT, if N - use database
		//if Y - use implementation from DOCUMENT_MGT_TYPE	
		String documentMgt = ReadConfig.getValue("ENABLE_EXTERNAL_DOC_MANAGEMENT");
		if (documentMgt != null) {
			if (documentMgt.toUpperCase().equalsIgnoreCase("N")) {
				//Call database implementation
				service = getDocMgtClass("DB");
			} else if (documentMgt.toUpperCase().equalsIgnoreCase("Y")) {

				String documentMgtType = ReadConfig.getValue("DOCUMENT_MGT_TYPE");
				if (documentMgtType != null) {
					
					if(className != null && (!className.isEmpty())) {
						service = getDocMgtClass(className);
					} else {
						service = getDocMgtClass(documentMgtType.toUpperCase());
					}			
					
				} else {
					log.error("ENABLE_EXTERNAL_DOC_MANAGEMENT set to 'Y' but no value set for DOCUMENT_MGT_TYPE");
				}
			}
		} else {
			log.error("ENABLE_EXTERNAL_DOC_MANAGEMENT not set in the properties file");
		}
		
		return service;
	}
	
	private DocMgtService<?> getDocMgtClass(String className) {
		
		DocMgtService<?> service = null;
		Class<?> obj;
		try {
			obj = Class.forName(DOC_IMPLEMENTATION_CLASS.get(className.toUpperCase()));
			service = (DocMgtService<?>) obj.newInstance();
		} catch (Exception e) {
			log.error(e);
		}

		return service;
	}
}