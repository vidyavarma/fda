package com.nrg.lau.junit;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

import junit.framework.TestCase;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import com.nrg.lau.render.GetAdminRoleAction;

public class Test extends TestCase {
	
	public void test()	{
		Properties p = System.getProperties();
		Locale locale = Locale.getDefault(); // Set the default locale to pre-defined 
		Locale.setDefault(Locale.FRENCH); 
		Enumeration keys = p.keys();
		while (keys.hasMoreElements()) {
		  String key = (String)keys.nextElement();
		  String value = (String)p.get(key);
		  System.out.println(key + ": " + value);
		 
		}
	}
	
}
