<%@ page import="java.util.*,java.net.URLDecoder,java.net.URLEncoder,com.nrg.lau.utils.ReadConfig" %>

<%
//Cache-Control: no-store, no-cache, must-revalidate, post-check=0, pre-check=0
//Expires: Thu, 19 Nov 1981 08:52:00 GMT
//Set Cache-Control to no-cache.
//response.setHeader("Cache-Control", "");
// Prevent proxy caching.
//response.setHeader("Pragma", ""); 
// Set expiration date to a date in the past.
//response.setDateHeader("Expires", 946080000000L); //Approx Jan 1, 2000
// Force always modified.
//response.header("Last-Modified", new Date());
String activityid = request.getParameter("activityid");
String userid = request.getParameter("userid");
String custnum = request.getParameter("custnum");
String conid = request.getParameter("conid");
String conid2 = request.getParameter("conid");
activityid = (activityid == null)? null:URLEncoder.encode(activityid);
conid = (conid == null)? null:URLEncoder.encode(conid);
String projEnv = "";


//Get the Environment Title
try {
	
	ResourceBundle resource = ReadConfig.getMessage();
	projEnv					= "- " + resource.getString("PROJ_ENV");
	
}catch(Exception e) { }
%>

<html lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />



<!--  BEGIN Browser History required section -->
<link rel="stylesheet" type="text/css" href="history/history.css" />
<!--  END Browser History required section -->

<title>PRIMO <%= projEnv %></title>


<!--  BEGIN Browser History required section -->
<script src="history/history.js" language="javascript"></script>
<!--  END Browser History required section -->

<style>
body {
	margin: 0px;
	overflow: hidden
}
</style>

<script language="JavaScript" type="text/javascript">
var jconid = "<%=conid%>";


// -----------------------------------------------------------------------------
// Globals
// Major version of Flash required
var requiredMajorVersion = 9;
// Minor version of Flash required
var requiredMinorVersion = 0;
// Minor version of Flash required
var requiredRevision = 28;
// -----------------------------------------------------------------------------
// 


//var ourInterval = setInterval("validateSession();", 30000);
var pollingFlag = false; //to be switched on and off from Flex

function setPollingOn()
{
	pollingFlag = true;
}
function setPollingOff()
{
	pollingFlag = false;
}

	function getFlexApp(appName) 
	{
	  if (navigator.appName.indexOf ("Microsoft") !=-1) 
	  {
	    return window[appName];
	  } 
	  else {
	    return document[appName];
		}
	}
function getHTTPObject() {
	
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
	     xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	}
	return xmlhttp;
}
var http = getHTTPObject();
function getSessionStatus(url) {
	http.open("GET", url , true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);			
}
//invoked directly from Flex (when the state change from session idle to session active
function validateSession()
{
	//if(pollingFlag == true)
	//{
	
		var url = "<%=request.getContextPath()%>/validate_session.do";
		getSessionStatus(url);
		//alert("xxx:");
	//}
}

function handleHttpResponse() 
{
	var message="";

	if (http.readyState == 4) { 
		if (http.status == 200) {
			message = http.responseXML.getElementsByTagName("STATUS")[0];
			//alert("jscript in handle http:"+message.childNodes[0].nodeValue);
		if(message.childNodes[0].nodeValue=="F")
		{
			//alert("xxx:"+message.childNodes[0].nodeValue);
			getFlexApp('primo').sessionEnded();  
		}
			
		}
		else
		{
			getFlexApp('primo').sessionEnded();  
		}
	 }  

}
function setFocus() 
{
	
	var focusObj=document.getElementById("primo" );
	if (focusObj!=null ) 
		focusObj.focus();
	//window.document.primo.focus()
}

function noBack(){
	window.history.forward();
}
noBack();
window.onload = noBack;
window.onpageshow = function(evt){
	if(evt.persisted)noBack()
}
window.onunload=function(){void(0)}


</script>
</head>

<body scroll="no" onLoad="setFocus()" >


<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" id="primo"
	width="100%" height="100%"
	codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
	<param name="movie" value="<%=request.getContextPath() %>/primo.swf" />
	<param name="quality" value="high" />
	<param name="bgcolor" value="#ffffff" />
	<param name="flashVars"
		value="contextRoot=<%=request.getContextPath()%>&axisActivityId=<%=activityid%>&userId=<%=userid%>&custnum=<%=custnum%>&conid=<%=conid%>" /> 
		
	<param name="allowScriptAccess" value="sameDomain" />
	<embed src="<%=request.getContextPath() %>/primo.swf" quality="high"
		bgcolor="#ffffff" width="100%" height="100%" name="primo"
		align="middle" play="true" loop="false" quality="high"
		allowScriptAccess="sameDomain" type="application/x-shockwave-flash"
		flashVars="contextRoot=<%=request.getContextPath()%>&axisActivityId=<%=activityid%>&userId=<%=userid%>&custnum=<%=custnum%>&conid=<%=conid%>"
		pluginspage="http://www.adobe.com/go/getflashplayer">
	</embed> </object>

</body>
</html>