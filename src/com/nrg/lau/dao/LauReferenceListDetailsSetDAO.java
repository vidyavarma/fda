package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauReferenceCollection;
import com.nrg.lau.beans.LauReferenceListDetails;
import com.nrg.lau.beans.LauReferenceLists;
import com.nrg.lau.commons.IReportChildSet1DAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauReferenceListDetailsSetDAO implements IReportChildSet1DAO<LauReferenceLists>{
	
	private static Logger log	= Logger.getLogger(LauReferenceListDetailsSetDAO.class);
	private ArrayList<LauReferenceListDetails> references	= null;
	private LauReferenceListDetails refList			 		= null;	

	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstmap) throws Exception {
		String userId = user;
		java.sql.Timestamp dt = dstmap;
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_REFERENCE_LIST_DETAILS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_REFERENCE_LIST_DETAILS_PARAM_NAME).length() > 0) {
			
			log.info("LauReferenceListDetailsSetDAO save() LAU_REFERENCE_LIST_DETAILS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_REFERENCE_LIST_DETAILS_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			log.info("before create bean -------->");
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_REFERENCE_LIST_DETAILS_PARAM_NAME));
			Iterator<LauReferenceListDetails> itr = this.references.iterator();
			log.info("before while -------->");
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{

				LauReferenceListDetails referenceLists = (LauReferenceListDetails)itr.next();
				
				log.info("itr.hasNext() -> " + referenceLists.toString());
				this.refList	= null;
				this.refList	= referenceLists;
				if(referenceLists.getTRANSACTION_TYPE().trim().equalsIgnoreCase("1"))	{
					insert(template,reportId,userId, dt);
				}else if (referenceLists.getTRANSACTION_TYPE().trim().equalsIgnoreCase("2")){
					update(template,userId, dt);			
				}else if(referenceLists.getTRANSACTION_TYPE().trim().equalsIgnoreCase("0")) {
					
				}
			}//end of while(itr.hasNext())
			log.info("out side while -------->");
			
		}	else	{
			log.info(IReportsConstants.LAU_REFERENCE_LIST_DETAILS_PARAM_NAME + " not found in request");
		}	
		
	}
	
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.addObjectCreate( mainXmlTag, LauReferenceCollection.class);		
		digester.addObjectCreate(mainXmlTag + "/REFERENCE_LIST_DETAIL", LauReferenceListDetails.class);
		digester.addSetProperties(mainXmlTag + "/REFERENCE_LIST_DETAIL");
		digester.addSetNext(mainXmlTag + "/REFERENCE_LIST_DETAIL", "addRefListsDetails");
		LauReferenceCollection collection  = (LauReferenceCollection)digester.parse(new StringReader(xml));
		references		= collection.getRefListsDetails();
		
	}
	
	public void delete(SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_REFERENCE_LIST_DETAILS WHERE REFERENCE_LIST_NAME = ?",
				new Object[]{this.refList.getREFERENCE_LIST_NAME()});
		log.info("LauReferenceListDetailsSetDAO delete() ID -> " + id);		
		
	}

	public void insert(SimpleJdbcTemplate template, long reportId,String userId, java.sql.Timestamp dt)
			throws Exception {
		int id = 0;
		id = template.update(SQL_INSERT_STRING,getParameters(userId, dt));
		log.info("LauReferenceListDetailsSetDAO insert() ID -> " + id);		
	}

	
	public void update(SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getUpdateParameters(userId,  dt));
		log.info("LauReferenceListDetailsSetDAO update() ID -> " + id);
		
	}
	
	private Object[] getParameters(String userId, java.sql.Timestamp dt)
	{
		LauReferenceListDetails lauReferenceLists = this.refList; 
		log.info(lauReferenceLists.getCODE_VALUE()+":"+
			lauReferenceLists.getLANGUAGE_CODE()+":"+
			lauReferenceLists.getDISPLAY_NUMBER()+":"+
			lauReferenceLists.getVISIBLE_FLAG()+":"+
			userId+":"+
			dt+":"+
			lauReferenceLists.getCODE()+":"+
			lauReferenceLists.getREFERENCE_LIST_NAME());
		return new Object[]{			
			lauReferenceLists.getCODE_VALUE(),
			//	PR-693 - 10 December 2013 - Mark	"en",
			lauReferenceLists.getLANGUAGE_CODE(),	//	PR-693 - 10 December 2013 - Mark	
			lauReferenceLists.getDISPLAY_NUMBER(),
			lauReferenceLists.getVISIBLE_FLAG(),
			userId,
			dt,
			lauReferenceLists.getCODE(),
			lauReferenceLists.getREFERENCE_LIST_NAME(),
			lauReferenceLists.getPARENT_CODE(),
			lauReferenceLists.getVALUE_DESCRIPTION(),
			lauReferenceLists.getRETIRED_FLAG(),
			lauReferenceLists.getIMPORT_VALUE_FLAG(),
			lauReferenceLists.getCONTEXT_CODE()
		};
	}
	private Object[] getUpdateParameters(String userId, java.sql.Timestamp dt)
	{
		LauReferenceListDetails lauReferenceLists = this.refList; 
		log.info(lauReferenceLists.getCODE_VALUE()+":"+
			lauReferenceLists.getLANGUAGE_CODE()+":"+lauReferenceLists.getDISPLAY_NUMBER()+":"+
			lauReferenceLists.getVISIBLE_FLAG()+":"+
			userId+":"+
			dt+":"+
			lauReferenceLists.getCODE()+":"+
			lauReferenceLists.getREFERENCE_LIST_NAME());
		return new Object[]{			
			lauReferenceLists.getCODE_VALUE(),
			//	PR-693 - 10 December 2013 - Mark	"en",
			lauReferenceLists.getLANGUAGE_CODE(),	//	PR-693 - 10 December 2013 - Mark
			lauReferenceLists.getDISPLAY_NUMBER(),
			lauReferenceLists.getVISIBLE_FLAG(),
			userId,
			dt,
			lauReferenceLists.getPARENT_CODE(),
			lauReferenceLists.getVALUE_DESCRIPTION(),
			lauReferenceLists.getRETIRED_FLAG(),
			lauReferenceLists.getIMPORT_VALUE_FLAG(),
			lauReferenceLists.getCONTEXT_CODE(),
			lauReferenceLists.getCODE(),
			lauReferenceLists.getREFERENCE_LIST_NAME()
		};
	}	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_REFERENCE_LIST_DETAILS SET CODE_VALUE=?," +
			"LANGUAGE_CODE=?,DISPLAY_NUMBER =?, VISIBLE_FLAG=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?,PARENT_CODE=?,VALUE_DESCRIPTION=?,RETIRED_FLAG=?,IMPORT_VALUE_FLAG=?,CONCEPT_CODE=? WHERE CODE=? and REFERENCE_LIST_NAME=? ";
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_REFERENCE_LIST_DETAILS (CODE_VALUE," +
			"LANGUAGE_CODE,DISPLAY_NUMBER,VISIBLE_FLAG,UPDATE_USER_ID,UPDATE_TIMESTAMP,CODE,REFERENCE_LIST_NAME,PARENT_CODE,VALUE_DESCRIPTION,RETIRED_FLAG,IMPORT_VALUE_FLAG,CONCEPT_CODE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 	private final String SQL_INSERT_STRING = "INSERT INTO LAU_REFERENCE_LIST_DETAILS (CODE_VALUE," +
			"LANGUAGE_CODE,DISPLAY_NUMBER,VISIBLE_FLAG,UPDATE_USER_ID,UPDATE_TIMESTAMP,CODE,REFERENCE_LIST_NAME) VALUES (?,?," +
			"(SELECT MAX(DISPLAY_NUMBER)+ 1 FROM LAU_REFERENCE_LIST_DETAILS WHERE REFERENCE_LIST_NAME = ?)," +
			"?,?,?,?,?)";
	 * **/
	 
}
