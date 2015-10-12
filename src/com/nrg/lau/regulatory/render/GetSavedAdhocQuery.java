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
import oracle.jdbc.OracleTypes;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.regulatory.beans.LauGetSavedResultSet;

public class GetSavedAdhocQuery implements View {

	private static Logger log = Logger.getLogger(GetSavedAdhocQuery.class);
	private DataSource ds;
	private String userId = "";
	private String groupId = "";
	private String codeList = "LAU_SAVED_QUERIES";
	private String code = "";
	private String lang = "";
	private StringBuffer valueArr = new StringBuffer();
	private Vector<LauGetSavedResultSet> reports		= null;

	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER -GetSavedAdhocQuery  render()---->");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		userId = CommonDAO.getUSERID(request);		
		PrintWriter pw = response.getWriter();
		log.info("Calling getData()");
		pw.write(getData());

		pw.flush();
		pw.close();

		log.info("EXIT - GetSavedAdhocQuery render()");
	}

	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("GetSavedAdhocQuery -Initialize db template()");
	}
	
	private String getData() throws Exception {

		StringBuffer tmp = new StringBuffer();
		String xml = "";
		Connection con = null;

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
				Reader reader = clob.getCharacterStream();
				CharArrayWriter writer = new CharArrayWriter();
				int i = -1;
				while ((i = reader.read()) != -1) {
					writer.write(i);
				}
				tmp.append(new String(writer.toCharArray()));
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
		xml = tmp.toString();
	//	log.info("XML content nn: "+ xml);
		createBeansFromXml(xml);
		Iterator<LauGetSavedResultSet> itr = this.reports.iterator();
		int i=0;
		while(itr.hasNext())	{
			i++;
			log.info("i---->"+i);
			if(i!=1){
				valueArr.append(",");
			}
			LauGetSavedResultSet lauSavedResult = (LauGetSavedResultSet)itr.next();	
			log.info("getCODE()123---->"+lauSavedResult.getCODE());
			valueArr.append(lauSavedResult.getCODE());			
		}
		log.info("valueArr--->"+valueArr);
		String finalData = findData(valueArr);
		valueArr = new StringBuffer(); 
	//	log.info(finalData);
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

	private String findData(StringBuffer CODE) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		String retStr = "";

		try {
			log.info("inside getData()--> "+CODE);				
			//String sqlStr = "'select * from LAU_QUERIES where QUERY_ID in ("+CODE+")'";
			String sqlStr = "'select q.query_id, q.QUERY_NAME, q.query_permission, q.query_description, q.query_sql, "
					+ "q.query_xml, q.saved_query, q.saved_query, q.top_component_id, q.update_timestamp, "
					+ "q.update_user_id, n.notification_rule_id from LAU_QUERIES q LEFT OUTER JOIN  "
					+ "lau_notification_rules n on q.query_id = n.query_id where q.QUERY_ID in ("+CODE+")'";
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
//		log.info("retStr-------:" + retStr);
		return retStr;

	}

}

