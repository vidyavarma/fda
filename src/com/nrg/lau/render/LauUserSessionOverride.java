package com.nrg.lau.render;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.View;

import com.nrg.lau.beans.LauUsers;
import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;

public class LauUserSessionOverride implements View{
	
	private static Logger log	= Logger.getLogger(LauUserSessionOverride.class);

	public String getContentType() {
		return "text/xml";
	}
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - LauUserSessionOverride render()");

	    response.setContentType(Constants.CONTENT_TYPE);
	    PrintWriter pw = response.getWriter();
		pw.write(setLauUaerInSession(request));
		pw.flush();
		pw.close();

		log.info("EXIT - LauUserSessionOverride render()");

	}
	
	private String setLauUaerInSession(HttpServletRequest request) {
		
		try {

			String groupId = request.getParameter("groupId");
			if(groupId != null && groupId.length() > 0) {
				
				HttpSession session  = request.getSession();	
				if (request.getSession().getAttribute("lauUsers") != null) {
					LauUsers lauUsers = (LauUsers) request.getSession().getAttribute(
							"lauUsers");
					//String userId = CommonDAO.getUSERID(request);
					//setting the new Group id passed from Request
					lauUsers.setUSER_GROUP_ID(groupId);
					//lauUsers.setUSER_ID(userId);
					//lauUsers.setUSER_NAME(userId);   
					log.info("@@@@ 2222- new group id is()"+lauUsers.getUSER_GROUP_ID());
					session.removeAttribute("lauUsers");
					session.setAttribute("lauUsers", lauUsers); 	
					
				} else {
					return XMLException.status("F", "Session not available");
				}
			}else {
				return XMLException.status("F", "Group Id not found in request to override");
			}
			
		}catch (Exception e) {
			return XMLException.status("F", e.getMessage());
		}	
		return XMLException.status("S", "Success");
	}
}
