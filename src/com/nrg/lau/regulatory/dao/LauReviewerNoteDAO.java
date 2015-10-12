package com.nrg.lau.regulatory.dao;

import java.io.StringReader;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.beans.LauReviewerNote;

public class LauReviewerNoteDAO {

	private static Logger log = Logger.getLogger(LauReviewerNoteDAO.class);
	private Vector<LauReviewerNote> reports = null;
	private LauReviewerNote reviewerNote = null;
	private String userId;
	private Timestamp dt;

	public String save(HttpServletRequest request, DataSource datasource,
			String user, Timestamp dstamp) throws Exception {

		String submitActivityType = "";
		String submitActivityDesc = "";

		// Check to make sure that XML is there in Request.
		if (request
				.getParameter(IReportsConstants.LAU_REVIEWER_NOTE_PARAM_NAME) != null
				&& request.getParameter(
						IReportsConstants.LAU_REVIEWER_NOTE_PARAM_NAME)
						.length() > 0) {

			log.info("LauReviewerNoteDAO save() LAU_REVIEWER_NOTE_PARAM_NAME -> "
					+ request
							.getParameter(IReportsConstants.LAU_REVIEWER_NOTE_PARAM_NAME));

			this.userId = user;
			this.dt = dstamp;

			// Create reviewer note beans from XML Request
			createBeansFromXml(request
					.getParameter(IReportsConstants.LAU_REVIEWER_NOTE_PARAM_NAME));

			// submitActivityType
			if (request.getParameter("reviewerNoteType") != null) {
				submitActivityType = request.getParameter("reviewerNoteType");
			}
			// submitActivityDesc
			if (request.getParameter("reviewerNoteDesc") != null) {
				submitActivityDesc = request.getParameter("reviewerNoteDesc");
			}

			Iterator<LauReviewerNote> itr = this.reports.iterator();

			insert(datasource, itr, user, submitActivityType,
					submitActivityDesc, request, dstamp);
		} else {
			log.info(IReportsConstants.LAU_REVIEWER_NOTE_PARAM_NAME
					+ " not found in request");
		}
		// return Long.toString(this.linkedReports.getLINKED_REPORT_ID());
		return null;
	}

	public void insert(DataSource datasource, Iterator<LauReviewerNote> itr,
			String user, String submitActivityType, String submitActivityDesc,
			HttpServletRequest request, Timestamp dstamp) throws Exception {
		SimpleJdbcTemplate template = new SimpleJdbcTemplate(datasource);
		log.info("dstamp------->" + dstamp);
		log.info("submitActivityType------->" + submitActivityType);
		log.info("submitActivityDesc------->" + submitActivityDesc);
		while (itr.hasNext()) {
			LauReviewerNote lauReviewerNote = (LauReviewerNote) itr.next();
			log.info("reportId-----> " + lauReviewerNote.getREPORT_ID());
			insertActivities(template, user, dstamp,
					lauReviewerNote.getREPORT_ID(), submitActivityType,
					submitActivityDesc);
		}

	}

	private void insertActivities(SimpleJdbcTemplate template, String userId,
			Timestamp dt, String reportId, String submitActivityType,
			String submitActivityDesc) {
		// TODO Auto-generated method stub
		int id = 0;
		long dispNum = 0;
		log.info("******insertActivities:");
		long activitiesID = CommonDAO.getPrimaryKey(template);
		log.info("activitiesID insert() ID -> " + activitiesID);
		id = template.update(
				SQL_INSERT_STRING_ACT,
				getActivitiesParameters(activitiesID, reportId, dispNum,
						submitActivityType, submitActivityDesc, userId, dt));
		log.info("LauIncludedReportsDAO insert() ID -> " + id);

	}

	private final String SQL_INSERT_STRING_ACT = "INSERT INTO LAU_REPORT_ACTIVITIES(ACTIVITY_ID, REPORT_ID, DISPLAY_NUMBER, ACTIVITY_DATE, ACTIVITY_TYPE, "
			+ "ACTIVITY_DESCRIPTION, UPDATE_USER_ID,UPDATE_TIMESTAMP) VALUES (?,?,?,?,?,?,?,?)";

	private Object[] getActivitiesParameters(Long activitesID, String strRepId,
			Long dispNo, String submitActivityType, String submitActivityDesc, 
			String userId, Timestamp dt) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		Calendar date = new GregorianCalendar();

		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		date.set(Calendar.MINUTE, 0);

		String actDate = dateFormat.format(date.getTime());
		log.info("actDate: " + actDate);

		return new Object[] { activitesID, strRepId, dispNo, actDate,
				submitActivityType, submitActivityDesc, userId, dt };
	}

	public void createBeansFromXml(String xml) throws Exception {

		String mainXmlTag = "ROWSET/ROW";
		reports = new Vector<LauReviewerNote>();
		Digester digester = new Digester();
		digester.push(this);
		digester.addObjectCreate(mainXmlTag, LauReviewerNote.class);

		// Explicitly specify property
		digester.addBeanPropertySetter(mainXmlTag + "/REPORT_ID", "REPORT_ID");

		// Move to next LauReports
		digester.addSetNext(mainXmlTag, "addXmlData");
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauReviewerNote lauReviewerNote) {
		reports.add(lauReviewerNote);
		log.info(lauReviewerNote.toString());
	}

}
