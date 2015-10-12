package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauUserGroupAccess;
import com.nrg.lau.beans.LauUserGroupCollection;
import com.nrg.lau.beans.LauUserGroups;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.sharepoint.NewGroupService;
import com.nrg.lau.sharepoint.SPUtil;
import com.nrg.lau.utils.ReadConfig;

public class LauUserGroupSetDAO {
	private static Logger log							= Logger.getLogger(LauUserGroupSetDAO.class);
	private ArrayList<LauUserGroups> groups				= null;
	private ArrayList<LauUserGroupAccess> groupAccess 	= null;

	public void save(HttpServletRequest request, SimpleJdbcTemplate template) throws Exception {
	
		String userId = CommonDAO.getUSERID(request);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		if(request.getParameter(IReportsConstants.LAU_USER_GROUP_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_USER_GROUP_PARAM_NAME).length() > 0) {
			
			log.info("LauUserGroupSetDAO save() LAU_USER_GROUP_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_USER_GROUP_PARAM_NAME));
			
			//Create beans from Request XML
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_USER_GROUP_PARAM_NAME));
			Iterator<LauUserGroups> itr = groups.iterator();
			while(itr.hasNext()) {
				int cnt = 0;
				LauUserGroups lauGroups = (LauUserGroups)itr.next();
				if(lauGroups.getTRANSACTION_TYPE().trim().equalsIgnoreCase("1") || 
						lauGroups.getTRANSACTION_TYPE().trim().equalsIgnoreCase("2")){
					log.info("LauUserGroupSetDAO - save() -> LauUserGroups[TRANSACTION_TYPE 1 & 2]: " + lauGroups.toString());
					updateGroup(template, Integer.valueOf(lauGroups.getTRANSACTION_TYPE().trim()), lauGroups, request,userId,dt);
				}
				Iterator<LauUserGroupAccess> itrAcess = groupAccess.iterator();
				while(itrAcess.hasNext()) {
					LauUserGroupAccess lauAccess = (LauUserGroupAccess)itrAcess.next();
					log.info("LauUserGroupSetDAO - save() -> LauUserGroupAccess: " + lauAccess.toString());
					if(cnt == 0) {
						CommonDAO.setDbmsClientInfo(template,userId );
						template.update("DELETE FROM LAU_USER_GROUP_ACCESS WHERE USER_GROUP_ID = ?", 
								new Object[]{lauAccess.getUSER_GROUP_ID()});
						log.info("First time delete from LAU_USER_GROUP_ACCESS with USER_GROUP_ID -> " + lauAccess.getUSER_GROUP_ID() + "]");
						cnt++;
					}
					
					if(lauGroups.getUSER_GROUP_ID().trim().equalsIgnoreCase(lauAccess.getUSER_GROUP_ID().trim())) {
						updateGroupAccess(template, Integer.valueOf(lauAccess.getTRANSACTION_TYPE().trim()), lauAccess,userId,dt);
					}
				}
				//This is to make sure after child only parent is getting deleted
				if(lauGroups.getTRANSACTION_TYPE().trim().equalsIgnoreCase("0")){
					log.info("LauUserGroupSetDAO - save() -> LauUserGroups[TRANSACTION_TYPE 0]: " + lauGroups.toString());
					updateGroup(template, Integer.valueOf(lauGroups.getTRANSACTION_TYPE().trim()), lauGroups, request,userId,dt);
				}
			}		
			
		}else {
			log.info(IReportsConstants.LAU_USER_GROUP_PARAM_NAME + " not found in request");
		}
	}
	
	/**
	 * @param template
	 * @param type
	 * @param lauGroups
	 * @param request
	 * @param userId
	 * @param dt
	 * @throws Exception
	 */
	private void updateGroup(SimpleJdbcTemplate template,int type,LauUserGroups lauGroups,
			HttpServletRequest request,String userId, java.sql.Timestamp dt) throws Exception {
		if(type == 0) {
			CommonDAO.setDbmsClientInfo(template,userId );
			template.update("DELETE FROM LAU_USER_GROUP_ACCESS WHERE USER_GROUP_ID = ?", 
					new Object[]{lauGroups.getUSER_GROUP_ID()});
			log.info("updateGroup - TRANSACTION_TYPE [0{LAU_USER_GROUP_ACCESS}] -> " + lauGroups.getUSER_GROUP_ID());
			
			template.update("DELETE FROM LAU_USER_GROUPS WHERE USER_GROUP_ID = ?", 
				new Object[]{lauGroups.getUSER_GROUP_ID()});
			log.info("updateGroup - TRANSACTION_TYPE [0] -> " + lauGroups.getUSER_GROUP_ID());			
		} else if(type == 1) {
			template.update(SQL_LAU_USER_GROUPS_INSERT_STRING,getParameters(lauGroups,userId,dt));
			log.info("calling NewGroupService.newGroup");
			ResourceBundle resource 	= ReadConfig.getMessage();
			String checkExternalDocMngmtOption = resource.getString("ENABLE_EXTERNAL_DOC_MANAGEMENT");
			/* S.Abraham 04/25 checkExternalDocMngmtOption.trim().equalsIgnoreCase ADDED for fda**/
			if (checkExternalDocMngmtOption.trim().equalsIgnoreCase("Y")) {
				if (NewGroupService.newGroup(lauGroups.getUSER_GROUP_ID()) == false) {
					log.info("User Group Creation Failed: Call to Sharepoint Service Failed");
					throw new Exception("SharePoint NewGroupService failed");
				}
			}
			if (lauGroups.getREPORT_ID_MODIFIER().length() > 0)
			{

				template.update("begin LAU_UTL.createrepidmodseq(?); end;",new Object[] {lauGroups.getREPORT_ID_MODIFIER()});
				log.info("begin LAU_UTL.createrepidmodseq(?) -> " + lauGroups.getREPORT_ID_MODIFIER());
			}
			log.info("updateGroup - TRANSACTION_TYPE [1] -> " + lauGroups.getUSER_GROUP_ID());
		} else if(type == 2) {
			template.update(SQL_LAU_USER_GROUPS_UPDATE_STRING,getParameters(lauGroups,userId,dt));
			if (lauGroups.getREPORT_ID_MODIFIER().length() > 0)
			{
				template.update("begin LAU_UTL.createrepidmodseq(?); end;",new Object[] {lauGroups.getREPORT_ID_MODIFIER()});
				log.info("begin LAU_UTL.createrepidmodseq(?) -> " + lauGroups.getREPORT_ID_MODIFIER());
			}
			log.info("updateGroup - TRANSACTION_TYPE [2] -> " + lauGroups.getUSER_GROUP_ID());
		}
	}
	
	private Object[] getParameters(LauUserGroups lauGroups,String userId, java.sql.Timestamp dt) throws Exception{
		return new Object[]{
			lauGroups.getUSER_GROUP_DESCRIPTION(),
			lauGroups.getPROMOTE_PRIVATE_DATA(),
			lauGroups.getREPORT_ID_MODIFIER(),
			lauGroups.getVIEW_ALL_OTHERS_REPORTS(),
			lauGroups.getEDIT_ALL_OTHERS_REPORTS(),
			userId,
			dt,
			lauGroups.getUSER_GROUP_ID()
		};
	}
	
	private void updateGroupAccess(SimpleJdbcTemplate template,int type,LauUserGroupAccess lauGroupAccess
			,String userId, java.sql.Timestamp dt) throws Exception {
		if(type == 0) {
			CommonDAO.setDbmsClientInfo(template,userId );
			template.update("DELETE FROM LAU_USER_GROUP_ACCESS WHERE ACCESSIBLE_USER_GROUP_ID = ? and USER_GROUP_ID = ?", 
					new Object[]{lauGroupAccess.getACCESSIBLE_USER_GROUP_ID(),lauGroupAccess.getUSER_GROUP_ID()});
			log.info("updateGroupAccess - TRANSACTION_TYPE [0] -> " + lauGroupAccess.getUSER_GROUP_ID() + "[" + lauGroupAccess.getACCESSIBLE_USER_GROUP_ID()  + "]");
		}else if(type == 1 || type == 2) {
			template.update(SQL_LAU_USER_GROUP_ACCESS_INSERT_STRING,  new Object[]{
				lauGroupAccess.getACCESSIBLE_USER_GROUP_ID(),
				lauGroupAccess.getUSER_GROUP_ID(),
				lauGroupAccess.getVIEW_REPORTS(),
				lauGroupAccess.getEDIT_REPORTS(),
				lauGroupAccess.getVIEW_PRIVATE_DATA(),
				userId,
				dt
			});
			log.info("updateGroupAccess - TRANSACTION_TYPE [1] -> " + lauGroupAccess.getUSER_GROUP_ID() + "[" + lauGroupAccess.getACCESSIBLE_USER_GROUP_ID()  + "]");
		}
	}
	
	private void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.addObjectCreate( mainXmlTag, LauUserGroupCollection.class);		
		digester.addObjectCreate(mainXmlTag + "/USER_GROUP", LauUserGroups.class);
		digester.addSetProperties(mainXmlTag + "/USER_GROUP");
		digester.addSetNext(mainXmlTag + "/USER_GROUP", "addGroups");
		LauUserGroupCollection collection  = (LauUserGroupCollection)digester.parse(new StringReader(xml));
		groups		= collection.getGroups();
				
		digester	= null;
		digester	= new Digester();
		digester.addObjectCreate( mainXmlTag, LauUserGroupCollection.class);		
		digester.addObjectCreate(mainXmlTag + "/USER_GROUP/ACCESSIBLE_GROUP", LauUserGroupAccess.class);
		digester.addSetProperties(mainXmlTag + "/USER_GROUP/ACCESSIBLE_GROUP");		
		digester.addSetNext(mainXmlTag + "/USER_GROUP/ACCESSIBLE_GROUP", "addAccessibleGroups");
		collection  = (LauUserGroupCollection)digester.parse(new StringReader(xml));
		groupAccess = collection.getAccessibleGroups();	
		
	}
	
	//SQL Statements	
	private final String SQL_LAU_USER_GROUPS_UPDATE_STRING = "UPDATE LAU_USER_GROUPS SET " +
			"USER_GROUP_DESCRIPTION=?,PROMOTE_PRIVATE_DATA=?,REPORT_ID_MODIFIER=?," +
			"VIEW_ALL_OTHERS_REPORTS=?,EDIT_ALL_OTHERS_REPORTS=?," +
			" UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE USER_GROUP_ID=?";
	
	private final String SQL_LAU_USER_GROUPS_INSERT_STRING = "INSERT INTO LAU_USER_GROUPS (" +
			"USER_GROUP_DESCRIPTION,PROMOTE_PRIVATE_DATA,REPORT_ID_MODIFIER," +
			"VIEW_ALL_OTHERS_REPORTS,EDIT_ALL_OTHERS_REPORTS,UPDATE_USER_ID," +
			"UPDATE_TIMESTAMP,USER_GROUP_ID) VALUES (?,?,?,?,?,?,?,?)";
	
	private final String SQL_LAU_USER_GROUP_ACCESS_INSERT_STRING = "INSERT INTO LAU_USER_GROUP_ACCESS (ACCESSIBLE_USER_GROUP_ID," +
			"USER_GROUP_ID,VIEW_REPORTS,EDIT_REPORTS,VIEW_PRIVATE_DATA,UPDATE_USER_ID,UPDATE_TIMESTAMP) VALUES (?,?,?,?,?,?,?)";
	
}
