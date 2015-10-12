package com.nrg.lau.regulatory.dao;


import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.beans.LauQueryJoinTableTemplate;

public class LauSetQueryJoinTableDAO {	
	private Vector<LauQueryJoinTableTemplate> templates	= null;
	private LauQueryJoinTableTemplate QueryJoinTableTemplate	= null;
	private static Logger log	= Logger.getLogger(LauSetQueryJoinTableDAO.class);	
				
	public void save(HttpServletRequest request,SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {
		log.info("Entered SAVE in LauSetQueryJoinTableDAO!!!");
		String	userId = CommonDAO.getUSERID(request);
		log.info("USERID: "+userId);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		log.info("TimeStamp: "+dt.toString());
		log.info("To check if null: "+request.getParameter(IReportsConstants.LAU_QUERY_JOIN_TABLE_PARAM_NAME));
		if(request.getParameter(IReportsConstants.LAU_QUERY_JOIN_TABLE_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_QUERY_JOIN_TABLE_PARAM_NAME).length() > 0) {

			log.info("LauSetQueryJoinTableDAO save() LAU_QUERY_JOIN_TABLE_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_QUERY_JOIN_TABLE_PARAM_NAME));
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_QUERY_JOIN_TABLE_PARAM_NAME));

			Iterator<LauQueryJoinTableTemplate> itr = this.templates.iterator();
			while(itr.hasNext())	{
				LauQueryJoinTableTemplate lauQueryJoinTableTemplate = (LauQueryJoinTableTemplate)itr.next();
				log.info("itr.hasNext() -> " + lauQueryJoinTableTemplate.toString());
				this.QueryJoinTableTemplate	= null;
				this.QueryJoinTableTemplate	= lauQueryJoinTableTemplate;				
				log.info("lauQueryJoinTableTemplate.TableName"+lauQueryJoinTableTemplate.getTableName());
				log.info("lauQueryJoinTableTemplate.getTransactionType()******"+lauQueryJoinTableTemplate.getTransactionType());
				if(lauQueryJoinTableTemplate.getTransactionType() == 2){
					log.info("updateUser - TRANSACTION_TYPE [2] -> " + lauQueryJoinTableTemplate.getJoinCriteriaID());
					updateQueryJoinTable(datasource,request,template,userId,dt);
				}	
				if(lauQueryJoinTableTemplate.getTransactionType() == 1){
					log.info("insert - TRANSACTION_TYPE [1] -> " + lauQueryJoinTableTemplate.getJoinCriteriaID());
					insertQueryJoinTable(datasource,request,template,userId,dt);
				}
				if(lauQueryJoinTableTemplate.getTransactionType() == IReportsConstants.deleteFlag){
					log.info("lauQueryJoinTableTemplate - save() -> [TRANSACTION_TYPE 0]: " + lauQueryJoinTableTemplate.toString());
					CommonDAO.setDbmsClientInfo(template,userId );
					template.update("DELETE FROM LAU_QUERY_TABLE_JOINS WHERE JOIN_CRITERIA_ID = ?", 
							new Object[]{lauQueryJoinTableTemplate.getJoinCriteriaID()});
				}
			}//end of while(itr.hasNext())

		}	else {
			log.info(IReportsConstants.LAU_DATA_TEMPLATE_PARAM_NAME + " not found in request");
		}

	}
	
	private void createBeansFromXml(String xml) throws Exception {
		templates			= new Vector<LauQueryJoinTableTemplate>();
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauQueryJoinTableTemplate.class );

		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/JOIN_CRITERIA_ID", "joinCriteriaID" );
		digester.addBeanPropertySetter( mainXmlTag+"/TABLE_NAME", "tableName" );
		digester.addBeanPropertySetter( mainXmlTag+"/PK1", "PK1" );
		digester.addBeanPropertySetter( mainXmlTag+"/PK2", "PK2" );
		digester.addBeanPropertySetter( mainXmlTag+"/PK3", "PK3" );
		digester.addBeanPropertySetter( mainXmlTag+"/PK4", "PK4" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARENT_TABLE", "parentTable" );
		digester.addBeanPropertySetter( mainXmlTag+"/FK1", "FK1" );
		digester.addBeanPropertySetter( mainXmlTag+"/FK2", "FK2" );
		digester.addBeanPropertySetter( mainXmlTag+"/FK3", "FK3" );
		digester.addBeanPropertySetter( mainXmlTag+"/FK4", "FK4" );
		digester.addBeanPropertySetter( mainXmlTag+"/JOIN_SQL", "joinSQL" );
		digester.addBeanPropertySetter( mainXmlTag+"/HINT_TEXT", "hintText" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );

		//Move to next LauReportAttachments
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));
	}

	public void addXmlData(LauQueryJoinTableTemplate lauQueryJoinTableTemplate) {
		templates.add(lauQueryJoinTableTemplate);
		log.info(lauQueryJoinTableTemplate.toString());
	}
	
	
	public void updateQueryJoinTable(DataSource datasource, HttpServletRequest request,
			SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {
		int id = 0;
		
		log.info("ID: "+this.QueryJoinTableTemplate.getJoinCriteriaID());
		log.info("DT----->"+dt);
		log.info("userid----->"+userId);
		log.info("TableName--->"+ this.QueryJoinTableTemplate.getTableName());
		log.info("ParentTableName--->"+this.QueryJoinTableTemplate.getParentTable());
		
		
		id = template.update(SQL_UPDATE_STRING,getParameters(QueryJoinTableTemplate,userId,dt));
	
		log.info("Query Join Table DAO update() ID -> " + id);

	}
	
	public void insertQueryJoinTable(DataSource datasource, HttpServletRequest request,
			SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {
		log.info("insert---------> ");
		int id = 0;		
		log.info("ID------ "+this.QueryJoinTableTemplate.getJoinCriteriaID());
		long fldId = CommonDAO.getPrimaryKey(template);;
		this.QueryJoinTableTemplate.setJoinCriteriaID(fldId);		
		log.info("Generated Primary Key for QUERY_ID ->" + fldId);
		
		id = template.update(SQL_INSERT_STRING,getParameters(QueryJoinTableTemplate,userId,dt));	
		log.info("Query Join Table DAO insert() ID -> " + id);

	}
	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_QUERY_TABLE_JOINS SET TABLE_NAME=?," +
			"PK1=?,PK2=?,PK3=?,PK4=?,PARENT_TABLE=?, FK1=?, FK2=?, FK3=?, FK4=?, "
			+ "JOIN_SQL=?,HINT_TEXT=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE " +
			"JOIN_CRITERIA_ID=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_QUERY_TABLE_JOINS (TABLE_NAME, PK1, "
			+ "PK2, PK3, PK4, PARENT_TABLE, FK1,FK2,FK3,FK4, "
			+ "JOIN_SQL, HINT_TEXT, UPDATE_USER_ID, UPDATE_TIMESTAMP, JOIN_CRITERIA_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	private Object[] getParameters(LauQueryJoinTableTemplate lauQueryJoinTableTemplate, String userId, java.sql.Timestamp dt)
	{	
		log.info("Entered getParameters");			
		return new Object[]{
				lauQueryJoinTableTemplate.getTableName(),
				lauQueryJoinTableTemplate.getPK1(),
				lauQueryJoinTableTemplate.getPK2(),
				lauQueryJoinTableTemplate.getPK3(),
				lauQueryJoinTableTemplate.getPK4(),
				lauQueryJoinTableTemplate.getParentTable(),
				lauQueryJoinTableTemplate.getFK1(),
				lauQueryJoinTableTemplate.getFK2(),
				lauQueryJoinTableTemplate.getFK3(),
				lauQueryJoinTableTemplate.getFK4(),
				lauQueryJoinTableTemplate.getJoinSQL(),
				lauQueryJoinTableTemplate.getHintText(),
				userId,
				dt,
				lauQueryJoinTableTemplate.getJoinCriteriaID()
		};
		
	}	
}	
		
