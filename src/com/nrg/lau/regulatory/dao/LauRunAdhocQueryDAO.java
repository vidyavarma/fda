package com.nrg.lau.regulatory.dao;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.commons.IReportChildRtnSetDAO;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.beans.LauRunAdhocQuery;
import com.nrg.lau.security.AppContext;
import com.nrg.lau.utils.JSONUtil;

public class LauRunAdhocQueryDAO extends JSONUtil implements
		IReportChildRtnSetDAO<LauRunAdhocQuery> {

	private static Logger log = Logger.getLogger(LauRunAdhocQueryDAO.class);
	private DataSource ds;
	private Vector<LauRunAdhocQuery> reports = null;
	private String queryXML = "";
	private String userId;
	private String groupId="";
	private Timestamp dt;
	private boolean runQuery;

	public LauRunAdhocQueryDAO(DataSource ds) {
		super();
		this.ds = ds;
		log.info("Hello" + ds);
	}

	public LauRunAdhocQueryDAO(DataSource ds, boolean runQuery) {
		super();
		this.ds = ds;
		log.info("Hello" + ds);
		this.runQuery = runQuery;
	}

	@Override
	public Object save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, String user, Timestamp dstamp) throws Exception {

		// Check to make sure that XML is there in Request.
		if (request.getParameter(IReportsConstants.LAU_ADHOC_QUERY_PARAM_NAME) != null
				&& request.getParameter(
						IReportsConstants.LAU_ADHOC_QUERY_PARAM_NAME).length() > 0) {

			log.info("LauRunAdhocQueryDAO save() LAU_ADHOC_QUERY_PARAM_NAME -> "
					+ request
							.getParameter(IReportsConstants.LAU_ADHOC_QUERY_PARAM_NAME));

			userId = user;
			groupId = CommonDAO.getUSERGROUPID(request);
			// userId ="PRIMOADMIN";
			dt = dstamp;

			// Create LauRunAdhocQuery beans from XML Request
			createBeansFromXml(request
					.getParameter(IReportsConstants.LAU_ADHOC_QUERY_PARAM_NAME));
			queryXML = request
					.getParameter(IReportsConstants.LAU_ADHOC_QUERY_PARAM_NAME);
			return process(template, reportId,request );

		} else {
			log.info(IReportsConstants.LAU_ADHOC_QUERY_PARAM_NAME
					+ " not found in request");
		}

		return null;

	}

	public String process(SimpleJdbcTemplate template, long reportId, HttpServletRequest request )
			throws Exception {

		StringBuffer tmp = new StringBuffer();
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;

		PreparedStatement psCompGroups = null;

		ResultSet rs = null;
		ApplicationContext ctx 	= 	AppContext.getApplicationContext();
		com.nrg.lau.dao.SharedConnectionDAO shConnection  	= 	(com.nrg.lau.dao.SharedConnectionDAO)ctx.getBean("getSharedConnection");
		Connection con 		= shConnection.getConVerify();
		String prevQueryId = "";
		try {

			long queryID ;
	
			if(request.getParameter("previousqueryid") != null)	{
				prevQueryId  = request.getParameter("previousqueryid").toString();
			}

			queryID = CommonDAO.getUniqueId(template);
			log.info("Generated Primary Key for queryID  -> " + queryID);


			// ((OracleConnection)con).setSessionTimeZone(TimeZone.getDefault().getID());
			con.setAutoCommit(false);

			String sql1 = "INSERT INTO LAU_QUERIES_T (QUERY_ID,TOP_COMPONENT_ID,QUERY_XML,UPDATE_USER_ID,UPDATE_TIMESTAMP) VALUES (?,?,?,?,?)";
			String sql2 = "INSERT INTO LAU_QUERY_COMPONENTS_T(COMPONENT_ID,DISPLAY_NUMBER,LINKING,UPDATE_USER_ID,UPDATE_TIMESTAMP) VALUES (?,?,?,?,?)";
			String sql3 = "INSERT INTO LAU_QUERY_CONDITIONS_T (CONDITION_ID,COMPONENT_ID,FIELD_ID,DISPLAY_NUMBER,"
					+ "OPERATOR,EXPRESSION_1,EXPRESSION_2,UPDATE_USER_ID,UPDATE_TIMESTAMP) VALUES (?,?,?,?,?,?,?,?,?)";
			String sqlCompGroups = "INSERT INTO LAU_QUERY_COMPONENT_GROUPS_T (COMPONENT_ID,CHILD_COMPONENT_ID,UPDATE_USER_ID,UPDATE_TIMESTAMP)  VALUES (?,?,?,?)";

			ps1 = con.prepareStatement(sql1);
			ps2 = con.prepareStatement(sql2);
			ps3 = con.prepareStatement(sql3);
			psCompGroups = con.prepareStatement(sqlCompGroups);

			HashMap<String, String> groupMap = new HashMap<String, String>();

			log.info("------------------------------------------------");
			Iterator<LauRunAdhocQuery> itr = this.reports.iterator();
			while (itr.hasNext()) {

				LauRunAdhocQuery lauRunAdhocQuery = itr.next();
				log.info(lauRunAdhocQuery.getGROUP_ID() + " -- "
						+ lauRunAdhocQuery.getDB_FIELD_NAME());
				int rtn;

				if (lauRunAdhocQuery.getTYPE().trim().equalsIgnoreCase("GROUP")
						&& lauRunAdhocQuery.getLEVEL().trim()
								.equalsIgnoreCase("0")) {
					// LAU_QUERIES_T
					long componentID = CommonDAO.getUniqueId(template);
					log.info("Generated Primary Key for componentID  -> "
							+ componentID);
					groupMap.put(lauRunAdhocQuery.getGROUP_ID(),
							Long.toString(componentID));

					ps1.setLong(1, queryID);
					ps1.setLong(2, componentID);
					ps1.setObject(3, queryXML);
					ps1.setString(4, userId);
					ps1.setObject(5, dt);
					ps1.addBatch();
					log.info("$$$$$$$$$$$$$$$query parameters:"+queryID + ","+componentID+ ","+queryXML+ ","+userId+ ","+dt);
					// ----------Second insert
					// LAU_QUERY_COMPONENTS_T
					ps2.setLong(1, componentID);
					ps2.setInt(2, 1);
					ps2.setString(3, lauRunAdhocQuery.getOPERATOR());
					ps2.setString(4, userId);
					ps2.setObject(5, dt);
					ps2.addBatch();
					log.info("$$$$$$$$$$$$$$$COMPONENT parameters:"+componentID + ","+1+ ","+lauRunAdhocQuery.getOPERATOR()+ ","+userId+ ","+dt);

				} else if (lauRunAdhocQuery.getSELECTED().trim()
						.equalsIgnoreCase("TRUE")) {
					if (lauRunAdhocQuery.getTYPE().trim()
							.equalsIgnoreCase("SUBGROUP")) {
						// LAU_QUERY_COMPONENT_GROUPS_T
						long subComponentID = CommonDAO.getUniqueId(template);
						groupMap.put(lauRunAdhocQuery.getGROUP_ID(),
								Long.toString(subComponentID));
						log.info("aaaa:"
								+ groupMap.get(lauRunAdhocQuery
										.getPARENTGROUP()));
						psCompGroups
								.setString(1, groupMap.get(lauRunAdhocQuery
										.getPARENTGROUP()));
						psCompGroups.setLong(2, subComponentID);
						psCompGroups.setString(3, userId);
						psCompGroups.setObject(4, dt);
						psCompGroups.addBatch();
						// /
						// LAU_QUERY_COMPONENTS_T
						ps2.setLong(1, subComponentID);
						ps2.setInt(2, 1);
						ps2.setString(3, lauRunAdhocQuery.getOPERATOR());
						ps2.setString(4, userId);
						ps2.setObject(5, dt);
						ps2.addBatch();

					} else {
						// LAU_QUERY_CONDITIONS_T
						long conditionID = CommonDAO.getUniqueId(template);
						ps3.setLong(1, conditionID);
						log.info("bbbbb:"
								+ groupMap.get(lauRunAdhocQuery
										.getPARENTGROUP()));
						ps3.setString(2,
								groupMap.get(lauRunAdhocQuery.getPARENTGROUP()));
						ps3.setString(3, lauRunAdhocQuery.getFIELD_ID());
						ps3.setString(4, lauRunAdhocQuery.getDISPLAY_NUMBER());
						ps3.setString(5, lauRunAdhocQuery.getOPERATOR());
						ps3.setString(6, lauRunAdhocQuery.getVALUE());
						ps3.setString(7, lauRunAdhocQuery.getVALUE2());
						ps3.setString(8, userId);
						ps3.setObject(9, dt);
						ps3.addBatch();
						log.info("$$$$$$$$$$$$$$$$$$LAU_QUERY_CONDITIONS_T parameters:"+conditionID + ","+groupMap.get(lauRunAdhocQuery.getPARENTGROUP())
						+ ","+lauRunAdhocQuery.getFIELD_ID()+ ","+lauRunAdhocQuery.getDISPLAY_NUMBER()+ ","+ lauRunAdhocQuery.getOPERATOR()+ ","+lauRunAdhocQuery.getVALUE()+
						","+lauRunAdhocQuery.getVALUE2() +	userId+ ","+dt);
					}
				}
			}

			log.info(" Start Batch execute------------------------------------------------");

			int[] rtn1 = ps1.executeBatch();
			ps1.close();
			log.info(" END executeBatch 1------------------------------------------------:");
			int[] rtn2 = ps2.executeBatch();
			ps2.close();
			log.info(" END executeBatch 2------------------------------------------------:");

			int[] rtn3 = ps3.executeBatch();
			ps3.close();
			log.info(" END executeBatch 3------------------------------------------------:");
			int[] rtn4 = psCompGroups.executeBatch();
			psCompGroups.close();

			log.info(" END executeBatch 4------------------------------------------------:");
			rs = null;
			log.info(" Start  LAU_QueryBuilder.ExecuteQuery------------------------------------------------");
			/**---------------------------------------------------------------
			 delete prev data
			 */
			try {
				if (prevQueryId.length() > 0)
				{
					log.info(" start call LAU_LIST.cleanupQuery with prevQueryId------------------------------------------------:"+prevQueryId);
					String sql = "{? = call LAU_LIST.cleanupQuery(?)}";
					CallableStatement stmt = con.prepareCall(sql);

					stmt.registerOutParameter(1, OracleTypes.VARCHAR);
					stmt.setString(2, prevQueryId);
					stmt.execute();
					stmt.close();
					log.info(" end call LAU_LIST.cleanupQuery with prevQueryId------------------------------------------------:"+prevQueryId);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//---------------------------------------------------------------	
			String sql = "{? = call LAU_LIST.submitQuery(?,?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.VARCHAR);
			stmt.setLong(2, queryID);
			stmt.registerOutParameter(3, OracleTypes.VARCHAR);
			stmt.setString(4, userId);
			stmt.setString(5, groupId);
			log.info ("queryID, userId, groupId:" + queryID +", "+userId + ", "+groupId);
			log.info(sql);
			stmt.execute();

			if (runQuery) {

				tmp.append(String.valueOf(stmt.getObject(3)));
			} else {
				log.info(stmt.getObject(3));
				log.info(stmt.getObject(1));
					
				String qryCount =  (String) stmt.getObject(1);
				JSONArray jsonArray = new JSONArray();
				JSONObject obj = new JSONObject();
				obj.append("TOTAL_RECORDS", qryCount);
				obj.append("QUERY_ID", queryID);
				jsonArray.put(obj);
				tmp.append(jsonArray.toString());
				log.info("RETURNED STRING........... :"+tmp);
			}
			stmt.close();
			//con.close();
			log.info(" END   LAU_LIST.submitQuery------------------------------------------------");
		} catch (Exception e) {
			log.error("IN --" + e);
			//con.rollback();
			log.error("IN -- Rollback---");
			throw new Exception(e);
		} finally {
			try {
				try {
					if (ps1 != null)
						ps1.close();
				} catch (Exception e) {
					log.error(e, e);
				}
				try {
					if (rs != null)
						rs.close();
				} catch (Exception e) {
					log.error(e, e);
				}

			} catch (Exception e) {
				log.error("IN 22 -- " + e);
			}
		}

		return tmp.toString();
	}

	@Override
	public void createBeansFromXml(String xml) throws Exception {

		String mainXmlTag = "ROWSET/ROW";
		reports = new Vector<LauRunAdhocQuery>();
		Digester digester = new Digester();
		digester.push(this);
		digester.addObjectCreate(mainXmlTag, LauRunAdhocQuery.class);

		// Explicitly specify property
		digester.addBeanPropertySetter(mainXmlTag + "/GROUP_ID", "GROUP_ID");
		digester.addBeanPropertySetter(mainXmlTag + "/GROUP_NAME", "GROUP_NAME");
		digester.addBeanPropertySetter(mainXmlTag + "/PARENTGROUP",
				"PARENTGROUP");
		digester.addBeanPropertySetter(mainXmlTag + "/LEVEL", "LEVEL");
		digester.addBeanPropertySetter(mainXmlTag + "/OPERATOR", "OPERATOR");
		digester.addBeanPropertySetter(mainXmlTag + "/TYPE", "TYPE");
		digester.addBeanPropertySetter(mainXmlTag + "/DB_FIELD_NAME",
				"DB_FIELD_NAME");
		digester.addBeanPropertySetter(mainXmlTag + "/DISPLAY_NUMBER",
				"DISPLAY_NUMBER");
		digester.addBeanPropertySetter(mainXmlTag + "/FIELD_ID", "FIELD_ID");
		digester.addBeanPropertySetter(mainXmlTag + "/SELECTED", "SELECTED");
		digester.addBeanPropertySetter(mainXmlTag + "/VALUE", "VALUE");
		digester.addBeanPropertySetter(mainXmlTag + "/VALUE2", "VALUE2");

		digester.addSetNext(mainXmlTag, "addXmlData");
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauRunAdhocQuery lauRunAdhocQuery) {
		reports.add(lauRunAdhocQuery);
	}

	@Override
	public void update(SimpleJdbcTemplate template) throws Exception {

	}

	@Override
	public void delete(SimpleJdbcTemplate template) throws Exception {

	}

	@Override
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
	}

}
