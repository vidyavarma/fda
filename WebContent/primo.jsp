<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0014)about:internet -->
<%@ page import="java.util.*,java.net.URLDecoder,java.net.URLEncoder,com.nrg.lau.utils.ReadConfig" %>

<%

String activityid = request.getParameter("activityid");
String userid = request.getParameter("userid");
String custnum = request.getParameter("custnum");
String conid = request.getParameter("conid");
String conid2 = request.getParameter("conid");
activityid = (activityid == null)? null:URLEncoder.encode(activityid);
conid = (conid == null)? null:URLEncoder.encode(conid);
String projEnv = "";
String authenticationMode = "";
String strVersion = "";
String documentUrl = "";

//Get the Environment Title
try {
	
	ResourceBundle resource = ReadConfig.getMessage();
	projEnv					= "- " + resource.getString("PROJ_ENV");
	authenticationMode		= resource.getString("AUTHENTICATION_OPTION");
	strVersion				= resource.getString("PROJ_VERSION");
	documentUrl				= resource.getString("DOCUMENTUM_VIEW_URL");
	
}catch(Exception e) { }
%>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en"> 

    <head>
       <title>PRIMO <%= projEnv %></title>
        <meta name="google" value="notranslate" />         
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        
        <!--
        <meta http-equiv="Cache-Control" content="no-cache"/>
         Include CSS to eliminate any default margins/padding and set the height of the html element and 
             the body element to 100%, because Firefox, or any Gecko based browser, interprets percentage as 
             the percentage of the height of its parent container, which has to be set explicitly.  Fix for
             Firefox 3.6 focus border issues.  Initially, don't display flashContent div so it won't show 
             if JavaScript disabled.
        -->
        <style type="text/css" media="screen"> 
            html, body  { height:100%; }
            body { margin:0; padding:0; overflow:auto; text-align:center; 
                   background-color: #ffffff; }   
            object:focus { outline:none; }
            #flashContent { display:none; }
        </style>
        
        <!-- Enable Browser History by replacing useBrowserHistory tokens with two hyphens -->
        <!-- BEGIN Browser History required section -->
        <link rel="stylesheet" type="text/css" href="history/history.css" />
        <script type="text/javascript" src="history/history.js"></script>
        <!-- END Browser History required section -->  
            
        <script type="text/javascript" src="swfobject.js"></script>
        <script type="text/javascript">
            // For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection. 
            var swfVersionStr = "11.1.0";
            // To use express install, set to playerProductInstall.swf, otherwise the empty string. 
            var xiSwfUrlStr = "playerProductInstall.swf";
            var flashvars = {"contextRoot":"<%=request.getContextPath()%>","axisActivityId":"<%=activityid%>",
            		"userId":"<%=userid%>","custnum":"<%=custnum%>","conid":"<%=conid%>","authenticationMode":"<%=authenticationMode%>",
            		"strVersion":"<%=strVersion%>","documentUrl":"<%=documentUrl%>" };
            var params = {};
            params.quality = "high";
            params.bgcolor = "#ffffff";
            params.allowscriptaccess = "sameDomain";
            params.allowfullscreen = "true";
            var attributes = {};
            attributes.id = "primo";
            attributes.name = "primo";
            attributes.align = "middle";
            swfobject.embedSWF(
                "<%=request.getContextPath()%>" +"/primo.swf?ver=<%=strVersion%>", "flashContent", 
                "100%", "100%", 
                swfVersionStr, xiSwfUrlStr, 
                flashvars, params, attributes);
            // JavaScript enabled so display the flashContent div in case it is not replaced with a swf object.
            swfobject.createCSS("#flashContent", "display:block;text-align:left;");
            
            
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
    <body scroll="no"  >
        <!-- SWFObject's dynamic embed method replaces this alternative HTML content with Flash content when enough 
             JavaScript and Flash plug-in support is available. The div is initially hidden so that it doesn't show
             when JavaScript is disabled.
        -->
        <div id="flashContent">
            <p>
                To view this page ensure that Adobe Flash Player version 
                11.1.0 or greater is installed. 
            </p>
            <script type="text/javascript"> 
                var pageHost = ((document.location.protocol == "https:") ? "https://" : "http://"); 
                document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
                                + pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" ); 
            </script> 
        </div>
        
        <noscript>
            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="primo">
                <param name="movie" value="<%=request.getContextPath() %>/primo.swf?ver=<%=strVersion%>" />
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ffffff" />
                <param name="allowScriptAccess" value="sameDomain" />
                <param name="allowFullScreen" value="true" />
                <param name="flashVars"
			    		value="contextRoot=<%=request.getContextPath()%>&axisActivityId=<%=activityid%>&userId=<%=userid%>&custnum=<%=custnum%>&conid=<%=conid%>&authenticationMode=<%=authenticationMode%>&strVersion=<%=strVersion%>&documentUrl=<%=documentUrl%>" /> 
	    	
                <!--[if !IE]>-->
                <object type="application/x-shockwave-flash" data="<%=request.getContextPath() %>/primo.swf?ver=<%=strVersion%>" width="100%" height="100%">
                    <param name="quality" value="high" />
                    <param name="bgcolor" value="#ffffff" />
                    <param name="allowScriptAccess" value="sameDomain" />
                    <param name="allowFullScreen" value="true" />
                    <param name="flashVars"
		    	    		value="contextRoot=<%=request.getContextPath()%>&axisActivityId=<%=activityid%>&userId=<%=userid%>&custnum=<%=custnum%>&conid=<%=conid%>&authenticationMode=<%=authenticationMode%>&strVersion=<%=strVersion%>&documentUrl=<%=documentUrl%>" /> 
	    	
                <!--<![endif]-->
                <!--[if gte IE 6]>-->
                    <p> 
                        Either scripts and active content are not permitted to run or Adobe Flash Player version
                        11.1.0 or greater is not installed.
                    </p>
                <!--<![endif]-->
                    <a href="http://www.adobe.com/go/getflashplayer">
                        <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
                    </a>
                <!--[if !IE]>-->
                </object>
                <!--<![endif]-->
            </object>
            
            
        </noscript>     
   </body>
</html>
