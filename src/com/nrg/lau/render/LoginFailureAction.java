package com.nrg.lau.render;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.security.AppContext;

public class LoginFailureAction implements View {

	private static Logger log	= Logger.getLogger(LoginFailureAction.class);

	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")

	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - LoginFailureView render()");

	    response.setContentType(Constants.CONTENT_TYPE);
	    //response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
	    PrintWriter pw = response.getWriter();
		pw.write(getXML(request));
		pw.flush();
		pw.close();

		log.info("EXIT - LoginFailureView render()");

	}

	private String getXML(HttpServletRequest request) {
		String message	 = "";
		ApplicationContext ctx 	= 	AppContext.getApplicationContext();
		java.util.HashMap errorMap = (java.util.HashMap)ctx.getBean("userLoginErrorMap");
		message = errorMap.get(request.getSession().getId()).toString();
		log.warn("Reason for log failure:"+ message);

		log.info("map message:"+errorMap.get(request.getSession().getId())+ request.getSession().getId());
		errorMap.remove(errorMap.get(request.getSession().getId()));
		return XMLException.status("F", message);
	}

}
