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

public class GetFieldAttributes implements View {

	private static Logger log = Logger.getLogger(GetFieldAttributes.class);
	private DataSource ds;
	private String userId = "";

	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER -GetFieldAttributes  render()---->");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		userId = CommonDAO.getUSERID(request);		
		PrintWriter pw = response.getWriter();
		log.info("Calling getData()");
		pw.write(getData());

		pw.flush();
		pw.close();

		log.info("EXIT - GetFieldAttributes render()");
	}

	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetFieldAttributes -Initialize db template()");
	}

	private String getData() {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		String retStr = "";

		try {
			log.info("inside getData() ");			
			//String sqlStr = "'SELECT * FROM LAU_QUERIES WHERE query_name is not null and UPDATE_USER_ID = ''" + userId + "'''";
			String sqlStr = "'SELECT * FROM LAU_FIELD_ATTRIBUTES'";
			log.info("sql back ----:"+sqlStr );
			String createStr = "select dbms_xmlgen.getxml("+sqlStr+") as xmlVal from dual";
			log.info("createStr------->"+createStr);
			retStr = jdbcTemplate
					.queryForObject(createStr,
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
		if (retStr == null) {
			retStr = "";
		}
	//	log.info("retStr-------:" + retStr);
		return retStr;

	}

}

