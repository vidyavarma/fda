package com.nrg.lau.reports;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import oracle.jdbc.OracleTypes;

public class LauFOReportUtil {
	
	private static Logger log = Logger.getLogger(LauFOReportUtil.class);
	
	public Reader getReportXML(String parameter, String sql, 
			DataSource ds) throws Exception{
		
		Connection con	= null;
		Reader reader 	= null;
		try {
			
			con = ds.getConnection();
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2, parameter);
			log.info(sql);
			stmt.execute();
			Clob clob = stmt.getClob(1);
			if (clob != null)
				reader = clob.getCharacterStream();

			stmt.close();
			con.close();

		} catch (SQLException e) {
			log.error(e, e);	
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			log.error(e, e);
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error(e, e);
			}
		}
		
		return reader;
	}
	
	public InputStream getLayoutTemplate(Connection con, String sql) throws Exception{
		
		InputStream in 	= null;		
		try {					
					
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs != null)
			{
				while(rs.next())
				{
					Clob clob = rs.getClob(1);				
					in = clob.getAsciiStream();
				}	
			}
			
		} catch (SQLException e) {
			log.error(e, e);
			throw new Exception("Layout Template - " + e.getMessage());
		} catch (Exception e) {
			log.error(e, e);
			throw new Exception("Layout Template - " + e.getMessage());
		}
		return in;
	}
	
	public InputStream getDataTemplate(Connection con, String sql) throws Exception{
		
		InputStream in 	= null;		
		try {
				
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			if(rs != null)
			{
				while(rs.next())
				{
					Clob clob = rs.getClob(1);				
					in = clob.getAsciiStream();
				}	
			}
		} catch (SQLException e) {
			log.error(e, e);
			throw new Exception("Data Template - " + e.getMessage());
		} catch (Exception e) {
			log.error(e, e);
			throw new Exception("Data Template - " + e.getMessage());
		}
		return in;
	}
}
