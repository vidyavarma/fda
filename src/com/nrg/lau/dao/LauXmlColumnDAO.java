package com.nrg.lau.dao;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauXmlColumn;
import com.nrg.lau.beans.LauXmlNodes;
import com.nrg.lau.commons.IReportsConstants;

public class LauXmlColumnDAO {

	private Vector<LauXmlColumn> templates	= null;
	private LauXmlColumn xmlColumn	= null;
	private static Logger log	= Logger.getLogger(LauXmlColumnDAO.class);
   								
	private final String SQL_UPDATE_STRING = "UPDATE LAU_XML_COLUMNS SET UPDATE_USER_ID=?," +
			"UPDATE_TIMESTAMP=?,COLUMN_FCT_NAME=?,COL_POSITION=?,MANDATORY_XML_TAG=?   WHERE " +
			"DOC_NAME=? AND NODE_NAME=? AND XML_TAG_NAME=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_XML_COLUMNS(DOC_NAME,NODE_NAME,UPDATE_USER_ID," +
			"UPDATE_TIMESTAMP,XML_TAG_NAME,COLUMN_FCT_NAME,COL_POSITION,MANDATORY_XML_TAG) VALUES(?,?,?,?,?,?,?,?)";
	
	public void save(HttpServletRequest request,SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {
		log.info("Entered SAVE!!!");
		String	userId = CommonDAO.getUSERID(request);
		log.info("USERID: "+userId);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		log.info("TimeStamp: "+dt.toString());
		log.info("To check if null: "+request.getParameter(IReportsConstants.LAU_XML_COLUMNS_PARAM_NAME));
		if(request.getParameter(IReportsConstants.LAU_XML_COLUMNS_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_XML_COLUMNS_PARAM_NAME).length() > 0) {

			log.info("LauXmlColumnsDAO save() LAU_XML_COLUMNS_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_XML_COLUMNS_PARAM_NAME));
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_XML_COLUMNS_PARAM_NAME));

			Iterator<LauXmlColumn> itr = this.templates.iterator();
			while(itr.hasNext())	{
				LauXmlColumn lauXmlColumns = (LauXmlColumn)itr.next();
				log.info("itr.hasNext() -> " + lauXmlColumns.toString());
				this.xmlColumn	= null;
				this.xmlColumn	= lauXmlColumns;
				log.info("lauXmlColumns.DocName"+lauXmlColumns.getDocName());
				log.info("lauXmlColumns.getTransactionType()******"+lauXmlColumns.getTransactionType());
				if(lauXmlColumns.getTransactionType() == IReportsConstants.deleteFlag){
					log.info("lauXmlColumnsDAO - save() -> [TRANSACTION_TYPE 0]: " + lauXmlColumns.toString());
					int id = 0;
					id = template.update("DELETE FROM LAU_XML_COLUMNS WHERE DOC_NAME = ? AND NODE_NAME = ? AND XML_TAG_NAME = ?", 
							new Object[]{lauXmlColumns.getDocName(),lauXmlColumns.getNodeName(),lauXmlColumns.getXmlTagName()});
					log.info("delete Column id ->"+id);
				}
				else if(lauXmlColumns.getTransactionType() == 1){
					insertColumn(SQL_INSERT_STRING,template,userId,dt);
				}
				else{
					log.info("updateUser - TRANSACTION_TYPE [2] -> " + lauXmlColumns.getDocName());
					updateColumn(template,userId,dt);
				}	
				
			}//end of while(itr.hasNext())

		}	else {
			log.info(IReportsConstants.LAU_XML_COLUMNS_PARAM_NAME + " not found in request");
		}

	}
	
	private void insertColumn(String sql,SimpleJdbcTemplate template,
			String userId, Timestamp dt) throws Exception {
		int id = 0;
		id = template.update(SQL_INSERT_STRING,getInsertParameters(template,userId,dt));
		log.info("LauE2BColumnActionDAO insert() ID -> " + id);
		
	}

	private Object[] getInsertParameters(SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) {
		
		LauXmlColumn lauCol = this.xmlColumn;
		return new Object[]{
				lauCol.getDocName(),
				lauCol.getNodeName(),
				userId,
				dt,
				lauCol.getXmlTagName(),
				lauCol.getColFctName(),
				lauCol.getColPosition(),
				lauCol.getMandatoryXmlTag()
								
		};
}
	private Object[] getParameters(LauXmlColumn lauNodes, String userId, java.sql.Timestamp dt)
	{	log.info("Entered getParameters");
		//LauXmlDoc lauTemplate = this.xmlColumn; 
		log.info("Doc Name---->"+lauNodes.getDocName());
		log.info("Node Name---->"+lauNodes.getNodeName());
		log.info("Col Position---->"+lauNodes.getColPosition());
		log.info("Id"+userId);
		log.info("DT"+dt);
		return new Object[]{
				userId,
				dt,
				lauNodes.getColFctName(),
				lauNodes.getColPosition(),
				lauNodes.getMandatoryXmlTag(),
				lauNodes.getDocName(),
				lauNodes.getNodeName(),
				lauNodes.getXmlTagName()
		};
		
	}	
	
	
	private void createBeansFromXml(String xml) throws Exception {

		templates			= new Vector<LauXmlColumn>();
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauXmlColumn.class );

		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/DOC_NAME", "docName" );
		digester.addBeanPropertySetter( mainXmlTag+"/NODE_NAME", "nodeName" );
		digester.addBeanPropertySetter( mainXmlTag+"/XML_TAG_NAME", "xmlTagName" );
		digester.addBeanPropertySetter( mainXmlTag+"/COLUMN_FCT_NAME", "colFctName" );
		digester.addBeanPropertySetter( mainXmlTag+"/COL_POSITION", "colPosition" );
		digester.addBeanPropertySetter( mainXmlTag+"/MANDATORY_XML_TAG", "mandatoryXmlTag" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );

		//Move to next LauReportAttachments
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauXmlColumn lauXmlCol) {
		templates.add(lauXmlCol);
		log.info(lauXmlCol.toString());
	}
	
	
	public void updateColumn(SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {
		int id = 0;
		
		log.info("ID: "+this.xmlColumn.getDocName());
		log.info("DT----->"+dt);
		log.info("userid----->"+userId);
		log.info("this.xmlColumn.getTemplateName()--->"+ this.xmlColumn.getNodeName());
		log.info("this.xmlColumn.getSqlText()--->"+this.xmlColumn.getColFctName());
			
		id = template.update(SQL_UPDATE_STRING,getParameters(xmlColumn,userId,dt));
	
		log.info("LauXmlColumnDAO update() ID -> " + id);

	}
}
/**
<?xml version="1.0"?>
<ROWSET>
 <ROW>
  <DOC_NAME>DTD21</DOC_NAME>
  <NODE_NAME>ICHICSR03</NODE_NAME>
  <XML_TAG_NAME>&quot;lang&quot;</XML_TAG_NAME>
  <COLUMN_FCT_NAME>&apos;en&apos;</COLUMN_FCT_NAME>
  <COL_POSITION>10</COL_POSITION>
  <TRANSACTION_TYPE>1</TRANSACTION_TYPE>
 </ROW>
</ROWSET>
**/
