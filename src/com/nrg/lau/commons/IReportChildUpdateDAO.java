package com.nrg.lau.commons;

import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;


public interface IReportChildUpdateDAO<T> {
	public void save(HttpServletRequest request,SimpleJdbcTemplate template,long reportId,String user,java.sql.Timestamp dstamp)throws Exception;
	public void update(SimpleJdbcTemplate template)throws Exception;
	public void createBeansFromXml(String xml)throws Exception;
	public void delete(SimpleJdbcTemplate template) throws Exception;
}
