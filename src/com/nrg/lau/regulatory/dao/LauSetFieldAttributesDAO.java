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
import com.nrg.lau.regulatory.beans.LauFieldAttrTemplate;

public class LauSetFieldAttributesDAO {	
	private Vector<LauFieldAttrTemplate> templates	= null;
	private LauFieldAttrTemplate fieldAttrTemplate	= null;
	private static Logger log	= Logger.getLogger(LauSetFieldAttributesDAO.class);	
				
	public void save(HttpServletRequest request,SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {
		log.info("Entered SAVE in LauSetFieldAttributesDAO!!!");
		String	userId = CommonDAO.getUSERID(request);
		log.info("USERID: "+userId);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		log.info("TimeStamp: "+dt.toString());
		log.info("To check if null: "+request.getParameter(IReportsConstants.LAU_FIELD_ATTR_PARAM_NAME));
		if(request.getParameter(IReportsConstants.LAU_FIELD_ATTR_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_FIELD_ATTR_PARAM_NAME).length() > 0) {

			log.info("LauSetFieldAttributesDAO save() LAU_FIELD_ATTR_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_FIELD_ATTR_PARAM_NAME));
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_FIELD_ATTR_PARAM_NAME));

			Iterator<LauFieldAttrTemplate> itr = this.templates.iterator();
			while(itr.hasNext())	{
				LauFieldAttrTemplate lauFieldAttrTemplate = (LauFieldAttrTemplate)itr.next();
				log.info("itr.hasNext() -> " + lauFieldAttrTemplate.toString());
				this.fieldAttrTemplate	= null;
				this.fieldAttrTemplate	= lauFieldAttrTemplate;				
				log.info("lauFieldAttrTemplate.FieldName"+lauFieldAttrTemplate.getFieldName());
				log.info("lauFieldAttrTemplate.getTransactionType()******"+lauFieldAttrTemplate.getTransactionType());
				if(lauFieldAttrTemplate.getTransactionType() == 2){
					log.info("updateUser - TRANSACTION_TYPE [2] -> " + lauFieldAttrTemplate.getFieldId());
					updateFieldAttr(datasource,request,template,userId,dt);
				}	
				if(lauFieldAttrTemplate.getTransactionType() == 1){
					log.info("insert - TRANSACTION_TYPE [1] -> " + lauFieldAttrTemplate.getFieldId());
					insertFieldAttr(datasource,request,template,userId,dt);
				}
				if(lauFieldAttrTemplate.getTransactionType() == IReportsConstants.deleteFlag){
					log.info("lauFieldAttrTemplate - save() -> [TRANSACTION_TYPE 0]: " + lauFieldAttrTemplate.toString());
					CommonDAO.setDbmsClientInfo(template,userId );
					template.update("DELETE FROM LAU_FIELD_ATTRIBUTES WHERE FIELD_ID = ?", 
							new Object[]{lauFieldAttrTemplate.getFieldId()});
				}
			}//end of while(itr.hasNext())

		}	else {
			log.info(IReportsConstants.LAU_DATA_TEMPLATE_PARAM_NAME + " not found in request");
		}

	}
	
	private void createBeansFromXml(String xml) throws Exception {
		templates			= new Vector<LauFieldAttrTemplate>();
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauFieldAttrTemplate.class );

		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/FIELD_ID", "fieldId" );
		digester.addBeanPropertySetter( mainXmlTag+"/FIELD_NAME", "fieldName" );
		digester.addBeanPropertySetter( mainXmlTag+"/FIELD_LABEL", "fieldLabel" );
		digester.addBeanPropertySetter( mainXmlTag+"/FIELD_TYPE", "fieldType" );
		digester.addBeanPropertySetter( mainXmlTag+"/QUERY_ENABLED", "queryEnabled" );
		digester.addBeanPropertySetter( mainXmlTag+"/TABLE_NAME", "tableName" );
		digester.addBeanPropertySetter( mainXmlTag+"/COLUMN_NAME", "columnName" );	
		digester.addBeanPropertySetter( mainXmlTag+"/MODULE_NAME", "moduleName" );
		digester.addBeanPropertySetter( mainXmlTag+"/REFERENCE_CODE_LIST", "referenceCodeList" );	
		digester.addBeanPropertySetter( mainXmlTag+"/SUB_QUERY", "subQuery" );
		digester.addBeanPropertySetter( mainXmlTag+"/NOTIFICATION_ENABLED", "notifyEnabled" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );

		//Move to next LauReportAttachments
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));
	}

	public void addXmlData(LauFieldAttrTemplate lauFieldAttrTemplate) {
		templates.add(lauFieldAttrTemplate);
		log.info(lauFieldAttrTemplate.toString());
	}
	
	
	public void updateFieldAttr(DataSource datasource, HttpServletRequest request,
			SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {
		int id = 0;
		
		log.info("ID: "+this.fieldAttrTemplate.getFieldId());
		log.info("DT----->"+dt);
		log.info("userid----->"+userId);
		log.info("FieldLabel--->"+ this.fieldAttrTemplate.getFieldLabel());
		log.info("FieldName--->"+this.fieldAttrTemplate.getFieldName());
		log.info("FieldType--->"+this.fieldAttrTemplate.getFieldType());
		
		id = template.update(SQL_UPDATE_STRING,getParameters(fieldAttrTemplate,userId,dt));
	
		log.info("field attr DAO update() ID -> " + id);

	}
	
	public void insertFieldAttr(DataSource datasource, HttpServletRequest request,
			SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {
		log.info("insert---------> ");
		int id = 0;		
		log.info("ID------ "+this.fieldAttrTemplate.getFieldId());
		long fldId = CommonDAO.getPrimaryKey(template);;
		this.fieldAttrTemplate.setFieldId(fldId);		
		log.info("Generated Primary Key for QUERY_ID ->" + fldId);
		
		id = template.update(SQL_INSERT_STRING,getParameters(fieldAttrTemplate,userId,dt));	
		log.info("field attr DAO insert() ID -> " + id);

	}
	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_FIELD_ATTRIBUTES SET FIELD_NAME=?," +
			"FIELD_LABEL=?,FIELD_TYPE=?,QUERY_ENABLED=?,COLUMN_NAME=?,TABLE_NAME=?, MODULE_NAME=?, REFERENCE_CODE_LIST=?, "
			+ "SUB_QUERY=?,NOTIFICATION_ENABLED=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE " +
			"FIELD_ID=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_FIELD_ATTRIBUTES (FIELD_NAME, FIELD_LABEL, "
			+ "FIELD_TYPE, QUERY_ENABLED, COLUMN_NAME, TABLE_NAME, MODULE_NAME,REFERENCE_CODE_LIST,"
			+ "SUB_QUERY, NOTIFICATION_ENABLED, UPDATE_USER_ID, UPDATE_TIMESTAMP, FIELD_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	private Object[] getParameters(LauFieldAttrTemplate lauFieldAttrTemplate, String userId, java.sql.Timestamp dt)
	{	
		log.info("Entered getParameters");			
		return new Object[]{
				lauFieldAttrTemplate.getFieldName(),
				lauFieldAttrTemplate.getFieldLabel(),
				lauFieldAttrTemplate.getFieldType(),
				lauFieldAttrTemplate.getQueryEnabled(),
				lauFieldAttrTemplate.getColumnName(),
				lauFieldAttrTemplate.getTableName(),
				lauFieldAttrTemplate.getModuleName(),
				lauFieldAttrTemplate.getReferenceCodeList(),
				lauFieldAttrTemplate.getSubQuery(),
				lauFieldAttrTemplate.getNotifyEnabled(),
				userId,
				dt,
				lauFieldAttrTemplate.getFieldId()
		};
		
	}	
}	
		
