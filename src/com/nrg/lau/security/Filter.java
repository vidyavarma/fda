package com.nrg.lau.security;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.xml.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.nrg.lau.commons.Constants;

public class Filter extends DelegatingFilterProxy {

	private static Logger log = Logger.getLogger(Filter.class);

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		//UTF-8 Encoding change

		request.setCharacterEncoding("UTF-8");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		if (log.isInfoEnabled()) {
			Enumeration<?> params = httpRequest.getParameterNames();
			StringBuffer str = new StringBuffer();
			int cnt = 0;
			String paramName;
			String paramVal;

			str.append("[");
			while (params.hasMoreElements()) {

				paramName = (String) params.nextElement();
				paramVal = httpRequest.getParameter(paramName);
				if (!paramName.trim().equalsIgnoreCase(
						Constants.SPRING_USER_PASSWORD)) {
					if (cnt == 0) {
						str.append(paramName);
						str.append("=");
						str.append(paramVal);
					} else {
						str.append(",");
						str.append(paramName);
						str.append("=");
						str.append(paramVal);
					}
					cnt++;
				}
			}
			str.append("]");
			log.warn(httpRequest.getRequestURI() + "{" + str.toString() + "}");
		}
		log.warn("servlet path:"+httpRequest.getServletPath());
		if (!(httpRequest.getServletPath().equals("/j_spring_security_check")
			    ||  httpRequest.getServletPath().equals("/j_spring_security_logout") 
			    ||  httpRequest.getServletPath().equals("/primo.jsp")
			    ||  httpRequest.getServletPath().equals("/index.jsp")
			    ||  httpRequest.getServletPath().equals("/index.html")
			    ||  httpRequest.getServletPath().equals("/alive.jsp")
			    ||  httpRequest.getServletPath().equals("/login-xml.jsp")
			    ||  httpRequest.getServletPath().equals("/history/history.css")
			    ||  httpRequest.getServletPath().equals("/history/history.js")
			    ||  httpRequest.getServletPath().equals("/history/historyFrame.html")
			    ||  httpRequest.getServletPath().equals("/AC_OETags.js")
			    ||  httpRequest.getServletPath().equals("/swfobject.js")
			    ||  httpRequest.getServletPath().equals("/primo.swf")
			    
			    ||  httpRequest.getServletPath().equals("/primodocbrowser.html")
			    ||  httpRequest.getServletPath().equals("/primodocbrowser.swf")		    
			    ||  httpRequest.getServletPath().equals("/login-xml.jsp")
			    ||  httpRequest.getServletPath().equals("/login2.jsp")
			    ||  httpRequest.getServletPath().equals("/log-reader.jsp")
			    ||  httpRequest.getServletPath().equals("/healthcheck.jsp") 	
			    ||  httpRequest.getServletPath().equals("/setAttachment.do") 	//attachment service call may not have session id    
			    ||  httpRequest.getServletPath().equals("/setLayoutTemplateFile.do") 	///setLayoutTemplateFile.do service call may not have session id  
			    ||  httpRequest.getServletPath().equals("/setDataTemplateFile.do") 	///setDataTemplateFile.do service call may not have session id  
			    ||  httpRequest.getServletPath().equals("/services/SetGroupInbox")  
			    ||  httpRequest.getServletPath().equals("/SetAppianValues") 
			    ||  httpRequest.getServletPath().equals("/webService/SetExternalRule") 
		)) {
					
			HttpSession session = httpRequest.getSession(false);
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			if (session == null) {
				log.warn("Filter: Invalid Session -> Redirecting...");
				httpResponse.sendRedirect("login-xml.jsp");
				return;
			}
		}

		super.doFilter(request, response, filterChain);
	}

	protected void initFilterBean() throws ServletException {

		try {

			URL fileURL = Filter.class.getResource("/log4j.xml");
			File log4j 	= new File(fileURL.toURI());

			if (log4j.exists()) {
				log.info("*** Initializing log4j ***");
				//PropertyConfigurator.configure(fileURL);
				DOMConfigurator.configure(fileURL);
			} else {
				log
				.info("*** File not found, so initializing log4j with BasicConfigurator ***");
				BasicConfigurator.configure();
				//DOMConfigurator.configure(element)
			}

		} catch (Exception e) {
			log.error("....Error initializing log4j.xml");
			log.error(e);
		}
		super.initFilterBean();
	}

}
