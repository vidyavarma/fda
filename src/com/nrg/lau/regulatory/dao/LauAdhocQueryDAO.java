/**
 * 
 */
package com.nrg.lau.regulatory.dao;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauAdhocQuery;
import com.nrg.lau.commons.IReportChildRtnSetDAO;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.beans.LauRunAdhocQuery;
import com.nrg.lau.security.AppContext;

/**
 * 
 * 
 */
public class LauAdhocQueryDAO implements IReportChildSetDAO<LauAdhocQuery> {
	private static Logger log = Logger.getLogger(LauAdhocQueryDAO.class);
	private Vector<LauAdhocQuery> reports = null;
	private LauAdhocQuery adhocQuery = null;
	private java.sql.Timestamp dt = null;
	private String userId = "";

	public String save(HttpServletRequest request, SimpleJdbcTemplate template,
			String user,java.sql.Timestamp dstamp)
			throws Exception{

		userId = user;
		dt = dstamp;
		log.info("userId---->"+userId);
		log.info("dt---->"+dt);
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_QUERY_SAVE_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_QUERY_SAVE_PARAM_NAME).length() > 0) {
			
			log.info("LauAdhocQueryDAO save() LAU_QUERY_SAVE_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_QUERY_SAVE_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_QUERY_SAVE_PARAM_NAME));
			Iterator<LauAdhocQuery> itr = this.reports.iterator();
			
			//Query Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauAdhocQuery lauAdhocQuery = (LauAdhocQuery)itr.next();
				log.info("itr.hasNext() -> " + lauAdhocQuery.toString());
				this.adhocQuery	= null;
				this.adhocQuery	= lauAdhocQuery;
				
				//-----------				
				ApplicationContext ctx 	= 	AppContext.getApplicationContext();
				DataSource ds	= (DataSource)ctx.getBean("dataSource");
				IReportChildRtnSetDAO<LauRunAdhocQuery> runAdhocQueryDAO = new LauRunAdhocQueryDAO(ds,true);
				log.info("Before rtnQuery --------------------------  " );
				String rtnSqlQuery = (String)runAdhocQueryDAO.save(request, template, 0, user, dstamp);
				log.info("After rtnQuery --------------------------  " + rtnSqlQuery);
				lauAdhocQuery.setQuerySql(rtnSqlQuery);
				if(lauAdhocQuery.getQueryId() == 0)	{					
					    boolean namePrsnt = checkNameExists(template, lauAdhocQuery.getQueryName());
					    if(namePrsnt == false){					    	
						  insert(template);
					    }else{
					    	return ("Name exists");
					    }
				}else   {					
					if(lauAdhocQuery.getTransactionType() == IReportsConstants.deleteFlag)	{						
						delete(template);
					}else	{						
						update(template);					
					}
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_QUERY_SAVE_PARAM_NAME + " not found in request");
		}
		
	    return "Name not exists";
	}

	public void update(SimpleJdbcTemplate template) throws Exception{
		log.info("update----> ");
		int id = 0;
		id = template.update(SQL_UPDATE_QUERY_STRING,getQueryParameters());
		log.info("LauAdhocQueryDAO update() ID -> " + id);	
	}

	public void insert(SimpleJdbcTemplate template)
			throws Exception{
		log.info("insert----> ");
		int id = 0;		
		long queryId = CommonDAO.getPrimaryKey(template);;
		this.adhocQuery.setQueryId(queryId);		
		log.info("Generated Primary Key for QUERY_ID ->" + queryId);
		id = template.update(SQL_INSERT_QUERY_STRING,getQueryParameters());
		log.info("LauAdhocQueryDAO insert() ID ---> " + id);
		
	}
	
	private Object[] getQueryParameters()
	{
		LauAdhocQuery lauAdhocQuery = this.adhocQuery;
		return new Object[]{				
				lauAdhocQuery.getQueryName(),
				lauAdhocQuery.getTopComponentId(),
				lauAdhocQuery.getSavedQuery(),
				lauAdhocQuery.getQueryPermission(),
				lauAdhocQuery.getQueryDescription(),
				lauAdhocQuery.getQueryXml(),
				lauAdhocQuery.getQuerySql(),
				userId,
				dt,
				lauAdhocQuery.getQueryId()
		};
	}

	public void createBeansFromXml(String xml) throws Exception {
		String mainXmlTag = "ROWSET/ROW";
		//reports = new Vector<LauAdhocQuery>();
		reports	= new Vector<LauAdhocQuery>();
		Digester digester = new Digester();
		digester.push(this);
		digester.addObjectCreate(mainXmlTag, LauAdhocQuery.class);

		// Explicitly specify property
		digester.addBeanPropertySetter(mainXmlTag + "/QUERY_ID", "queryId");
		digester.addBeanPropertySetter(mainXmlTag + "/QUERY_NAME", "queryName");
		digester.addBeanPropertySetter(mainXmlTag + "/TOP_COMPONENT_ID","topComponentId");
		digester.addBeanPropertySetter(mainXmlTag + "/SAVED_QUERY", "savedQuery");
		digester.addBeanPropertySetter(mainXmlTag + "/QUERY_PERMISSION", "queryPermission");
		digester.addBeanPropertySetter(mainXmlTag + "/QUERY_DESCRIPTION","queryDescription");
		digester.addBeanPropertySetter(mainXmlTag + "/QUERY_XML", "queryXml");
	//	digester.addBeanPropertySetter(mainXmlTag + "/QUERY_SQL","querySql");
		digester.addBeanPropertySetter(mainXmlTag + "/UPDATE_USER_ID","updateUserId");
		digester.addBeanPropertySetter(mainXmlTag + "/TRANSACTION_TYPE","transactionType");
		// digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP","updateTimeStamp" );

		// Move to next LauReports
		log.info("till here 1---------->");
		digester.addSetNext(mainXmlTag, "addXmlData");
		log.info("till here 2---------->");
		digester.parse(new StringReader(xml));
	}
	
	public void addXmlData(LauAdhocQuery lauAdhocQuery) {
		reports.add(lauAdhocQuery);
	}

	public void delete(SimpleJdbcTemplate template) throws Exception{
		log.info("delete----> ");
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_QUERIES WHERE QUERY_ID = ?",this.adhocQuery.getQueryId());
		log.info("LauAdhocQueryDAO delete() ID -> " + id);		
	}
	
	public boolean findNotfnExists(SimpleJdbcTemplate template, String queryId) throws Exception{
		log.info("findNotfnExists enter----> ");
		boolean notfnPrsnt = false;
		String getQryRsltSet = "SELECT * FROM LAU_NOTIFICATION_RULES where QUERY_ID = ?";
		List<Map<String, Object>> rows = template.queryForList(getQryRsltSet, queryId);
		log.info("Size of list----> "+rows.size());
		int listSize = rows.size();
		if(listSize!=0){
			notfnPrsnt = true;
		}
		return notfnPrsnt;
	}
	
	public boolean checkNameExists(SimpleJdbcTemplate template, String queryName) throws Exception{
		log.info("checkNameExists enter----> ");
		boolean namePrsnt = false;
		String getQryRsltSet = "SELECT * FROM LAU_QUERIES where QUERY_NAME = ?";
		List<Map<String, Object>> rows = template.queryForList(getQryRsltSet, queryName);
		log.info("Size of name list----> "+rows.size());
		int listSize = rows.size();
		if(listSize!=0){
			namePrsnt = true;
		}
		return namePrsnt;
	}
	
	public void delete(SimpleJdbcTemplate template, String queryId) throws Exception{
		log.info("delete from popUp----> ");
		String qryRsltId = "";
		ArrayList<String> arrQryRsltId = new ArrayList<String>();
		int id1 = 0;
		//int id2 = 0;
		//int id3 = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		String getQryRsltIds = "select * from lau_query_result_sets WHERE QUERY_ID = ?";
		List<Map<String, Object>> rows = template.queryForList(getQryRsltIds, queryId);
		for (Map<String, Object> row : rows) {
			qryRsltId = row.get("QUERY_RESULT_SET_ID").toString();
			arrQryRsltId.add(qryRsltId);
		}
		log.info("size---->"+ arrQryRsltId.size());
		for(int i=0;i<arrQryRsltId.size();i++){
			log.info("QryRsltIds---->"+ arrQryRsltId.get(i));
			id1 = template.update("DELETE FROM lau_saved_query_results WHERE query_result_set_id = ?",arrQryRsltId.get(i));
			log.info("delete() ID1 ----> " + id1);
		}
		log.info("queryId0 ----> " + queryId);
		
		int id2 = template.update("DELETE FROM lau_query_result_sets WHERE QUERY_ID = ?",queryId);
		log.info("delete() for lau_query_result_sets ----> " + id2);
		
		log.info("queryId1 ----> " + queryId);
		
		int id3 = template.update("DELETE FROM LAU_QUERIES WHERE QUERY_ID = ?",queryId);
		log.info("delete() ID3 ----> " + id3);
		
		log.info("a ----> ");
		
		/*id2 = template.update("DELETE FROM lau_query_result_sets WHERE QUERY_ID = ?",queryId);
		log.info("delete() ID1 ----> " + id1);
		id3 = template.update("DELETE FROM LAU_QUERIES WHERE QUERY_ID = ?",queryId);
		log.info("delete() ID2 ----> " + id2);		*/
	}
	
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp)
			throws Exception{
		
	}
	
	private final String SQL_UPDATE_QUERY_STRING = "UPDATE LAU_QUERIES SET QUERY_NAME=?,TOP_COMPONENT_ID=?,"
			+ "SAVED_QUERY=?,QUERY_PERMISSION=?,QUERY_DESCRIPTION=?,QUERY_XML=?,"
			+ "QUERY_SQL=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE QUERY_ID=?";
	
	private final String SQL_INSERT_QUERY_STRING = "INSERT INTO LAU_QUERIES (QUERY_NAME,TOP_COMPONENT_ID,"
			+ "SAVED_QUERY,QUERY_PERMISSION,"
			+ "QUERY_DESCRIPTION,QUERY_XML,QUERY_SQL,UPDATE_USER_ID,UPDATE_TIMESTAMP,QUERY_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
	
	@Override
	public void insert(SimpleJdbcTemplate template, long reportId)throws Exception{
		
	}
}
