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

import com.nrg.lau.beans.LauXmlDoc;
import com.nrg.lau.beans.LauXmlNodes;
import com.nrg.lau.commons.IReportsConstants;

public class LauXmlNodeDAO {

	private Vector<LauXmlNodes> templates	= null;
	private LauXmlNodes xmlNode	= null;
	private static Logger log	= Logger.getLogger(LauXmlNodeDAO.class);
	private String	userId = "";
   								
	private final String SQL_UPDATE_STRING = "UPDATE LAU_XML_NODES SET UPDATE_USER_ID=?," +
			"UPDATE_TIMESTAMP=?,XML_NODE_NAME=?,NODE_TYPE=?,PARENT=?,SQLTXT=?,POSITION=?,"+
			"NODE_LEVEL=?, USE_REPORTID=?, MANDATORY_NODE=? WHERE DOC_NAME=? AND NODE_NAME=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_XML_NODES(DOC_NAME,NODE_NAME,UPDATE_USER_ID," +
			"UPDATE_TIMESTAMP,XML_NODE_NAME,NODE_TYPE,PARENT,SQLTXT,POSITION,NODE_LEVEL,USE_REPORTID,"+
			"MANDATORY_NODE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			
	public void save(HttpServletRequest request,SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {
		log.info("Entered SAVE!!!");
		userId = CommonDAO.getUSERID(request);
		log.info("USERID: "+userId);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		log.info("TimeStamp: "+dt.toString());
		log.info("To check if null: "+request.getParameter(IReportsConstants.LAU_XML_NODES_PARAM_NAME));
		if(request.getParameter(IReportsConstants.LAU_XML_NODES_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_XML_NODES_PARAM_NAME).length() > 0) {

			log.info("LauXmlNodesDAO save() LAU_XML_NODES_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_XML_NODES_PARAM_NAME));
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_XML_NODES_PARAM_NAME));

			Iterator<LauXmlNodes> itr = this.templates.iterator();
			while(itr.hasNext())	{
				LauXmlNodes lauXmlNodes = (LauXmlNodes)itr.next();
				log.info("itr.hasNext() -> " + lauXmlNodes.toString());
				this.xmlNode	= null;
				this.xmlNode	= lauXmlNodes;
				log.info("lauXmlNodes.DocName"+lauXmlNodes.getDocName());
				log.info("lauXmlNodes.getTransactionType()******"+lauXmlNodes.getTransactionType());
					
				if(lauXmlNodes.getTransactionType() == IReportsConstants.deleteFlag){
					log.info("lauE2BDocActionDAO - save() -> [TRANSACTION_TYPE 0]: " + lauXmlNodes.toString());
					deleteNode(template,userId,dt);
				}	
				else if(lauXmlNodes.getTransactionType() == 1){
					insertNode(SQL_INSERT_STRING,template,userId,dt);
				}
				else{
					log.info("updateUser - TRANSACTION_TYPE [2] -> " + lauXmlNodes.getDocName());
					updateNode(template,userId,dt);
				}
			}

		}	else {
			log.info(IReportsConstants.LAU_XML_NODES_PARAM_NAME + " not found in request");
		}

	}
	
	private void deleteNode(SimpleJdbcTemplate template, String userId, Timestamp dt) {
		int id = 0;
		id = template.update("DELETE FROM LAU_XML_COLUMNS WHERE DOC_NAME = ? AND NODE_NAME = ?", 
			       new Object[]{xmlNode.getDocName(),xmlNode.getNodeName()});
		log.info("delete Columns() ID -> " + id);
		id = template.update("DELETE FROM LAU_XML_NODES WHERE DOC_NAME = ? AND NODE_NAME = ?", 
				new Object[]{xmlNode.getDocName(),xmlNode.getNodeName()});
		log.info("delete Node() ID -> " + id);
		log.info("LauDataTemplateDAO delete() ");
		
	}

	private void insertNode(String sql, SimpleJdbcTemplate template,
			String userId, Timestamp dt) throws Exception  {
		int id = 0;
		id = template.update(SQL_INSERT_STRING,getInsertParameters(template,userId,dt));
		log.info("LauE2BnodeActionDAO insert() ID -> " + id);

	}
	

	private Object[] getInsertParameters(SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) {
				
		LauXmlNodes lauXmlNode = this.xmlNode;
		return new Object[]{
					lauXmlNode.getDocName(),
					lauXmlNode.getNodeName(),
					userId ,
					dt,
					lauXmlNode.getXmlNodeName(),
					lauXmlNode.getNodeType(),
					lauXmlNode.getParent(),
					lauXmlNode.getSqlTxt(),
					lauXmlNode.getPosition(),
					lauXmlNode.getNodeLevel(),
					lauXmlNode.getUseReportId(),
					lauXmlNode.getMandatoryNode()					
	};
}
	private Object[] getParameters(LauXmlNodes lauNodes, String userId, java.sql.Timestamp dt)
	{	log.info("Entered getParameters");
		//LauXmlDoc lauTemplate = this.xmlNode; 
		log.info("Doc Name---->"+lauNodes.getDocName());
		log.info("Node Name---->"+lauNodes.getNodeName());
		log.info("Node Type---->"+lauNodes.getNodeType());
		log.info("Id"+userId);
		log.info("DT"+dt);
		return new Object[]{
				userId,
				dt,
				lauNodes.getXmlNodeName(),
				lauNodes.getNodeType(),
				lauNodes.getParent(),
				lauNodes.getSqlTxt(),
				lauNodes.getPosition(),
				lauNodes.getNodeLevel(),
				lauNodes.getUseReportId(),
				lauNodes.getMandatoryNode(),
				lauNodes.getDocName(),
				lauNodes.getNodeName()
				
		};
		
	}	
	
	
	private void createBeansFromXml(String xml) throws Exception {

		templates			= new Vector<LauXmlNodes>();
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauXmlNodes.class );

		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/DOC_NAME", "docName");
		digester.addBeanPropertySetter( mainXmlTag+"/NODE_NAME", "nodeName");
		digester.addBeanPropertySetter( mainXmlTag+"/XML_NODE_NAME", "xmlNodeName");
		digester.addBeanPropertySetter( mainXmlTag+"/NODE_TYPE", "nodeType" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARENT", "parent");
		digester.addBeanPropertySetter( mainXmlTag+"/SQLTXT", "sqlTxt");
		digester.addBeanPropertySetter( mainXmlTag+"/POSITION", "position");
		digester.addBeanPropertySetter( mainXmlTag+"/NODE_LEVEL", "nodeLevel");
		digester.addBeanPropertySetter( mainXmlTag+"/USE_REPORTID", "useReportId");
		digester.addBeanPropertySetter( mainXmlTag+"/MANDATORY_NODE", "mandatoryNode" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );

		//Move to next LauReportAttachments
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauXmlNodes lauXmlNode) {
		templates.add(lauXmlNode);
		log.info(lauXmlNode.toString());
	}
	
	
	public void updateNode(SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {
		int id = 0;
		
		log.info("ID: "+this.xmlNode.getDocName());
		log.info("DT----->"+dt);
		log.info("userid----->"+userId);
		log.info("this.xmlNode.getTemplateName()--->"+ this.xmlNode.getNodeName());
		log.info("this.xmlNode.getSqlText()--->"+this.xmlNode.getSqlTxt());
			
		id = template.update(SQL_UPDATE_STRING,getParameters(xmlNode,userId,dt));
	
		log.info("LauDataTemplateDAO update() ID -> " + id);

	}
}

/**<?xml version="1.0"?>
<ROWSET>
 <ROW>
  <DOC_NAME>DTD21</DOC_NAME>
  <NODE_NAME>ICHICSR03</NODE_NAME>
  <XML_NODE_NAME>ichicsr</XML_NODE_NAME>
  <NODE_TYPE>COMPLEX</NODE_TYPE>
  <SQLTXT>(select  XMLELEMENT(&quot;x_xml_node_name&quot;, XMLAttributes(x_node_query ), XYZ1_ICHICSR </SQLTXT>
  <POSITION>0</POSITION>
  <NODE_LEVEL>0</NODE_LEVEL>
  <USE_REPORTID>Y</USE_REPORTID>
  <MANDATORY_NODE>Y</MANDATORY_NODE>
 <TRANSACTION_TYPE>1</TRANSACTION_TYPE>
 </ROW>
</ROWSET>
**/