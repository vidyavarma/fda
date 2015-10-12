package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauReferenceCollection;
import com.nrg.lau.beans.LauReferenceLists;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauReferenceListsSetDAO implements IReportChildSetDAO<LauReferenceLists>{
	
	private static Logger log	= Logger.getLogger(LauReferenceListsSetDAO.class);
	private ArrayList<LauReferenceLists> references	= null;
	private LauReferenceLists refList			 	= null;	
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp) throws Exception {
		dt = dstamp;
		userId = user;
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_REFERENCE_LISTS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_REFERENCE_LISTS_PARAM_NAME).length() > 0) {
			
			log.info("LauReferenceListsSetDAO save() LAU_REFERENCE_LISTS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_REFERENCE_LISTS_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_REFERENCE_LISTS_PARAM_NAME));
			Iterator<LauReferenceLists> itr = this.references.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauReferenceLists referenceLists = (LauReferenceLists)itr.next();
				log.info("itr.hasNext() -> " + referenceLists.toString());
				this.refList	= null;
				this.refList	= referenceLists;
				if(referenceLists.getTRANSACTION_TYPE().trim().equalsIgnoreCase("1"))	{
					insert(template,reportId);
				}else if (referenceLists.getTRANSACTION_TYPE().trim().equalsIgnoreCase("2")){
					update(template);			
				}else if(referenceLists.getTRANSACTION_TYPE().trim().equalsIgnoreCase("0")) {
					
				}
			}//end of while(itr.hasNext())
			
			
		}	else	{
			log.info(IReportsConstants.LAU_REFERENCE_LISTS_PARAM_NAME + " not found in request");
		}	
		
	}
	
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.addObjectCreate( mainXmlTag, LauReferenceCollection.class);		
		digester.addObjectCreate(mainXmlTag + "/REFERENCE_LIST", LauReferenceLists.class);
		digester.addSetProperties(mainXmlTag + "/REFERENCE_LIST");
		digester.addSetNext(mainXmlTag + "/REFERENCE_LIST", "addRefLists");
		LauReferenceCollection collection  = (LauReferenceCollection)digester.parse(new StringReader(xml));
		references		= collection.getRefLists();
		
	}
	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_REFERENCE_LISTS WHERE REFERENCE_LIST_NAME = ?",
				new Object[]{this.refList.getREFERENCE_LIST_NAME()});
		log.info("LauReferenceListsSetDAO delete() ID -> " + id);		
		
	}

	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		int id = 0;
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauReferenceListsSetDAO insert() ID -> " + id);		
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauReferenceListsSetDAO update() ID -> " + id);
		
	}
	
	private Object[] getParameters()
	{
		LauReferenceLists lauReferenceLists = this.refList; 
		return new Object[]{
			lauReferenceLists.getREFERENCE_LIST_TYPE(),
			lauReferenceLists.getDESCRIPTION(),
			lauReferenceLists.getEXTERNAL_LIST_NAME(),
			lauReferenceLists.getLIST_SQL(),
			userId,
			dt,
			lauReferenceLists.getREFERENCE_LIST_NAME()
		};
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_REFERENCE_LISTS SET REFERENCE_LIST_TYPE=?,DESCRIPTION=?," +
			"EXTERNAL_LIST_NAME=?,LIST_SQL=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REFERENCE_LIST_NAME=?";
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_REFERENCE_LISTS (REFERENCE_LIST_TYPE,DESCRIPTION," +
			"EXTERNAL_LIST_NAME,LIST_SQL,UPDATE_USER_ID,UPDATE_TIMESTAMP,REFERENCE_LIST_NAME) VALUES (?,?,?,?,?,?,?)";
}
