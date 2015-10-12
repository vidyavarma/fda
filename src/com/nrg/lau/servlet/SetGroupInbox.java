package com.nrg.lau.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauGroupInbox;
import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.commons.Util;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.security.AppContext;
import com.nrg.lau.utils.ReadConfig;

import javax.sql.DataSource;

public class SetGroupInbox extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Logger log	= Logger.getLogger(SetGroupInbox.class);
	private Vector<LauGroupInbox> reports		= null;
	private LauGroupInbox lauGroup 	= null;
	private SimpleJdbcTemplate template;
	java.sql.Timestamp dt  = null; 
    
	protected void finalize() throws Throwable {
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	
    public SetGroupInbox() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Accepts only POST
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw 	= response.getWriter();
	    int validate	= -1;
	    
		if(request.getParameter("userName") != null && 
				request.getParameter("userPassword") != null) {
		
			try {
				ResourceBundle resource 	= ReadConfig.getMessage();
				log.info("group user password:"+request.getParameter("userName")+request.getParameter("userPassword"));
				if((resource.getString("SP_USER_NAME").equals(request.getParameter("userName"))) &&
						resource.getString("SP_USER_PASS").equals(request.getParameter("userPassword")))
				{ 
					log.info("group inbox authenticated");
					validate = 0;
				}
				/**Util.LDAPBind(request.getParameter("userName"), 
					request.getParameter("userPassword"));			**/
			}catch (Exception e) {
				log.error(e);
				pw.write(XMLException.xmlError(e, "Invalid username/password"));		
				pw.flush();
				pw.close();
			}
			
				if(validate == 0)	{
				
					if(request.getParameter(IReportsConstants.LAU_GROUP_INBOX_PARAM_NAME) != null && 
						request.getParameter(IReportsConstants.LAU_GROUP_INBOX_PARAM_NAME).length() > 0 ) {
									
						try {
							
							ApplicationContext ctx 	= 	AppContext.getApplicationContext();
							DataSource ds 	= 	(DataSource)ctx.getBean("dataSource");
							template		=	new SimpleJdbcTemplate(ds);
							dt				=	CommonDAO.getTimestamp(template);
							
							log.info("SetGroupInbox doPost() LAU_GROUP_INBOX_PARAM_NAME -> " 
								+ request.getParameter(IReportsConstants.LAU_GROUP_INBOX_PARAM_NAME));
						
							//Create LauGroupInbox beans from XML Request
							createBeansFromXml(request.getParameter(IReportsConstants.LAU_GROUP_INBOX_PARAM_NAME));
							Iterator<LauGroupInbox> itr = this.reports.iterator();
							while(itr.hasNext())	{
								LauGroupInbox lauGroupInbox = (LauGroupInbox)itr.next();
								log.info("itr.hasNext() -> " + lauGroupInbox.toString());
								this.lauGroup	= null;
								this.lauGroup	= lauGroupInbox;
								insert(template);					
							}
							
							pw.write(XMLException.status("S","Successfuly Inserted"));		
							pw.flush();
							pw.close();
						
						}catch (Exception e) {
							log.error(e);
							pw.write(XMLException.xmlError(e, "F"));		
							pw.flush();
							pw.close();
						}	
						
					}else	{
							
						pw.write(XMLException.status("F", IReportsConstants.LAU_GROUP_INBOX_PARAM_NAME + " not found in request"));		
						pw.flush();
						pw.close();
					}
				}
				else
				{
					pw.write(XMLException.status("F", "Invalid username/password"));
					pw.flush();
					pw.close();
				}
			}
		else {
			pw.write(XMLException.status("F", "UserName/Password not found in request"));		
			pw.flush();
			pw.close();
		}
	}
	
	public void insert(SimpleJdbcTemplate template)
								throws Exception {
		int id = 0;
		long groupId = CommonDAO.getPrimaryKey(template);
		this.lauGroup.setEXTERNAL_DOCUMENT_ID(groupId);
		log.info("Generated Primary Key for EXTERNAL_DOCUMENT_ID ->" + groupId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("SetGroupInbox insert() ID -> " + id);
	
	}
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_EXTERNAL_DOCUMENTS(DOCUMENT_NAME,DOCUMENT_DESCRIPTION,FILE_TYPE," +
			"FILE_NAME,OBJECT_ID,DOCUMENT_STATUS,DOCUMENT_URL,RECEIVED_DATE,RECEIVED_FROM,USER_GROUP_ID,UPDATE_TIMESTAMP,EXTERNAL_DOCUMENT_ID,UPDATE_USER_ID) " +
			"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,user)";
	
	private Object[] getParameters()
	{
		LauGroupInbox lauGroupInbox = this.lauGroup; 
		return new Object[]{
				lauGroupInbox.getDOCUMENT_NAME(),
				lauGroupInbox.getDOCUMENT_DESCRIPTION(),
				lauGroupInbox.getFILE_TYPE(),
				lauGroupInbox.getFILE_NAME(),
				lauGroupInbox.getOBJECT_ID(),
				lauGroupInbox.getDOCUMENT_STATUS(),
				lauGroupInbox.getDOCUMENT_URL(),
				lauGroupInbox.getRECEIVED_DATE(),
				lauGroupInbox.getRECEIVED_FROM(),
				lauGroupInbox.getUSER_GROUP_ID(),
				dt,
				lauGroupInbox.getEXTERNAL_DOCUMENT_ID()
		};
	}
	
	public void createBeansFromXml(String xml) throws Exception {
		
		reports				= new Vector<LauGroupInbox>();
		Digester digester	= new Digester();
		digester.push(this);
		String mainXmlTag = "ROWSET/ROW";
		digester.addObjectCreate( mainXmlTag, LauGroupInbox.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/DOCUMENT_NAME", "DOCUMENT_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/DOCUMENT_DESCRIPTION", "DOCUMENT_DESCRIPTION" );
		digester.addBeanPropertySetter( mainXmlTag+"/FILE_TYPE", "FILE_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/FILE_NAME", "FILE_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/OBJECT_ID", "OBJECT_ID" );	
		digester.addBeanPropertySetter( mainXmlTag+"/DOCUMENT_STATUS", "DOCUMENT_STATUS" );
		digester.addBeanPropertySetter( mainXmlTag+"/DOCUMENT_URL", "DOCUMENT_URL" );
		digester.addBeanPropertySetter( mainXmlTag+"/RECEIVED_DATE", "RECEIVED_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/RECEIVED_FROM", "RECEIVED_FROM" );
		digester.addBeanPropertySetter( mainXmlTag+"/USER_GROUP_ID", "USER_GROUP_ID" );
				
		//Move to next LauGroupInbox
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauGroupInbox lauGroupInbox) {
		reports.add(lauGroupInbox);
		log.info(lauGroupInbox.toString());
	}

}
