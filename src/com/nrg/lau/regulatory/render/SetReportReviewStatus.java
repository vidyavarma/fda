package com.nrg.lau.regulatory.render;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.dao.LauReportReviewStatusSetDAO;

public class SetReportReviewStatus implements View {

	private static Logger log = Logger.getLogger(SetReportReviewStatus.class);
	private DataSource ds;
	private String userId = "";

	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - SetReportReviewStatus  render()");
				
		String status 	= "";
		userId 			= CommonDAO.getUSERID(request);
		
		try	{
			LauReportReviewStatusSetDAO lauReportReviewStatusSetDAO = new LauReportReviewStatusSetDAO();
			lauReportReviewStatusSetDAO.save(request, ds, userId);
			status = XMLException.status("Report review status insert was successful");
		}catch (Exception e) {
			e.printStackTrace();
			status = XMLException.xmlError(e, "failed");
		}
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw = response.getWriter();		
		pw.write(status);		
		pw.flush();
		pw.close();			

		log.info("EXIT - SetReportReviewStatus render()");
	}

	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		log.info("SetReportReviewStatus -Initialize db template()");
	}
	
}
