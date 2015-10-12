package com.nrg.lau.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.nrg.lau.commons.Constants;

public class LauFilter implements Filter {
	
	private static Logger log = Logger.getLogger(LauFilter.class);

	public void destroy() {	
		
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest=(HttpServletRequest)request;
		
		if(log.isInfoEnabled())	{
			Enumeration params 	= request.getParameterNames ();
			StringBuffer str 	= new StringBuffer();
			int cnt				= 0;
			String paramName;
		    String paramVal;		    
		   	
		    str.append("[");
		    while (params.hasMoreElements ()) {				
		    	
		    	paramName = (String) params.nextElement ();
		    	paramVal = request.getParameter (paramName);
		    	if(!paramName.trim().equalsIgnoreCase(Constants.SPRING_USER_PASSWORD)) {
			    	if(cnt == 0)	{
			    		str.append(paramName);
			    		str.append("=");		    		
			    		str.append(paramVal);
			    	}	else {
			    		str.append(",");
			    		str.append(paramName);
			    		str.append("=");		    		
			    		str.append(paramVal);
			    	}		    		
			        cnt++;
		    	}
			}
		    str.append("]");
		    log.info(httpRequest.getRequestURI() + "{" + str.toString() + "}");
		}		
		
		Enumeration headerNames = httpRequest.getHeaderNames();
	    while(headerNames.hasMoreElements()) {
	      String headerName = (String)headerNames.nextElement();
	      System.out.println(headerName);
	      System.out.println(httpRequest.getHeader(headerName));
	    }
	    
	    BufferedReader bufferedReader = null;
	    StringBuilder stringBuilder = new StringBuilder();  
	    InputStream inputStream = httpRequest.getInputStream(); 
	    if (inputStream != null) {  
	        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
	        char[] charBuffer = new char[128];  
	        int bytesRead = -1;  
	        while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {  
	          stringBuilder.append(charBuffer, 0, bytesRead);  
	        }  
	    }
	    System.out.println(stringBuilder.toString() + "--");
	    

	    
		chain.doFilter(request, response); 
	}

	public void init(FilterConfig config) throws ServletException {	
		
	}

}
