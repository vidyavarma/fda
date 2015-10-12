package com.nrg.lau.render;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.digester.Digester;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;

public class ExternalSiteView implements View {

	private static Logger log	= Logger.getLogger(ExternalSiteView.class);
	//private Hashtable dataSources = new Hashtable();
	
	public String getContentType() {
		return "text/xml";
	}
	
	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("ENTER - ExternalSiteView render()");
		//log.info("ExternalSiteView render() - request.getParameter(externalText)   --->  " + request.getParameter("externalText"));
		log.info("ExternalSiteView render() - request.getParameter(var)   --->  " + request.getParameter("var"));
		
		
		Digester digester = new Digester();

	    digester.push(this);

	    digester.addCallMethod("datasources/datasource", "addDataSource", 5 );
	    digester.addCallParam("datasources/datasource/name", 0);
	    digester.addCallParam("datasources/datasource/driver", 1);
	    digester.addCallParam("datasources/datasource/url", 2);
	    digester.addCallParam("datasources/datasource/username", 3);
	    digester.addCallParam("datasources/datasource/password", 4);
	    
	    digester.parse(new StringReader(request.getParameter("var")));
		
		System.out.println("::::::::::::::::::::::::::::::::::::::");
		
		
		
		MultipartHttpServletRequest httpRequest = (MultipartHttpServletRequest)request;
		MultipartFile file = httpRequest.getFile("externalFile");
		if(file != null) {   
			log.info(file.getOriginalFilename());
			log.info(file.getContentType());    
            log.info(file.getName());  
            File save = new File("C:/test/" + file.getOriginalFilename());
            System.out.println("***");
            FileUtils.writeByteArrayToFile(save, file.getBytes());
            
        }   
	    response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw = response.getWriter();		
		pw.write(getXML().toString());		
		pw.flush();
		pw.close();
		
		log.info("EXIT - ExternalSiteView render()");		
	}
	
	private StringBuffer getXML()
	{
		StringBuffer tmp = new StringBuffer();
		tmp.append("<success>Y</success>");		
		return tmp;
	}
	
	 public void addDataSource(String name,
             String driver,
             String url,
             String userName,
             String password)
	{
	DataSource dataSource = new DataSource(name, driver,url, userName, password);
	//dataSources.put(name, dataSource);
	System.out.println("DataSource added: " + name);
	System.out.println(dataSource.toString());
	}

}
