package com.nrg.lau.commons;

import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import javax.sql.DataSource;

public interface IReportParentSetDAO<T> {
	public String[] save(HttpServletRequest request,SimpleJdbcTemplate template, DataSource ds,String user,java.sql.Timestamp dstamp) throws Exception;
	public long update(SimpleJdbcTemplate template)throws Exception;
	public long insert(SimpleJdbcTemplate template, DataSource ds)throws Exception;
	public void createBeansFromXml(String xml)throws Exception;
}
