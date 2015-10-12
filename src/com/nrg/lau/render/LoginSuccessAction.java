package com.nrg.lau.render;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;

public class LoginSuccessAction implements View {

	private static Logger log	= Logger.getLogger(LoginSuccessAction.class);

	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")

	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - LoginSuccessView render()");

	    response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
	    String buffer = (String)request.getSession().getAttribute("flexProfile");
	    PrintWriter pw = response.getWriter();  
	    pw.write(buffer != null ? buffer : "");
		pw.flush();
		pw.close();

		log.info("EXIT - LoginSuccessView render()");

	}
}
