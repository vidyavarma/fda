package com.nrg.lau.security;

import java.io.CharArrayWriter;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class GenericLoader {
	
	private static Logger log = Logger.getLogger(GenericLoader.class);
	
	public void init() { 
		try {
			this.meddra 		= getDBFile("MEDDRA-LLT-XML");
			this.countryData 	= getDBFile("COUNTRY-CODES-XML");
			log.info("Meddra and Country file loaded");
		}catch (Exception e) {
			log.error(e);
			log.error("Meddra/Country file not loaded");
		}
	}

	private StringBuffer meddra;
	private StringBuffer countryData;
	
	public StringBuffer getCountryData() {
		return countryData;
	}

	public void setCountryData(StringBuffer countryData) {
		this.countryData = countryData;
	}

	private DataSource ds;

	public StringBuffer getMeddra() {
		return meddra;
	}

	public void setMeddra(StringBuffer meddra) {
		this.meddra = meddra;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;	
	}
	
	public StringBuffer getDBFile(String param) throws Exception{
		
		log.info("START *******");
		StringBuffer tmp 	= new StringBuffer();
		Connection con	= null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		try {
			String sql = "SELECT DATA_FILE FROM LAU_E2BVIEWER_DATA WHERE " +
					"DATA_TYPE = '" + param + "'";
			con = this.ds.getConnection();	
			stmt = con.createStatement();			
			
			rs = stmt.executeQuery(sql);
			if(rs != null)
			{
				while(rs.next())
				{					
					Clob clob = rs.getClob(1);
					if (clob != null) {
						Reader reader = clob.getCharacterStream();
						CharArrayWriter writer = new CharArrayWriter();
						int i = -1;
						while ((i = reader.read()) != -1) {
							writer.write(i);
						}
						tmp.append(new String(writer.toCharArray()));
					}
				}	
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try { if (stmt != null) stmt.close(); } catch (Exception e) { e.printStackTrace();log.error(e, e); }
			try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace();log.error(e, e); }
			try	{ if (con != null)	con.close();	}
			catch (Exception e)
			{	log.error(e, e);	}
		}
		log.info("END *******");
		return tmp;
	}
}
