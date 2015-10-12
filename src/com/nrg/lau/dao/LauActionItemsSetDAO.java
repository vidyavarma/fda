package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauActionItems;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauActionItemsSetDAO implements IReportChildSetDAO<LauActionItems>{

	private static Logger log	= Logger.getLogger(LauActionItemsSetDAO.class);
	private Vector<LauActionItems> reports	= null;
	private LauActionItems actionItems 		= null;	
	private java.sql.Timestamp dt  = null;  
	private String userId	= "";
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	
	
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp) throws Exception {
		//Check to make sure that XML is there in Request.
		userId = user;
		dt = dstamp;
		if(request.getParameter(IReportsConstants.LAU_ACTION_ITEMS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_ACTION_ITEMS_PARAM_NAME).length() > 0) {
			
			log.info("LauActionItemsDAO save() LAU_ACTION_ITEMS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_ACTION_ITEMS_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_ACTION_ITEMS_PARAM_NAME));
			Iterator<LauActionItems> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauActionItems lauActionItems = (LauActionItems)itr.next();
				log.info("itr.hasNext() -> " + lauActionItems.toString());
				this.actionItems	= null;
				this.actionItems	= lauActionItems;
				if(lauActionItems.getActionItemId() == 0)	{
						insert(template,reportId);
				}else  {
					if(lauActionItems.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_ACTION_ITEMS_PARAM_NAME + " not found in request");
		}
		
	}
	
	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {		
		int id = 0;
		long actionItemId = CommonDAO.getPrimaryKey(template);
		this.actionItems.setActionItemId(actionItemId);
		if(this.actionItems.getReportId() == 0)	{
			this.actionItems.setReportId(reportId);	}
		log.info("Generated Primary Key for ACTION_ITEM_ID ->" + actionItemId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauActionItemsDAO insert() ID -> " + id);
		
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauActionItemsDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauActionItems lauActionItems = this.actionItems; 
		return new Object[]{
			lauActionItems.getDisplayNumber(),
			lauActionItems.getActionItemType(),
			lauActionItems.getActionItemDescription(),
			lauActionItems.getPriority(),
			lauActionItems.getAssignedToUser(),
			lauActionItems.getAssignedToGroup(),
			lauActionItems.getDueDate(),
			lauActionItems.getActionItemStatus(),
			userId,
			dt,
			lauActionItems.getReportId(),
			lauActionItems.getActionItemId()
		};
	}
	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		reports				= new Vector<LauActionItems>();
		Digester digester	= new Digester();
		digester.push(this);
		String mainXmlTag = "ROWSET/ROW";
		digester.addObjectCreate( mainXmlTag, LauActionItems.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/ACTION_ITEM_ID", "actionItemId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "displayNumber2" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACTION_ITEM_TYPE", "actionItemType" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACTION_ITEM_DESCRIPTION", "actionItemDescription" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRIORITY", "priority" );
		digester.addBeanPropertySetter( mainXmlTag+"/ASSIGNED_TO_USER", "assignedToUser" );		
		digester.addBeanPropertySetter( mainXmlTag+"/ASSIGNED_TO_GROUP", "assignedToGroup" );
		digester.addBeanPropertySetter( mainXmlTag+"/DUE_DATE", "dueDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACTION_ITEM_STATUS", "actionItemStatus" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
				
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauActionItems lauActionItems) {
		reports.add(lauActionItems);
		log.info(lauActionItems.toString());
	}	
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_ACTION_ITEMS SET DISPLAY_NUMBER=?,ACTION_ITEM_TYPE=?," +
			"ACTION_ITEM_DESCRIPTION=?,PRIORITY=?,ASSIGNED_TO_USER=?,ASSIGNED_TO_GROUP=?,DUE_DATE=?," +
			"ACTION_ITEM_STATUS=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=? AND ACTION_ITEM_ID=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_ACTION_ITEMS(DISPLAY_NUMBER,ACTION_ITEM_TYPE," +
		"ACTION_ITEM_DESCRIPTION,PRIORITY,ASSIGNED_TO_USER,ASSIGNED_TO_GROUP,DUE_DATE,ACTION_ITEM_STATUS," +
		"UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID,ACTION_ITEM_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_ACTION_ITEMS WHERE ACTION_ITEM_ID = ?",
				new Object[]{this.actionItems.getActionItemId()});
		log.info("LauActionItemsSetDAO delete() ID -> " + id);		
	}
	
	/**
	 	LAU_ACTION_ITEMS
	 	
	 	ACTION_ITEM_ID NUMBER(22 , 0) NOT NULL,
		REPORT_ID NUMBER(22 , 0),
		DISPLAY_NUMBER NUMBER(22 , 0),
		ACTION_ITEM_TYPE VARCHAR2(50),
		ACTION_ITEM_DESCRIPTION VARCHAR2(4000),
		PRIORITY VARCHAR2(50),
		ASSIGNED_TO_USER VARCHAR2(300),
		ASSIGNED_TO_GROUP VARCHAR2(300),
		DUE_DATE VARCHAR2(24),
		ACTION_ITEM_STATUS VARCHAR2(50),
		UPDATE_USER_ID VARCHAR2(300),
		UPDATE_DATE date
	 
	 */

}
