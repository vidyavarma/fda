package com.nrg.lau.service.batch;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class EmailLetterTasklet implements Tasklet{

	private static Logger log = Logger.getLogger(EmailLetterTasklet.class);
	private String key;
	
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	@Override
	public RepeatStatus execute(StepContribution step, ChunkContext context)
			throws Exception {
		
		CallableStatement stmt = null;
		Connection con 	= null;
		
		try {
			
			if(!getKey().isEmpty()) {
				
				log.info("Generated_Letter_ID -> " + getKey());
				con 		= dataSource.getConnection();
				String sql 	= "{call ? := LAU_MAIL.Email_Letter(?)}";
				stmt = con.prepareCall(sql);
	
				stmt.registerOutParameter(1, OracleTypes.INTEGER);
				stmt.setInt(2, Integer.valueOf(getKey()));					
				log.info(sql);
				stmt.execute();
				int rtn = stmt.getInt(1);
				log.info("Rtn from LAU_MAIL.Email_Letter() -> " + rtn);
			}

		} catch (SQLException e) {
			log.error(e, e);
		} catch (Exception e) {
			log.error(e, e);
		} finally {					
			try { if (stmt != null) stmt.close(); } catch (Exception e) { log.error(e, e); }
			try	{ if (con != null) con.close(); } catch (Exception e) { log.error(e, e); }			
		}	
		
		return RepeatStatus.FINISHED;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
