<%@ page import="com.nrg.lau.commons.*,java.io.PrintWriter"%><%
	response.setContentType(Constants.CONTENT_TYPE);
	response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
	PrintWriter pw = response.getWriter();		
	pw.write(XMLException.status("F","login redirect"));		
	pw.flush();
	pw.close();
%>