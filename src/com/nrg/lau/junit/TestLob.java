package com.nrg.lau.junit;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.lob.LobHandler;

import com.nrg.lau.beans.LauJobScheduler;

public class TestLob extends TestCase {
	private static Logger log	= Logger.getLogger(TestLob.class);
	public void testCodeList()	{
ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("junitdispatcher-servlet.xml");
		
		//LauReportsSetTransactionManagerJUnit.runReports(ctx);
		log.info("xxxxxxxxxxx");

		DataSource dataSource = (DataSource) ctx.getBean("dataSource");
	    //DataSource mysqlDataSource = (DataSource) ac.getBean("mysqlDataSource");

	    final LobHandler lobHandler = (LobHandler) ctx.getBean("lobHandler");
	    log.info("yyyy");
	    JdbcTemplate jdbcTemplate= new JdbcTemplate(dataSource);    
	    SimpleJdbcTemplate simpleJdbcTemplate= new SimpleJdbcTemplate(dataSource);  

	    
	   /* List<Map<String, Object>> list = jdbcTemplate.query("select DATA_TEMPLATE from LAU_DATA_TEMPLATES where DATA_TEMPLATE_ID = ?", new Object[]{"7"},
	            new RowMapper<Map<String, Object>>() {
	              public Map<String, Object> mapRow(ResultSet rs, int i) throws SQLException {
	                Map<String, Object> results = new HashMap<String, Object>();
	                String clobText = lobHandler.getClobAsString(rs, "DATA_TEMPLATE");                                                
	                results.put("CLOB", clobText);
//	                byte[] blobBytes = lobHandler.getBlobAsBytes(rs, "a_blob");                                                
//	                results.put("BLOB", blobBytes);
	                log.info(clobText);
	                return results;
	              }
	            });
//	    
	
	    String ackStr = jdbcTemplate.queryForObject(
	            "select XML_CONTENT_CLOB from LAU_E2B_EXCHANGE_HISTORY where E2B_EXCHANGE_ID  = ?",
	            new Object[]{4820},
	            new RowMapper<String>() {
	                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	                    String strClob = "";
	                    strClob = lobHandler.getClobAsString(rs,"XML_CONTENT_CLOB");
	                    return strClob;
	                }
	            });
	    log.info("dddddddddd   ::"+ackStr );
	 */   
	    String sql = "SELECT E2B_EXCHANGE_ID,E2B_XML_FILE_NAME,E2B_MESSAGE_TYPE,XML_CONTENT_CLOB,E2B_PARTNER_ID,E2B_MESSAGENUMB " +
				"FROM LAU_E2B_EXCHANGE_HISTORY WHERE TASK_ID = " + 4819 + 
				" AND E2B_MESSAGE_TYPE != 'MULTI_CASES' AND REF_E2B_MESSAGENUMB = 'MSG-0421-FIRST-1SAJ'";
		
	    log.info("11111111111111111111111");
		 List<LauJobScheduler> results = simpleJdbcTemplate.query(sql, new RowMapper<LauJobScheduler>() {
		      public LauJobScheduler mapRow(ResultSet rs, int i) throws SQLException {
		    	  log.info("2222222222222");
		    	  LauJobScheduler jobScheduler = new LauJobScheduler();
		    	  jobScheduler.setE2B_EXCHANGE_ID(rs.getLong("E2B_EXCHANGE_ID"));
		    	  jobScheduler.setE2B_XML_FILE_NAME(rs.getString("E2B_XML_FILE_NAME"));
		    	  jobScheduler.setE2B_MESSAGE_TYPE(rs.getString("E2B_MESSAGE_TYPE")); 
		    	 jobScheduler.setXML_CONTENT_CLOB(clobToString(rs.getClob("XML_CONTENT_CLOB")));
		    	  jobScheduler.setE2B_PARTNER_ID(rs.getString("E2B_PARTNER_ID")); 
		    	  jobScheduler.setE2B_MESSAGENUMB(rs.getString("E2B_MESSAGENUMB")); 
		    	  log.info("555555555555555");
		    	  return jobScheduler;
		      }
		      private String clobToString(java.sql.Clob data) {
		  		
		  	    final StringBuilder sb = new StringBuilder();
		  	    try {
		  	        
		  	    	final Reader reader = data.getCharacterStream();
		  	        final BufferedReader br = new BufferedReader(reader);

		  	        int b;
		  	        while(-1 != (b = br.read()))	{
		  	            sb.append((char)b);
		  	        }
		  	        br.close();
		  	    
		  	    }	catch (Exception e)
		  	    {
		  	        log.error("SQL. Could not convert CLOB to string ", e);
		  	        return e.toString();
		  	    }	    

		  	    return sb.toString();
		  	}
		 });	 
		
	}
	

}

/**
Querying (SELECT)

---------------------Here is a simple query for getting the number of rows in a relation:

int rowCount = this.jdbcTemplate.queryForInt("select count(*) from t_actor");
A simple query using a bind variable:

int countOfActorsNamedJoe = this.jdbcTemplate.queryForInt(
        "select count(*) from t_actor where first_name = ?", "Joe");
Querying for a String:

String lastName = this.jdbcTemplate.queryForObject(
        "select last_name from t_actor where id = ?", 
        new Object[]{1212L}, String.class);
---------------------------Querying and populating a single domain object:

Actor actor = this.jdbcTemplate.queryForObject(
        "select first_name, last_name from t_actor where id = ?",
        new Object[]{1212L},
        new RowMapper<Actor>() {
            public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
                Actor actor = new Actor();
                actor.setFirstName(rs.getString("first_name"));
                actor.setLastName(rs.getString("last_name"));
                return actor;
            }
        });
--------------------Querying and populating a number of domain objects:

List<Actor> actors = this.jdbcTemplate.query(
        "select first_name, last_name from t_actor",
        new RowMapper<Actor>() {
            public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
                Actor actor = new Actor();
                actor.setFirstName(rs.getString("first_name"));
                actor.setLastName(rs.getString("last_name"));
                return actor;
            }
        });
        
----------------------------------------------------
-----------------------------------------------------
public List<Actor> findAllActors() {
    return this.jdbcTemplate.query( "select first_name, last_name from t_actor", new ActorMapper());
}

private static final class ActorMapper implements RowMapper<Actor> {

    public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Actor actor = new Actor();
        actor.setFirstName(rs.getString("first_name"));
        actor.setLastName(rs.getString("last_name"));
        return actor;
    }        
}
**/