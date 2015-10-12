package com.nrg.lau.render;

import java.util.Date;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppianServicesAccesserWrapper {
	public long setCompletionDate(long activityID, Date completeDate  ) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
		AppianServicesAccesserWrapper appianServices = (AppianServicesAccesserWrapper) ctx.getBean("AppianServicesAccesser");
		Long successID = appianServices.setCompletionDate(activityID, completeDate);
		return successID;
	}
}
