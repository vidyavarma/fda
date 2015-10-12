package com.nrg.lau.regulatory.dao;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauProducts;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.CommonDAO;

public class LauReportReviewStatusSetDAO{
	
	private static Logger log	= Logger.getLogger(LauReportReviewStatusSetDAO.class);
	private Vector<LauProducts> reports			= null;	
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;		
		super.finalize();
	}
	
	
	public void save(HttpServletRequest request, DataSource datasource, 
			String user) throws Exception {
		String reviewmode  = "";
		String reviewChangeReason  = "";
		String reviewaction = "";

		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_REPORT_VIEW_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_REPORT_VIEW_PARAM_NAME).length() > 0) {
			
			log.info("LauReportReviewStatusSetDAO save() LAU_REPORT_VIEW_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_REPORT_VIEW_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_REPORT_VIEW_PARAM_NAME));	
			//reviewmode
			if(request.getParameter("reviewmode") != null)	{
				reviewmode = request.getParameter("reviewmode");
			}
			//reviewaction
			if(request.getParameter("reviewaction") != null)	{
				reviewaction = request.getParameter("reviewaction");
			}
			if(request.getParameter("changeReason") != null)	{
				reviewChangeReason = request.getParameter("changeReason");
			}
			Iterator<LauProducts> itr = this.reports.iterator();
			insert(datasource, itr, user,reviewmode,reviewaction,reviewChangeReason ,request );
			
		}
		
	}	
	
	public void insert(DataSource datasource, Iterator<LauProducts> itr, 
			String user, String reviewmode ,String reviewaction, String reviewChangeReason , HttpServletRequest request) throws Exception {
		
		Connection con			= null;
		CallableStatement stmt 	= null;
		String productId ="";
		SimpleJdbcTemplate template = new SimpleJdbcTemplate(datasource);
		java.sql.Timestamp dt   = CommonDAO.getTimestamp(template);;
		con = datasource.getConnection();
		try {
			while(itr.hasNext())	{
				LauProducts lauProducts = (LauProducts)itr.next();
				log.info("itr.hasNext() -> " + lauProducts.toString());

				if (lauProducts.getProductIdList().length() != 0){
					productId = lauProducts.getProductIdList();
					
				}	

				String sql 	= "{call ? := LAU_UTL.setReportAndProdReviewStatus(?,?,?,?)}";
				stmt = con.prepareCall(sql);
				stmt.registerOutParameter(1, OracleTypes.VARCHAR);
				stmt.setLong(2, lauProducts.getReportId());	
				stmt.setString(3, user);	
				stmt.setString(4, reviewaction);
				stmt.setString(5, productId);	
				log.info(sql);
				stmt.execute();
				String rtn = stmt.getString(1);
				
				log.info("user, repid, ProductId ---> " + user+ " ,"+lauProducts.getReportId() + ", "+ productId);		
				log.info("TEST ---> " + rtn);	
				if (rtn.equals("1"))
				{
					int intInsStatus = CommonDAO.insertActivityDetails(template, null, lauProducts.getReportId(), reviewmode, "", reviewChangeReason, request,user,dt);
				}
				stmt.close();
			}
		//	con.commit();
		}catch(Exception e)	{
			log.error(e);
		//	con.rollback();
			throw new Exception(e);

		} finally {

			try
			{
				if (stmt != null) 	stmt.close();
				if (con != null)	con.close();
				log.info("connection closed");
			}catch (Exception e)	{
				con.rollback();
				throw new Exception(e);
			}
		}
		
	}

	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauProducts>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauProducts.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/PRODUCT_ID", "productIdList" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );		
				
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		//getProductIdList
	}
	
	public void addXmlData(LauProducts lauProducts) {
		reports.add(lauProducts);
		log.info(lauProducts.toString());
	}	
	
}
