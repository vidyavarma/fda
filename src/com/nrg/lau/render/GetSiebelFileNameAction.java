package com.nrg.lau.render;

import java.io.PrintWriter;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.LauReferenceListDetailsGetDAO;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;

public class GetSiebelFileNameAction implements View {
	
	private static Logger log = Logger.getLogger(GetSiebelFileNameAction.class);
	private SimpleJdbcTemplate template;
	
	public String getContentType() {
		return "text/xml";
	}
	
	//Success XML - <ROWSET><ROW><FILE_NAME/></FILE_TYPE></CREATED_BY>
	//</CREATED_DATE></ATTACHED_BY></ATTACHED_DATE></INDICATOR></ROW></ROWSET>
	//Failure XML - XMLException.xmlError(Exception e, String message)
	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("ENTER - GetSiebelFileNameAction render()");
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
	    PrintWriter pw = response.getWriter();

		if(request.getParameter(Constants.SIEBEL_REQ_PARAMS) != null)	{
			pw.write(getData(request.getParameter(Constants.SIEBEL_REQ_PARAMS).toString()).toString());
		} else {
			pw.write(XMLException.xmlError(new NoSuchElementException(Constants.SIEBEL_REQ_PARAMS + " not found in request"),
					Constants.SIEBEL_REQ_PARAMS + " not found in request"));
		}
			
		pw.flush();
		pw.close();	
		
		log.info("EXIT - GetSiebelFileNameAction render()");		
	}
	
	private StringBuffer getData(String contactId) {
		
		StringBuffer buffer	= new StringBuffer();
		SiebelDataBean dataBean = null;
		try {
			
			System.setProperty("file.encoding", Constants.SIEBEL_FILE_ENCODING);
			dataBean = new SiebelDataBean();
			LauReferenceListDetailsGetDAO lauReferenceListDetailsGetDAO = new LauReferenceListDetailsGetDAO();
			Map<String, String> listMap = lauReferenceListDetailsGetDAO.getCodeValuesAsMap(template, Constants.SIEBEL_FEFLIST_NAME);			 

			System.out.println(listMap.get(Constants.SIEBEL_FEFLIST_URL));  

			String siebelUrl = listMap.get(Constants.SIEBEL_FEFLIST_URL);
			String siebelUser = listMap.get(Constants.SIEBEL_FEFLIST_USER);
			String siebelPwd = listMap.get(Constants.SIEBEL_FEFLIST_PASSWORD);
			dataBean = new SiebelDataBean();
		//	dataBean.login(Constants.SIEBEL_CONNECT_URL, Constants.SIEBEL_USER, Constants.SIEBEL_USER);
			log.info("axis connection info"+"siebelUrl:"+siebelUrl+"siebelUser:"+siebelUser);
			dataBean.login(siebelUrl, siebelUser, siebelPwd );
			log.info("connected to databean");	
			
			
			SiebelBusObject busObject	= dataBean.getBusObject(Constants.SIEBEL_CONTACT_OBJ);
			SiebelBusComp busComp 		= busObject.getBusComp(Constants.SIEBEL_CONTACT_COMP); 
			busComp.setViewMode(3);
			busComp.clearToQuery();
			busComp.activateField(Constants.SIEBEL_CONTACT_ID);
			busComp.activateField("Attachment Type BIIB");
			busComp.activateField("Created By Name BIIB");
			busComp.activateField("Attached By Name BIIB");
			busComp.activateField("Attached Date BIIB");
			busComp.activateField("AE Indicator BIIB");
			busComp.activateField("Created");
			
			busComp.setSearchSpec(Constants.SIEBEL_CONTACT_ID, contactId);
			busComp.setSortSpec(Constants.SIEBEL_CONTACT_DESC);
			busComp.executeQuery(true);
			if (busComp.firstRecord()) {
				buffer.append(Constants.FLEX_XML_ROWSET);
				buffer.append(appendFileDetails(busComp).toString());				
				while (busComp.nextRecord()){
					buffer.append(appendFileDetails(busComp).toString());
				}
				buffer.append(Constants.FLEX_XML_END_ROWSET);				
			}
			log.info("Siebel File Name XML -> " + buffer.toString());
			dataBean.logoff();
		}catch (Exception e) {
			buffer.append(XMLException.xmlError(e, "Failed to retrieve siebel file names"));
			try {
				dataBean.logoff();
			} catch (SiebelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return buffer;
	}
	
	private StringBuffer appendFileDetails(SiebelBusComp busComp) 
						throws Exception {
		StringBuffer temp = new StringBuffer();
		temp.append(Constants.FLEX_XML_START_ROW);
		temp.append("<FILE_NAME>");
		temp.append(busComp.getFieldValue(Constants.SIEBEL_CONTACT_FL_NM));
		temp.append("</FILE_NAME>");
		temp.append("<FILE_TYPE>");
		temp.append(busComp.getFieldValue("Attachment Type BIIB"));
		temp.append("</FILE_TYPE>");
		temp.append("<CREATED_BY>");
		temp.append(busComp.getFieldValue("Created By Name BIIB"));
		temp.append("</CREATED_BY>");
		temp.append("<CREATED_DATE>");
		temp.append(busComp.getFieldValue("Created"));
		temp.append("</CREATED_DATE>");
		temp.append("<ATTACHED_BY>");
		temp.append(busComp.getFieldValue("Attached By Name BIIB"));
		temp.append("</ATTACHED_BY>");
		temp.append("<ATTACHED_DATE>");
		temp.append(busComp.getFieldValue("Attached Date BIIB"));
		temp.append("</ATTACHED_DATE>");
		temp.append("<INDICATOR>");
		temp.append(busComp.getFieldValue("AE Indicator BIIB"));
		temp.append("</INDICATOR>");				
		temp.append(Constants.FLEX_XML_END_ROW);	
		return temp;
	}
	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
	}
}
