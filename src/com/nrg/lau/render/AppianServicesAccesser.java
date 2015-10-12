package com.nrg.lau.render;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;

public class AppianServicesAccesser {
	private static Logger log = Logger.getLogger(AppianServicesAccesser.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;	

	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("AppianServicesAccesser - Initialize db template()");
	}
	public long setCompletionDate(long activityID, Date completeDate  ) {
		Connection con		= null;
		long successID = 0;

		try {
			con 		= ds.getConnection();
			template.update("UPDATE lau_report_activities SET completion_date= ? WHERE activity_id= ?",
					new Object[]{completeDate,activityID});
			successID =1;
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
		return successID;
	}
}