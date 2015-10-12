package com.nrg.lau.render;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import oracle.jdbc.OracleTypes;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.e2b.E2BFileOutput;
import com.nrg.lau.e2b.E2BUtil;

@SuppressWarnings("unused")
public class PrimoAuditReport implements View {
	
	private static Logger log = Logger.getLogger(PrimoAuditReport.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;	
	private String status = "";
	private String userId	= "";
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		long reportId = 0;
		userId = CommonDAO.getUSERID(request);
		if (request.getParameter("reportId") != null) {
			
			try {
			
				log.info("Report id - :" + request.getParameter("reportId"));
				reportId = Long.valueOf((String) request.getParameter("reportId"));
				log.info("ENTER - PrimoAuditReport render()");
				
				log.info("PrimoAuditReport () -->  " + reportId);
				
				E2BUtil util 		= new E2BUtil();			
				StreamSource source = new StreamSource(getData(reportId));
				
				// creation of transform source
				StreamSource transformSource = new StreamSource(
						E2BFileOutput.class
								.getResourceAsStream("/AuditLog.xsl"));
				
				
				
				// create an instance of fop factory
				
				FopFactory fopFactory = FopFactory.newInstance();
				DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
				Configuration cfg = cfgBuilder.build( new StreamSource(GetReportPDFAction.class.getResourceAsStream("/fopconfig.xml")).getInputStream());

				fopFactory.setUserConfig(cfg);

				FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
				// to store output
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				Transformer xslfoTransformer;
		
				xslfoTransformer = util.getTransformer(transformSource);
				// Construct fop with desired output format
				Fop fop;
		
				fop = fopFactory.newFop(MimeConstants.MIME_PDF,
						foUserAgent, outStream);
				// Resulting SAX events (the generated FO)
				// must be piped through to FOP
				Result res = new SAXResult(fop.getDefaultHandler());
		
				// everything will happen here..
				xslfoTransformer.transform(source, res);
				// to write the content to out put stream
				byte[] pdfBytes = outStream.toByteArray();
				response.setContentLength(pdfBytes.length);
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition",
						"attachment;filename=AuditHistoryReport.pdf");
				response.getOutputStream().write(pdfBytes);
				response.getOutputStream().flush();
			
			}catch (Exception e) {
				status = XMLException.xmlError(e,e.getMessage());
			}	
			
		} else {
			status = XMLException.xmlError(new Exception(),
					"Report Id not available!");
		}
		
		

		log.info("EXIT - PrimoAuditReport()");
	}

	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("PrimoAuditReport - Initialize db template()");
	}
	private Reader getData(long reportId) {
		
		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;
		Reader reader 		= null;		

		try {
			
			con 		= ds.getConnection();
			
			String sql 	= "{call ? := lau_report_audit_log .PrintAuditLogReportXML(?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setLong(2, reportId);
			stmt.setString(3,userId);
			log.info(sql);
			stmt.execute();
			Clob clob = stmt.getClob(1);
			if (clob != null) {
				reader = clob.getCharacterStream();
				return reader;
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
		log.info(tmp);
		return reader;
	}
	
}
