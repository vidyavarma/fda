package com.nrg.lau.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauReferenceListDetails;

public class LauReferenceListDetailsGetDAO {
	
	public List<String> getCodeValues(SimpleJdbcTemplate template, String name) throws Exception{
		
		String SQL	= "SELECT CODE_VALUE FROM LAU_REFERENCE_LIST_DETAILS WHERE REFERENCE_LIST_NAME = ?";
		return template.query(SQL, new RowMapper<String>() {
			public String mapRow(ResultSet resultSet, int i) throws SQLException {
				return resultSet.getString(1).trim().toUpperCase();
		    }
		}, new Object[]{name});
	}
	
	public Map<String, String> getCodeValuesAsMap(SimpleJdbcTemplate template, String name) throws Exception{
		
		String SQL	= "SELECT CODE,CODE_VALUE FROM LAU_REFERENCE_LIST_DETAILS WHERE REFERENCE_LIST_NAME = ?";
		List<LauReferenceListDetails> lists = template.query(SQL, new RowMapper<LauReferenceListDetails>() {
			public LauReferenceListDetails mapRow(ResultSet rs, int i) throws SQLException {
				LauReferenceListDetails results = new LauReferenceListDetails();
				results.setCODE(rs.getString("CODE"));
				results.setCODE_VALUE(rs.getString("CODE_VALUE"));
				return results;
		    }
		}, new Object[]{name});
		
		Iterator<LauReferenceListDetails> itr 	= lists.iterator();
		Map<String, String> codeList	= new HashMap<String, String>();
		while(itr.hasNext())	{
			LauReferenceListDetails refList = (LauReferenceListDetails)itr.next();
			codeList.put(refList.getCODE(), refList.getCODE_VALUE());
		}
		return codeList;
	}
}


