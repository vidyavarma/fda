package com.nrg.lau.service.bi;

import com.oracle.xmlns.oxp.service.PublicReportService.ParamNameValue;
import com.oracle.xmlns.oxp.service.PublicReportService.ReportResponse;

public interface BIPublisher {
	
	public boolean createReportWithDataModel(String reportName,
			String dataModelName, String templateFileName, byte[] templateData)
			throws Exception;

	public boolean createReportWithDataModel(String reportName,
			String dataModelName, String templateFileName, byte[] templateData,
			String XLIFFFileName, byte[] XLIFFData) throws Exception;

	public boolean removeReport(String reportName) throws Exception;

	public boolean updateTemplate(String reportName, String templateName,
			byte[] templateData) throws Exception;

	public boolean removeTemplate(String reportName, String templateName)
			throws Exception;

	public byte[] getTemplate(String reportName, String templateName)
			throws Exception;

	public boolean createDataModel(String dataModelName, String dataSetName,
			byte[] dataSet) throws Exception;

	public boolean updateDataModel(String dataModelName, byte[] dataSet)
			throws Exception;

	public boolean deleteDataModel(String dataModelName) throws Exception;

	public byte[] getDataModel(String dataModelName) throws Exception;

	public byte[] generatePDF(String reportName, String templateName,
			ParamNameValue[] paramNmVals);

	public ReportResponse generateOutput(String reportName,
			String templateName, String outFormat, ParamNameValue[] paramNmVals);
	
}
