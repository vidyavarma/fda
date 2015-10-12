<html>
<head>
<title>Application Health Check</title>
<meta http-equiv="Pragma" CONTENT="no-cache">
<meta http-equiv="Expires" CONTENT="0">
<meta http-equiv="Cache-Control", "no-cache">
</head>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<%
    String ipadress = java.net.InetAddress.getLocalHost().getHostAddress().toString();
    String host = java.net.InetAddress.getLocalHost().getHostName().toString();
    Runtime runtime = Runtime.getRuntime();
    long allocatedMemory = runtime.totalMemory();
    long freeMemory = runtime.freeMemory();

%>
<body>

  <h3># HTTP request server name : <%= request.getServerName() %></h3>
  <h3># Server: <% out.println(host); %> (<% out.println(ipadress); %>)</h3>
  <h3># TimeZone: <% out.println(java.util.TimeZone.getDefault().getID()); %> (<% out.println(java.util.TimeZone.getDefault().getDisplayName()); %>)</h3>
  <h3># Java Version:
        <% out.println(System.getProperty("java.version")); %> (<% out.println(System.getProperty("java.vendor")); %>)</h3>
  <h3># Operating system:
        <% out.println(System.getProperty("os.name")); %></h3>

  <h3># <% out.println("free memory: " + freeMemory / 1024); %> Mo</h3>
  <h3># <% out.println("allocated memory: " + allocatedMemory / 1024);  %>Mo</h3>
  <h3># Locale:  <% out.println(java.util.Locale.getDefault()); %></h3>
  <h3># 
   <% out.println("\u3053\u3093\u306b\u3061\u308f");    %>
  </h3>

  <h3># Deployment CVS TAG: $Name: $  </h3>
  
</body>
</html>
