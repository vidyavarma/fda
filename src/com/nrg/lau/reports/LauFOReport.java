package com.nrg.lau.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import oracle.apps.xdo.dataengine.DataProcessor;

import oracle.xdo.template.FOProcessor;
import oracle.xdo.template.RTFProcessor;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.security.AppContext;
import com.nrg.lau.service.bi.BIPublisher;
import com.nrg.lau.service.bi.BIPublisherLoader;
import com.nrg.lau.utils.ReadConfig;
import com.oracle.xmlns.oxp.service.PublicReportService.ParamNameValue;
import com.oracle.xmlns.oxp.service.PublicReportService.ReportResponse;
import com.sun.java.util.collections.Hashtable;

public class LauFOReport {

	private static Logger log = Logger.getLogger(LauFOReport.class);
	private String defaultLanguage = "en";
	private String defaultDateformat = "STD";
	private String renderType = "PDF";
	private String reportName = " ";
	private String renderContentType = "application/pdf;charset=utf-8";
	private byte outputByte = FOProcessor.FORMAT_PDF;
	private String userId = "";
	private String userGroupId = "";
	private JobLauncher jobLauncher;
	private Job jobEmailLetter;

	public void getReport(HttpServletResponse response, DataSource ds,
			HttpServletRequest request, String reportId, String dataTemplateId,
			String layoutTemplateId, String lauReportId, String templateName)
			throws Exception {
		
		userId = CommonDAO.getUSERID(request);
		userGroupId = (String) request.getParameter("usergroupid");	

		Connection con = null;
		
		try {
			
			con = ds.getConnection();
			//BI Publisher 11g web service integration
			boolean biServer = Boolean.valueOf(ReadConfig.getValue("BI_SERVER"));
			if(biServer) {				
				generate11gReport(response, ds, request, reportId, dataTemplateId, 
						layoutTemplateId, lauReportId, templateName, con);
			} else {				
				generate10gReport(response, ds, request, reportId, dataTemplateId,
						layoutTemplateId, lauReportId, templateName, con);
			}
			
		} catch (Exception e) {
			log.error(e);
			response.setContentType(Constants.CONTENT_TYPE);
			response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
			PrintWriter pw = response.getWriter();
			pw.write(XMLException.status("F", e.getMessage()));
			pw.flush();
			pw.close();
		} finally {
			try {
				if (con != null) {
					con.close();
					log.info("Connection close from FO Report");
				}
			} catch (Exception e) {
				log.error(e);
			}
		}

	}
	
	private void generate11gReport(HttpServletResponse response, DataSource ds,
			HttpServletRequest request, String reportId, String dataTemplateId,
			String layoutTemplateId, String lauReportId, String templateName, Connection con) throws Exception{
		
		log.info("*** Inside 11g report generation ***");
		BIPublisher bi = BIPublisherLoader.getBIPublisher();
		String sq = "SELECT BI_REPORT_ID FROM LAU_LAYOUT_TEMPLATES WHERE LAYOUT_TEMPLATE_ID = ?";
		Long biRptID = new JdbcTemplate(ds).queryForObject(sq, Long.class, layoutTemplateId);				
		log.info("biReportID for update -> " + biRptID);
		
		if(biRptID != null) {
			
			HashMap<String,String>  params = new HashMap<String, String> ();
			params.put("reportid", reportId);
	        
			if (request.getParameter("contactId") != null)
				params.put("contactId", request.getParameter("contactId"));

			log.info("Contact ID ->" + request.getParameter("contactId"));

			if (request.getParameter("langcd") != null) {
				params.put("langcd", request.getParameter("langcd"));
			} else {
				params.put("langcd", defaultLanguage);
			}

			log.info("Lang CD ->" + request.getParameter("langcd"));

			if (request.getParameter("dateformat") != null) {
				params.put("dateformat", request.getParameter("dateformat"));
			} else {
				params.put("dateformat", defaultDateformat);
			}
			
			if (request.getParameter("contactId") != null)
				params.put("contactId", request.getParameter("contactId"));
			
			if (request.getParameter("generatedletterid") != null)
				params.put("generatedletterid", request.getParameter("generatedletterid"));			
			
			log.info("dateformat -> " + request.getParameter("dateformat"));
			params.put("userId", userId);
			params.put("userGroupId", userGroupId);
			
			log.info("User ID -> " + userId);
			log.info("User Group ID -> " + userGroupId);
			log.info("Generated Letter ID -> " + request.getParameter("generatedletterid"));
	        
			int cnt = 0;
			ParamNameValue[] paramNmVals = new ParamNameValue[params.size()];				        
			for (Map.Entry<String, String> entry : params.entrySet()) {
	            
				paramNmVals[cnt] = new ParamNameValue() ;
				paramNmVals[cnt].setName(entry.getKey());
				paramNmVals[cnt].setValues(new String[] {entry.getValue()});
				cnt++;
	        }       
			
			log.info("*** Start BI Publisher report generation ***");
			log.info("BI Report ID ->  " + String.valueOf(biRptID));
			log.info("Layout Template ID -> " + layoutTemplateId);
			
			if (request.getParameter("renderType") != null) {
				renderType = request.getParameter("renderType");
				log.info("renderType -> " + renderType);
			}
			
			Calendar date = new GregorianCalendar();
			Format formatter = new SimpleDateFormat("yyyyMMddhhmm");
			reportName = request.getParameter("templateName") + "_" + formatter.format(date.getTime()) + "." + renderType;
			log.info("Report Name: " + reportName);
			
			ReportResponse repResponse = bi.generateOutput(String.valueOf(biRptID), layoutTemplateId, renderType, paramNmVals);
			byte[] pdfBytes = repResponse.getReportBytes();			
			String contType = repResponse.getReportContentType();			
			log.info("ContentType -> " + contType);			
			
			String generatedletterid = request.getParameter("generatedletterid");
			if(generatedletterid != null ) {				
				
				PreparedStatement pstmt = con.prepareStatement("update LAU_GENERATED_LETTERS set GENERATED_LETTER_BINARY = ? " +
						"where LAU_GENERATED_LETTER_ID = " + Long.valueOf(generatedletterid));
				pstmt.setBinaryStream(1, new ByteArrayInputStream(pdfBytes),(int)pdfBytes.length); 
				pstmt.executeUpdate();
				con.commit();
				log.info("Document saved successfully to LAU_GENERATED_LETTERS");	
				
				//This call is to retrieve GENERATE_EMAIL from LAU_LAYOUT_TEMPLATES
				//if its Y, call batch job to send email.
				String query = "SELECT GENERATE_EMAIL FROM LAU_LAYOUT_TEMPLATES WHERE LAYOUT_TEMPLATE_ID = ?";
				String generatedEmail = new JdbcTemplate(ds).queryForObject(query, new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int i) throws SQLException {
						
						String result = rs.getString("GENERATE_EMAIL");								
						return result;
					}
				} , layoutTemplateId);
				
				log.info("GENERATE_EMAIL -> " + generatedEmail);				
				//Invoke EmailLetter batch job 
				if(generatedEmail != null && generatedEmail.equalsIgnoreCase("Y")) {
					
					log.info("Invoked email letter batch job");
					ApplicationContext ctx 	= 	AppContext.getApplicationContext();
					jobLauncher = (JobLauncher)ctx.getBean("jobLauncher");
					jobEmailLetter = (Job)ctx.getBean("jobEmailLetter");	
					
					JobParametersBuilder builder = new JobParametersBuilder();
					builder.addString("input.key", String.valueOf(generatedletterid));
					jobLauncher.run(jobEmailLetter, builder.toJobParameters());
				}
				
			}		
			
			response.setContentLength(pdfBytes.length);
			response.setContentType(contType);
			response.addHeader("Content-Disposition",
					"attachment;filename= " + reportName);
			response.getOutputStream().write(pdfBytes);
			response.getOutputStream().flush();
		}
		
		
	}
	
	private void generate10gReport(HttpServletResponse response, DataSource ds,
			HttpServletRequest request, String reportId, String dataTemplateId,
			String layoutTemplateId, String lauReportId, String templateName, Connection con) throws Exception{
		
		LauFOReportUtil util = new LauFOReportUtil();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ByteArrayOutputStream xslStream = new ByteArrayOutputStream();
		ByteArrayOutputStream xmlStream = new ByteArrayOutputStream();

		if (request.getParameter("renderType") != null) {

			String type = request.getParameter("renderType");
			if (type.equals("RTF")) {
				renderType = type;
				renderContentType = "application/rtf;charset=utf-8";
				outputByte = FOProcessor.FORMAT_RTF;
			} else if (type.equals("XLS")) {
				renderType = type;
				renderContentType = "application/vnd.ms-excel";
				outputByte = FOProcessor.FORMAT_EXCEL;
			} else if (type.equals("XML")) {
				renderType = type;
				renderContentType = "application/rtf;charset=utf-8";
				outputByte = FOProcessor.FORMAT_HTML;
			}
		}

		String layoutTemplateSQL = "SELECT LAYOUT_TEMPLATE FROM LAU_LAYOUT_TEMPLATES WHERE LAYOUT_TEMPLATE_ID = "
				+ Integer.parseInt(layoutTemplateId);
		String dataTemplateSQL = "SELECT DATA_TEMPLATE FROM LAU_DATA_TEMPLATES WHERE DATA_TEMPLATE_ID = "
				+ Integer.parseInt(dataTemplateId);

		
		try {

			// ((OracleConnection)con).setSessionTimeZone(TimeZone.getDefault().getID());
			// Generate XML from Data Template
			Calendar date = new GregorianCalendar();
			Format formatter = new SimpleDateFormat("yyyyMMddhhmm");
			reportName = request.getParameter("templateName") + "_" + formatter.format(date.getTime()) + "." +renderType;
			log.info("Report Name: " + reportName);
			
			DataProcessor dataProcessor = new DataProcessor();
			dataProcessor.setDataTemplate(util.getDataTemplate(con,	dataTemplateSQL));
			dataProcessor.setConnection(con);
			dataProcessor.setOutput(xmlStream);

			Hashtable parameters = new Hashtable();
			parameters.put("reportid", reportId);

			if (request.getParameter("contactId") != null)
				parameters.put("contactId",
						Integer.parseInt(request.getParameter("contactId")));

			log.info("contactId ->" + request.getParameter("contactId"));

			if (request.getParameter("langcd") != null) {
				parameters.put("langcd", request.getParameter("langcd"));
			} else {
				parameters.put("langcd", defaultLanguage);
			}

			log.info("langcd ->" + request.getParameter("langcd"));

			if (request.getParameter("dateformat") != null) {
				parameters
						.put("dateformat", request.getParameter("dateformat"));
			} else {
				parameters.put("dateformat", defaultDateformat);
			}
			if (request.getParameter("contactId") != null)
				parameters.put("contactId",
						Integer.parseInt(request.getParameter("contactId")));
			if (request.getParameter("generatedletterid") != null)
				parameters.put("generatedletterid",
						Integer.parseInt(request.getParameter("generatedletterid")));			
			
			log.info("dateformat ->" + request.getParameter("dateformat"));
			parameters.put("userId", userId);
			parameters.put("userGroupId", userGroupId);
			log.info("userId ->" + userId);
			log.info("userGroupId ->" + userGroupId);
			log.info("generatedletterid ->" + request.getParameter("generatedletterid"));
			dataProcessor.setParameters(parameters);
			dataProcessor.processData();

			if (renderType.equals("XML")) {
				
				byte[] xmlBytes = xmlStream.toByteArray();
				response.setContentLength(xmlBytes.length);
				response.setContentType(renderContentType);
				response.addHeader("Content-Disposition",
						"attachment;filename= " + reportName);
				response.getOutputStream().write(xmlBytes);
				response.getOutputStream().flush();
			
			} else {				
								
				// Generate XSL
				RTFProcessor rtfp = new RTFProcessor(util.getLayoutTemplate(
						con, layoutTemplateSQL));
				rtfp.setOutput(xslStream);
				rtfp.process();

				// Process generated files
				FOProcessor p = new FOProcessor();

				p.setData(new ByteArrayInputStream(xmlStream.toByteArray()));
				p.setTemplate(new ByteArrayInputStream(xslStream.toByteArray()));
				p.setOutput(outStream);
				p.setOutputFormat(outputByte);
				p.generate();

				// Output as pdf
				byte[] pdfBytes = outStream.toByteArray();
				
				
				String generatedletterid = request.getParameter("generatedletterid");
				if(generatedletterid != null ) {
					
					PreparedStatement pstmt = con.prepareStatement("update LAU_GENERATED_LETTERS set GENERATED_LETTER_BINARY = ? " +
							"where LAU_GENERATED_LETTER_ID = " + Long.valueOf(generatedletterid));
					pstmt.setBinaryStream(1, new ByteArrayInputStream(pdfBytes),(int)pdfBytes.length); 
					pstmt.executeUpdate();
					con.commit();
					log.info("Document saved successfully to LAU_GENERATED_LETTERS");			
				}				
				
				
				response.setContentLength(pdfBytes.length);
				response.setContentType(renderContentType);
				response.addHeader("Content-Disposition",
						"attachment;filename= " + reportName);
				response.getOutputStream().write(pdfBytes);
				response.getOutputStream().flush();			
				
			}

		} catch (Exception e) {
			log.error(e);
			throw e;
		} 		
	}
}
