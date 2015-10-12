package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauUserRoleCollection;
import com.nrg.lau.beans.LauUserRoles;
import com.nrg.lau.beans.LauUsers;
import com.nrg.lau.commons.IReportsConstants;


public class LauUserRolesSetDAO {
	
	private static Logger log					= Logger.getLogger(LauUserRolesSetDAO.class);
	private ArrayList<LauUsers> users			= null;
	private ArrayList<LauUserRoles> userRoles 	= null;

	public void save(HttpServletRequest request, SimpleJdbcTemplate template) throws Exception {
		String userId = CommonDAO.getUSERID(request);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);

		if(request.getParameter(IReportsConstants.LAU_USERS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_USERS_PARAM_NAME).length() > 0) {
			
			log.info("LauUserRolesSetDAO save() LAU_USERS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_USERS_PARAM_NAME));
			//Create beans from Request XML
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_USERS_PARAM_NAME));
			Iterator<LauUsers> itr = users.iterator();
			while(itr.hasNext()) {
				LauUsers lauUsers = (LauUsers)itr.next();
				if(lauUsers.getTRANSACTION_TYPE().trim().equalsIgnoreCase("1") || 
						lauUsers.getTRANSACTION_TYPE().trim().equalsIgnoreCase("2")){
					log.info("LauUserRolesSetDAO - save() -> LauUsers[TRANSACTION_TYPE 1 & 2]: " + lauUsers.toString());
					updateUser(template, Integer.valueOf(lauUsers.getTRANSACTION_TYPE().trim()), lauUsers, request, userId,dt);
				}				
				Iterator<LauUserRoles> itrRoles = userRoles.iterator();
				while(itrRoles.hasNext()) {
					LauUserRoles lauRoles = (LauUserRoles)itrRoles.next();
					log.info("LauUserRolesSetDAO - save() -> LauUserRoles: " + lauRoles.toString());
					if(lauUsers.getUSER_ID().trim().equalsIgnoreCase(lauRoles.getUSER_ID().trim())) {
						updateUserRoles(template, Integer.valueOf(lauRoles.getTRANSACTION_TYPE().trim()), lauRoles,userId,dt);
					}
				}				
				//This is to make sure after child only parent is getting deleted
				if(lauUsers.getTRANSACTION_TYPE().trim().equalsIgnoreCase("0")){
					log.info("LauUserRolesSetDAO - save() -> LauUsers[TRANSACTION_TYPE 0]: " + lauUsers.toString());
					updateUser(template, Integer.valueOf(lauUsers.getTRANSACTION_TYPE().trim()), lauUsers, request
							,userId,dt);
				}
			}
			
		}
	}
	
	private void updateUser(SimpleJdbcTemplate template,int type,LauUsers lauUsers,
			HttpServletRequest request, String userId, java.sql.Timestamp dt) throws Exception {
		if(type == 0) {
			log.info("deleteing user and user roles");
			CommonDAO.setDbmsClientInfo(template,userId );
			template.update("DELETE FROM LAU_USER_ROLES WHERE USER_ID = ?", 
					new Object[]{lauUsers.getUSER_ID()});
			log.info("updateUser - TRANSACTION_TYPE [0{LAU_USER_ROLES}] -> " + lauUsers.getUSER_ID());
			CommonDAO.setDbmsClientInfo(template,userId );
			template.update("DELETE FROM LAU_USERS WHERE USER_ID = ?", 
				new Object[]{lauUsers.getUSER_ID()});
			log.info("updateUser - TRANSACTION_TYPE [0] -> " + lauUsers.getUSER_ID());
		} else if(type == 1) {
			template.update(SQL_LAU_USER_INSERT_STRING,getParameters(lauUsers, userId, dt));
			log.info("updateUser - TRANSACTION_TYPE [1] -> " + lauUsers.getUSER_ID());
		} else if(type == 2) {
			template.update(SQL_LAU_USERS_UPDATE_STRING,getParameters(lauUsers, userId, dt));
			log.info("updateUser - TRANSACTION_TYPE [2] -> " + lauUsers.getUSER_ID());
		}
	}
	
	private Object[] getParameters(LauUsers lauUsers,String userId, java.sql.Timestamp dt) throws Exception{
		return new Object[]{
			lauUsers.getUSER_GROUP_ID(),
			lauUsers.getUSER_NAME(),
			lauUsers.getUSER_EMAIL(),
			lauUsers.getUSER_DEPARTMENT(),
			lauUsers.getLDAP_EMAIL_ID(),
			lauUsers.getLDAP_CORPORATE_ID(),
			lauUsers.getAE_SYSTEM_USER_ID(),
			lauUsers.getINACTIVE_USER(),
			userId,
			dt,
			lauUsers.getUSER_ORGANIZATION(),
			lauUsers.getUSER_PHONE_NUMBER(),
			lauUsers.getUSER_COMMENTS(),
			lauUsers.getUSER_SIGNATURE(),
			lauUsers.getUSER_ID()
		};
	}
	
	private void updateUserRoles(SimpleJdbcTemplate template,int type,LauUserRoles lauUserRoles, String userId, java.sql.Timestamp dt
			) throws Exception {
		if(type == 0) {
			log.info("updateUserRoles ...before client info");
			CommonDAO.setDbmsClientInfo(template,userId );
			log.info("updateUserRoles ...after client info");
			int i = template.update("DELETE FROM LAU_USER_ROLES WHERE USER_ROLE = ? and USER_ID=? ", 
					new Object[]{lauUserRoles.getUSER_ROLE(),lauUserRoles.getUSER_ID()});
			log.info("updateUserRoles - TRANSACTION_TYPE [0] -> " + i + lauUserRoles.getUSER_ROLE() + "[" + lauUserRoles.getUSER_ID()  + "]");
		}else if(type == 1) {
			template.update(SQL_LAU_USER_ROLES_INSERT_STRING,  new Object[]{
				lauUserRoles.getUSER_ROLE(),
				lauUserRoles.getUSER_ID(),
				userId,
				dt
			});
			log.info("updateUserRoles - TRANSACTION_TYPE [1] -> " + lauUserRoles.getUSER_ROLE() + "[" + lauUserRoles.getUSER_ID()  + "]");
		}
	}
	
	private void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.addObjectCreate( mainXmlTag, LauUserRoleCollection.class);		
		digester.addObjectCreate(mainXmlTag + "/USER", LauUsers.class);
		digester.addSetProperties(mainXmlTag + "/USER");
		digester.addSetNext(mainXmlTag + "/USER", "addUsers");
		LauUserRoleCollection roleCollection  = (LauUserRoleCollection)digester.parse(new StringReader(xml));
		users		= roleCollection.getUsers();
				
		digester	= null;
		digester	= new Digester();
		digester.addObjectCreate( mainXmlTag, LauUserRoleCollection.class);		
		digester.addObjectCreate(mainXmlTag + "/USER/ROLE", LauUserRoles.class);
		digester.addSetProperties(mainXmlTag + "/USER/ROLE");		
		digester.addSetNext(mainXmlTag + "/USER/ROLE", "addUserRoles");
		roleCollection  = (LauUserRoleCollection)digester.parse(new StringReader(xml));
		userRoles 	= roleCollection.getUserRoles();	
		
	}
	
	//SQL Statements	
	private final String SQL_LAU_USERS_UPDATE_STRING = "UPDATE LAU_USERS SET USER_GROUP_ID=?," +
			"USER_NAME=?,USER_EMAIL=?,USER_DEPARTMENT=?,LDAP_EMAIL_ID=?,LDAP_CORPORATE_ID=?," +
			"AE_SYSTEM_USER_ID=?,INACTIVE_USER=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?,USER_ORGANIZATION=?,USER_PHONE_NUMBER=?,"
			+ "USER_COMMENTS=?,USER_SIGNATURE=? WHERE USER_ID=?";
	
	private final String SQL_LAU_USER_INSERT_STRING = "INSERT INTO LAU_USERS (USER_GROUP_ID," +
			"USER_NAME,USER_EMAIL,USER_DEPARTMENT,LDAP_EMAIL_ID,LDAP_CORPORATE_ID,AE_SYSTEM_USER_ID,INACTIVE_USER," +
			"UPDATE_USER_ID,UPDATE_TIMESTAMP,USER_ORGANIZATION,USER_PHONE_NUMBER,USER_COMMENTS,USER_SIGNATURE,USER_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private final String SQL_LAU_USER_ROLES_INSERT_STRING = "INSERT INTO LAU_USER_ROLES(USER_ROLE," +
			"USER_ID,UPDATE_USER_ID,UPDATE_TIMESTAMP) VALUES (?,?,?,?)";
}