package com.nrg.lau.security;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**	
 *	This class provides an application-wide access to the
 *	Spring ApplicationContext! The ApplicationContext is
 *	injected in a static method of the class "AppContext".
 *
 *	Use AppContext.getApplicationContext() to get access
 *	to all Spring Beans.
 *	
 *	@author Rahul  
 **/ 

public class ApplicationContextProvider implements ApplicationContextAware {

	
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		AppContext.setApplicationContext(ctx);
		
	}

}
