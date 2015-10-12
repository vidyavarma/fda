package com.nrg.lau.webservice;
import java.util.List;
import java.util.Map;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import com.nrg.lau.dao.LauReportActivitiesSetDAO;
import com.nrg.lau.security.AppContext;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@WebService(serviceName = "SetExternalRule")
public class SetExternalRule {
	@Resource
	WebServiceContext wsctx;
	private DataSource ds;
	private static Logger log	= Logger.getLogger(LauReportActivitiesSetDAO.class);
	private SimpleJdbcTemplate template;

	@WebMethod(operationName="RaisePRIMONotification")
	public String RaisePRIMONotification(@WebParam(name="notificationXML") String notificationXML)
	{
        ApplicationContext ctx 	= 	AppContext.getApplicationContext();
		DataSource ds 	= 	(DataSource)ctx.getBean("dataSource");
		template		=	new SimpleJdbcTemplate(ds);
		String sResult = "";
		String RuleSQL = "Select lau_notification.EvaluateExternalRule(?) result from dual";
		List<Map<String, Object>> rows 	= template.queryForList(RuleSQL, new Object[]{
				notificationXML});
		for(Map<String, Object> row : rows) {
	    		log.info("External Rule -> " + row.get("result"));
	    		sResult = String.valueOf(row.get("result"));
			}
		ctx = null;
		ds = null;
		template = null;
		return 	sResult;
 	}
}
