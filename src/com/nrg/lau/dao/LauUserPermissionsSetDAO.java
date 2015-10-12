package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauRolePermissions;
import com.nrg.lau.beans.LauUserPermissionsCollection;
import com.nrg.lau.beans.LauUserRoles;
import com.nrg.lau.commons.IReportsConstants;


public class LauUserPermissionsSetDAO {
	
	private static Logger log							= Logger.getLogger(LauUserPermissionsSetDAO.class);
	private ArrayList<LauUserRoles> userRoles 			= null;
	private ArrayList<LauRolePermissions> premission	= null;
	
	public void save(HttpServletRequest request, SimpleJdbcTemplate template) throws Exception {
		if(request.getParameter(IReportsConstants.LAU_ROLES_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_ROLES_PARAM_NAME).length() > 0) {
			String userId = CommonDAO.getUSERID(request);
			java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
			log.info("LauUserPermissionsSetDAO save() LAU_ROLES_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_ROLES_PARAM_NAME));
			//Create beans from Request XML
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_ROLES_PARAM_NAME));
			Iterator<LauUserRoles> itr = userRoles.iterator();
			while(itr.hasNext()) {
				LauUserRoles lauRoles = (LauUserRoles)itr.next();
				if(lauRoles.getTRANSACTION_TYPE().trim().equalsIgnoreCase("1") || 
						lauRoles.getTRANSACTION_TYPE().trim().equalsIgnoreCase("2")){
					log.info("LauUserPermissionsSetDAO - save() -> LauUserRoles[TRANSACTION_TYPE 1 & 2]: " + lauRoles.toString());
					updateRole(template, Integer.valueOf(lauRoles.getTRANSACTION_TYPE().trim()), lauRoles, request,userId,  dt);
				}				
				Iterator<LauRolePermissions> itrPermissions = premission.iterator();
				while(itrPermissions.hasNext()) {
					LauRolePermissions lauPermissions = (LauRolePermissions)itrPermissions.next();
					log.info("LauUserPermissionsSetDAO - save() -> LauRolePermissions: " + lauPermissions.toString());
					if(lauRoles.getUSER_ROLE().trim().equalsIgnoreCase(lauPermissions.getUSER_ROLE().trim())) {
						updateUserPermissions(template, Integer.valueOf(lauPermissions.getTRANSACTION_TYPE().trim()), lauPermissions,userId,dt);
					}
				}				
				//This is to make sure after child only parent is getting deleted
				if(lauRoles.getTRANSACTION_TYPE().trim().equalsIgnoreCase("0")){
					log.info("LauUserPermissionsSetDAO - save() -> LauUserRoles[TRANSACTION_TYPE 0]: " + lauRoles.toString());
					updateRole(template, Integer.valueOf(lauRoles.getTRANSACTION_TYPE().trim()), lauRoles, request, userId,  dt);
				}
			}
			
		}
	}
	
	private void updateRole(SimpleJdbcTemplate template,int type,LauUserRoles lauUserRoles,
			HttpServletRequest request, String userId, java.sql.Timestamp dt) throws Exception {
		if(type == 0) {
			CommonDAO.setDbmsClientInfo(template,userId );
			template.update("DELETE FROM LAU_ROLE_PERMISSIONS WHERE USER_ROLE = ?", 
					new Object[]{lauUserRoles.getUSER_ROLE()});
			log.info("updateRole - TRANSACTION_TYPE [0{LAU_ROLE_PERMISSIONS}] -> " + lauUserRoles.getUSER_ROLE());
			
			template.update("DELETE FROM LAU_ROLES WHERE USER_ROLE = ?", 
				new Object[]{lauUserRoles.getUSER_ROLE()});
			log.info("updateRole - TRANSACTION_TYPE [0{LAU_ROLES}] -> " + lauUserRoles.getUSER_ROLE());
		} else if(type == 1) {
			template.update(SQL_LAU_ROLES_INSERT_STRING,getParameters(lauUserRoles,userId,dt));
			log.info("updateRole - TRANSACTION_TYPE [1] -> " + lauUserRoles.getUSER_ROLE());
		} else if(type == 2) {
			template.update(SQL_LAU_ROLES_UPDATE_STRING,getParameters(lauUserRoles,userId,dt));
			log.info("updateRole - TRANSACTION_TYPE [2] -> " + lauUserRoles.getUSER_ROLE());
		}
	}
	
	private Object[] getParameters(LauUserRoles lauRoles,String userId, java.sql.Timestamp dt) throws Exception{
		return new Object[]{
			lauRoles.getUSER_ROLE_DESCRIPTION(),
			userId,
			dt,
			lauRoles.getUSER_ROLE()
		};
	}
	
	private void updateUserPermissions(SimpleJdbcTemplate template,int type,LauRolePermissions lauPermissions
			,String userId, java.sql.Timestamp dt) throws Exception {
		if(type == 0) {
			CommonDAO.setDbmsClientInfo(template,userId );
			template.update("DELETE FROM LAU_ROLE_PERMISSIONS WHERE APPLICATION_PERMISSION = ? and USER_ROLE=?", 
					new Object[]{lauPermissions.getAPPLICATION_PERMISSION(),lauPermissions.getUSER_ROLE()});
			log.info("updateUserPermissions - TRANSACTION_TYPE [0] -> " + lauPermissions.getUSER_ROLE() + "[" + lauPermissions.getAPPLICATION_PERMISSION()  + "]");
		}else if(type == 1) {
			template.update(SQL_LAU_ROLE_PERMISSIONS_INSERT_STRING,  new Object[]{
				lauPermissions.getAPPLICATION_PERMISSION(),
				lauPermissions.getUSER_ROLE(),
				userId,
				dt
			});
			log.info("updateUserPermissions - TRANSACTION_TYPE [1] -> " + lauPermissions.getUSER_ROLE() + "[" + lauPermissions.getAPPLICATION_PERMISSION()  + "]");
		}
	}
	
	private void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.addObjectCreate( mainXmlTag, LauUserPermissionsCollection.class);		
		digester.addObjectCreate(mainXmlTag + "/ROLE", LauUserRoles.class);
		digester.addSetProperties(mainXmlTag + "/ROLE");
		digester.addSetNext(mainXmlTag + "/ROLE", "addRoles");
		LauUserPermissionsCollection permissinsCollection  = (LauUserPermissionsCollection)digester.parse(new StringReader(xml));
		userRoles		= permissinsCollection.getRoles();
				
		digester	= null;
		digester	= new Digester();
		digester.addObjectCreate( mainXmlTag, LauUserPermissionsCollection.class);		
		digester.addObjectCreate(mainXmlTag + "/ROLE/PERMISSION", LauRolePermissions.class);
		digester.addSetProperties(mainXmlTag + "/ROLE/PERMISSION");		
		digester.addSetNext(mainXmlTag + "/ROLE/PERMISSION", "addRolePermissions");
		permissinsCollection  = (LauUserPermissionsCollection)digester.parse(new StringReader(xml));
		premission 	= permissinsCollection.getRolePermissions();	
		
	}
	
	//SQL Statements	
	private final String SQL_LAU_ROLES_UPDATE_STRING = "UPDATE LAU_ROLES SET USER_ROLE_DESCRIPTION=?," +
			"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE USER_ROLE=?";
	
	private final String SQL_LAU_ROLES_INSERT_STRING = "INSERT INTO LAU_ROLES (USER_ROLE_DESCRIPTION," +
			"UPDATE_USER_ID,UPDATE_TIMESTAMP,USER_ROLE) VALUES (?,?,?,?)";
	
	private final String SQL_LAU_ROLE_PERMISSIONS_INSERT_STRING = "INSERT INTO LAU_ROLE_PERMISSIONS(APPLICATION_PERMISSION," +
			"USER_ROLE,UPDATE_USER_ID,UPDATE_TIMESTAMP) VALUES (?,?,?,?)";
}
