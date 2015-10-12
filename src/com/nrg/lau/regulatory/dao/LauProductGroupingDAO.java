package com.nrg.lau.regulatory.dao;

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

import com.nrg.lau.regulatory.beans.LauProductGrouping;
import com.nrg.lau.beans.LauXmlNodes;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.CommonDAO;

public class LauProductGroupingDAO {

	private Vector<LauProductGrouping> templates	= null;
	private LauProductGrouping prodGrouping	= null;
	private static Logger log	= Logger.getLogger(LauProductGroupingDAO.class);
	   								
	private final String SQL_UPDATE_STRING = "UPDATE LAU_PRODUCT_GROUPING SET UPDATE_USER_ID=?," +
			"UPDATE_TIMESTAMP=?,PRODUCT_CODE_TYPE=?,MEDICAL_SPECIALITY=?,DESCRIPTION=? WHERE PRODUCT_GROUP=? AND PRODUCT_CODE=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_PRODUCT_GROUPING(PRODUCT_GROUP,PRODUCT_CODE,UPDATE_USER_ID," +
			"UPDATE_TIMESTAMP,PRODUCT_CODE_TYPE,MEDICAL_SPECIALITY,DESCRIPTION) VALUES(?,?,?,?,?,?,?)";
	
	public void save(HttpServletRequest request,SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {
		log.info("Entered SAVE!!!");
		//String	userId = CommonDAO.getUSERID(request);
		String userId = "saju";
		log.info("USERID: "+userId);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		log.info("TimeStamp: "+dt.toString());
		log.info("To check if poductGrouping is null: "+request.getParameter(IReportsConstants.LAU_PRODUCT_GROUPING_PARAM_NAME));
		
		if(request.getParameter(IReportsConstants.LAU_PRODUCT_GROUPING_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_PRODUCT_GROUPING_PARAM_NAME).length() > 0) {
		
			log.info("LauUserProcodesDAO save() LAU_PRODUCT_GROUPING_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_PRODUCT_GROUPING_PARAM_NAME));
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_PRODUCT_GROUPING_PARAM_NAME));

			Iterator<LauProductGrouping> itr = this.templates.iterator();
			while(itr.hasNext())	{
				LauProductGrouping lauProdGroup = (LauProductGrouping)itr.next();
				log.info("itr.hasNext() -> " + lauProdGroup.toString());
				this.prodGrouping	= null;
				this.prodGrouping	= lauProdGroup;
				log.info("ProdCode: "+prodGrouping.getProdCode());
				log.info("ProdGroup: "+prodGrouping.getProdGroup());
				log.info("getTransactionType()******"+prodGrouping.getTransactionType());
				
				if(prodGrouping.getTransactionType() == IReportsConstants.deleteFlag){
					log.info("lauProdGroupingActionDAO - save() -> [TRANSACTION_TYPE 0]");
					deleteProductGroup(template,userId,dt);
				}	
				else if(prodGrouping.getTransactionType() == 1){
					insertProductGroup(SQL_INSERT_STRING,template,userId,dt);
				}
				else{
					log.info("updateGroup - TRANSACTION_TYPE [2] -> " + prodGrouping.getProdGroup());
					updateProductGroup(template,userId,dt);
				}

				
			}//end of while(itr.hasNext())

		}	else {
			log.info(IReportsConstants.LAU_PRODUCT_GROUPING_PARAM_NAME + " not found in request");
		}

	}
	
	private void deleteProductGroup(SimpleJdbcTemplate template,
			String userId, Timestamp dt) {
		int id = 0;
		id = template.update("DELETE FROM LAU_PRODUCT_GROUPING WHERE PRODUCT_GROUP = ? AND PRODUCT_CODE = ?", 
			       new Object[]{prodGrouping.getProdGroup(),prodGrouping.getProdCode()});
		log.info("delete ProdGroups() ID -> " + id);
		
	}

	
	private void updateProductGroup(SimpleJdbcTemplate template,
			String userId, Timestamp dt) {
		int id = 0;
		log.info("ID: "+this.prodGrouping.getProdGroup());
		log.info("DT----->"+dt);
		log.info("userid----->"+userId);
		log.info("this.prodGrouping.getProdCode()--->"+ this.prodGrouping.getProdCode());
		log.info("this.prodGrouping.getProdCodeType()--->"+this.prodGrouping.getProdCodeType());
			
		id = template.update(SQL_UPDATE_STRING,getParameters(prodGrouping,userId,dt));
	
		log.info("LauProductGroupingDAO update() ID -> " + id);
		
	}
 
	private Object[] getParameters(LauProductGrouping lauprodGrouping, String userId, java.sql.Timestamp dt)
	{	log.info("Entered getParameters");
		//LauXmlDoc lauTemplate = this.xmlNode; 
		log.info("prodGroup---->"+lauprodGrouping.getProdGroup());
		log.info("prodCode---->"+lauprodGrouping.getProdCode());
		log.info("CodeType---->"+lauprodGrouping.getProdCodeType());
		log.info("Id"+userId);
		log.info("DT"+dt);
		log.info("medicalSpeciality---->"+lauprodGrouping.getMedicalSpeciality());
		log.info("Description---->"+lauprodGrouping.getDescription());
		return new Object[]{
				userId ,
				dt,
				lauprodGrouping.getProdCodeType(),
				lauprodGrouping.getMedicalSpeciality(),
				lauprodGrouping.getDescription(),
				lauprodGrouping.getProdGroup(),
				lauprodGrouping.getProdCode()
				
		};
		
	}	
	
	private void insertProductGroup(String sQL_INSERT_STRING2,
			SimpleJdbcTemplate template, String userId, Timestamp dt) {
		int id;
		id = template.update(SQL_INSERT_STRING,getInsertParameters(template,userId,dt));
		log.info("Product Grouping insert() ID -> " + id);
		
	}

	private Object[] getInsertParameters(SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) {
		LauProductGrouping lauProdGroup = this.prodGrouping;
		
		return new Object[]{
				lauProdGroup.getProdGroup(),
				lauProdGroup.getProdCode(),
				userId ,
				dt,
				lauProdGroup.getProdCodeType(),
				lauProdGroup.getMedicalSpeciality(),
				lauProdGroup.getDescription()
	};
}
	
	
	
	private void createBeansFromXml(String xml) throws Exception {

		templates			= new Vector<LauProductGrouping>();
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauProductGrouping.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_GROUP", "prodGroup" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_CODE", "prodCode" );
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_CODE_TYPE", "prodCodeType" );
		digester.addBeanPropertySetter( mainXmlTag+"/MEDICAL_SPECIALITY", "medicalSpeciality" );
		digester.addBeanPropertySetter( mainXmlTag+"/DESCRIPTION", "description" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		
		//Move to next LauReportAttachments
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));

	}
	
		
	public void addXmlData(LauProductGrouping lauProductGrouping) {
		templates.add(lauProductGrouping);
		log.info(lauProductGrouping.toString());
	}
	
	
}


