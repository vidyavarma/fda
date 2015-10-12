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
import com.nrg.lau.commons.IReportChildRtnSetDAO;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.beans.LauLinkedReports;
import com.nrg.lau.regulatory.dao.LauLinkedReportsDAO;
import com.nrg.lau.utils.JSONUtil;

public class SetLinkedReportsAction implements View {

	private static Logger log = Logger.getLogger(SetLinkedReportsAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;

	public String getContentType() {
		return "text/xml";
	}

	@Override
	public void render(Map<String, ?> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - SetLinkedReportsAction render()");
		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		String status = "";
		try {
			String userId = CommonDAO.getUSERID(request);
			java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
			IReportChildRtnSetDAO<LauLinkedReports> lauLinkedReportsDAO = new LauLinkedReportsDAO();
			String linkedReportid = (String) lauLinkedReportsDAO.save(request,
					template, 0, userId, dt);
			status = XMLException.status("Save was successful");

			// String linkedReportid = request.getParameter("linkedreportid");

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
				pw.write(JSONUtil.getJSON(ds, sql));
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			log.error(e);
			status = XMLException.xmlError(e, "Failed");
		}

		log.info("EXIT - SetLinkedReportsAction render()");

	}

	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
		this.template = new SimpleJdbcTemplate(dataSource);
	}

}
