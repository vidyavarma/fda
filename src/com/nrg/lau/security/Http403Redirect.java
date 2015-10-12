package com.nrg.lau.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

public class Http403Redirect extends Http403ForbiddenEntryPoint{

	private static Logger log	= Logger.getLogger(Http403Redirect.class);

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authExp)
			throws IOException, ServletException {	
		log.info("Http403Redirect ->.>>>>");
		response.setStatus(403);
		response.sendRedirect("/primo.jsp");

	}

}
