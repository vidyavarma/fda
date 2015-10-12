package com.nrg.lau.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import javax.sql.DataSource;

public class SharedConnectionDAO {
	private static Logger log = Logger.getLogger(SharedConnectionDAO.class);
	private DataSource sharedDS;
	private Connection con;

	public DataSource getSharedDS() {
		return sharedDS;
	}

	public void setSharedDS(DataSource sharedDS) {
		this.sharedDS = sharedDS;
		try {
			setCon(sharedDS.getConnection());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getCon() {
		return con;
	}
	public Connection getConVerify()  {
		// re evaluate the connection 

		try {
			String sql = "SELECT 1 FROM DUAL ";
			log.info("getConVerify() :" + sql);
			Statement stmt = this.con.createStatement();
			Boolean bReturn = stmt.execute(sql);
			log.info("getConVerify() EXECUTED:");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info(" getCon() ....sql exception connection closed?");
			try {
				log.info(" getCon() ....attempting reopen a connection");
				con = sharedDS.getConnection();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			log.info(" getCon() ....setting auto commit");
			con.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}
		return con;
	}
	public void setCon(Connection con) throws SQLException {
		this.con = con;
		con.setAutoCommit(false);
		String sql = "{call LAU_LIST.init}";
		log.info("shared connection:" + sql);
		CallableStatement stmt = con.prepareCall(sql);
		stmt.execute();
		log.info("shared connection initialized");
	}

	public void closeConnection() throws Throwable {
		this.con.close();
	}

	public void pingConnection() throws Throwable {
		String sql = "SELECT 1 FROM DUAL ";
		log.info("pingConnection() :" + sql);
		Statement stmt = this.con.createStatement();
		Boolean bReturn = stmt.execute(sql);
		log.info("pingConnection() EXECUTED:");
	}

	protected void finalize() throws Throwable {
		this.con.close();
	}
}
