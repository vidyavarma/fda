package com.nrg.lau.regulatory.dao;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.regulatory.beans.LauUserProcodes;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.CommonDAO;

public class LauUserProcodesDAO {

	private Vector<LauUserProcodes> templates = null;
	private LauUserProcodes userProcodes = null;
	private static Logger log = Logger.getLogger(LauUserProcodesDAO.class);
	private String UserID = "";

	private final String SQL_INSERT_STRING = "INSERT INTO LAU_PROD_GROUP_ASSIGNMENTS(PRODUCT_GROUP_ASSIGNMENT_ID,USER_ID,UPDATE_USER_ID,"
			+ "UPDATE_TIMESTAMP,PRODUCT_GROUP,PRODUCT_CODE) VALUES(?,?,?,?,?,?)";

	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {
		log.info("Entered SAVE!!!");
		String userId = CommonDAO.getUSERID(request);
		// String userId = "LAU30DEV";
		log.info("USERID: " + userId);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		log.info("TimeStamp: " + dt.toString());
		log.info("To check if userId is null: "
				+ request.getParameter("UserID"));
		UserID = request.getParameter("UserID");
		log.info("To check if procodesList is null: "
				+ request
						.getParameter(IReportsConstants.LAU_PROD_GROUP_ASSIGNMENTS_PARAM_NAME));

		if (request
				.getParameter(IReportsConstants.LAU_PROD_GROUP_ASSIGNMENTS_PARAM_NAME) != null
				&& request
						.getParameter(
								IReportsConstants.LAU_PROD_GROUP_ASSIGNMENTS_PARAM_NAME)
						.length() > 0) {
			int id = 0;
			id = template
					.update("DELETE FROM LAU_PROD_GROUP_ASSIGNMENTS WHERE upper(USER_ID) = ? ",
							new Object[] { UserID.toUpperCase() });
			log.info("Delete ID: " + id);
			log.info("LauUserProcodesDAO save() LAU_PROD_GROUP_ASSIGNMENTS_PARAM_NAME -> "
					+ request
							.getParameter(IReportsConstants.LAU_PROD_GROUP_ASSIGNMENTS_PARAM_NAME));
			createBeansFromXml(request
					.getParameter(IReportsConstants.LAU_PROD_GROUP_ASSIGNMENTS_PARAM_NAME));

			Iterator<LauUserProcodes> itr = this.templates.iterator();
			while (itr.hasNext()) {
				LauUserProcodes lauUserProcodes = itr.next();
				log.info("itr.hasNext() -> " + lauUserProcodes.toString());
				this.userProcodes = null;
				this.userProcodes = lauUserProcodes;
				log.info("lauUserProcodes.LABEL" + lauUserProcodes.getLabel());
				log.info("lauUserProcodes.TYPE" + lauUserProcodes.getType());
				lauUserProcodes.setUserID(UserID);
				log.info("lauUserProcodes.getUserID"
						+ lauUserProcodes.getUserID());

				insert(SQL_INSERT_STRING, template, userId, dt);

			}// end of while(itr.hasNext())

		} else {
			log.info(IReportsConstants.LAU_PROD_GROUP_ASSIGNMENTS_PARAM_NAME
					+ " not found in request");
		}

	}

	private void insert(String sql, SimpleJdbcTemplate template, String userId,
			Timestamp dt) throws Exception {

		int id;
		id = template.update(SQL_INSERT_STRING,
				getInsertParameters(template, userId, dt));
		log.info("LauUserProcodesDAO insert() ID -> " + id);

	}

	private Object[] getInsertParameters(SimpleJdbcTemplate template,
			String userId, java.sql.Timestamp dt) {
		LauUserProcodes lauProCode = this.userProcodes;
		long prodId = CommonDAO.getPrimaryKey(template);
		this.userProcodes.setProdID(prodId);
		log.info("this.userProcodes.setProdID(prodId): "
				+ this.userProcodes.getProdID());
		String prodLabel = lauProCode.getLabel();
		String code = "CODE";
		String grp = "GRP";
		String prodType = lauProCode.getType();
		log.info("prodLabel:: " + prodLabel);
		log.info("prodType:: " + prodType);
		if (prodType.equals(code)) {
			log.info("entered Codetype");
			this.userProcodes.setProdCode(prodLabel);
			this.userProcodes.setProdGroup(null);
		} else if (prodType.equals(grp)) {
			log.info("entered grptype");
			this.userProcodes.setProdCode(null);
			this.userProcodes.setProdGroup(prodLabel);
		}
		log.info("lauProCode.getProdGroup(): "
				+ this.userProcodes.getProdGroup());
		log.info("lauProCode.getProdCode(): " + this.userProcodes.getProdCode());
		return new Object[] { this.userProcodes.getProdID(),
				lauProCode.getUserID(), userId, dt,
				lauProCode.getProdGroup(), lauProCode.getProdCode()

		};
	}

	private void createBeansFromXml(String xml) throws Exception {

		templates = new Vector<LauUserProcodes>();
		String mainXmlTag = "ROWSET/ROW";
		Digester digester = new Digester();
		digester.push(this);
		digester.addObjectCreate(mainXmlTag, LauUserProcodes.class);

		// Explicitly specify property
		digester.addBeanPropertySetter(mainXmlTag + "/label", "label");
		digester.addBeanPropertySetter(mainXmlTag + "/type", "type");

		// Move to next LauReportAttachments
		digester.addSetNext(mainXmlTag, "addXmlData");
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauUserProcodes lauUserProcode) {
		templates.add(lauUserProcode);
		log.info(lauUserProcode.toString());
	}

}
