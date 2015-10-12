package com.nrg.lau.regulatory.render;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.View;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.regulatory.dao.LetterGenerationTransactionMgr;
import com.nrg.lau.security.AppContext;

public class SetLetterGeneration implements View{
	
	private static Logger log	= Logger.getLogger(SetLetterGeneration.class);
	
	public String getContentType() {
		return "text/xml";
	}
	
	@SuppressWarnings("rawtypes")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		ApplicationContext ctx 	= 	AppContext.getApplicationContext();
		LetterGenerationTransactionMgr lauReportsMgr = (LetterGenerationTransactionMgr)ctx.getBean("setLetter");
		String generateMode = request.getParameter("generateMode") ;
		String genPrvwFlag = request.getParameter("genPrvwFlag") ;
		log.info("generateMode in ac------>"+generateMode);
		log.info("genPrvwFlag in ac------>"+genPrvwFlag);
		String status = "";
		/**
		 * Scenarios:
				1. Preview           - 'PREVIEW' and 'P' pressing "PREVIEW BUTTON"
				2. Preview and Close - 'PC'
				3. Generate          - 'FINAL' and 'G' - pressing "GENERATE BUTTON"
				4. Save Letter       - 'FINAL' and 'PG'
				5. Discard Preview   - 'PC'
		 */
		if(generateMode.trim().equals("PREVIEW") && genPrvwFlag.trim().equals("P")){
			log.info("PREVIEW P------>");
		    status = lauReportsMgr.insertLetterGenValues(request, response);
		    log.info("status------>"+status);
		}
		
		if(generateMode.trim().equals("FINAL") && genPrvwFlag.trim().equals("PG")){
			log.info("FINAL P------>");
			status = lauReportsMgr.insertLetterAttAct(request, response);
			log.info("status in final------>"+status);
		}
		
		if(generateMode.trim().equals("FINAL") && genPrvwFlag.trim().equals("G")){
			log.info("PREVIEW G------>");
		    status = lauReportsMgr.insertLetterGenValues(request, response);
		    log.info("status------>"+status);
		}
		//CLEANING UP FROM TABLES
		if(genPrvwFlag.trim().equals("PC")){
			log.info("Preview and Close Window------>");
			String letterId = request.getParameter("generatedLetterId");
			log.info("letterId for close------>"+letterId);
			lauReportsMgr.deletePreviewData(letterId);
		}
		
		/*if(generateMode.trim().equals("PREVIEW")){
			log.info("PREVIEW------>");
		    status = lauReportsMgr.insertLetterGenValues(request, response);
		    log.info("status------>"+status);
		}
		
		if(generateMode.trim().equals("FINAL")){
			log.info("FINAL------>");
			status = lauReportsMgr.insertLetterAttAct(request, response);
			log.info("status in final------>"+status);
		}*/
			
		response.setContentType(Constants.CONTENT_TYPE);
	    response.setHeader("Cache-Control", Constants.CACHE_CONTROL);	    
	    PrintWriter pw = response.getWriter();		
		pw.write(status);		
		pw.flush();
		pw.close();	
			
	}
}
