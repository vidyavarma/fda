package com.nrg.lau.render;

import java.io.PrintWriter;
import java.util.Map;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;

public class GetSuspProductsAction implements View {

	private static Logger log = Logger.getLogger(GetSuspProductsAction.class);

	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - getSuspProducts render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		pw.write(readData("/getSuspProducts.do").toString());
		pw.flush();
		pw.close();

		log.info("EXIT - getSuspProducts render()");
	}

	private StringBuffer readData(String fileName) throws IOException {

		StringBuffer sb = new StringBuffer();
		URL fileURL = GetSuspProductsAction.class.getResource(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				fileURL.openStream()));
		String theBuf = new String();

		while ((theBuf = reader.readLine()) != null) {
			sb.append(theBuf + "\n");
		}

		reader.close();
		log.info(sb);
		return sb;
		// return sb.toString();
	}

}
