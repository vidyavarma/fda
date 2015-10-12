package com.nrg.lau.regulatory.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.commons.IReportChildRtnSetDAO;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.beans.LauResultSet;
import com.nrg.lau.security.AppContext;
import com.nrg.lau.utils.JSONUtil;

public class LauSaveResultSetDAO extends JSONUtil implements
		IReportChildRtnSetDAO<LauResultSet> {

	private static Logger log = Logger.getLogger(LauSaveResultSetDAO.class);

	private String userId;
	private Timestamp dt;
	private String sqlChild = "INSERT INTO LAU_SAVED_QUERY_RESULTS (QUERY_RESULT_SET_ID, REPORT_ID, REPORT_PICKED_FLAG, UPDATE_USER_ID, UPDATE_TIMESTAMP) VALUES (?,?,?,?,?)";
	private String sqlParent = "INSERT INTO LAU_QUERY_RESULT_SETS (QUERY_RESULT_SET_ID,QUERY_ID,RESULT_SET_NAME,"
			+ "RESULT_SET_DESCRIPTION,CREATE_USER_ID, CREATE_TIMESTAMP, UPDATE_USER_ID, UPDATE_TIMESTAMP,RESULT_SET_PERMISSION) VALUES (?,?,?,?,?,?,?,?,?)";

	public String save(HttpServletRequest request, SimpleJdbcTemplate template,
			Timestamp dstamp) {
		userId = CommonDAO.getUSERID(request);
		String success = "";
		try {
			String resultsetname = "";
			String resultdesc = "";
			String reportids = "";
			String repFlag = "";
			String resultPermission = "";
			String createUserId = "";
			String resultSetID = "";
			String transactionType = "";
			String queryId = "";
			String resultSetMode = "";

			resultsetname = request.getParameter("resultName");
			resultdesc = request.getParameter("resultDesc");
			reportids = request.getParameter("reprtIds");
			resultPermission = request.getParameter("resultPermn");
			repFlag = request.getParameter("reportPickedFlag");
			createUserId = request.getParameter("createUserID");
			transactionType = request.getParameter("transactionType");
			resultSetID = request.getParameter("resultSetID");
			String TT = String.valueOf(IReportsConstants.deleteFlag);
			queryId = request.getParameter("queryID");
			resultSetMode = request.getParameter("resultSetMode");
			String resultCharts = "charts";
			log.info("queryId: " + queryId);
			if (queryId == null) {
				log.info("queryID set to null");
				queryId = "";
			}

			log.info("transactiontype: " + transactionType);
			if (transactionType.equals(TT)) { // DELETE
				log.info("DELETE*****************");
				success = deleteData(resultSetID, template);
			} else { // INSERT
				log.info("resultSetID------>" + resultSetID);
				if (transactionType.equals(String.valueOf(1))) {
					log.info("INSERT*******************");
					Boolean namePrsnt = checkNameExists(template, resultsetname);
					if (namePrsnt == false) {
						if (resultSetMode.equals("charts")) {
							success = insertChartData(resultsetname,
									resultdesc, resultPermission, repFlag,
									queryId, template, dstamp);
						} else {
							success = insertData(resultsetname, resultdesc,
									resultPermission, reportids, repFlag,
									queryId, template, dstamp);
						}
					} else {
						success = "exists";
					}
				} else {
					log.info("UPDATE*******************");
					success = updateData(resultSetID, resultsetname,
							resultdesc, resultPermission, reportids, repFlag,
							queryId, template, dstamp);
				}
			}
		} catch (Exception e) {
			log.info(e);
		}
		return success;
	}

	private String insertChartData(String resultSetName, String resultdesc,
			String resultPermission, String repFlag, String queryId,
			SimpleJdbcTemplate template, Timestamp dstamp) throws Exception {

		log.info("Insert Chart!!!!");
		String success = "";
		CallableStatement stmtPar = null;
		CallableStatement stmtChild = null;
		ApplicationContext ctx = AppContext.getApplicationContext();
		com.nrg.lau.dao.SharedConnectionDAO shConnection = (com.nrg.lau.dao.SharedConnectionDAO) ctx
				.getBean("getSharedConnection");
		Connection con = shConnection.getConVerify();
		try {
			log.info("Chart QuerID: " + queryId);
			String chartParentSql = "{call ? := LAU_LIST.saveResultSet(?,?,?,?,?)}";
			stmtPar = con.prepareCall(chartParentSql);
			stmtPar.registerOutParameter(1, OracleTypes.VARCHAR);
			stmtPar.setString(2, queryId);
			stmtPar.setString(3, resultSetName);
			stmtPar.setString(4, resultPermission);
			stmtPar.setString(5, resultdesc);
			stmtPar.setString(6, userId);

			log.info("parSQL  " + chartParentSql);

			Object s = stmtPar.execute(); // Function executed here
			log.info("function executed successfully!!!");
			if (s != null)
				success = "-2";

			log.info("reached the end of chart insert = " + success);
			stmtPar.close();
		} catch (Exception e) {
			log.error("IN --" + e);
			con.rollback();
			log.error("IN -- Rollback---");
			throw new Exception(e);
		} finally {
			try {
				try {
					if (stmtPar != null)
						stmtPar.close();
				} catch (Exception e) {
					log.error(e, e);
				}

				try {
					if (stmtChild != null)
						stmtChild.close();
				} catch (Exception e) {
					log.error(e, e);
				}
			} catch (Exception e) {
				log.error("Error -- " + e);
			}

		}
		return success;
	}

	public String deleteData(String resultSetID, SimpleJdbcTemplate template) {
		int id = 0, idPar = 0;
		CommonDAO.setDbmsClientInfo(template, userId);
		log.info("******************ENTERED DELETE");
		id = template
				.update("DELETE FROM LAU_SAVED_QUERY_RESULTS WHERE QUERY_RESULT_SET_ID = ?",
						resultSetID);
		idPar = template
				.update("DELETE FROM LAU_QUERY_RESULT_SETS WHERE QUERY_RESULT_SET_ID = ?",
						resultSetID);

		log.info("LauSaveResultSetDAO delete() ID Child -> " + id
				+ " Parent -> " + idPar);
		return String.valueOf(idPar);
	}

	public String insertData(String resultsetname, String resultdesc,
			String resultPermission, String reportids, String repFlag,
			String queryID, SimpleJdbcTemplate template, Timestamp dstamp)
			throws Exception {
		log.info("insert----> ");
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ApplicationContext ctx = AppContext.getApplicationContext();
		com.nrg.lau.dao.SharedConnectionDAO shConnection = (com.nrg.lau.dao.SharedConnectionDAO) ctx
				.getBean("getSharedConnection");
		Connection con = shConnection.getConVerify();
		String success = "";
		// queryID = "";
		try {
			long queryRsltID = CommonDAO.getUniqueId(template);

			log.info("Generated Primary Key for save resultset  -> "
					+ queryRsltID);

			log.info("queryRsltID------------" + queryRsltID);
			log.info("queryID----------------" + queryID);
			log.info("resultsetname----------" + resultsetname);
			log.info("resultdesc-------------" + resultdesc);
			log.info("resultPermission-------------" + resultPermission);
			log.info("userId-----------------" + userId);
			log.info("dt---------------------" + dstamp);
			log.info("queryRsltID------------" + queryRsltID);

			ps1 = con.prepareStatement(sqlParent);
			ps1.setLong(1, queryRsltID);
			ps1.setString(2, "");
			ps1.setString(3, resultsetname);
			ps1.setString(4, resultdesc);
			ps1.setString(5, userId);
			ps1.setObject(6, dstamp);
			ps1.setString(7, userId);
			ps1.setObject(8, dstamp);
			ps1.setString(9, resultPermission);
			ps1.addBatch();

			ps2 = con.prepareStatement(sqlChild);

			String strReportIds = reportids;
			String[] reportIdArray = strReportIds.split(",");

			for (int i = 0; i < reportIdArray.length; i++) {
				String rprtId = reportIdArray[i];
				Long rptId = Long.valueOf(rprtId);
				// log.info(" rptId-----------"+rptId);
				ps2.setLong(1, queryRsltID);
				ps2.setLong(2, rptId);
				ps2.setString(3, repFlag);
				ps2.setString(4, userId);
				ps2.setObject(5, dstamp);
				ps2.addBatch();
			}

			log.info(" Start Batch execute------------------------------------------------");
			int[] rtn1 = ps1.executeBatch();
			log.info(" rtn1---------" + rtn1);
			ps1.close();
			log.info(" END executeBatch 1------------------------------------------------:");

			int[] rtn2 = ps2.executeBatch();

			log.info(" rtn2 length---------" + rtn2.length);
			log.info(" rtn2 data##### " + rtn2[0]);
			success = Integer.toString(rtn2[0]);

			ps2.close();
			log.info(" END executeBatch 2------------------------------------------------:");
			con.commit();
		} catch (Exception e) {
			log.error("IN --" + e);
			con.rollback();
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
					if (ps2 != null)
						ps2.close();
				} catch (Exception e) {
					log.error(e, e);
				}

				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error("Error -- " + e);
			}

		}
		return success;
	}

	public Boolean checkNameExists(SimpleJdbcTemplate template, String rsltName)
			throws Exception {
		log.info("checkNameExists enter----> ");
		boolean namePrsnt = false;
		String getRsltSet = "SELECT * FROM LAU_QUERY_RESULT_SETS WHERE RESULT_SET_NAME = ?";
		List<Map<String, Object>> rows = template.queryForList(getRsltSet,
				rsltName);
		log.info("Size of name list----> " + rows.size());
		int listSize = rows.size();
		if (listSize != 0) {
			namePrsnt = true;
		}
		return namePrsnt;
	}

	public String updateData(String resultSetID, String resultsetname,
			String resultdesc, String resultPermission, String reportids,
			String repFlag, String queryID, SimpleJdbcTemplate template,
			Timestamp dstamp) throws Exception {
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ApplicationContext ctx = AppContext.getApplicationContext();
		com.nrg.lau.dao.SharedConnectionDAO shConnection = (com.nrg.lau.dao.SharedConnectionDAO) ctx
				.getBean("getSharedConnection");
		Connection con = shConnection.getConVerify();
		String success = "";
		// queryID = "";
		try {

			int idchild = 0;

			String sqlParent = "UPDATE LAU_QUERY_RESULT_SETS SET QUERY_ID=?,RESULT_SET_NAME=?,"
					+ "RESULT_SET_DESCRIPTION=?,CREATE_USER_ID=?, CREATE_TIMESTAMP=?, UPDATE_USER_ID=?, UPDATE_TIMESTAMP=?, RESULT_SET_PERMISSION=? WHERE QUERY_RESULT_SET_ID=?";

			log.info("queryRsltID------------" + resultSetID);
			log.info("queryID----------------" + queryID);
			log.info("resultsetname----------" + resultsetname);
			log.info("resultdesc-------------" + resultdesc);
			log.info("resultPermission-------------" + resultPermission);
			log.info("userId-----------------" + userId);
			log.info("dt---------------------" + dstamp);

			ps1 = con.prepareStatement(sqlParent);
			ps1.setString(1, queryID);
			ps1.setString(2, resultsetname);
			ps1.setString(3, resultdesc);
			ps1.setString(4, userId);
			ps1.setObject(5, dstamp);
			ps1.setString(6, userId);
			ps1.setObject(7, dstamp);
			ps1.setString(8, resultPermission);
			ps1.setString(9, resultSetID);
			ps1.addBatch();

			CommonDAO.setDbmsClientInfo(template, userId);
			log.info("******************DELETING Saved ResultSet");
			idchild = template
					.update("DELETE FROM LAU_SAVED_QUERY_RESULTS WHERE QUERY_RESULT_SET_ID = ?",
							resultSetID);
			log.info("LauSaveResultSetDAO delete() ID Child -> " + idchild);

			// INSERTING THE SavedResultSet Content

			ps2 = con.prepareStatement(sqlChild);
			String strReportIds = reportids;
			String[] reportIdArray = strReportIds.split(",");

			for (int i = 0; i < reportIdArray.length; i++) {
				String rprtId = reportIdArray[i];
				Long rptId = Long.valueOf(rprtId);
				// log.info(" rptId-----------"+rptId);
				ps2.setString(1, resultSetID);
				ps2.setLong(2, rptId);
				ps2.setString(3, repFlag);
				ps2.setString(4, userId);
				ps2.setObject(5, dstamp);
				ps2.addBatch();
			}

			log.info(" Start Batch execute------------------------------------------------");
			int[] rtn1 = ps1.executeBatch();
			ps1.close();
			log.info(" END executeBatch 1------------------------------------------------:");

			log.info(" START executeBatch Child------------------------------------------------:");
			int[] rtn2 = ps2.executeBatch();
			log.info(" Update length---------" + rtn2.length);
			log.info(" Update data##### " + rtn2[0]);
			success = Integer.toString(rtn2[0]);
			ps2.close();
			log.info(" END executeBatch 2Child------------------------------------------------:");
		} catch (Exception e) {
			log.error("IN --" + e);
			con.rollback();
			log.error("IN -- Rollback---");
			throw new Exception(e);
		} finally {
			try {
				try {
					if (ps2 != null)
						ps2.close();
				} catch (Exception e) {
					log.error(e, e);
				}
			} catch (Exception e) {
				log.error("Error -- " + e);
			}

		}
		return success;
	}

	@Override
	public Object save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, String user, Timestamp dstamp) throws Exception {
		return null;
	}

	@Override
	public void createBeansFromXml(String xml) throws Exception {
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
