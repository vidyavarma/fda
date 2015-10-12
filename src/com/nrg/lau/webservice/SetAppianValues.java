package com.nrg.lau.webservice;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.dao.LauReportActivitiesSetDAO;
import com.nrg.lau.security.AppContext;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@WebService(serviceName = "SetAppianValues")
public class SetAppianValues {
	@Resource
	WebServiceContext wsctx;
	private DataSource ds;
	private static Logger log	= Logger.getLogger(LauReportActivitiesSetDAO.class);
	private SimpleJdbcTemplate template;

	@WebMethod(operationName="getCheckedOutUser")
	public String getCheckedOutUser(@WebParam(name="activityID") long activityID) {
        ApplicationContext ctx 	= 	AppContext.getApplicationContext();
		DataSource ds 	= 	(DataSource)ctx.getBean("dataSource");
		template		=	new SimpleJdbcTemplate(ds);
		String sReservedBy = "";
		String UserContactDetailsSQL = "SELECT REPORT_RESERVED_BY FROM LAU_REPORTS WHERE REPORT_ID=" +
				"(SELECT  REPORT_ID FROM LAU_REPORT_ACTIVITIES WHERE ACTIVITY_ID = ?)";
		List<Map<String, Object>> rows 	= template.queryForList(UserContactDetailsSQL, new Object[]{
				activityID});
		for(Map<String, Object> row : rows) {
	    		log.info("REPORT_RESERVED_BY -> " + row.get("REPORT_RESERVED_BY"));
	    		sReservedBy = String.valueOf(row.get("REPORT_RESERVED_BY"));
			}
		ctx = null;
		ds = null;
		template = null;
		return 	sReservedBy;
 	}
	@WebMethod(operationName="setCompletionDate")
	public int setCompletionDate (@WebParam(name="completionDate")Date completionDate, @WebParam(name="activityID")long activityID)
	{
		int iResult = 0;
		ApplicationContext ctx 	= 	AppContext.getApplicationContext();
		DataSource ds 	= 	(DataSource)ctx.getBean("dataSource");
		template		=	new SimpleJdbcTemplate(ds);
		SimpleDateFormat dDBDateFormat = new SimpleDateFormat("yyyyMMdd"); 
		String sComplateDate = dDBDateFormat.format(completionDate) ; 
		String sUpdateCompletionDate = "UPDATE LAU_REPORT_ACTIVITIES SET COMPLETION_DATE = ? WHERE ACTIVITY_ID = ?";
		template.update(sUpdateCompletionDate,new Object[]{sComplateDate,activityID});
		iResult = 1;
		ctx = null;
		ds = null;
		template = null;
		return iResult;
	}
}
