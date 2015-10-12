package com.nrg.lau.service.docmgt;

import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;

public interface DocMgtService<T> {
	
	public T upload(HttpServletRequest request, DocMgtParameters params) throws Exception;
	public T outBound(DocMgtParameters params) throws Exception;		
	public boolean rename(DocMgtParameters params);
	public boolean detach(DocMgtParameters params);
	public boolean reject(DocMgtParameters params);
	public boolean newGroup(DocMgtParameters params);
	public InputStream retrieveDocument(DocMgtParameters params) throws Exception;
	
}
