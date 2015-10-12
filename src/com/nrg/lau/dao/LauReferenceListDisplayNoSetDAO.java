package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauReferenceCollection;
import com.nrg.lau.beans.LauReferenceListDetails;
import com.nrg.lau.beans.LauReferenceLists;
import com.nrg.lau.commons.IReportChildSet1DAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauReferenceListDisplayNoSetDAO implements IReportChildSet1DAO<LauReferenceLists>{
	
	private static Logger log	= Logger.getLogger(LauReferenceListDisplayNoSetDAO.class);
	private Vector<LauReferenceListDetails> references	= null;
	private LauReferenceListDetails refList			 		= null;	

	protected void finalize() throws Throwable {		
		this.references.removeAllElements();
		this.references = null;		
		super.finalize();
	}
	
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp) throws Exception {
		//Check to make sure that XML is there in Request.
		String userId = user;
		java.sql.Timestamp dt = dstamp;
		if(request.getParameter(IReportsConstants.LAU_REFERENCE_LIST_DISPLAY_NO) != null && 
				request.getParameter(IReportsConstants.LAU_REFERENCE_LIST_DISPLAY_NO).length() > 0) {
			
			log.info("LauReferenceListDisplayNoSetDAO save() LAU_REFERENCE_LIST_DISPLAY_NO -> " 
					+ request.getParameter(IReportsConstants.LAU_REFERENCE_LIST_DISPLAY_NO));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_REFERENCE_LIST_DISPLAY_NO));
			Iterator<LauReferenceListDetails> itr = this.references.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauReferenceListDetails referenceLists = (LauReferenceListDetails)itr.next();
				log.info("itr.hasNext() -> " + referenceLists.toString());
				this.refList	= null;
				this.refList	= referenceLists;
		//		if(referenceLists.getTRANSACTION_TYPE().trim().equalsIgnoreCase("1"))	{
		//			insert(template,reportId);
		//		}else if (referenceLists.getTRANSACTION_TYPE().trim().equalsIgnoreCase("2")){
					update(template, userId,  dt);			
			//	}else if(referenceLists.getTRANSACTION_TYPE().trim().equalsIgnoreCase("0")) {
					
			//	}
			}//end of while(itr.hasNext())
			
			
		}	else	{
			log.info(IReportsConstants.LAU_REFERENCE_LIST_DISPLAY_NO + " not found in request");
		}	
		
	}
	
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";
		references = new Vector<LauReferenceListDetails>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauReferenceListDetails.class );
		
		digester.addBeanPropertySetter(mainXmlTag + "/CODE");
		digester.addBeanPropertySetter(mainXmlTag + "/CODE_VALUE");
		digester.addBeanPropertySetter(mainXmlTag + "/DISPLAY_NUMBER");
		digester.addBeanPropertySetter(mainXmlTag + "/LANGUAGE_CODE");
		digester.addBeanPropertySetter(mainXmlTag + "/REFERENCE_LIST_NAME");
		digester.addBeanPropertySetter(mainXmlTag + "/TRANSACTION_TYPE");
		digester.addBeanPropertySetter(mainXmlTag + "/VISIBLE_FLAG");
				
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));		

		
	}
	
	public void addXmlData(LauReferenceListDetails lauReferenceListDetails) {
		references.add(lauReferenceListDetails);
		log.info(lauReferenceListDetails.toString());
	}
	
	public void delete(SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_REFERENCE_LIST_DETAILS WHERE REFERENCE_LIST_NAME = ?",
				new Object[]{this.refList.getREFERENCE_LIST_NAME()});
		log.info("LauReferenceListDisplayNoSetDAO delete() ID -> " + id);		
		
	}

	public void insert(SimpleJdbcTemplate template, long reportId,String userId, java.sql.Timestamp dt)
			throws Exception {
		int id = 0;
		id = template.update(SQL_INSERT_STRING,getParameters(userId, dt));
		log.info("LauReferenceListDisplayNoSetDAO insert() ID -> " + id);		
	}

	
	public void update(SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters(userId,  dt));
		log.info("LauReferenceListDisplayNoSetDAO update() ID -> " + id);
		
	}
	
	private Object[] getParameters(String userId, java.sql.Timestamp dt)
	{
		LauReferenceListDetails lauReferenceLists = this.refList; 
		return new Object[]{
			lauReferenceLists.getDISPLAY_NUMBER(),
			userId,
			dt,
			lauReferenceLists.getREFERENCE_LIST_NAME(),
			lauReferenceLists.getCODE()
		};
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_REFERENCE_LIST_DETAILS SET DISPLAY_NUMBER=?," +
			"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REFERENCE_LIST_NAME=? and CODE = ? ";
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_REFERENCE_LIST_DETAILS (CODE,CODE_VALUE," +
			"LANGUAGE_CODE,DISPLAY_NUMBER,VISIBLE_FLAG,UPDATE_USER_ID,UPDATE_TIMESTAMP,REFERENCE_LIST_NAME) VALUES (?,?,?,?,?,?,?,?)";
}
