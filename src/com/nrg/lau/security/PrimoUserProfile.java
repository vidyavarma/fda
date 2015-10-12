package com.nrg.lau.security;

import java.io.CharArrayWriter;
import java.io.Reader;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import com.nrg.lau.beans.LauUsers;
import com.nrg.lau.commons.Constants;
import com.nrg.lau.dao.CommonDAO;


public class PrimoUserProfile {
	
	private static Logger log = Logger.getLogger(PrimoUserProfile.class);
	private LauUsers rtnUsers = null;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String loadUserInSession(HttpServletRequest request, 
			String uid) throws Exception{
		
		ApplicationContext ctx 	= 	AppContext.getApplicationContext();
		DataSource ds			= (DataSource)ctx.getBean("dataSource");
		
		HashMap errorMap 		= (HashMap)ctx.getBean("userLoginErrorMap");		
		String buffer			= "";
		buffer =	getUserProfile(ds, uid);
		log.info(buffer);
		
		try {
			rtnUsers = new LauUsers();
			getUserDetailsFromXML(buffer);
			log.info("USER_ID from xml -> " + rtnUsers.getUSER_ID());
		} catch (Exception e) {							
			log.error("Failed to get user from xml " + e);
	        throw new AuthenticationCredentialsNotFoundException("getUserDetailsFromXML() -> Failed to get user from xml");
		}	
		
		if(rtnUsers != null && rtnUsers.getUSER_ID() != "") {	
			
			//After successful login set user details
			//and user groups to session as an Object [lauUsers].
			uid = rtnUsers.getUSER_ID();
			HttpSession session  = request.getSession();
			LauUsers lauUsers 	= new LauUsers();
			lauUsers.setUSER_ID(rtnUsers.getUSER_ID());
			lauUsers.setUSER_NAME(rtnUsers.getUSER_ID());    
			lauUsers.setUSER_GROUP_ID(CommonDAO.getUserGroup(rtnUsers.getUSER_ID(),null, ds));
			session.setAttribute("lauUsers", lauUsers);      					

			String temp = 	buffer.substring(0, buffer.indexOf(Constants.FLEX_XML_END_ROW ));
			temp		+= 	Constants.FLEX_XML_JSESSION;
			temp		+= 	session.getId();
			temp		+= 	Constants.FLEX_XML_END_JSESSION;
			temp		+= 	Constants.FLEX_XML_END_ROW;
			temp		+= 	Constants.FLEX_XML_END_ROWSET;   					
			session.setAttribute("flexProfile", temp);  
			
			log.info("Primo User -> " + uid);
			log.info("PrimoUserProfile - load user in session() -> LAU user connected!");
			
		}else {
			errorMap.put(request.getSession().getId(),"User(" + rtnUsers.getUSER_ID() + ") do not have a PRIMO account or your account has been disabled. Please contact your application administrator.");
            log.error("Not a valid Primo user!");
            throw new AuthenticationCredentialsNotFoundException("PrimoUserProfile - load user in session() -> Database - Not a Primo user!");
		} 
		
		return uid;
		
	}
	
	private String getUserProfile(DataSource ds,String uid) {
		StringBuffer tmp 	= null;
		Connection con 		= null;

		try {
			
			con 		= ds.getConnection();
			String sql 	= "{call ? := LAU_UTL.getUserProfileByLoginID(?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2, uid);					
			log.info(sql);
			stmt.execute();
			Clob clob = stmt.getClob(1);
			
			if (clob != null) {
				tmp = new StringBuffer();
				Reader reader = clob.getCharacterStream();
				CharArrayWriter writer = new CharArrayWriter();
				int i = -1;
				while ((i = reader.read()) != -1) {
					writer.write(i);
				}
				tmp.append(new String(writer.toCharArray()));
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
		log.info("getUserProfile:beofre return ");
		return tmp != null ? tmp.toString() : "";
	}
	
	private void getUserDetailsFromXML(String xml) throws Exception{
		
		String mainXmlTag = "ROWSET/ROW";
		Digester digester = new Digester();
        digester.push(this);

        digester.addObjectCreate( mainXmlTag, LauUsers.class );	        
        digester.addSetProperties( mainXmlTag+"/USER/", "USER_ID" ,"USER_ID");
        digester.addSetNext( mainXmlTag, "addUserProfileData" );		
		digester.parse(new StringReader(xml));
        
	}
	
	public void addUserProfileData(LauUsers users ) {		
		rtnUsers =  users;	  	
	}
	
}
