package com.nrg.lau.render;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import com.nrg.lau.commons.Constants;

public class GetResourceAction implements View {

	private static Logger log = Logger.getLogger(GetResourceAction.class);
	private DataSource ds;

	public String getContentType() {
		return "text/xml";
	}

	@SuppressWarnings("unchecked")
	public void setDataSource(DataSource dataSource) {
		new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("get Roles -Initialize db template()");
	}

	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("ENTER - get Roles  render()");
		response.setContentType(Constants.CONTENT_TYPE);
		response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
		PrintWriter pw = response.getWriter();
		if (Constants.DEBUG)
			pw.write(readData("/getResourceList.do").toString());
		else {
			String signature=getSignature().toString();
			String userData=getData().toString();
			String mergedUserData=mergeSignatureToUser(signature,userData);
			pw.write(mergedUserData);
		}
		pw.flush();
		pw.close();

		log.info("EXIT - get Resource render()");
	}

	private String mergeSignatureToUser(String signature,String userDet){
		String completeUserAttribute="";
		try{

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(userDet)));
			doc.getDocumentElement().normalize();

			Document docSign = dBuilder.parse(new InputSource(new StringReader(signature)));
			docSign.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("USER_GROUP");
			NodeList nListSign = docSign.getElementsByTagName("ROW");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) nNode;
					String userGroupId=element.getAttribute("USER_GROUP_ID");
					String userName="";
					NodeList userNodeList=element.getChildNodes();
					for(int k=0;k<userNodeList.getLength();k++){
						Node userNode=userNodeList.item(k);
						if (userNode.getNodeType() == Node.ELEMENT_NODE) {
							Element userElement=(Element)userNode;
							userName=userElement.getAttribute("USER_ID");

							for(int i=0;i<nListSign.getLength();i++){
								Node nNodeSign = nListSign.item(i);
								if (nNodeSign.getNodeType() == Node.ELEMENT_NODE) {
									Element elementSign=(Element)nNodeSign;
									NodeList nodeListSign=elementSign.getChildNodes();
									String userNameSign="";
									String userGroupIdSign="";
									String userSignatureSign="";

									String currentTag="";
									for(int j=0;j<nodeListSign.getLength();j++){

										Node mainNodeSign=nodeListSign.item(j);
										if (mainNodeSign.getNodeType() == Node.ELEMENT_NODE) {
											Element rowElementsSign= (Element)mainNodeSign;
											currentTag=rowElementsSign.getNodeName();

											if(currentTag=="USER_ID"){
												if(rowElementsSign.getFirstChild()!=null)
													userNameSign =rowElementsSign.getFirstChild().getNodeValue();
											}
											else if(currentTag=="USER_GROUP_ID"){
												if(rowElementsSign.getFirstChild()!=null)
													userGroupIdSign=rowElementsSign.getFirstChild().getNodeValue();
											}
											else if(currentTag=="USER_SIGNATURE")
											{
												if(rowElementsSign.getFirstChild()!=null)
													userSignatureSign=rowElementsSign.getFirstChild().getNodeValue();
											}

										}
									}
									if(userNameSign.equals(userName) && userGroupIdSign.equals(userGroupId)){
										userElement.setAttribute("USER_SIGNATURE",userSignatureSign);
										break;
									}

								}
							}
						}
					}
				}
			}

			TransformerFactory tf = new com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl();
			Transformer transformer = tf.newTransformer();

			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();

			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			completeUserAttribute = writer.getBuffer().toString().replaceAll("\n|\r", "");
		//	log.info(completeUserAttribute);
		}catch(Exception e){
			e.printStackTrace();
		}

		return completeUserAttribute;
	}
	private StringBuffer getSignature(){
		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;

		try {

			con 		= ds.getConnection();

			String sql 	= "{call ? := LAU_UTL.GetXMLFromQuery(?)}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			stmt.setString(2, "select user_signature,user_id,user_group_id from lau_users");
			log.info(sql);
			stmt.execute();
			Clob clob = stmt.getClob(1);
			if (clob != null) {
				Reader reader = clob.getCharacterStream();
				CharArrayWriter writer = new CharArrayWriter();
				int i = -1;
				while ((i = reader.read()) != -1) {
					writer.write(i);
				}
				tmp.append(new String(writer.toCharArray()));
			}

			stmt.close();
			con.close();

		} catch (SQLException e) {
			log.error(e, e);			
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error(e, e);
			}
		}
		//	log.info(tmp);
		return tmp;
	}
	private StringBuffer getData() {

		StringBuffer tmp 	= new StringBuffer();
		Connection con		= null;

		try {

			con 		= ds.getConnection();

			String sql 	= "{call ? := LAU_UTL.getResourceListdo()}";
			CallableStatement stmt = con.prepareCall(sql);

			stmt.registerOutParameter(1, OracleTypes.CLOB);
			log.info(sql);
			stmt.execute();
			Clob clob = stmt.getClob(1);
			if (clob != null) {
				Reader reader = clob.getCharacterStream();
				CharArrayWriter writer = new CharArrayWriter();
				int i = -1;
				while ((i = reader.read()) != -1) {
					writer.write(i);
				}
				tmp.append(new String(writer.toCharArray()));
			}

			stmt.close();
			con.close();

		} catch (SQLException e) {
			log.error(e, e);			
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.error(e, e);
			}
		}
		//	log.info(tmp);
		return tmp;
	}

	private StringBuffer readData(String fileName) throws IOException {

		log.info("11111");
		StringBuffer sb = new StringBuffer();
		URL fileURL = GetResourceAction.class.getResource(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				fileURL.openStream()));
		String theBuf = new String();

		while ((theBuf = reader.readLine()) != null) {
			sb.append(theBuf + "\n");
		}

		reader.close();
		log.info(sb);
		return sb;
		// return sb.toString();
	}

}
