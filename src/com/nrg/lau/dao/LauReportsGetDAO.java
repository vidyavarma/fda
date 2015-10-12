package com.nrg.lau.dao;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauUsers;

public class LauReportsGetDAO {
	
	private SimpleJdbcTemplate template;

    public void setDataSource(DataSource dataSource){
        this.template = new SimpleJdbcTemplate(dataSource);
    }
    
    public StringBuffer findReportsByUser(HttpServletRequest request)	{
    	
    	StringBuffer data 	= new StringBuffer();
    	LauUsers lauUsers	= (LauUsers)request.getSession().getAttribute("lauUsers");
    	String userName		= lauUsers.getUSER_ID();
    	String attribute	= lauUsers.getUSER_EMAIL();
    	
    	//if(request.getParameter("SEARCH_XML") != null)	{
    		//getReportSearch(String userName, String attribute)
    		//createBeansFromXml(request.getParameter("SEARCH_XML"));
    	//}else if(request.getParameter("ALL_OPEN_XML") != null){
    		//getOpenReports(String userName, String attribute)
    	//}else	{
    		//getReports(String userName, String attribute)
    	//}
    	//http://www.vaannila.com/spring/spring-jdbc-tutorial-1.html
    	return data;
    }
    
    public void getOpenReports(String userName, String attribute)	{
    	//template.qu
    	//Object[] parameters = new Object[] {new Integer(1)};
    	//List l = jt.queryForList("select id, name from employee where id > ?",parameters);
    	    
    }
}
