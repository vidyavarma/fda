package com.nrg.lau.regulatory.render;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.nrg.lau.beans.LauLabTests;
import com.nrg.lau.beans.LauPatientDetails;
import com.nrg.lau.commons.Constants;

import oracle.jdbc.OracleTypes;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.beans.LauGetSavedResultSet;

public class GetSavedResultReportsAction implements View {

	private static Logger log = Logger.getLogger(GetSavedResultReportsAction.class);
	private DataSource ds;
	private String userId = "";
	private String groupId = "";
	private String codeList = "LAU_SAVED_RESULT_SETS";
	private String code = "";
	private String lang = "";
	private Vector<LauGetSavedResultSet> reports		= null;
	LauGetSavedResultSet lauOpenResultSet = null;
	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER -GetSavedResultSetAction  render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		userId = CommonDAO.getUSERID(request);
		groupId = CommonDAO.getUSERGROUPID(request);
	//	userId = "saju";
	//	groupId = "Administration";
		PrintWriter pw = response.getWriter();
		log.info("Calling getData()");
		pw.write(getData());

		pw.flush();
		pw.close();

		log.info("EXIT - GetSavedResultSet render()");
	}

	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetSavedResultSet -Initialize db template()");
	}

	/*
	 * private String getData() {
	 * 
	 * JdbcTemplate jdbcTemplate = new JdbcTemplate(ds); String retStr = "";
	 * 
	 * try { log.info("inside getData() "); retStr = jdbcTemplate
	 * .queryForObject(
	 * "select dbms_xmlgen.getxml('select * from LAU_QUERY_RESULT_SETS') as xmlVal from dual"
	 * , new RowMapper<String>() {
	 * 
	 * @Override public String mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { String strClob = ""; strClob = rs.getString("xmlVal");
	 * return strClob; } });
	 * 
	 * } catch (Exception e) { retStr = ""; log.error(e);
	 * 
	 * } log.info("aaaaaaaaaaaaaaaa:" + retStr); if (retStr.length() == 0) {
	 * retStr = ""; } log.info("$&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:" + retStr);
	 * return retStr;
	 * 
	 * }
	 */

	
	  private String findData(StringBuffer CODE) {
	  
	  JdbcTemplate jdbcTemplate = new JdbcTemplate(ds); 
	  String retStr = "";
	  String sql = "'select * from LAU_QUERY_RESULT_SETS where query_result_set_id in ("+CODE+")'";
	 	try { 
	 		log.info("inside getData() ");
	 		retStr = jdbcTemplate.queryForObject(
	 				"select dbms_xmlgen.getxml("+sql+") as xmlVal from dual", new RowMapper<String>() {
	  
	 		@Override public String mapRow(ResultSet rs, int rowNum) throws SQLException { 
	 			String strClob = ""; 
	 			strClob = rs.getString("xmlVal");
	 			return strClob; 
	 			}
	 		});
	  
	 		} 
	 	catch (Exception e) { 
	 		retStr = ""; 
	 		log.error(e);
	  
	 	}
	 	log.info("aaaaaaaaaaaaaaaa:" + retStr); 
	 	if (retStr== null) {
	 		retStr = ""; 
	 		} 
	 	log.info("$&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:" + retStr);
	 	return retStr;
	 
	  }
	 
	
	private String getData() throws Exception {

		StringBuffer tmp = new StringBuffer();
		StringBuffer valueArr = new StringBuffer();
		String xml = "";
		Connection con = null;
		String finalData = "";

		try {

			con = ds.getConnection();
			String sql = "{call ? := LAU_UTL.codelist(?,?,?,?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2, codeList);
			stmt.setString(3, code);
			stmt.setString(4, lang);
			stmt.setString(5, userId);
			stmt.setString(6, groupId);

			log.info(sql);
			stmt.execute();
			Clob clob = stmt.getClob(1);
			if (clob != null) {
				log.info("inside clob");
				Reader reader = clob.getCharacterStream();
				CharArrayWriter writer = new CharArrayWriter();
				int i = -1;
				while ((i = reader.read()) != -1) {
					writer.write(i);
				}
				tmp.append(new String(writer.toCharArray()));
				
				xml = tmp.toString();
					log.info("XML content: "+ xml);
					createBeansFromXml(xml);
					Iterator<LauGetSavedResultSet> itr = this.reports.iterator();
					while(itr.hasNext())	{					
						LauGetSavedResultSet lauSavedResult = (LauGetSavedResultSet)itr.next();	
						valueArr.append(lauSavedResult.getCODE());
						if(itr.hasNext())
							valueArr.append(",");
					}
					log.info(valueArr);
					finalData = findData(valueArr);
					log.info(finalData);
			}
			else
			{
				finalData = "There are no saved Resultsets";
			}

			stmt.close();
			con.close();

		} catch (SQLException e) {
			log.error(e, e);
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error(e, e);
			}
		}

		return finalData;
		
	}

	public void createBeansFromXml(String xml) throws Exception
	{
		String mainXmlTag = "ROWSET/ROW";
		Digester digester = new Digester();
		reports				= new Vector<LauGetSavedResultSet>();
	
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauGetSavedResultSet.class );
		
		digester.addBeanPropertySetter( mainXmlTag+"/CODE", "CODE" );
		digester.addBeanPropertySetter( mainXmlTag+"/CODE_VALUE", "CODE_VALUE" );
		
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
		
	}

	public void addXmlData(LauGetSavedResultSet lauSavedResultSet) throws Exception {
		reports.add(lauSavedResultSet);
		log.info(lauSavedResultSet.toString());
	}	
}
