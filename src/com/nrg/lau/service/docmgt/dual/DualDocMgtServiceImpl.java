package com.nrg.lau.service.docmgt.dual;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.nrg.lau.service.docmgt.DocMgtLoader;
import com.nrg.lau.service.docmgt.DocMgtParameters;
import com.nrg.lau.service.docmgt.DocMgtResponse;
import com.nrg.lau.service.docmgt.DocMgtService;

public class DualDocMgtServiceImpl implements DocMgtService<DocMgtResponse> {
	
	private static Logger log = Logger.getLogger(DualDocMgtServiceImpl.class);

	@Override
	public InputStream retrieveDocument(DocMgtParameters params)
			throws Exception {
	
		if (!params.getObjectID().isEmpty()) {
			
			log.info("DualDocMgtServiceImpl (retrieveDocument) -> SHAREPOINT");
			DocMgtService<?> doc = null;
			doc = new DocMgtLoader().getDocMgtService("SHAREPOINT");
			return doc.retrieveDocument(params);
			
		} else {

			log.info("DualDocMgtServiceImpl (retrieveDocument) -> DATABASE");
			DocMgtService<?> doc = null;
			doc = new DocMgtLoader().getDocMgtService("DB");
			return doc.retrieveDocument(params);
		}
		
	}
	
	@Override
	public DocMgtResponse upload(HttpServletRequest request,
			DocMgtParameters params) throws Exception {
		
		if (!params.getObjectID().isEmpty()) {
			
			log.info("DualDocMgtServiceImpl (upload) -> SHAREPOINT");
			DocMgtService<?> doc = null;
			doc = new DocMgtLoader().getDocMgtService("SHAREPOINT");
			return (DocMgtResponse) doc.upload(request, params);
			
		} else {

			log.info("DualDocMgtServiceImpl (upload) -> DATABASE");
			DocMgtService<?> doc = null;
			doc = new DocMgtLoader().getDocMgtService("DB");
			return (DocMgtResponse) doc.upload(request, params);
		}
		
	}
	
	@Override
	public boolean reject(DocMgtParameters params) {
		return false;
	}	

	@Override
	public DocMgtResponse outBound(DocMgtParameters params) 
			throws Exception {
		return null;
	}

	@Override
	public boolean rename(DocMgtParameters params) {
		return false;
	}

	@Override
	public boolean detach(DocMgtParameters params) {
		return false;
	}	

	@Override
	public boolean newGroup(DocMgtParameters params) {
		return false;
	}	

}