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
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import oracle.jdbc.OracleTypes;

import org.apache.fop.apps.FOPException;
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
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;

@SuppressWarnings("unused")
public class GetReportPDFAction implements View {
	
	private static Logger log = Logger.getLogger(GetReportPDFAction.class);
	private DataSource ds;	
	private String userId	= "";
	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - GetReportPDFAction render()");
		log.info("ENTER - new pdf config file used()");
		userId = CommonDAO.getUSERID(request);
		if(request.getParameter("reportId") != null)	{
			
			Reader buffer = getData(request.getParameter("reportId"));
			if(buffer != null)	{
				
				StreamSource source = new StreamSource(buffer);
				
				//StreamSource source = new StreamSource(GetReportPDFAction.class.getResourceAsStream("/report_2.xml"));
				// creation of transform source
				StreamSource transformSource = new StreamSource(GetReportPDFAction.class.getResourceAsStream("/PRIMOReport.xsl"));
				// create an instance of fop factory
				FopFactory fopFactory = FopFactory.newInstance();
				DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
				Configuration cfg = cfgBuilder.build( new StreamSource(GetReportPDFAction.class.getResourceAsStream("/fopconfig.xml")).getInputStream());

				fopFactory.setUserConfig(cfg);


				// a user agent is needed for transformation
				FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
				// to store output
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();

				Transformer xslfoTransformer;
				try
				{
					xslfoTransformer = getTransformer(transformSource);
					// Construct fop with desired output format
				    Fop fop;
					try
					{
						fop = fopFactory.newFop
							(MimeConstants.MIME_PDF, foUserAgent, outStream);
						// Resulting SAX events (the generated FO) 
						// must be piped through to FOP
		                Result res = new SAXResult(fop.getDefaultHandler());

						// Start XSLT transformation and FOP processing
						try
						{
					        // everything will happen here..
							xslfoTransformer.transform(source, res);					

							// to write the content to out put stream
							byte[] pdfBytes = outStream.toByteArray();
		                    response.setContentLength(pdfBytes.length);
		                    response.setContentType("application/pdf");
		                    response.addHeader("Content-Disposition","attachment;filename=primo.pdf");
				            response.getOutputStream().write(pdfBytes);
				            response.getOutputStream().flush();
						}
						catch (TransformerException e) {
							log.error(e);
							throw e;							
						}
					} catch (FOPException e) {
						log.error(e);
						throw e;						
					}
				}catch (Exception e)	{
					log.error(e);
					response.setContentType(Constants.CONTENT_TYPE);
					response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
					PrintWriter pw = response.getWriter();
					XMLException.xmlError(e, e.getMessage());
					pw.flush();
					pw.close();
				}
			} else 	{
				response.setContentType(Constants.CONTENT_TYPE);
				response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
				PrintWriter pw = response.getWriter();
				XMLException.status("F", "Database Exception");
				pw.flush();
				pw.close();
			}
			
		}	else {
			response.setContentType(Constants.CONTENT_TYPE);
			response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
			PrintWriter pw = response.getWriter();
			pw.write(XMLException.status("F", "Report Id not found in request"));
			pw.flush();
			pw.close();
		}
		
		
		log.info("EXIT - GetReportPDFAction render()");
	}

	public void setDataSource(DataSource dataSource) {		
		this.ds = dataSource;
		log.info("GetReportDataAction - Initialize db template()");
	}

	private Reader getData(String reportId) {

		StringBuffer tmp 	= new StringBuffer();
		Connection con 		= null;
		Reader reader  = null;

		try {
			
			con 		= this.ds.getConnection();
			String sql 	= "{call ? := PrintReportXML(?,?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2, reportId);		
			stmt.setString(3,userId);
			log.info(sql);
			stmt.execute();
			Clob clob = stmt.getClob(1);
			
			if (clob != null) {
				 reader = clob.getCharacterStream();
				
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
		
		return reader != null ? reader : null;
	
	}
	
	private Transformer getTransformer(StreamSource streamSource) throws Exception{
		
		// setup the xslt transformer
		net.sf.saxon.TransformerFactoryImpl impl = new net.sf.saxon.TransformerFactoryImpl();

		return impl.newTransformer(streamSource);
	}
}
