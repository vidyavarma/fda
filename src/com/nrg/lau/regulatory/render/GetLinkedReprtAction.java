package com.nrg.lau.regulatory.render;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.utils.JSONUtil;

public class GetLinkedReprtAction extends JSONUtil implements View{
	
	private static Logger log = Logger
			.getLogger(GetLinkedReprtAction.class);
	private DataSource ds;

	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
	}
	
	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	public void render(Map<String, ?> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String linkedReportid = request.getParameter("linkedreportid");
		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		log.info("linkedReportID -> " + linkedReportid);
		if (linkedReportid != null && !linkedReportid.isEmpty()) {
			
			String sql = "SELECT a.LINKED_REPORT_ID,PRIMARY_REPORT_ID,LINK_METHOD,LINK_COMMENT, b.REPORT_ID,b.LAU_REPORT_ID"
					+ " FROM LAU_LINKED_REPORTS a, LAU_REPORTS b"
					+ " WHERE PRIMARY_REPORT_ID IN  "
					+ "(SELECT PRIMARY_REPORT_ID FROM LAU_LINKED_REPORTS b  WHERE b.LINKED_REPORT_ID = "
					+ linkedReportid
					+ ")"
					+ " AND a.LINKED_REPORT_ID = b.LINKED_REPORT_ID";						
			
			PrintWriter pw = response.getWriter();
			pw.write(getJSON(ds, sql));
			pw.flush();
			pw.close();
			
			
		}else {

			PrintWriter pw = response.getWriter();
			pw.write(XMLException.xmlError(new NullPointerException(),
					"Linked Report Id not found"));
			pw.flush();
			pw.close();
		}
		
	}

}
