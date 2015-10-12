package com.nrg.lau.regulatory.render;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import com.nrg.lau.commons.Constants;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.dao.CommonDAO;

public class GetPatientNarratives implements View {

	private static Logger log = Logger.getLogger(GetPatientNarratives.class);
	private DataSource ds;

	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER -GetPatientNarratives  render()");
		 String patientDetId = "";
		 String reportId ="";
		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		if( (request.getParameter("reportid") != null))
			reportId=request.getParameter("reportid");
		if( (request.getParameter("patientdetid") != null))
			patientDetId=request.getParameter("patientdetid");

		PrintWriter pw = response.getWriter();
		log.info("Calling getData()");
		pw.write(getData(patientDetId, reportId));

		pw.flush();
		pw.close();

		log.info("EXIT - GetPatientNarratives render()");
	}

	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetPatientNarratives -Initialize db template()");
	}

	private String getData(String patientDetId, String reportId) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		String retStr = "";

		try {
			log.info("inside getData() ");
			
			String sqlStr = "'SELECT * FROM LAU_NARRATIVE_TEXT "
					+ "where report_id="
					+ reportId
					+ "  and PATIENT_DETAILS_ID="
					+ patientDetId + "'";
			log.info("sql :"+sqlStr );
			retStr = jdbcTemplate
					.queryForObject("select dbms_xmlgen.getxml("+sqlStr+") as xmlVal from dual",
							new RowMapper<String>() {
								@Override
								public String mapRow(ResultSet rs, int rowNum)
										throws SQLException {
									String strClob = "";
									strClob = rs.getString("xmlVal");
									return strClob;
								}
							});

		} catch (Exception e) {
			retStr = "";
			log.error(e);

		}
		log.info("Patient Narratives:" + retStr);
		if (retStr == null) {
			retStr = "";
		}
		return retStr;

	}

}
