package com.nrg.lau.render;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.IReportsConstants;

public class GetSessionStatusAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(GetSessionStatusAction.class);
       
    public GetSessionStatusAction() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("ENTER - GetSessionStatusAction render()");
		
		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();		
		if(request.getSession().getAttribute(IReportsConstants.USER_SESSION) != null)	{			
			pw.write("<STATUS>S</STATUS>");
		}	else	{
			System.out.println("failure");
			pw.write("<STATUS>F</STATUS>");
		}		
		pw.flush();
		pw.close();

		log.info("EXIT - GetSessionStatusAction render()");
	}

}
