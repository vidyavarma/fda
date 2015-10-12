<%@ page buffer="none" import="java.io.*" %>
<%@ page import="java.net.*" %>
<%
	
	try {
		
		response.setHeader("Cache-Control", "no-cache");		
		PrintWriter pw 	= response.getWriter();
		StringBuffer buffer = new StringBuffer();
		BufferedReader rd = new BufferedReader(new FileReader("/waslogs/primo/web/primo.log"));
		String line = null; 
	    while ((line =  rd.readLine()) != null) {
	    	buffer.append(line + "</br>");
	    }
		pw.write(buffer.toString());
		//pw.flush();
		rd.close();
	}catch(Exception e) {
		System.out.println(e);
	}	

%>

