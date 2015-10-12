package com.nrg.lau.render;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;

public class GetCiomsPdfAction implements View {

	private static Logger log = Logger.getLogger(GetCiomsPdfAction.class);

	
	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - GetReportsCioms render()");

		response.setContentType("application/pdf");
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		OutputStream outStream = response.getOutputStream();
		log.info("1111111");
		URL fileURL = GetCiomsPdfAction.class.getResource("/cioms.pdf");				
		try{
			log.info("2222222222");
			BufferedReader bf = new BufferedReader(new InputStreamReader(fileURL.openStream()));
			log.info("333333333333");
			String str = bf.readLine();
			log.info("4444444444444");
			File file = new File(str); 
			log.info("55555555555");
			//File length
			int size = (int)file.length(); 
			log.info("66666666666:"+ size);
			if (size > Integer.MAX_VALUE){
				System.out.println("File is to larger");
			}
			log.info("77777777");
			byte[] bytes = new byte[size]; 
	//		DataInputStream dis = new DataInputStream(new FileInputStream(file)); 
			log.info("8888888");
			int read = 0;
			int numRead = 0;
			log.info("before loop");
			while (read < bytes.length && (numRead=bf.read()) >= 0) {
				read = read + numRead;
				  outStream.write(bytes, 0, numRead);
			}
			System.out.println("File size: " + read);
			log.info("File size: " + read);
			// Ensure all the bytes have been read in
			if (read < bytes.length) {
				System.out.println("Could not completely read: "+file.getName());
			}
		//	dis.close();
	        outStream.close();
	        outStream.flush();
		}
		catch (Exception e){
			log.info("error............");
			log.error(e, e);
		}
	

		log.info("EXIT - GetReportsCiomsn render()");
	}

	

}
