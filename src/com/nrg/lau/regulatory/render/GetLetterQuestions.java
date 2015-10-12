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

public class GetLetterQuestions implements View {

	private static Logger log = Logger.getLogger(GetLetterQuestions.class);
	private DataSource ds;
	private String userId = "";

	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER -GetPartnerListAction  render()");
		String templateid = "";
		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		userId = CommonDAO.getUSERID(request);
		if ((request.getParameter("templateid") != null))
			templateid = request.getParameter("templateid");
		log.info("Calling getData():" + templateid);
		PrintWriter pw = response.getWriter();
		log.info("Calling getData()");
		pw.write(getData(templateid));

		pw.flush();
		pw.close();

		log.info("EXIT - GetAssignedProcodes render()");
	}

	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetAssignedProcodes -Initialize db template()");
	}

	private String getData(String templateid) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		String retStr = "";

		try {
			log.info("inside getData() ");
			String sqlStr = "SELECT A.*,  B. DISPLAY_NUMBER FROM LAU_QUESTIONS A,  LAU_LAYOUT_QUESTIONS B WHERE B.LAYOUT_TEMPLATE_ID =  "+templateid+
					 " AND B.LAU_QUESTION_ID  = A.LAU_QUESTION_ID ORDER BY B. DISPLAY_NUMBER";
			retStr = jdbcTemplate.queryForObject("select dbms_xmlgen.getxml('"
					+ sqlStr + "') as xmlVal from dual",
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
		log.info("aaaaaaaaaaaaaaaa temp id:" + retStr);
		if (retStr == null) {
			retStr = "";
		}
		log.info("$&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:" + retStr);
		return retStr;

	}

}
