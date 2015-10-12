package com.nrg.lau.regulatory.render;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.dao.LauSaveResultSetDAO;

public class SetSaveResultSetAction implements View {
	private static Logger log = Logger.getLogger(SetSaveResultSetAction.class);
	private DataSource ds;
	private SimpleJdbcTemplate template;
	private java.sql.Timestamp dt = null;
	private String userId = "";

	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("ENTER - SetSaveResultAction render()");

		String status = "";
		try {
			// userId = CommonDAO.getUSERID(request);
			dt = CommonDAO.getTimestamp(template);
			// log.info("userId in action---->"+userId);

			LauSaveResultSetDAO lauSaveResultSetDAO = new LauSaveResultSetDAO();

			status = lauSaveResultSetDAO.save(request, template, dt);
			if (status.trim().equals("-2")) {
				status = "Saved Successfully!";
			} else if (status.trim().equals("1")) {
				status = "Deleted Successfully!";
			} else if (status.trim().equals("exists")) {
				status = "This name has already been given to a Saved Result Set. Please enter a new name.";
			} else {
				status = "Failed!";
			}

			log.info("status in SetSaveResultAction---->" + status);
			// status =
			// XMLException.status("SetSaveResultAction save was successful!");
		} catch (Exception e) {
			log.error(e);
			// status = XMLException.xmlError(e,
			// "SetSaveResultAction save was successful!");
		}

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		pw.write(status);
		pw.flush();
		pw.close();

		log.info("EXIT - SetSaveResultAction render()");
	}

	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		this.template = new SimpleJdbcTemplate(dataSource);
	}
}
