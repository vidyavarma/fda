package com.nrg.lau.service.bi;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.nrg.lau.utils.ReadConfig;
import com.oracle.xmlns.oxp.service.PublicReportService.ParamNameValue;
import com.oracle.xmlns.oxp.service.PublicReportService.PublicReportService;
import com.oracle.xmlns.oxp.service.PublicReportService.PublicReportServiceService;
import com.oracle.xmlns.oxp.service.PublicReportService.PublicReportServiceServiceLocator;
import com.oracle.xmlns.oxp.service.PublicReportService.ReportDefinition;
import com.oracle.xmlns.oxp.service.PublicReportService.ReportRequest;
import com.oracle.xmlns.oxp.service.PublicReportService.ReportResponse;
import com.oracle.xmlns.oxp.service.PublicReportService.TemplateFormatsLabelValues;
import com.oracle.xmlns.oxp.service.v2.CatalogService_PortType;
import com.oracle.xmlns.oxp.service.v2.CatalogService_Service;
import com.oracle.xmlns.oxp.service.v2.CatalogService_ServiceLocator;


public class BIPublisherWebServiceImpl implements BIPublisher{
	
	private static Logger log	= Logger.getLogger(BIPublisherWebServiceImpl.class);
	public static final String TEMPLATE_TYPE 	= ".rtf";
	private static final String DEFAULT_LOCALE 	= "en_US";
	private static final String REPORT_EXT 		= ".xdo";
	public static final String DATA_MODEL_EXT 		= ".xdm";
	
	public boolean createReportWithDataModel(String reportName, String dataModelName, String templateFileName, 
			byte[] templateData) throws Exception{
		
		boolean rtn = false;
		rtn = createReportWithDataModel(reportName, dataModelName,
				templateFileName, templateData, "", "".getBytes());

		return rtn;
	}
	
	public boolean createReportWithDataModel(String reportName, String dataModelName, String templateFileName, 
			byte[] templateData, String XLIFFFileName, byte[] XLIFFData) throws Exception{
		
		boolean rtn = false;
		PublicReportService reportService = null;
		try {
			
			reportService = getReportService();
			reportService.getReportDefinition(getReportPath() + reportName
					+ REPORT_EXT, getUserName(), getPassword());
			log.info(reportName + REPORT_EXT
					+ " report already available or created");
		} catch (Exception ce) {
			try {
				if(reportService == null)	reportService = getReportService();
				reportService.createReportWithDataModel(
						reportName + REPORT_EXT, getReportPath(),
						getDataModelPath() + dataModelName + DATA_MODEL_EXT,
						templateFileName + TEMPLATE_TYPE, templateData,
						XLIFFFileName, XLIFFData, true, getUserName(),
						getPassword());
				rtn = true;
			} catch (Exception e) {
				log.error(e);
				throw e;
			}
		}
		
		return rtn;
	}
	
	@Override
	public boolean removeReport(String reportName) throws Exception{
		
		boolean rtn = false;
		PublicReportService reportService = getReportService();
		rtn = reportService.deleteReport(getReportPath() + reportName
				+ REPORT_EXT, getUserName(), getPassword());

		return rtn;
	}	
	
	
	@Override
	public boolean updateTemplate(String reportName, String templateName, byte[] templateData) 
			throws Exception {
		
		boolean rtn = false;
		PublicReportService reportService = getReportService();		
		String sessionID = reportService.login(getUserName(), getPassword());
		String reportPath = getReportPath() + reportName + REPORT_EXT;
		String templateID = getTemplateID(reportService, sessionID, reportPath,
				templateName);
		
		rtn = reportService.updateTemplateForReportInSession(reportPath,					
				templateID, "", templateData, sessionID);

		return rtn;
	}
	
	@Override
	public boolean removeTemplate(String reportName, String templateName)
			throws Exception {

		boolean rtn = false;
		PublicReportService reportService = getReportService();
		String sessionID = reportService.login(getUserName(), getPassword());
		String reportPath = getReportPath() + reportName + REPORT_EXT;
		String templateID = getTemplateID(reportService, sessionID, reportPath,
				templateName);

		rtn = reportService.removeTemplateForReportInSession(reportPath,
				templateID, sessionID);

		return rtn;
	}
	
	@Override
	public byte[] getTemplate(String reportName, String templateName)
			throws Exception {

		byte[] rtn = null;
		PublicReportService reportService = getReportService();
		String sessionID = reportService.login(getUserName(), getPassword());
		String reportPath = getReportPath() + reportName + REPORT_EXT;
		String templateID = getTemplateID(reportService, sessionID, reportPath,
				templateName);

		rtn = reportService.getTemplateInSession(reportPath, templateID,
				DEFAULT_LOCALE, sessionID);

		return rtn;
	}
	
	public boolean createDataModel(String dataModelName, String dataSetName, byte[] dataSet) 
			throws Exception {
		
		boolean rtn = false;
		createDefaultFolderStructure();
		CatalogService_PortType catalogService = getCatalogService();
		catalogService.createObject(getDataModelPath(), dataModelName,
				DATA_MODEL_EXT, dataSetName, dataSet, getUserName(),
				getPassword());
		rtn = true;

		return rtn;
	}
	
	public boolean updateDataModel(String dataModelName, byte[] dataSet) throws Exception{
		
		boolean rtn = false;
		CatalogService_PortType catalogService = getCatalogService();
		rtn = catalogService.updateObject(getDataModelPath() + dataModelName
				+ DATA_MODEL_EXT, dataSet, getUserName(), getPassword());

		return rtn;
	}
	
	public boolean deleteDataModel(String dataModelName) throws Exception{
		
		boolean rtn = false;
		CatalogService_PortType catalogService = getCatalogService();
		rtn = catalogService.deleteObject(getDataModelPath() + dataModelName
				+ DATA_MODEL_EXT, getUserName(), getPassword());

		return rtn;
	}
	
	public byte[] getDataModel(String dataModelName) throws Exception{
		
		byte[] rtn = null;	
		CatalogService_PortType catalogService = getCatalogService();
		rtn = catalogService.getObject(getDataModelPath() + dataModelName
				+ DATA_MODEL_EXT, getUserName(), getPassword());		
		
		return rtn;		
	}
	
	@Override
	public byte[] generatePDF(String reportName, String templateName, ParamNameValue[] paramNmVals) {
		
		byte[] baReport = null;
		try {
			
			PublicReportService reportService = getReportService();
			
			ReportRequest repRequest = new ReportRequest();
			repRequest.setReportAbsolutePath(getReportPath() + reportName + REPORT_EXT);			
			repRequest.setAttributeTemplate(templateName);
			repRequest.setAttributeFormat("pdf");
			repRequest.setAttributeLocale(DEFAULT_LOCALE);
			repRequest.setSizeOfDataChunkDownload(-1);
			repRequest.setParameterNameValues(paramNmVals);
	        
	        log.info("********** Start PDF generation **********");
			
			ReportResponse repResponse = new ReportResponse();
			repResponse = reportService.runReport(repRequest, getUserName(), getPassword());
			
			baReport = repResponse.getReportBytes();
			
			log.info("********** End PDF generation **********");
			
			
		} catch (Exception e) {
			log.error(e);
		}
		
		return baReport;
		
	}
	
	public ReportResponse generateOutput(String reportName, String templateName, String outFormat, 
			ParamNameValue[] paramNmVals) {
		
		try {
			
			PublicReportService reportService = getReportService();
			
			ReportRequest repRequest = new ReportRequest();
			repRequest.setReportAbsolutePath(getReportPath() + reportName + REPORT_EXT);			
			//repRequest.setReportData(reportData);
			repRequest.setAttributeTemplate(templateName);
			repRequest.setAttributeFormat(outFormat.toLowerCase());
			log.info(outFormat.toLowerCase());
			repRequest.setAttributeLocale(DEFAULT_LOCALE);
			repRequest.setSizeOfDataChunkDownload(-1);
			repRequest.setParameterNameValues(paramNmVals);
			
			log.info("********** Start report generation **********");
			log.info("ReportName -> " + reportName);
			log.info("TemplateName -> " + templateName);
			log.info("OutFormat -> " + outFormat);
			
			ReportResponse repResponse = new ReportResponse();
			repResponse = reportService.runReport(repRequest, getUserName(), getPassword());
			
			log.info("********** End report generation **********");
			
			return repResponse;			
			
		} catch (Exception e) {
			log.error(e);
		}
		
		return new ReportResponse();
		
	}
	
	private void createDefaultFolderStructure() {
		
		try {
			
			PublicReportService reportService = getReportService();
			String sessionID = reportService.login(getUserName(), getPassword()); 
			CatalogService_PortType catalogService = getCatalogService();
			boolean exists = catalogService.objectExistInSession(getDefaultPath(), sessionID);
			if(!exists) {
				
				catalogService.createFolderInSession(getDefaultPath(), sessionID);
				catalogService.createFolderInSession(getReportPath(), sessionID);
				catalogService.createFolderInSession(getDataModelPath(), sessionID);
				log.info("Created default folder structure");
			}
		}catch (Exception e) {
			log.error(e);
		}
		
	}
	
	private String getTemplateID(PublicReportService reportService,
			String sessionID, String reportPath, String templateName) {

		String templateID = "";
		try {

			ReportDefinition rf = reportService.getReportDefinitionInSession(
					reportPath, sessionID);
			//Setting default template ID if getTemplateURL() doesn't have a match with templateName
			//Probability of not matching is 0
			templateID = rf.getDefaultTemplateId();
			TemplateFormatsLabelValues[] items = rf
					.getListOfTemplateFormatsLabelValues();
			
			for (TemplateFormatsLabelValues item : items) {
				if (templateName.equalsIgnoreCase(item.getTemplateURL())) {
					templateID = item.getTemplateID();
				}
			}		

		} catch (Exception e) {
			log.error(e);
		}
		log.info("getTemplateID -> " + templateID);
		return templateID;
	}
	
	private CatalogService_PortType getCatalogService() throws ServiceException {
		CatalogService_Service catalogService = new CatalogService_ServiceLocator(getBICatalogtUrl());
		return catalogService.getCatalogService();
		
	}
	
	private PublicReportService getReportService() throws ServiceException {	
		PublicReportServiceService publicReportServiceService = new PublicReportServiceServiceLocator(getBIReportUrl());
		return publicReportServiceService.getPublicReportService();		
	}
	
	private String getUserName() throws Exception {
		return ReadConfig.getMessage().getString("BI_USER_NAME");
	}
	
	private String getPassword() throws Exception {
		return ReadConfig.getMessage().getString("BI_USER_PASS");
	}
	
	private String getDefaultPath() throws Exception {
		return ReadConfig.getMessage().getString("BI_DEFAULT_PATH");
	}
	
	private String getReportPath() throws Exception {
		return ReadConfig.getMessage().getString("BI_DEFAULT_PATH") + "Reports/";
	}
	
	private String getDataModelPath() throws Exception {
		return ReadConfig.getMessage().getString("BI_DEFAULT_PATH") + "Data Models/";
	}
	
	private String getBIReportUrl() {
		return ReadConfig.getValue("BI_WEB_SERVICE_REPORT_URL");
	}
	
	private String getBICatalogtUrl() {
		return ReadConfig.getValue("BI_WEB_SERVICE_CATALOG_URL");
	}
}
