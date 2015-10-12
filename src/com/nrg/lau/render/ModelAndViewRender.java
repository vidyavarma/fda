package com.nrg.lau.render;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * 
 * @author 
 * This class is used to return XML view to flex.
 * According to the constructor argument passed it will
 * call the appropriate XML render() view.
 *
 */

public class ModelAndViewRender extends AbstractController  {
	
	private static final long serialVersionUID 	= -5883622788160368528L;
	private static Logger log 	= Logger.getLogger(ModelAndViewRender.class);		
	private String view			= "";
	private String parameter1	= "";
	private String parameter2	= "";
	private String parameter3	= "";
	private String parameter4	= "";

	public String getView() {
		return this.view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public ModelAndViewRender(String renderView) {
		log.info("ModelAndViewRender(view) - Constructor - " + renderView);		
		setView(renderView);
	}
	
	public ModelAndViewRender(String renderView,String parameter1) {
		log.info("ModelAndViewRender(view,parameter1) - Constructor - " 
				+ renderView + "  , " + parameter1);		
		setView(renderView);
		setParameter1(parameter1);
	}
	
	public ModelAndViewRender(String renderView,String parameter1,String parameter2) {
		log.info("ModelAndViewRender(view,parameter1,parameter2) - Constructor - " 
				+ renderView + "  , " + parameter1 + "  , " + parameter2);		
		setView(renderView);
		setParameter1(parameter1);
		setParameter2(parameter2);
	}
	public ModelAndViewRender(String renderView,String parameter1,String parameter2,String parameter3) {
		log.info("ModelAndViewRender(view,parameter1,parameter2,parameter3) - Constructor - " 
				+ renderView + "  , " + parameter1 + "  , " + parameter2+ "  , " + parameter3);		
		setView(renderView);
		setParameter1(parameter1);
		setParameter2(parameter2);
		setParameter3(parameter3);
	}
	public ModelAndViewRender(String renderView,String parameter1,String parameter2,String parameter3,String parameter4) {
		log.info("ModelAndViewRender(view,parameter1,parameter2,parameter3,parameter4) - Constructor - " 
				+ renderView + "  , " + parameter1 + "  , " + parameter2+ "  , " + parameter3+ "  , " + parameter4);		
		setView(renderView);
		setParameter1(parameter1);
		setParameter2(parameter2);
		setParameter3(parameter3);
		setParameter4(parameter4);
	}	


	
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("ModelAndViewRender - handleRequestInternal() - " + getView());
		if(getParameter1().trim() != "")	{
			request.setAttribute("parameter1", getParameter1());
			log.info("ModelAndViewRender - handleRequestInternal() - getParameter1() -> " + getParameter1());
		}
		if(getParameter2().trim() != "")	{
			request.setAttribute("parameter2", getParameter2());
			log.info("ModelAndViewRender - handleRequestInternal() - getParameter2() -> " + getParameter2());
		}
		if(getParameter3().trim() != "")	{
			request.setAttribute("parameter3", getParameter3());
			log.info("ModelAndViewRender - handleRequestInternal() - getParameter2() -> " + getParameter3());
		}
		if(getParameter4().trim() != "")	{
			request.setAttribute("parameter4", getParameter4());
			log.info("ModelAndViewRender - handleRequestInternal() - getParameter4() -> " + getParameter4());
		}		
		return new ModelAndView(getView());
	}

	public String getParameter1() {
		return this.parameter1;
	}

	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}

	public String getParameter2() {
		return parameter2;
	}

	public void setParameter2(String parameter2) {
		this.parameter2 = parameter2;
	}
	public String getParameter3() {
		return parameter3;
	}

	public void setParameter3(String parameter3) {
		this.parameter3 = parameter3;
	}

	public String getParameter4() {
		return parameter4;
	}

	public void setParameter4(String parameter4) {
		this.parameter4 = parameter4;
	}
}
