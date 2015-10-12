package com.nrg.lau.render;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.View;

import com.nrg.lau.service.bi.BIPublisher;
import com.nrg.lau.service.bi.BIPublisherLoader;
import com.nrg.lau.service.bi.BIPublisherWebServiceImpl;
import com.nrg.lau.utils.ReadConfig;

public class GetTemplateAttachmentAction implements View {

	private static Logger log = Logger.getLogger(GetTemplateAttachmentAction.class);
	private DataSource ds;	
	
	public String getContentType() {
		return "text/xml";
	}
	
	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("ENTER - GetTemplateAttachmentAction render()");
		
		if(request.getParameter("dataTemplateId") != null)	{
			
			boolean biServer = Boolean.valueOf(ReadConfig.getValue("BI_SERVER"));		
			if(biServer) {
				
				log.info("*** Start downloading BI Publisher datamodel ***");	
				String templateID = request.getParameter("dataTemplateId");
				BIPublisher bi = BIPublisherLoader.getBIPublisher();
				byte[] rtn = bi.getDataModel(templateID);				
	            response.setContentLength(rtn.length);
	            response.addHeader("Content-Disposition","attachment; filename=" + templateID + BIPublisherWebServiceImpl.DATA_MODEL_EXT);
	            response.getOutputStream().write(rtn);
				response.getOutputStream().flush();
					
				log.info("*** End downloading BI Publisher datamodel ***");
				
			} else {
				getData(response, request.getParameter("dataTemplateId") );
			}
		}
		
		log.info("EXIT - GetTemplateAttachmentAction()");
		
	}
	
	private void getData(HttpServletResponse response, String layoutTemplateId) {
		
		Connection con		= null;

		try {
			
			con 		= ds.getConnection();
			String sql 	= "{call ? := LAU_UTL.getdatatemplateasclob(?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2,layoutTemplateId);
			
			log.info(sql);
			stmt.execute();
			Clob clob = stmt.getClob(1);
			if (clob != null) {
				
				InputStream in = clob.getAsciiStream();	
				
				response.setContentType("application/rtf;charset=utf-8");
		        response.addHeader("Content-Disposition","attachment;filename=datatemplate.xml");
		        
		        byte[] buffer = new byte[1024];
				int read = 0;
				while ((read = in.read(buffer, 0, buffer.length)) != -1) {
					response.getOutputStream().write(buffer, 0, read);
				}
				
				response.getOutputStream().flush();
				response.getOutputStream().close();				
				
			}

			stmt.close();
			con.close();

		} catch (SQLException e) {
			log.error(e, e);			
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error(e, e);
			}
		}
	}
	
	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
	}
	
}
