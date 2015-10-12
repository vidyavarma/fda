package com.nrg.lau.render;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;

public class GetHomeReportAction implements View {

	private static Logger log	= Logger.getLogger(GetHomeReportAction.class);
	
	public String getContentType() {
		return "text/xml";
	}
	
	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("ENTER - HomeListView render()");
		log.info("HomeListView render() - request.getParameter(test)   --->  " + request.getParameter("test"));
			
	    response.setContentType(Constants.CONTENT_TYPE); 
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
	    PrintWriter pw = response.getWriter();		
		pw.write(getXML().toString());		
		pw.flush();
		pw.close();
		
		log.info("EXIT - HomeListView render()");		
	}
	
	private StringBuffer getXML()
	{
		StringBuffer tmp = new StringBuffer();
		tmp.append(Constants.FLEX_XML_HEADER);
		tmp.append(Constants.FLEX_XML_ROWSET + Constants.FLEX_XML_DATAROOT);
		tmp.append("<primoid>20090015</primoid><classification>AE</classification><eventcountry>USA</eventcountry>");
		tmp.append("<datagroup>UK</datagroup><lastedited>Bill Picky </lastedited><patient>Tim Riley</patient>");
		tmp.append("<reporter>John Smith</reporter><repsource>Spontaneous</repsource><reptype>HCP</reptype>");
		tmp.append("<drug>Viagra</drug><event>Chest Pain</event><seriousness>Yes</seriousness>");
		tmp.append("<status>Open</status><noaction>Yes</noaction><medrecords>No</medrecords><daysold>10</daysold>");
		tmp.append(Constants.FLEX_XML_END_DATAROOT);
		tmp.append(Constants.FLEX_XML_DATAROOT);
		tmp.append("<primoid>20090016</primoid><classification>AE</classification><eventcountry>USA</eventcountry><datagroup>UK</datagroup>");
		tmp.append("<lastedited>Fred Moor</lastedited><patient>Philip Johny</patient><reporter>Amy Pet</reporter><repsource>MTP</repsource>");
		tmp.append("<reptype>HCP</reptype><drug>Drug-4</drug><event>Hair Loss</event><seriousness>No</seriousness>");
		tmp.append("<status>Open</status><noaction>Yes</noaction><medrecords>No</medrecords><daysold>10</daysold>");
		tmp.append(Constants.FLEX_XML_END_DATAROOT + Constants.FLEX_XML_END_ROWSET);
		return tmp;
	}

}
