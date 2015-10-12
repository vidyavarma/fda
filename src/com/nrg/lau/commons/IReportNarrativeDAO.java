package com.nrg.lau.commons;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;



public interface IReportNarrativeDAO<T> {
	public void save(HttpServletRequest request,SimpleJdbcTemplate template,long reportId,String user,java.sql.Timestamp dstamp, HashMap<String, String> patientMap, HashMap<String, List<Long>> productMap)throws Exception;
	public void update(SimpleJdbcTemplate template)throws Exception;
	public void insert(SimpleJdbcTemplate template, long reportId, String patientDetailsId)throws Exception;
	public void createBeansFromXml(String xml)throws Exception;
	public void delete(SimpleJdbcTemplate template) throws Exception;
}