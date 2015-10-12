package com.nrg.lau.dao;

import java.io.OutputStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;



import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;


import com.nrg.lau.beans.LauTradingPartner;
import com.nrg.lau.beans.LauUserGroups;
import com.nrg.lau.beans.LauUsers;
import com.nrg.lau.commons.IReportsConstants;

public class LauTradingPartnerDAO {
	
	private Vector<LauTradingPartner> templates	= null;
	private LauTradingPartner tradingPartner	= null;
	private static Logger log	= Logger.getLogger(LauTradingPartnerDAO.class);
	//private java.sql.Timestamp dt  = null; 
	//private String userId	= "";
	static public long DATATEMPLATE_ID;
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_TRADING_PARTNER (TRADING_PARTNER_ID," +
			"UPDATE_USER_ID,UPDATE_TIMESTAMP,E2B_PARTNER_ID,E2B_DOC_NAME_INBOUND,E2B_DOC_NAME_FU_INBOUND,E2B_HOST,"+
			"E2B_IMPORT_TYPE,ACK_TYPE,ACK_TIMING,E2B_USE_GLOBAL_CASE_ID,XML_DECLARATION_LINE,"+
			"XML_DOCTYPE_LINE,ACK_DECLARATION_LINE,ACK_DOCTYPE_LINE,INBOUND_USER_GROUP_ID,IN_DIRECTORY,OUT_DIRECTORY,"+
			"PARTNER_TYPE,PARTNER_ORGANIZATION,PARTNER_DEPARTMENT,PARTNER_TITLE,PARTNER_FIRST_NAME,PARTNER_MIDDLE_NAME,PARTNER_LAST_NAME,PARTNER_ADDRESS,PARTNER_CITY,"+
			"PARTNER_STATE,PARTNER_POSTAL_CODE,PARTNER_COUNTRY,PARTNER_PHONE_NUMBER,PARTNER_PHONE_EXT,PARTNER_PHONE_COUNTRY_CODE,PARTNER_EMAIL,"+
			"PARTNER_FAX_NUMBER,PARTNER_FAX_EXT,PARTNER_FAX_COUNTRY_CODE,E2B_DOC_NAME_OUTBOUND,E2B_DOC_NAME_FU_OUTBOUND) "+  
			"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";     							
	private final String SQL_UPDATE_STRING = "UPDATE LAU_TRADING_PARTNER SET UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?," +
			"E2B_PARTNER_ID=?,E2B_DOC_NAME_INBOUND=?,E2B_DOC_NAME_FU_INBOUND=?,E2B_HOST=?,E2B_IMPORT_TYPE=?,ACK_TYPE=?,ACK_TIMING=?, " +
			"E2B_USE_GLOBAL_CASE_ID=?,XML_DECLARATION_LINE=?,XML_DOCTYPE_LINE=?,ACK_DECLARATION_LINE=?,ACK_DOCTYPE_LINE=?, "+
			"INBOUND_USER_GROUP_ID=?,IN_DIRECTORY=?,OUT_DIRECTORY=?,PARTNER_TYPE=?,PARTNER_ORGANIZATION=?,PARTNER_DEPARTMENT=?,"+
			"PARTNER_TITLE=?,PARTNER_FIRST_NAME=?,PARTNER_LAST_NAME=?,PARTNER_ADDRESS=?,PARTNER_CITY=?,"+
			"PARTNER_STATE=?,PARTNER_POSTAL_CODE=?,PARTNER_MIDDLE_NAME=?,PARTNER_COUNTRY=?,PARTNER_PHONE_NUMBER=?,PARTNER_PHONE_EXT=?,PARTNER_PHONE_COUNTRY_CODE=?,PARTNER_EMAIL=?,"+
			"PARTNER_FAX_NUMBER=?,PARTNER_FAX_EXT=?,PARTNER_FAX_COUNTRY_CODE=?,E2B_DOC_NAME_OUTBOUND=?,E2B_DOC_NAME_FU_OUTBOUND=? " +
			"WHERE TRADING_PARTNER_ID=?";
		//INBOUND_USER_GROUP_ID=?,IN_DIRECTORY=?,OUT_DIRECTORY=?
	public void save(HttpServletRequest request,SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {
		log.info("Entered SAVE!!!");
		String	userId = CommonDAO.getUSERID(request);
		log.info("USERID: "+userId);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		log.info("TimeStamp: "+dt.toString());
		log.info("To check if null: "+request.getParameter(IReportsConstants.LAU_TRADING_PARTNER_PARAM_NAME));
		if(request.getParameter(IReportsConstants.LAU_TRADING_PARTNER_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_TRADING_PARTNER_PARAM_NAME).length() > 0) {

			log.info("LauTradingPartnerDAO save() LAU_TRADING_PARTNER_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_TRADING_PARTNER_PARAM_NAME));
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_TRADING_PARTNER_PARAM_NAME));

			Iterator<LauTradingPartner> itr = this.templates.iterator();
			while(itr.hasNext())	{
				LauTradingPartner lauTradingPartner = (LauTradingPartner)itr.next();
				log.info("itr.hasNext() -> " + lauTradingPartner.toString());
				this.tradingPartner	= null;
				this.tradingPartner	= lauTradingPartner;
				log.info("lauTradingPartner.getTransactionType()******"+lauTradingPartner.getTransactionType());
				if(lauTradingPartner.getTransactionType() == 1  || 
						lauTradingPartner.getTransactionType() == 2){
					log.info("LauDataTemplateDAO - save() -> [TRANSACTION_TYPE 1 & 2]: " + lauTradingPartner.toString());
					updateUser(template, lauTradingPartner.getTransactionType(), lauTradingPartner, request, userId,dt,datasource);
				}	
				if(lauTradingPartner.getTransactionType() == IReportsConstants.deleteFlag){
					log.info("lauDataTemplateDAO - save() -> [TRANSACTION_TYPE 0]: " + lauTradingPartner.toString());
					updateUser(template, lauTradingPartner.getTransactionType(), lauTradingPartner, request
							,userId,dt,datasource);
				}
			}//end of while(itr.hasNext())

		}	else {
			log.info(IReportsConstants.LAU_DATA_TEMPLATE_PARAM_NAME + " not found in request");
		}

	}
	
	
	public void insert(DataSource datasource, HttpServletRequest request,
			SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) throws Exception {
		// Normal HTTP Attachments
		insertTemplate(datasource, request,SQL_INSERT_STRING,template,userId,dt);
	}
	
	private void insertTemplate(DataSource datasource, HttpServletRequest request,
			String sql,SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {

		Connection con					= null;
		PreparedStatement pst 			= null;
		ResultSet rs 					= null;
		Statement stmt 					= null;
		
		try	{

			log.info("LAUTRADINGPARTNER_INSERT");
			log.info("Id"+userId);
			log.info("DT"+dt);
		
			log.info("datasource.getConnection()");
			long tradingPartnerId = CommonDAO.getPrimaryKey(template);
			log.info("TRADINGPARTNERID"+tradingPartnerId);
			this.tradingPartner.setTradingPartnerId(tradingPartnerId);
			
			log.info("insertTemplate - > Normal HTTP Attachments");
			con = datasource.getConnection();
			con.setAutoCommit(false);
			
			log.info("dataTemplateId--->"+tradingPartnerId);
			log.info("DT----->"+dt);
			log.info("userid----->"+userId);
			log.info("this.tradingPartner.getDocName()--->"+ this.tradingPartner.getE2bDocName());
			log.info("this.tradingPartner.getE2bImportType()--->"+this.tradingPartner.getE2bImportType());
			log.info("this.tradingPartner.getAckType()--->"+this.tradingPartner.getAckDocTypeLine());
					
		
		pst = con.prepareStatement(sql);
				
		pst.setLong(1, tradingPartnerId);
		pst.setString(2, userId);
		pst.setTimestamp(3, dt);
		pst.setString(4, this.tradingPartner.getE2bPartnerId());
		pst.setString(5, this.tradingPartner.getE2bDocName());
		pst.setString(6, this.tradingPartner.getE2bDocNameFU());
		pst.setString(7, this.tradingPartner.getE2bHost());
		pst.setString(8, this.tradingPartner.getE2bImportType());
		pst.setString(9, this.tradingPartner.getAckType());
		pst.setString(10, this.tradingPartner.getAckTiming());
		pst.setString(11, this.tradingPartner.getE2bUseCaseId());
		pst.setString(12, this.tradingPartner.getXmlDecLine());
		pst.setString(13, this.tradingPartner.getXmlDocTypeLine());
		pst.setString(14, this.tradingPartner.getAckDecLine());
		pst.setString(15, this.tradingPartner.getAckDocTypeLine());
		pst.setString(16, this.tradingPartner.getUserGrp());
		pst.setString(17, this.tradingPartner.getInDir());
		pst.setString(18, this.tradingPartner.getOutDir());
		pst.setString(19, this.tradingPartner.getpType());
		pst.setString(20, this.tradingPartner.getpOrg());
		pst.setString(21, this.tradingPartner.getpDept());
		pst.setString(22, this.tradingPartner.getTitle());
		pst.setString(23, this.tradingPartner.getfName());
		pst.setString(24, this.tradingPartner.getmName());
		pst.setString(25, this.tradingPartner.getlName());
		pst.setString(26, this.tradingPartner.getAddress());
		pst.setString(27, this.tradingPartner.getCity());
		pst.setString(28, this.tradingPartner.getState());
		pst.setString(29, this.tradingPartner.getPostal());
		pst.setString(30, this.tradingPartner.getCountry());
		pst.setString(31, this.tradingPartner.getPhone());
		pst.setString(32, this.tradingPartner.getPhoneExt());
		pst.setString(33, this.tradingPartner.getCountryCode());
		pst.setString(34, this.tradingPartner.getEmail());
		pst.setString(35, this.tradingPartner.getFax());
		pst.setString(36, this.tradingPartner.getFaxExt());
		pst.setString(37, this.tradingPartner.getFaxCode());
		pst.setString(38, this.tradingPartner.getE2bDocNameOutbound());
		pst.setString(39, this.tradingPartner.getE2bDocNameFUOutbound());
		boolean query = pst.execute();
		log.info(query);
		
	
		//DATA_TEMPLATE
		sql = "SELECT * FROM LAU_TRADING_PARTNER WHERE TRADING_PARTNER_ID = " +
				this.tradingPartner.getTradingPartnerId() + " FOR UPDATE";
		log.info(sql);
		
		stmt 							= con.createStatement();
		rs 								= stmt.executeQuery(sql);
		
		

	//	rs.next();
		
		
		
		log.info("insertTemplate() -> Normal template save completed");
		//con.commit();

		
		}catch(Exception e)	{
			log.error(e);
			con.rollback();
			throw new Exception(e);

		} finally {
			con.setAutoCommit(true);
			try
			{
				
				if (pst != null) 	pst.close();
				if (rs != null) 	rs.close();
				if (stmt != null) 	stmt.close();
				if (con != null)	con.close();
				log.info("connection closed");
			}catch (Exception e)	{
				con.rollback();
				throw new Exception(e);
			}
		}
		
	}
	
	private void updateUser(SimpleJdbcTemplate template,long type,LauTradingPartner lauTradingPartner,
			HttpServletRequest request, String userId, java.sql.Timestamp dt, DataSource datasource) throws Exception {
		log.info("TYPPPPEEE: "+type);
		if(type == 0) {
			log.info("deleteing tradingPartner");
			CommonDAO.setDbmsClientInfo(template,userId );
			template.update("DELETE FROM LAU_TRADING_PARTNER WHERE TRADING_PARTNER_ID = ?", 
					new Object[]{lauTradingPartner.getTradingPartnerId()});
			log.info("updateUser - TRANSACTION_TYPE [0{LAU_USER_ROLES}] -> " +lauTradingPartner.getTradingPartnerId()+"]");
			//delete(template, userId); 
		} else if(type == 1) {
			log.info("updateUser - TRANSACTION_TYPE [1] -> " + lauTradingPartner.getTradingPartnerId());
			insert(datasource,request,template,userId,dt);
			
		} else if(type == 2) {
			log.info("updateUser - TRANSACTION_TYPE [2] -> " + lauTradingPartner.getTradingPartnerId());
			updateTemplate(datasource,request,template,userId,dt);
			
		}
	}
	
	private Object[] getParameters(LauTradingPartner lauPartner, String userId, java.sql.Timestamp dt)
	{	log.info("Entered getParameters");
		//LauDataTemplate lauPartner = this.tradingPartner; 
		log.info("E2B DOCName---->"+lauPartner.getE2bDocName());
		log.info("ACK DOCTypeLine---->"+lauPartner.getAckDocTypeLine());
		log.info("E2BPartner ID---->"+lauPartner.getE2bPartnerId());
		log.info("Out Dir ---->"+lauPartner.getOutDir());
		log.info("TradingPartner Id---->"+lauPartner.getTradingPartnerId());
		log.info("Id"+userId);
		log.info("DT"+dt);
		return new Object[]{
				userId,
				dt,
				lauPartner.getE2bPartnerId(),
				lauPartner.getE2bDocName(),
				lauPartner.getE2bDocNameFU(),
				lauPartner.getE2bHost(),
				lauPartner.getE2bImportType(),
				lauPartner.getAckType(),
				lauPartner.getAckTiming(),
				lauPartner.getE2bUseCaseId(),
				lauPartner.getXmlDecLine(),
				lauPartner.getXmlDocTypeLine(),
				lauPartner.getAckDecLine(),
				lauPartner.getAckDocTypeLine(),
				lauPartner.getUserGrp(),
				lauPartner.getInDir(),
				lauPartner.getOutDir(),
				lauPartner.getpType(),
				lauPartner.getpOrg(),
				lauPartner.getpDept(),
				lauPartner.getTitle(),
				lauPartner.getfName(),
				lauPartner.getmName(),
				lauPartner.getlName(),
				lauPartner.getAddress(),
				lauPartner.getCity(),
				lauPartner.getState(),
				lauPartner.getPostal(),
				lauPartner.getCountry(),
				lauPartner.getPhone(),
				lauPartner.getPhoneExt(),
				lauPartner.getCountryCode(),
				lauPartner.getEmail(),
				lauPartner.getFax(),
				lauPartner.getFaxExt(),
				lauPartner.getFaxCode(),
				lauPartner.getE2bDocNameOutbound(),
				lauPartner.getE2bDocNameFUOutbound(),
				lauPartner.getTradingPartnerId()
		};
		
	}	
	
	
	private void createBeansFromXml(String xml) throws Exception {

		templates			= new Vector<LauTradingPartner>();
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauTradingPartner.class );

		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/TRADING_PARTNER_ID", "tradingPartnerId" );
		digester.addBeanPropertySetter( mainXmlTag+"/E2B_PARTNER_ID", "e2bPartnerId" );
		digester.addBeanPropertySetter( mainXmlTag+"/E2B_DOC_NAME_INBOUND", "e2bDocName" );
		digester.addBeanPropertySetter( mainXmlTag+"/E2B_DOC_NAME_FU_INBOUND", "e2bDocNameFU" );
		digester.addBeanPropertySetter( mainXmlTag+"/E2B_HOST", "e2bHost" );
		digester.addBeanPropertySetter( mainXmlTag+"/E2B_IMPORT_TYPE", "e2bImportType" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACK_TYPE", "ackType" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACK_TIMING", "ackTiming" );
		digester.addBeanPropertySetter( mainXmlTag+"/E2B_USE_GLOBAL_CASE_ID", "e2bUseCaseId" );
		digester.addBeanPropertySetter( mainXmlTag+"/XML_DECLARATION_LINE", "xmlDecLine" );
		digester.addBeanPropertySetter( mainXmlTag+"/XML_DOCTYPE_LINE", "xmlDocTypeLine" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACK_DECLARATION_LINE", "ackDecLine" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACK_DOCTYPE_LINE", "ackDocTypeLine" );
		digester.addBeanPropertySetter( mainXmlTag+"/INBOUND_USER_GROUP_ID", "userGrp" );
		digester.addBeanPropertySetter( mainXmlTag+"/IN_DIRECTORY", "inDir" );
		digester.addBeanPropertySetter( mainXmlTag+"/OUT_DIRECTORY", "outDir" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_TYPE", "pType" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_ORGANIZATION", "pOrg" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_DEPARTMENT", "pDept" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_TITLE", "title" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_FIRST_NAME", "fName" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_MIDDLE_NAME", "mName" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_LAST_NAME", "lName" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_ADDRESS", "address" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_CITY", "city" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_STATE", "state" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_POSTAL_CODE", "postal" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_COUNTRY", "country" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_PHONE_NUMBER", "phone" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_PHONE_EXT", "phoneExt" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_PHONE_COUNTRY_CODE", "countryCode" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_EMAIL", "email" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_FAX_NUMBER", "fax" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_FAX_EXT", "faxExt" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_FAX_COUNTRY_CODE", "faxCode" );
		digester.addBeanPropertySetter( mainXmlTag+"/E2B_DOC_NAME_OUTBOUND", "e2bDocNameOutbound" );
		digester.addBeanPropertySetter( mainXmlTag+"/E2B_DOC_NAME_FU_OUTBOUND", "e2bDocNameFUOutbound" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
	
		//Move to next LauReportAttachments
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauTradingPartner lauTradingPartner) {
		templates.add(lauTradingPartner);
		log.info(lauTradingPartner.toString());
	}
	
	
	public void updateTemplate(DataSource datasource, HttpServletRequest request,
			SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {
		int id = 0;
		
		log.info("ID: "+this.tradingPartner.getTradingPartnerId());
		log.info("DT----->"+dt);
		log.info("userid----->"+userId);
		log.info("this.tradingPartner.getE2bDocName()--->"+ this.tradingPartner.getE2bDocName());
		log.info("this.tradingPartner.getE2bHost()--->"+this.tradingPartner.getE2bHost());
		log.info("this.tradingPartner.getImportType()--->"+this.tradingPartner.getE2bImportType());
		
		id = template.update(SQL_UPDATE_STRING,getParameters(tradingPartner,userId,dt));
	
		log.info("LauDataTemplateDAO Update() ID -> " + id);

	}
	
}	
		
