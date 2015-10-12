package com.nrg.lau.e2b;

import java.io.InputStream;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.nrg.lau.security.AppContext;

public class E2BRejectOutput {
	
	private static Logger log = Logger.getLogger(E2BRejectOutput.class);
	public static String writeContentsToFile(InputStream in, String e2bRejectReason, 
			String code) {
		
		//variables
    	StringBuffer headerContents 	= new StringBuffer("");
    	StringBuffer messageAckContents = new StringBuffer("");
    	StringBuffer reportAckContents 	= new StringBuffer("");
    	StringBuffer filecontents 		= new StringBuffer("");

	    try {		    		   		 	
	            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();	           	            
	            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();	             	            
	            Document doc = docBuilder.parse(in);	            	           
			
	            // normalize text representation
	            doc.getDocumentElement().normalize();
	            
				//getting the ichicsrmessageheader details (there is always only one)
				NodeList listOfichicsr = doc.getElementsByTagName("ichicsrmessageheader");
				
				E2BUtil util = new E2BUtil();
				ApplicationContext ctx 	= 	AppContext.getApplicationContext();
				DataSource ds 	= 	(DataSource)ctx.getBean("dataSource");
				String encoding = "utf-8";
				try {
					encoding = util.convertinputStreamToString(util.getDBFiles(ds, "ENCODING")).trim();
				}catch (Exception e) {
					log.error("Encoding set to default utf-8 - " + e);
				}		
	                        
	            //setting the filecontents
	            String topLines = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>\r\n";
	            
	            String fdaDomain = util.convertinputStreamToString(util.getDBFiles(ds, "FDA-DOMAIN")).trim();	            
	            String doctypeSystemURL = "";
	            try {
	            	DocumentType doctype = doc.getDoctype();
	            	doctypeSystemURL = doctype.getSystemId();
	            	log.info("doctypeSystemURL - " + doctypeSystemURL);
	            }catch (Exception e) {
					log.error(e);
					log.error("Doctype set to EMA");
				}
				if(doctypeSystemURL.indexOf(fdaDomain) != -1) {
					 topLines += util.convertinputStreamToString(util.getDBFiles(ds, "FDA-HEADER")).trim();
					 topLines += "\r\n";
				}else {
					 topLines += util.convertinputStreamToString(util.getDBFiles(ds, "EMA-HEADER")).trim();
					 topLines += "\r\n";
				}
				log.debug("topLines - " + topLines);
				topLines += "<ichicsrack lang=\"en\">\r\n";							  	
								  		            
	            headerContents.append("<ichicsrmessageheader>\r\n");
	            messageAckContents.append("<acknowledgement>\r\n");	            	            	            
	            messageAckContents.append("<messageacknowledgement>\r\n");
	                      
	            
	            Node ichicsrNode = listOfichicsr.item(0);            
	            
	            if (ichicsrNode.getNodeType() == Node.ELEMENT_NODE) {
	            	
	            	Element ichicsrNodeElement = (Element)ichicsrNode;
	            	
	            	try {
	            		headerContents.append("<messagetype>" + util.convertinputStreamToString(util.getDBFiles(ds, "NODE-MESSAGE-TYPE")).trim() + "</messagetype>\n");	            	           	              	
	            	}catch (Exception e) {
						log.error("No Message Type set - " + e);
					}    
	            		
	                //messageformatversion
	            	NodeList messageformatversionList = ichicsrNodeElement.getElementsByTagName("messageformatversion");
	            	Element messageformatversionElement = (Element)messageformatversionList.item(0);   	            	
	            
	            	if (messageformatversionElement != null) { 
	            		NodeList textMFVList = messageformatversionElement.getChildNodes();	
            			if (textMFVList.getLength() > 0) {
            				headerContents.append("<messageformatversion>"+ ((Node)textMFVList.item(0)).getNodeValue().trim() +"</messageformatversion>\r\n");
            			}	            
	            	}else {
	            		headerContents.append("<messageformatversion></messageformatversion>\r\n");
	            	} 
	            	
	                //messageformatrelease  
	            	NodeList messageformatreleaseList = ichicsrNodeElement.getElementsByTagName("messageformatrelease");
	            	Element messageformatreleaseElement = (Element)messageformatreleaseList.item(0);  
	            		            	
	            	if (messageformatreleaseElement != null) {
	            		NodeList textMFRList = messageformatreleaseElement.getChildNodes(); 
	            		if (textMFRList.getLength() > 0) {
	            			headerContents.append("<messageformatrelease>"+ ((Node)textMFRList.item(0)).getNodeValue().trim() +"</messageformatrelease>\r\n");
	            		}	                		            
	                }else {	                
	                	headerContents.append("<messageformatrelease></messageformatrelease>\r\n");	            
	                }
	                
	                //messagenumb
	            	NodeList messagenumbList = ichicsrNodeElement.getElementsByTagName("messagenumb");
	            	Element messagenumbElement = (Element)messagenumbList.item(0); 
	            	     	
	            	if (messagenumbElement != null) {
	            		NodeList textMNList = messagenumbElement.getChildNodes();     
	            		if (textMNList.getLength() > 0) {
	            			headerContents.append("<messagenumb>"+((Node)textMNList.item(0)).getNodeValue().trim()+"</messagenumb>\r\n");
	            			messageAckContents.append("<icsrmessagenumb>"+((Node)textMNList.item(0)).getNodeValue().trim()+"</icsrmessagenumb>\r\n");
	            		} 
	            	}else {
	            		headerContents.append("<messagenumb></messagenumb>\r\n");
	            		headerContents.append("<icsrmessagenumb></icsrmessagenumb>\r\n");
	            	}       		               
	                
	                //messagesenderidentifier
	            	NodeList messagesenderidentifierList = ichicsrNodeElement.getElementsByTagName("messagesenderidentifier");
	            	Element messagesenderidentifierElement = (Element)messagesenderidentifierList.item(0);   
	            		            	
	            	if (messagesenderidentifierElement != null) {	
	            		NodeList textMSIList = messagesenderidentifierElement.getChildNodes();
	            		if (textMSIList.getLength() > 0) {
	            			headerContents.append("<messagesenderidentifier>"+ ((Node)textMSIList.item(0)).getNodeValue().trim() +"</messagesenderidentifier>\r\n");
	                		messageAckContents.append("<icsrmessagesenderidentifier>"+ ((Node)textMSIList.item(0)).getNodeValue().trim() +"</icsrmessagesenderidentifier>\r\n");	   
	            		}        
	            	}else {
	            		headerContents.append("<messagesenderidentifier></messagesenderidentifier>\r\n");
	                	messageAckContents.append("<icsrmessagesenderidentifier></icsrmessagesenderidentifier>\r\n");
	            	}	            	
	                
	                //messagereceiveridentifier
	            	NodeList messagereceiveridentifierList = ichicsrNodeElement.getElementsByTagName("messagereceiveridentifier");
	            	Element messagereceiveridentifierElement = (Element)messagereceiveridentifierList.item(0);   
	            	
	            	if (messagereceiveridentifierElement != null) {
	            		NodeList textMRIList = messagereceiveridentifierElement.getChildNodes();
	            		if (textMRIList.getLength() > 0) {
	            			headerContents.append("<messagereceiveridentifier>"+ ((Node)textMRIList.item(0)).getNodeValue().trim() +"</messagereceiveridentifier>\r\n");
	            			messageAckContents.append("<icsrmessagereceiveridentifier>"+ ((Node)textMRIList.item(0)).getNodeValue().trim() +"</icsrmessagereceiveridentifier>\r\n");	    
	            		}       
	            	}else {
	            		headerContents.append("<messagereceiveridentifier></messagereceiveridentifier>\r\n");
	            		messageAckContents.append("<icsrmessagereceiveridentifier></icsrmessagereceiveridentifier>\r\n");  
	            	}
	            
	                //messagedateformat
	            	NodeList messagedateformatList = ichicsrNodeElement.getElementsByTagName("messagedateformat");
	            	Element messagedateformatElement = (Element)messagedateformatList.item(0);  
	            		                
	                if (messagedateformatElement != null) {
	                	NodeList textMDFList = messagedateformatElement.getChildNodes();
	                	if (textMDFList.getLength() > 0) {
	                		headerContents.append("<messagedateformat>"+ ((Node)textMDFList.item(0)).getNodeValue().trim() +"</messagedateformat>\r\n");
	            			messageAckContents.append("<icsrmessagedateformat>"+ ((Node)textMDFList.item(0)).getNodeValue().trim() +"</icsrmessagedateformat>\r\n");	  
	                	}
	                }else {
	                	headerContents.append("<messagedateformat></messagedateformat>\r\n");
	            		messageAckContents.append("<icsrmessagedateformat></icsrmessagedateformat>\r\n");
	                }
	                
	                //messagedate
	            	NodeList messagedateList = ichicsrNodeElement.getElementsByTagName("messagedate");
	            	Element messagedateElement = (Element)messagedateList.item(0);  
	            		              		            		                
	                if (messagedateElement != null) {
	                	NodeList textMDList = messagedateElement.getChildNodes();
	                	if (textMDList.getLength() > 0) {
	                		headerContents.append("<messagedate>"+ ((Node)textMDList.item(0)).getNodeValue().trim() +"</messagedate>\r\n</ichicsrmessageheader>\r\n");
	            			messageAckContents.append("<icsrmessagedate>"+ ((Node)textMDList.item(0)).getNodeValue().trim() +"</icsrmessagedate>\r\n");
	                	}	                	
	                }else {
	                	headerContents.append("<messagedate></messagedate>\r\n</ichicsrmessageheader>\r\n");
	            		messageAckContents.append("<icsrmessagedate></icsrmessagedate>\r\n");
	                }
	                
	                messageAckContents.append("<transmissionacknowledgementcode>"+code+"</transmissionacknowledgementcode>\r\n</messageacknowledgement>\r\n");	                
	            }            
	           
	            
	            //-----------------------------REPORT ACKNOWLEDGEMENT SECTION------------------------------------------ 
	            
	            NodeList listOfsafetyreport = doc.getElementsByTagName("safetyreport");
	           
	            for (int s=0; s<listOfsafetyreport.getLength(); s++) {
	
					reportAckContents.append("<reportacknowledgement>\r\n");
					
	                Node safetyreportNode = listOfsafetyreport.item(s);
	                if(safetyreportNode.getNodeType() == Node.ELEMENT_NODE){
	                	
	                    Element safetyreportElement = (Element)safetyreportNode;
	
	                    //-------safetyreportversion
	                    NodeList safetyreportversionList = safetyreportElement.getElementsByTagName("safetyreportversion");
	                    Element safetyreportversionElement = (Element)safetyreportversionList.item(0);		                    
	                    
	                    if (safetyreportversionElement != null) {
	                    	NodeList textSRVList = safetyreportversionElement.getChildNodes();
	                    	if (textSRVList.getLength() > 0) {
	                    		reportAckContents.append("<safetyreportversion>"+((Node)textSRVList.item(0)).getNodeValue().trim()+"</safetyreportversion>\r\n");
	                    	}	                    
	                    }else {
	                    	reportAckContents.append("<safetyreportversion></safetyreportversion>\r\n");
	                    }	                    
	
	                    //-------safetyreportid
	                    NodeList safetyreportidList = safetyreportElement.getElementsByTagName("safetyreportid");
	                    Element safetyreportidElement = (Element)safetyreportidList.item(0);	
	                    	                    
	                    if (safetyreportidElement != null) {
	                    	NodeList textSRIList = safetyreportidElement.getChildNodes();
	                    	if (textSRIList.getLength() > 0) {
	                    		reportAckContents.append("<safetyreportid>"+((Node)textSRIList.item(0)).getNodeValue().trim()+"</safetyreportid>\r\n");
	                    	}
	                    }else {
	                    	reportAckContents.append("<safetyreportid></safetyreportid>\r\n");
	                    }	                    
	
						//----localreportnumber
	                    NodeList localreportnumberList = safetyreportElement.getElementsByTagName("localreportnumber");
	                    Element localreportnumberElement = (Element)localreportnumberList.item(0);	
	                    	                    
	                    if (localreportnumberElement != null) {
	                    	NodeList textLRNList = localreportnumberElement.getChildNodes();
	                    	if (textLRNList.getLength() > 0) {
	                    		reportAckContents.append("<localreportnumber>"+((Node)textLRNList.item(0)).getNodeValue().trim()+"</localreportnumber>\r\n");	                    
	                    	}
	                    }else {
	                    	reportAckContents.append("<localreportnumber></localreportnumber>\r\n");
	                    }
	                    
	                    //----authoritynumber
	                    NodeList authoritynumberList = safetyreportElement.getElementsByTagName("authoritynumber");
	                    Element authoritynumberElement = (Element)authoritynumberList.item(0);
	                    	
	                    if (authoritynumberElement != null) {
	                    	NodeList textANList = authoritynumberElement.getChildNodes();
	                    	if (textANList.getLength() > 0) {
	                    		reportAckContents.append("<authoritynumber>"+((Node)textANList.item(0)).getNodeValue().trim()+"</authoritynumber>\r\n");	                    
	                    	}
	                    }else {
	                    	reportAckContents.append("<authoritynumber></authoritynumber>\r\n");
	                    }
	                    
	                    //----companynumber
	                    NodeList companynumberList = safetyreportElement.getElementsByTagName("companynumber");
	                    Element companynumberElement = (Element)companynumberList.item(0);
		                                       
	                    if (companynumberElement != null) {
	                    	NodeList textCNList = companynumberElement.getChildNodes();	 
	                    	if (textCNList.getLength() > 0) {
	                    		reportAckContents.append("<companynumber>"+((Node)textCNList.item(0)).getNodeValue().trim()+"</companynumber>\r\n");
	                    	}	                    	
	                    }else {
	                    	reportAckContents.append("<companynumber></companynumber>\r\n");
	                    }
	                    
	                    //----receiptdateformat
	                    NodeList receiptdateformatList = safetyreportElement.getElementsByTagName("receiptdateformat");
	                    Element receiptdateformatElement = (Element)receiptdateformatList.item(0);
		                    
	                    if (receiptdateformatElement != null) {
	                    	NodeList textRDFList = receiptdateformatElement.getChildNodes();
	                    	if (textRDFList.getLength() > 0) {
	                    		reportAckContents.append("<receiptdateformat>"+((Node)textRDFList.item(0)).getNodeValue().trim()+"</receiptdateformat>\r\n");
	                    	}	                    	
	                    }else {
	                    	reportAckContents.append("<receiptdateformat></receiptdateformat>\r\n");
	                    }
	                    
	                    //------receiptdate
						NodeList receiptdateList = safetyreportElement.getElementsByTagName("receiptdate");
	                    Element receiptdateElement = (Element)receiptdateList.item(0);
		                    
	                    if (receiptdateElement != null) {
	                    	NodeList textRDList = receiptdateElement.getChildNodes();
	                    	if (textRDList.getLength() > 0) {
	                    		reportAckContents.append("<receiptdate>"+((Node)textRDList.item(0)).getNodeValue().trim()+"</receiptdate>\r\n");
	                    	}	                    	
	                    }else {
	                    	reportAckContents.append("<receiptdate></receiptdate>\r\n");
	                    }
	                    
	                    //------reportacknowledgementcode						
	                    reportAckContents.append("<reportacknowledgementcode>"+code+"</reportacknowledgementcode>\r\n");
	                   	                    
	                    
						//----errormessagecomment	                  
	                    reportAckContents.append("<errormessagecomment>"+e2bRejectReason+"</errormessagecomment>\r\n"); 	                    	                    
	                    	                    
	                }//end of if clause	
	                
	                reportAckContents.append("</reportacknowledgement>\r\n");
	
	            }//end of for loop with s var	
				
				filecontents.append(topLines);
				filecontents.append(headerContents);
				filecontents.append(messageAckContents);
				filecontents.append(reportAckContents);				
				filecontents.append("</acknowledgement>\r\n</ichicsrack>");							
							    
			}catch (SAXParseException err) {		        
				log.info("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
				log.error(err);
	
	        }catch (SAXException e) {
	        	log.error(e);
	
	        }catch (Exception ex) {
	        	log.error(ex);
	        }	        
	       
	       return filecontents.toString();  
	}
	
}
