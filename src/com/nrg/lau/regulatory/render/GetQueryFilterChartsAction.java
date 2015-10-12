package com.nrg.lau.regulatory.render;

import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nrg.lau.commons.Constants;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.View;

import java.io.*;
import java.net.*;

public class GetQueryFilterChartsAction implements View {

	private static Logger log = Logger.getLogger(GetQueryFilterChartsAction.class);

	
	@Override
	public String getContentType() {
		return "text/xml";
	}

	@Override
	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER -GetQueryFilterChartsAction render()");

		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		pw.write(readData("/getFilterCharts.do").toString());
		pw.flush();
		pw.close();

		log.info("EXIT -GetQueryFilterChartsAction render()");
	}

	private StringBuffer readData(String fileName) throws IOException {
		StringBuffer sb = new StringBuffer();
		URL fileURL = GetQueryFilterChartsAction.class.getResource(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				fileURL.openStream()));
		String theBuf = new String();

		while ((theBuf = reader.readLine()) != null) {
			sb.append(theBuf + "\n");
		}

		reader.close();
		//log.info(sb);
		return sb;
		// return sb.toString();
	}

}