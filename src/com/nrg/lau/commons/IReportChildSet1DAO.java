package com.nrg.lau.commons;

import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;


public interface IReportChildSet1DAO<T> {
	public void save(HttpServletRequest request,SimpleJdbcTemplate template,long reportId,String user,java.sql.Timestamp dstamp)throws Exception;
	public void update(SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt)throws Exception;
	public void insert(SimpleJdbcTemplate template, long reportId,String userId, java.sql.Timestamp dt)throws Exception;
	public void createBeansFromXml(String xml)throws Exception;
	public void delete(SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) throws Exception;
}
