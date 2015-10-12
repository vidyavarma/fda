package com.nrg.lau.e2b;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.httpclient.util.URIUtil;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.ExternalDocuments;
import com.nrg.lau.commons.Constants;
import com.nrg.lau.sharepoint.GroupInboxService;
import com.nrg.lau.sharepoint.OutboundService;
import com.nrg.lau.sharepoint.RejectService;
import com.nrg.lau.sharepoint.SPResponse;
import com.nrg.lau.sharepoint.SPUtil;

public class E2BFileOutput extends E2BUtil{
	
	private static Logger log = Logger.getLogger(E2BFileOutput.class);
	
	public static void outputPDF(String fileName, 
			HttpServletResponse response, String sPURL) {
		
		try {
			E2BUtil util 		= new E2BUtil();
			InputStream in 		= SPUtil.getSharePointDocument(sPURL);
			StreamSource source = new StreamSource(in);
			
			// creation of transform source
			StreamSource transformSource = new StreamSource(
					E2BFileOutput.class
							.getResourceAsStream("/E2Bstyle_fop.xsl"));
	
			// create an instance of fop factory
			FopFactory fopFactory = FopFactory.newInstance();
			// a user agent is needed for transformation
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
					"attachment;filename=primo.pdf");
			response.getOutputStream().write(pdfBytes);
			response.getOutputStream().flush();
		}catch (Exception e) {
			E2BUtil.getE2BError(response, e);
		}	
	
	}
	
	public static void outputHTML(String fileName, 
			HttpServletResponse response, String sPURL) throws Exception{
		
		try {
			InputStream in 	= SPUtil.getSharePointDocument(sPURL);
			StreamSource source = new StreamSource(in);
			// creation of transform source
			StreamSource transformSource = new StreamSource(
					E2BFileOutput.class
							.getResourceAsStream("/E2Bstyle.xsl"));
			PrintWriter out = response.getWriter();
			StreamResult result = new StreamResult(out);
			TransformerFactory tFactory = TransformerFactory
					.newInstance();
			Transformer transformer = tFactory
					.newTransformer(transformSource);						
			response.setContentType(Constants.CONTENT_TYPE_HTML);
			response.setHeader("Cache-Control",
					Constants.CACHE_CONTROL);
			transformer.transform(source, result);
			out.flush();
			out.close();
		}catch (Exception e) {
			E2BUtil.getE2BError(response, e);
		}
	}
	
	public static void outputRTF(String fileName, 
			HttpServletResponse response, String sPURL) throws Exception{
		
		try {
			String fileNm 	= fileName.substring(0,fileName.lastIndexOf("."));
			log.info("File Name after substring() -> " + fileNm);
			long startSP = System.currentTimeMillis();
			log.info("startSP - " + startSP);
			InputStream in = SPUtil.getSharePointDocument(sPURL);			
			long endSP = System.currentTimeMillis();
			log.info(endSP);
			log.info("Total millsecs elapsed:"+(endSP - startSP));
			StreamSource source = new StreamSource(E2BUtil.replaceE2BCodes(in));
			// creation of transform source
			StreamSource transformSource = new StreamSource(
					E2BFileOutput.class
							.getResourceAsStream("/E2Bstyle.xsl"));
			PrintWriter out = response.getWriter();
			StreamResult result = new StreamResult(out);
			TransformerFactory tFactory = TransformerFactory
					.newInstance();
			Transformer transformer = tFactory
					.newTransformer(transformSource);
			response.setContentType("application/rtf");
			response.setHeader("Cache-Control",
					Constants.CACHE_CONTROL);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + URIUtil.encodeQuery(fileNm + ".rtf"));
			transformer.transform(source, result);
			out.flush();
			out.close();
		}catch (Exception e) {
			E2BUtil.getE2BError(response, e);
		}
	}
	
	public static void outputE2BReject(String fileName,
			String sPURL, ExternalDocuments externalDocs, 
			SimpleJdbcTemplate template, DataSource ds, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {
			
			if (request.getParameter("nack") != null) {
				if(request.getParameter("nack").equalsIgnoreCase("Y"))
				{	
					String nackFileName = "NACK_" + fileName;
					//PUSH NACK FILE TO OUTBOUND
					String fileContents = E2BRejectOutput.writeContentsToFile(SPUtil.getSharePointDocument(externalDocs.getFileURL()),
							externalDocs.getRejectReason(), "02");
					//PUSH TO GROUP INBOX
					SPResponse sPResponse = GroupInboxService.uploadToGroupInbox(request, nackFileName, fileContents);
					if(sPResponse.getSuccess().length() > 0) {
						if(Boolean.valueOf(sPResponse.getSuccess())) {
							
							log.info("Success " + Boolean.valueOf(sPResponse.getSuccess()));
							//CALL REJECT FOR NACK
							log.info("sPResponse.getFileId() -> " + sPResponse.getFileId());
							RejectService.reject(sPResponse.getFileId());
							
							OutboundService.outBound(fileContents, sPResponse.getFileName());
							
							//INSERT NACK DETAILS TO EXTERNAL_DOCUMENT TABLE
							ExternalDocuments extDocs = externalDocs;
							extDocs.setDocumentName(nackFileName);
							log.info("extDocs.toString() -> " + extDocs.toString());
							int insertId = SPUtil.insertDocumentStatus(extDocs, template, ds);
							log.info("insertId -> " + insertId);
							
							//REJECT SERVICE FOR ORGINAL FILE
							log.info("externalDocs.getFileID() -> " + externalDocs.getFileID());
							RejectService.reject(externalDocs.getFileID());							
							
							//UPDATE DETAILS FOR ORGINAL FILE TO EXTERNAL_DOCUMENT TABLE
							log.info("externalDocs.toString() -> " + externalDocs.toString());
							int updateId = SPUtil.updateDocumentStatus(externalDocs, template, ds);
							log.info("updateid -> " + updateId);						
							
						}else {
							log.info("Failure " + Boolean.valueOf(sPResponse.getSuccess()));
						}
					}
				}else {
					log.info("request.getParameter(nack) -> " + request.getParameter("nack"));
					//REJECT SERVICE FOR ORGINAL FILE
					log.info("externalDocs.getFileID() -> " + externalDocs.getFileID());
					RejectService.reject(externalDocs.getFileID());
					
					//UPDATE DETAILS FOR ORGINAL FILE TO EXTERNAL_DOCUMENT TABLE
					log.info("externalDocs.toString() -> " + externalDocs.toString());
					int updateId = SPUtil.updateDocumentStatus(externalDocs, template, ds);
					log.info("updateid -> " + updateId);
				}
			}else {
				log.info("nack parameter not found in request");
			}
			
		}catch (Exception e) {
			E2BUtil.getE2BError(response, e);
		}
	}
	
	public static void outputOthers(String fileName, 
			HttpServletResponse response, String sPURL) throws Exception{
		
		try {
			InputStream in 	= SPUtil.getSharePointDocument(sPURL);

			response.setContentType("application/download");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + URIUtil.encodeQuery(fileName));
			response.getOutputStream().write(getBytes(in));
			response.getOutputStream().flush();
			
		}catch (Exception e) {
			E2BUtil.getE2BError(response, e);
		}
	}
	
	public static byte[] getBytes(InputStream in) throws Exception {

		int len;
		int size = 1024;
		byte[] buf;

		if (in instanceof ByteArrayInputStream) {
			size = in.available();
			buf = new byte[size];
			len = in.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			while ((len = in.read(buf, 0, size)) != -1)
				bos.write(buf, 0, len);
			buf = bos.toByteArray();
		}
		return buf;
	}
}