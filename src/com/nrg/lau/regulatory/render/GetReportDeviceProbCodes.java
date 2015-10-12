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

public class GetReportDeviceProbCodes implements View {

	private static Logger log = Logger.getLogger(GetReportDeviceProbCodes.class);
	private DataSource ds;
	private String userId = "";
	private String prodId = "";
	private String deviceId ="";
	private String reportId ="";
	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER -GetReportDeviceProbCodes  render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		if( (request.getParameter("reportid") != null))
			reportId=request.getParameter("reportid");
		if( (request.getParameter("prodid") != null))
			prodId=request.getParameter("prodid");
		if( (request.getParameter("deviceid") != null))
			deviceId=request.getParameter("deviceid");
		userId = CommonDAO.getUSERID(request);
		PrintWriter pw = response.getWriter();
		log.info("Calling getData()");
		pw.write(getData());

		pw.flush();
		pw.close();

		log.info("EXIT - GetReportDeviceProbCodes render()");
	}

	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetReportDeviceProbCodes -Initialize db template()");
	}

	private String getData() {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		String retStr = "";

		try {
			log.info("inside getData() ");
			
			String sqlStr = "'select * from LAU_DEVICE_PROBLEM_CODES where report_id="+reportId + "  and product_id=" +prodId + " and DEVICE_DETAILS_ID= "+deviceId+"'";
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
		log.info("aaaaaaaaaaaaaaaa:" + retStr);
		if (retStr == null) {
			retStr = "";
		}
		log.info("$&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:" + retStr);
		return retStr;

	}

}
