package com.nrg.lau.render;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.View;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.lob.LobHandler;


public class GetAttachmentAction implements View {
	
	private static Logger log = Logger.getLogger(GetAttachmentAction.class);
	private DataSource ds;	
	private LobHandler lobHandler;
	private SimpleJdbcTemplate template;
	
	public String getContentType() {
		return "text/xml";
	}
	
	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("ENTER - GetAttachmentAction render()");
		String attachId = "";
		
		if(request.getParameter("attachmentId") != null)	{
			getData(response, request.getParameter("attachmentId") );
			attachId = request.getParameter("attachmentId");
		}
		log.info("attachId---->"+attachId); 
		int id = 0;
		String status = "VIEWED";
		try {
			   if(ds != null)
			   {
				    template = new SimpleJdbcTemplate(ds);
			   }
			    id = template.update(SQL_UPDATE_STRING,new Object[] {
			    		status,attachId});
			    log.info("id---->"+id); 
			}catch (Exception e) {
			   log.info("GetAttachmentAction() failed: " + e.getMessage());
			   log.error(e);
			}		
		log.info("EXIT - GetAttachmentAction render()");
		
	}
	
	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_REPORT_ATTACHMENTS SET VIEW_STATUS=? WHERE ATTACHMENT_ID=?";
	
		
	public LobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}
	
	private void getData(HttpServletResponse response, String attachmentId) {
	
		Connection con				= null;
		InputStream blobInputStream	= null;
		
		try {
			
			con 			= ds.getConnection();
			con.setAutoCommit(false);
			String sql 		= " SELECT NVL(A.ATTACHMENT,B.GENERATED_LETTER_BINARY) AS ATTACHMENT,  A.FILE_NAME,  A.DOCUMENT_NAME,"+
			 " A.LAU_GENERATED_LETTER_ID,  A.ATTACHMENT	FROM LAU_REPORT_ATTACHMENTS A,	  LAU_GENERATED_LETTERS B"+
			" WHERE A.ATTACHMENT_ID  = " + attachmentId + "	AND A.LAU_GENERATED_LETTER_ID = B.LAU_GENERATED_LETTER_ID(+)";
			
			
			Statement pstmt = null;
			ResultSet rs 	= null;
			int bytesRead	= 0;
			
			int chunkSize;

			pstmt 	= con.createStatement(); 
			rs 		= pstmt.executeQuery(sql);
		//	weblogic.jdbc.wrapper.ResultSet wlsResultSet = (weblogic.jdbc.wrapper.ResultSet) rs;
			OracleResultSet oracleResultSet;
			if(rs != null)
			{
				try
				{
					while (rs.next()) {	
						
						response.setContentType("application/download");
						response.setHeader("Content-Transfer-Encoding", "binary");
						response.setHeader("Content-Disposition", "attachment; filename=" + rs.getString("FILE_NAME"));				
					//	response.setHeader("Cache-Control", "no cache");
					//	response.setHeader("Cache-Control", "no store");
					//	response.setHeader("Pragma", "no cache");				
					//	oracleResultSet = (OracleResultSet) rs;
						log.info(rs.findColumn("ATTACHMENT"));
						byte[] buffer = lobHandler.getBlobAsBytes(rs,"ATTACHMENT");
						bytesRead = buffer.length;
						log.info("blobLen: "+bytesRead);
						log.info("Start reading attachment");
						blobInputStream = lobHandler.getBlobAsBinaryStream(rs,"ATTACHMENT");
						//blob = oracleResultSet.getBLOB("ATTACHMENT");
						log.info("inputStream: "+blobInputStream.toString());
						log.info("End reading attachment");
					
					
					//	byte[] buffer = new byte[10000000];
						int blobLen = blobInputStream.read(buffer,0,bytesRead);
					
						response.getOutputStream().write(buffer, 0, blobLen);
					//	while ((bytesRead = blobInputStream.read(buffer)) != -1) {
					//		 response.getOutputStream().write(buffer, 0, bytesRead);
					//	}
						
						response.getOutputStream().flush();
						response.getOutputStream().close();
						
					}					
				}finally {
					if (rs != null) rs.close();
					if (pstmt != null) pstmt.close();
				}
			}				
			//con.commit();            
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e, e);			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error(e, e);
			}
		}
	}
	
	 public static byte[] getBytes(InputStream is) throws IOException {

		    int len;
		    int size = 1024;
		    byte[] buf;

		    if (is instanceof ByteArrayInputStream) {
		      size = is.available();
		      buf = new byte[size];
		      len = is.read(buf, 0, size);
		    } else {
		      ByteArrayOutputStream bos = new ByteArrayOutputStream();
		      buf = new byte[size];
		      while ((len = is.read(buf, 0, size)) != -1)
		        bos.write(buf, 0, len);
		      buf = bos.toByteArray();
		    }
		    return buf;
		  }
	
	public void setDataSource(DataSource dataSource) {
		this.template 	= new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
	}
}
