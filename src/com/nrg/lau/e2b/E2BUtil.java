package com.nrg.lau.e2b;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.security.AppContext;
import com.nrg.lau.security.GenericLoader;
import com.nrg.lau.sharepoint.SPUtil;

public class E2BUtil {
	
	private static Logger log = Logger.getLogger(E2BUtil.class);
	private File tempMeddraFile;
	private File tempCountryFile;
			
	public Transformer getTransformer(StreamSource streamSource) 
				throws Exception {
		net.sf.saxon.TransformerFactoryImpl impl = new net.sf.saxon.TransformerFactoryImpl();
		return impl.newTransformer(streamSource);
	}
	
	public static void getE2BError(HttpServletResponse response, 
			Exception e) {
		
		try {
			log.error(e);
			if(e.getMessage().equalsIgnoreCase("File Not Found")) {
				log.info("e.getMessage().equalsIgnoreCase('File Not Found') -> " + e.getMessage());				
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition",
						"attachment;filename=file_not_found.pdf");
				response.getOutputStream().write(E2BFileOutput.getBytes
						(SPUtil.class.getResourceAsStream("/File_Not_Found.pdf")));
				response.getOutputStream().flush();
			} else {
				response.setContentType(Constants.CONTENT_TYPE);
				response.setHeader("Cache-Control",
						Constants.CACHE_CONTROL);
				PrintWriter pw = response.getWriter();
				pw.write(XMLException.xmlError(e, e.getMessage()));
				pw.flush();
				pw.close();
			}
			
		}catch (Exception error) {
			log.error(error);
		}	
	}
	
	public static InputStream replaceE2BCodes(InputStream in) throws Exception{
		
		E2BUtil util = new E2BUtil();
		ApplicationContext ctx 	= 	AppContext.getApplicationContext();
		DataSource ds 	= 	(DataSource)ctx.getBean("dataSource");
		return new ByteArrayInputStream(
				(util.transformCodes(util.convertinputStreamToString(in), ds, ctx).getBytes())
				);
	}
	
	public String convertinputStreamToString(InputStream in) throws Exception {
		//long start= System.currentTimeMillis();
		//log.info("convertinputStreamToString start:"+start); 
        if (in != null) {
            StringBuilder sb = new StringBuilder();
            String line;
 
            try {
                BufferedReader r1 = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                while ((line = r1.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
            	in.close();
            }
    		//long first = System.currentTimeMillis();
    		//log.info(first);
    		//log.info("convertinputStreamToString millsecs elapsed:"+(first-start));
            return sb.toString();
        } else {       
            return "";
        }
    }
	
	private String transformCodes(String filecontents, DataSource ds, 
			ApplicationContext ctx) throws Exception {
	   	
		long start= System.currentTimeMillis();
		log.info(start);
		
		List<String> arrayMeddraContents = createArrayFromTxtFile(
				getDBFiles(ds, "MEDDRA-TAGS-TXT"));
		List<String> arrayCountryContents = createArrayFromTxtFile(
				getDBFiles(ds, "COUNTRY-TAGS-TXT"));
		
		tempMeddraFile = File.createTempFile("tempMeddraFile", ".tmp");   
		tempMeddraFile.deleteOnExit();
		
		GenericLoader gl 	= 	(GenericLoader)ctx.getBean("genericLoader");
		
		log.info("START MEDDRA");
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(tempMeddraFile));
			out.write(gl.getMeddra().toString());
		}catch (Exception e) {
			log.error(e);
		}finally {
			out.close();
		}
	    
	    log.info("END MEDDRA");		
		
		tempCountryFile = File.createTempFile("tempCountryFile", ".tmp");   
		tempCountryFile.deleteOnExit();
		
		log.info("START COUNTRY");
		BufferedWriter outC = null;
		try {
			outC = new BufferedWriter(new FileWriter(tempCountryFile));
			outC.write(gl.getCountryData().toString());
		}catch (Exception e) {
			log.error(e);
		}finally {
			outC.close();
		}
		
		log.info("END COUNTRY");
		
		// variables
		String tmpfilecontents = "";
		StringBuffer sb = new StringBuffer("");

		String wordGetReplaced = "";
		String wordToReplace = "";
		String sentenceGetReplaced = "";
		String theWord = "";
		
		tmpfilecontents = filecontents.toLowerCase();
		      	    		
		//for each meddraversioncode in the meddra.txt  	    	
		for (int i=0; i<arrayMeddraContents.size(); i++) {      
		
			String currentIndexItem = "";
			currentIndexItem = (String)arrayMeddraContents.get(i);
				
			//if the meddracode is found in the meddra.txt
			if (tmpfilecontents.indexOf(currentIndexItem) > 0) {
																				
				//store the meddracode in an arraylist for the meddraversioncode	
				ArrayList<String> tmpContents = getMatchedContents(filecontents, currentIndexItem);
											
				//for each of the meddracode in the arraylist
				for (int n=0; n<tmpContents.size(); n++) {
																
					sb = new StringBuffer(""); 
					
					//get the meddracode								
					wordGetReplaced = (String)tmpContents.get(n);					
					
					theWord = getWord(tempMeddraFile , "meddraroot", "r"+wordGetReplaced);					
					sentenceGetReplaced = "<"+currentIndexItem+">"+wordGetReplaced+"</"+currentIndexItem+">";
									
					//get the meddra description for the meddracode
					wordToReplace = "<"+currentIndexItem+">"+theWord+"</"+currentIndexItem+">";
						
					if (theWord != "") {
					
					//find the code in the filecontents and replace with the meddra description
					Pattern p = Pattern.compile(sentenceGetReplaced,Pattern.CASE_INSENSITIVE);
					Matcher m = p.matcher(filecontents);
					
					while (m.find()) {					
						m.appendReplacement(sb, wordToReplace);
					}			   		
			   		m.appendTail(sb);	
			   		filecontents = sb.toString();
					}
				}				
			}					
		}
		
		wordGetReplaced = "";
		wordToReplace = "";
		sentenceGetReplaced = "";
		theWord = ""; 
		
		//for each meddraversioncode in the meddra.txt  	    	
		for (int i=0; i<arrayCountryContents.size(); i++) {      
		
			String currentIndexItem = "";
			currentIndexItem = (String)arrayCountryContents.get(i);
				
			//if the meddracode is found in the meddra.txt
			if (tmpfilecontents.indexOf(currentIndexItem) > 0) {
																				
				//store the meddracode in an arraylist for the meddraversioncode	
				ArrayList<String> tmpContents = getMatchedContents(filecontents, currentIndexItem);
											
				//for each of the meddracode in the arraylist
				for (int n=0; n<tmpContents.size(); n++) {
																
					sb = new StringBuffer(""); 
					
					//get the meddracode								
					wordGetReplaced = (String)tmpContents.get(n);					
												
					theWord = getWord(tempCountryFile, "countrycoderoot", wordGetReplaced);					
					sentenceGetReplaced = "<"+currentIndexItem+">"+wordGetReplaced+"</"+currentIndexItem+">";
									
					//get the meddra description for the meddracode
					wordToReplace = "<"+currentIndexItem+">"+theWord+"</"+currentIndexItem+">";
						
					if (theWord != "") {
					
					//find the code in the filecontents and replace with the meddra description
					Pattern p = Pattern.compile(sentenceGetReplaced,Pattern.CASE_INSENSITIVE);
					Matcher m = p.matcher(filecontents);
					
					while (m.find()) {					
						m.appendReplacement(sb, wordToReplace);
					}			   		
			   		m.appendTail(sb);	
			   		filecontents = sb.toString();
					}
				}				
			}					
		}   
		long end= System.currentTimeMillis();
		log.info(end);
		log.info("Total millsecs elapsed:" + (end-start));
		return filecontents;		
	} 
	
	public InputStream getDBFiles(DataSource ds, String param) throws Exception{
		
		InputStream in 	= null;
		Connection con	= null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		try {
			String sql = "SELECT DATA_FILE FROM LAU_E2BVIEWER_DATA WHERE " +
					"DATA_TYPE = '" + param + "'";
			con = ds.getConnection();	
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			if(rs != null)
			{
				while(rs.next())
				{
					Clob clob = rs.getClob(1);
					in = clob.getAsciiStream();
				}	
			}
		}finally
		{
			try { if (stmt != null) stmt.close(); } catch (Exception e) { e.printStackTrace();log.error(e, e); }
			try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace();log.error(e, e); }
			try	{ if (con != null)	con.close();	}
			catch (Exception e)
			{	log.error(e, e);	}
		}
		return in;
	}
	
public StringBuffer getDBFile(DataSource ds, String param) throws Exception{
		
	log.info("PPPPPPPPPPPPPPPPPPPPPPPP");
		StringBuffer tmp 	= new StringBuffer();
		Connection con	= null;
		Statement stmt 	= null;
		ResultSet rs	= null;
		try {
			String sql = "SELECT DATA_FILE FROM LAU_E2BVIEWER_DATA WHERE " +
					"DATA_TYPE = '" + param + "'";
			con = ds.getConnection();	
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			if(rs != null)
			{
				while(rs.next())
				{
					Clob clob = rs.getClob(1);
					if (clob != null) {
						Reader reader = clob.getCharacterStream();
						CharArrayWriter writer = new CharArrayWriter();
						int i = -1;
						while ((i = reader.read()) != -1) {
							writer.write(i);
						}
						tmp.append(new String(writer.toCharArray()));
					}
				}	
			}
		}finally
		{
			try { if (stmt != null) stmt.close(); } catch (Exception e) { e.printStackTrace();log.error(e, e); }
			try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace();log.error(e, e); }
			try	{ if (con != null)	con.close();	}
			catch (Exception e)
			{	log.error(e, e);	}
		}
		log.info("OOOOOOOOOOOOOOOOOOOOOOOOO");
		return tmp;
	}
	
	private List<String> createArrayFromTxtFile(InputStream in) throws Exception{
		
		List<String> temp			= new ArrayList<String>();
		BufferedReader br 	= new BufferedReader(new InputStreamReader(in));
		String strLine;
		while ((strLine = br.readLine()) != null)   {
		   temp.add(strLine);
		}
		return temp;
	}
	
	private static ArrayList<String> getMatchedContents(String filecontents, String wordToMatch) {
		
		NodeList wordList = null;		    
		NodeList textWList = null;

		ArrayList<Element> elementList = new ArrayList<Element>();
		ArrayList<String> dataList = new ArrayList<String>();
		
		String nodeData = "";
		
	    try {		    		   		 	
	            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();	           	            
	            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();	             	            
	            Document doc = docBuilder.parse(
	            		new ByteArrayInputStream(filecontents.getBytes("UTF-8")));	            	           
			
	            // normalize text representation
	            doc.getDocumentElement().normalize();	            	            
			
	            NodeList listOfsafetyreport = doc.getElementsByTagName("safetyreport");	                  

	            for (int s=0; s<listOfsafetyreport.getLength(); s++) {
						
	                Node safetyreportNode = listOfsafetyreport.item(s);
	                if(safetyreportNode.getNodeType() == Node.ELEMENT_NODE){
	                	
	                    Element safetyreportElement = (Element)safetyreportNode;

	                    wordList = safetyreportElement.getElementsByTagName(wordToMatch);
	                                    	                    
	                    Element wordElement = null;
	                    for (int i=0; i<wordList.getLength(); i++) {	                    	
	                    	wordElement = (Element)wordList.item(i);
	                    	elementList.add(wordElement);
	                    }
	                    
	                    for (int n=0; n<elementList.size(); n++) {
	                    	
	                    	if (elementList.get(n) != null) {
	                    		textWList = ((Element)(elementList.get(n))).getChildNodes();
	                    		if (textWList.getLength() > 0) {
	                    			nodeData = ((Node)textWList.item(0)).getNodeValue().trim();
	                    			if (dataList.contains(nodeData) == false) {
	                    				dataList.add(nodeData);
	                    			}                    		
	                    		}	                    		
	                    	}
	                 	}   	                    
	                }//end of if clause		
	            }//end of for loop with s var
									
											    
			}	catch (Exception err) {
		       log.error(" " + err.getMessage() + err.toString());		      
	        }
	 
	 	return dataList; 	
	}	
	
	private String getWord(File in, String rootNode, String wordNode) throws Exception {
		
		NodeList wordList = null;
		NodeList textWList = null;
		String wordDesc = "";
				
	  	try {	    		   		 	
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();	           	            
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();	                       	            
            Document doc = docBuilder.parse(in);	            	           
		
            // normalize text representation
            doc.getDocumentElement().normalize();	            	            
	            
            NodeList listOfRootNode = doc.getElementsByTagName(rootNode);
            for (int s=0; s<listOfRootNode.getLength(); s++) {
					
                Node theRootNode = listOfRootNode.item(s);
                if(theRootNode.getNodeType() == Node.ELEMENT_NODE){
                	
                    Element rootNodeElement = (Element)theRootNode;	
                    
                    wordList = rootNodeElement.getElementsByTagName(wordNode);
                    Element wordElement = (Element)wordList.item(0);		                    
                    
                    if (wordElement != null) {
                    	textWList = wordElement.getChildNodes();
                    	if (textWList.getLength() > 0) {	                    		
                    		//if (fileName.indexOf("meddra") > 0) {
                    			wordDesc =((Node)textWList.item(0)).getNodeValue().trim() + " ("+wordNode.substring(1, wordNode.length())+")";
                    		//}else {
                    		//	wordDesc =((Node)textWList.item(0)).getNodeValue().trim();
                    		//}	                    			                    
                    	}
                    }   	                    
                }//end of if clause		
            }//end of for loop with s var
							    
		}catch (Exception err) {
			log.error(" " + err.getMessage() + err.toString());			
        }
	    return wordDesc;	
	}
	
}
