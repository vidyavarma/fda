package com.nrg.lau.regulatory.dao;

import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.commons.IReportParentSetDAO;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.CommonDAO;

public class LauGeneratedLettersDAO{
	
	private static Logger log	= Logger.getLogger(LauGeneratedLettersDAO.class);
	private Vector<LauGeneratedLetters> reports		= null;
	private LauGeneratedLetters generatedLetters = null;
	private HashMap<Long, List<String>> LetterGeneratedMap 	= null;
	private String userId;
	private Timestamp dt;
	
	public HashMap<Long, List<String>> save(HttpServletRequest request,
			SimpleJdbcTemplate template, DataSource ds, String user,
			Timestamp dstamp) throws Exception {
		LetterGeneratedMap = new HashMap<Long, List<String>>();

		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_GENERATED_LETTER_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_GENERATED_LETTER_PARAM_NAME).length() > 0) {
			
			log.info("LauGeneratedLettersDAO save() LAU_GENERATED_LETTER_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_GENERATED_LETTER_PARAM_NAME));
			
			this.userId = user;
			this.dt = dstamp;
			
			//Create LauGeneratedLetters beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_GENERATED_LETTER_PARAM_NAME));
			Iterator<LauGeneratedLetters> itr = this.reports.iterator();
			
			//generatedLetterID is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauGeneratedLetters lauGeneratedLetters = (LauGeneratedLetters)itr.next();
				log.info("itr.hasNext() -> " + lauGeneratedLetters.toString());
				this.generatedLetters	= null;
				this.generatedLetters	= lauGeneratedLetters;
				//generatedLetterID = insert(template,ds);
				LetterGeneratedMap = insert(template,ds);
			}//end of while(itr.hasNext())
			
		}	else	{
			log.info(IReportsConstants.LAU_GENERATED_LETTER_PARAM_NAME + " not found in request");
		}
		
	
		return LetterGeneratedMap;
		
	}

	public long update(SimpleJdbcTemplate template) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public HashMap<Long, List<String>> insert(SimpleJdbcTemplate template, DataSource ds)
			throws Exception {
		int id = 0;
		String query = "SELECT GENERATE_EMAIL,AUTO_UPLOAD FROM LAU_LAYOUT_TEMPLATES WHERE LAYOUT_TEMPLATE_ID = ?";
		Map<String, String> rtnValues = template.queryForObject(query, new RowMapper<Map<String, String>>() {

			@Override
			public Map<String, String> mapRow(ResultSet rs, int i) throws SQLException {
				
				Map<String, String> result = new HashMap<String, String>();
				result.put("GENERATE_EMAIL", rs.getString("GENERATE_EMAIL"));
				result.put("AUTO_UPLOAD", rs.getString("AUTO_UPLOAD"));			
				return result;
			}
		} , this.generatedLetters.getLAYOUT_TEMPLATE_ID());
		
		log.info("GENERATE_EMAIL -> " + rtnValues.get("GENERATE_EMAIL"));
		log.info("AUTO_UPLOAD -> " + rtnValues.get("AUTO_UPLOAD"));	
		
		long letterGeneratedId = CommonDAO.getPrimaryKey(template);
		this.generatedLetters.setLAU_GENERATED_LETTER_ID(letterGeneratedId);
		log.info("Generated Primary Key for LAU_GENERATED_LETTER_ID ->" + letterGeneratedId);
		id = template.update(SQL_INSERT_STRING,getParameters(rtnValues.get("GENERATE_EMAIL"),
				rtnValues.get("AUTO_UPLOAD")));
		log.info("LauGeneratedLettersDAO insert() ID -> " + id);
		//return letterGenereatedId;
		String generationID = "";
		String dueDay = "";
		generationID = Long.toString(generatedLetters.getLAU_GENERATED_LETTER_ID());
		dueDay = Long.toString(generatedLetters.getDUE_IN_DAYS());
		
		List<String> list = new ArrayList<String>();
		list.add(generationID);
		list.add(dueDay);
		list.add(generatedLetters.getGENERATE_ACTIVITY());
		list.add(generatedLetters.getOUTPUT_FORMAT());
		
		LetterGeneratedMap.put(generatedLetters.getLAU_GENERATED_LETTER_ID(), list);
		
		return LetterGeneratedMap;
	}
	
	private Object[] getParameters(String generatedEmail, 
			String autoUpload) {
		
		LauGeneratedLetters lauGeneratedLetters = this.generatedLetters;
				
		return new Object[]{
			
			lauGeneratedLetters.getLAYOUT_TEMPLATE_ID(),
			lauGeneratedLetters.getDUE_IN_DAYS(),
			lauGeneratedLetters.getBATCH_PRINTED(),
			lauGeneratedLetters.getGENERATION_COMMENT(),
			lauGeneratedLetters.getLETTER_ADDRESSEE_TYPE(),
			lauGeneratedLetters.getADDRESSEE_CONTACT_SOURCE(),
			lauGeneratedLetters.getADDRESSEE_CONTACT_EDITED(),
			lauGeneratedLetters.getADDRESSEE_TITLE(),
			lauGeneratedLetters.getADDRESSEE_FIRST_NAME(),
			lauGeneratedLetters.getADDRESSEE_MIDDLE_NAME(),
			lauGeneratedLetters.getADDRESSEE_LAST_NAME(),
			lauGeneratedLetters.getADDRESSEE_PHONE_CC(),
			lauGeneratedLetters.getADDRESSEE_PHONE_NO(),
			lauGeneratedLetters.getADDRESSEE_PHONE_EXT(),
			lauGeneratedLetters.getADDRESSEE_EMAIL(),
			lauGeneratedLetters.getADDRESSEE_COMPANY_NAME(),
			lauGeneratedLetters.getLETTER_ADDRESS1(),
			lauGeneratedLetters.getLETTER_ADDRESS2(),
			lauGeneratedLetters.getLETTER_ADDRESS3(),
			lauGeneratedLetters.getLETTER_ADDRESS4(),
			lauGeneratedLetters.getLETTER_CITY(),
			lauGeneratedLetters.getLETTER_STATE(),
			lauGeneratedLetters.getLETTER_POSTAL_CODE(),
			lauGeneratedLetters.getLETTER_COUNTRY(),
			generatedEmail,
			autoUpload,
			userId,dt,
			lauGeneratedLetters.getLAU_GENERATED_LETTER_ID()
		};
	}

	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag 	= "ROWSET/ROW";
		reports				= new Vector<LauGeneratedLetters>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauGeneratedLetters.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/LAU_GENERATED_LETTER_ID", "LAU_GENERATED_LETTER_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/LAYOUT_TEMPLATE_ID", "LAYOUT_TEMPLATE_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/DUE_IN_DAYS", "DUE_IN_DAYS" );
		digester.addBeanPropertySetter( mainXmlTag+"/BATCH_PRINTED", "BATCH_PRINTED" );
		digester.addBeanPropertySetter( mainXmlTag+"/GENERATION_COMMENT", "GENERATION_COMMENT" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_ADDRESSEE_TYPE", "LETTER_ADDRESSEE_TYPE" );		
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_CONTACT_SOURCE", "ADDRESSEE_CONTACT_SOURCE" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_CONTACT_EDITED", "ADDRESSEE_CONTACT_EDITED" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_TITLE", "ADDRESSEE_TITLE" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_FIRST_NAME", "ADDRESSEE_FIRST_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_MIDDLE_NAME", "ADDRESSEE_MIDDLE_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_LAST_NAME", "ADDRESSEE_LAST_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_PHONE_CC", "ADDRESSEE_PHONE_CC" );		
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_PHONE_NO", "ADDRESSEE_PHONE_NO" );		
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_PHONE_EXT", "ADDRESSEE_PHONE_EXT" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_EMAIL", "ADDRESSEE_EMAIL" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_COMPANY_NAME", "ADDRESSEE_COMPANY_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_ADDRESS1", "LETTER_ADDRESS1" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_ADDRESS2", "LETTER_ADDRESS2" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_ADDRESS3", "LETTER_ADDRESS3" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_ADDRESS4", "LETTER_ADDRESS4" );		
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_CITY", "LETTER_CITY" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_STATE", "LETTER_STATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_POSTAL_CODE", "LETTER_POSTAL_CODE" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_COUNTRY", "LETTER_COUNTRY" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "UPDATE_USER_ID" );	
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "UPDATE_TIMESTAMP" );
		digester.addBeanPropertySetter( mainXmlTag+"/GENERATE_ACTIVITY", "GENERATE_ACTIVITY" );		
		digester.addBeanPropertySetter( mainXmlTag+"/OUTPUT_FORMAT", "OUTPUT_FORMAT" );	
		
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));	
		
	}
	
	public void addXmlData(LauGeneratedLetters lauGeneratedLetters) {
		reports.add(lauGeneratedLetters);
	}
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_GENERATED_LETTERS(LAYOUT_TEMPLATE_ID,DUE_IN_DAYS,BATCH_PRINTED,GENERATION_COMMENT," +
			"LETTER_ADDRESSEE_TYPE,ADDRESSEE_CONTACT_SOURCE,ADDRESSEE_CONTACT_EDITED,ADDRESSEE_TITLE,ADDRESSEE_FIRST_NAME,ADDRESSEE_MIDDLE_NAME,ADDRESSEE_LAST_NAME,ADDRESSEE_PHONE_CC," +
			"ADDRESSEE_PHONE_NO,ADDRESSEE_PHONE_EXT,ADDRESSEE_EMAIL,ADDRESSEE_COMPANY_NAME,LETTER_ADDRESS1,LETTER_ADDRESS2,LETTER_ADDRESS3,LETTER_ADDRESS4,LETTER_CITY," +
			"LETTER_STATE,LETTER_POSTAL_CODE,LETTER_COUNTRY,GENERATE_EMAIL,AUTO_UPLOAD,UPDATE_USER_ID,UPDATE_TIMESTAMP,LAU_GENERATED_LETTER_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/*private final String SQL_UPDATE_STRING = "UPDATE LAU_GENERATED_LETTERS SET LAYOUT_TEMPLATE_ID=?,DUE_IN_DAYS=?,BATCH_PRINTED=?,GENERATION_COMMENT=?," +
			"LETTER_ADDRESSEE_TYPE=?,ADDRESSEE_CONTACT_SOURCE=?,ADDRESSEE_CONTACT_EDITED=?,ADDRESSEE_TITLE=?,ADDRESSEE_FIRST_NAME=?,ADDRESSEE_LAST_NAME=?,ADDRESSEE_PHONE_CC=?," +
			"ADDRESSEE_PHONE_NO=?,ADDRESSEE_PHONE_EXT=?,ADDRESSEE_EMAIL=?,ADDRESSEE_COMPANY_NAME=?,LETTER_ADDRESS1=?,LETTER_ADDRESS2=?,LETTER_ADDRESS3=?,LETTER_ADDRESS4=?," +
			"LETTER_CITY=?,LETTER_STATE=?,LETTER_POSTAL_CODE=?,LETTER_COUNTRY=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE LAU_GENERATED_LETTER_ID= ?";*/
	

}