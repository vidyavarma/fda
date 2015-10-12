<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo">
<xsl:template match="/">
<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
  
<fo:layout-master-set>
  	<fo:simple-page-master master-name="A4">
  		<fo:region-body margin="1cm"/>
  		<fo:region-before extent="0.5cm"/>
  		<fo:region-after extent="0.5cm"/>
  		<fo:region-start extent="0.5cm"/>
  		<fo:region-end extent="0.5cm"/>  
	</fo:simple-page-master>
</fo:layout-master-set>


<fo:page-sequence master-reference="A4">
  <fo:flow flow-name="xsl-region-body">
  <!-- Message header details-->
  <fo:block font-size="10pt" font-family="Arial,Times">
		<fo:table margin="3pt">
		<fo:table-body>
    		<fo:table-row>
    			<fo:table-cell  font-weight="bold" background-color="#DADADA">
    				<fo:block><xsl:text>M.1 Message Header Details</xsl:text></fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		<xsl:if test="/ichicsr/ichicsrmessageheader/messagetype[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Message Type: </xsl:text>
    				<xsl:value-of select="/ichicsr/ichicsrmessageheader/messagetype" />
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		 <xsl:if test="/ichicsr/ichicsrmessageheader/messageformatversion[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Message Format Version: </xsl:text>
    				<xsl:value-of select="/ichicsr/ichicsrmessageheader/messageformatversion" />
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/ichicsrmessageheader/messageformatrelease[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Message Format Release: </xsl:text>
    				<xsl:value-of select="/ichicsr/ichicsrmessageheader/messageformatrelease"/>
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/ichicsrmessageheader/messagenumb[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Message Number: </xsl:text>
    				<xsl:value-of select="/ichicsr/ichicsrmessageheader/messagenumb" />
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/ichicsrmessageheader/messagesenderidentifier[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Message Sender Identifier: </xsl:text>
    				<xsl:value-of select="/ichicsr/ichicsrmessageheader/messagesenderidentifier"/>
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/ichicsrmessageheader/messagereceiveridentifier[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Message Receiver Identifier: </xsl:text>
    				<xsl:value-of select="/ichicsr/ichicsrmessageheader/messagereceiveridentifier" />
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/ichicsrmessageheader/messagedate[.!='']">  
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Message Date: </xsl:text>
    				<xsl:variable name="messagedate" select="/ichicsr/ichicsrmessageheader/messagedate"/>               
					 <xsl:value-of select="concat(substring($messagedate, 7,
					 2),'-')"/>
					 <xsl:choose>
						 <xsl:when test="substring($messagedate, 5, 2) = 01">JAN</xsl:when>
						 <xsl:when test="substring($messagedate, 5, 2) = 02">FEB</xsl:when>
						 <xsl:when test="substring($messagedate, 5, 2) = 03">MAR</xsl:when>
						 <xsl:when test="substring($messagedate, 5, 2) = 04">APR</xsl:when>
						 <xsl:when test="substring($messagedate, 5, 2) = 05">MAY</xsl:when>
						 <xsl:when test="substring($messagedate, 5, 2) = 06">JUN</xsl:when>
						 <xsl:when test="substring($messagedate, 5, 2) = 07">JUL</xsl:when>
						 <xsl:when test="substring($messagedate, 5, 2) = 08">AUG</xsl:when>
						 <xsl:when test="substring($messagedate, 5, 2) = 09">SEP</xsl:when>
						 <xsl:when test="substring($messagedate, 5, 2) = 10">OCT</xsl:when>
						 <xsl:when test="substring($messagedate, 5, 2) = 11">NOV</xsl:when>
						 <xsl:when test="substring($messagedate, 5, 2) = 12">DEC</xsl:when>
					 </xsl:choose>
					 <xsl:value-of select="concat('-',substring($messagedate, 1,
					 4))"/>
					 <xsl:text> </xsl:text>
				     <xsl:value-of select="concat(substring($messagedate, 9,
					 2),':',substring($messagedate, 11, 2),':',substring($messagedate,13,2))"/>
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    	</fo:table-body>	
		</fo:table>
	</fo:block>
	
	<!-- Safety Report details-->
	<fo:block font-size="10pt" font-family="Arial,Times">
		<!-- Message header details-->  
		<fo:table margin="3pt">
		<fo:table-body>
    		<fo:table-row>
    			<fo:table-cell  font-weight="bold" background-color="#DADADA">
    				<fo:block><xsl:text>A.1 Safety Report Details</xsl:text></fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		<xsl:if test="/ichicsr/safetyreport/safetyreportversion[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Safety Report Version: </xsl:text>
    				<xsl:value-of select="/ichicsr/safetyreport/safetyreportversion" />
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/safetyreportid[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Safety Report Identifer: </xsl:text>
    				<xsl:value-of select="/ichicsr/safetyreport/safetyreportid" />
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block>
						<xsl:if test="/ichicsr/safetyreport/primarysourcecountry[.!='']">
			    			<xsl:text>Country of Primary Source: </xsl:text>
			    			<xsl:value-of select="/ichicsr/safetyreport/primarysourcecountry" />						    			
			    		</xsl:if>			    		
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
			<fo:table-row>	
				<fo:table-cell>
					<fo:block>
						<xsl:if test="/ichicsr/safetyreport/occurcountry[.!='']">
			    			<xsl:text>Country where Event occured: </xsl:text>
			    			<xsl:value-of select="/ichicsr/safetyreport/occurcountry" />						    			
			    		</xsl:if>
					</fo:block>
				</fo:table-cell>
    		</fo:table-row>
    		<xsl:if test="/ichicsr/safetyreport/transmissiondate[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Transmission Date: </xsl:text>
    				<xsl:variable name="transmissiondate" select="/ichicsr/safetyreport/transmissiondate"/>               
					 <xsl:value-of select="concat(substring($transmissiondate, 7,
					 2),'-')"/>
					 <xsl:choose>
						 <xsl:when test="substring($transmissiondate, 5, 2) = 01">JAN</xsl:when>
						 <xsl:when test="substring($transmissiondate, 5, 2) = 02">FEB</xsl:when>
						 <xsl:when test="substring($transmissiondate, 5, 2) = 03">MAR</xsl:when>
						 <xsl:when test="substring($transmissiondate, 5, 2) = 04">APR</xsl:when>
						 <xsl:when test="substring($transmissiondate, 5, 2) = 05">MAY</xsl:when>
						 <xsl:when test="substring($transmissiondate, 5, 2) = 06">JUN</xsl:when>
						 <xsl:when test="substring($transmissiondate, 5, 2) = 07">JUL</xsl:when>
						 <xsl:when test="substring($transmissiondate, 5, 2) = 08">AUG</xsl:when>
						 <xsl:when test="substring($transmissiondate, 5, 2) = 09">SEP</xsl:when>
						 <xsl:when test="substring($transmissiondate, 5, 2) = 10">OCT</xsl:when>
						 <xsl:when test="substring($transmissiondate, 5, 2) = 11">NOV</xsl:when>
						 <xsl:when test="substring($transmissiondate, 5, 2) = 12">DEC</xsl:when>
					 </xsl:choose>
					 <xsl:value-of select="concat('-',substring($transmissiondate, 1,
					 4))"/>
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/reporttype[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Report Type: </xsl:text>
    				<xsl:choose>            			
	          			<xsl:when test="/ichicsr/safetyreport/reporttype = 1">	          				
	            			Spontaneous
				        </xsl:when> 
				        <xsl:when test="/ichicsr/safetyreport/reporttype = 2">
				            Report from study
				        </xsl:when> 
				        <xsl:when test="/ichicsr/safetyreport/reporttype = 3">
				          	Other
				        </xsl:when>
				        <xsl:when test="/ichicsr/safetyreport/reporttype = 4">
				          	Not available to sender (unknown)
				        </xsl:when>		          		          		                  
				        <xsl:otherwise>		            
				        </xsl:otherwise>
				    </xsl:choose>  
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/serious[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Serious: </xsl:text>
    				<xsl:choose>
		            	<xsl:when test="/ichicsr/safetyreport/serious = 1">
			            	Yes
			          	</xsl:when> 
			           	<xsl:when test="/ichicsr/safetyreport/serious = 2">
			            	No
			          	</xsl:when> 		          		          		          		                  
			          	<xsl:otherwise>		            
			          	</xsl:otherwise>
		        	</xsl:choose>
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/seriousnessdeath[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Seriousness - Results in death: </xsl:text>
    				 <xsl:choose>
				          <xsl:when test="/ichicsr/safetyreport/seriousnessdeath = 1">
				            	Yes
				          </xsl:when> 
				           <xsl:when test="/ichicsr/safetyreport/seriousnessdeath = 2">
				           	 	No
				          </xsl:when> 		          		          		          		                  
				          <xsl:otherwise>		            
				          </xsl:otherwise>
			        </xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/seriousnesshospitalization[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Seriousness - Caused/Prolonged Hospitalization: </xsl:text>
    				<xsl:choose>
				          <xsl:when test="/ichicsr/safetyreport/seriousnesshospitalization = 1">
				            	Yes
				          </xsl:when> 
				           <xsl:when test="/ichicsr/safetyreport/seriousnesshospitalization = 2">
				            	No
				          </xsl:when> 		          		          		          		                  
				          <xsl:otherwise>		            
				          </xsl:otherwise>
			        </xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>    		
    		<xsl:if test="/ichicsr/safetyreport/seriousnessdisabling[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Seriousness - Disabling/Incapacitating: </xsl:text>
    				<xsl:choose>
				          <xsl:when test="/ichicsr/safetyreport/seriousnessdisabling = 1">
				            	Yes
				          </xsl:when> 
				           <xsl:when test="/ichicsr/safetyreport/seriousnessdisabling = 2">
				            	No
				          </xsl:when> 		          		          		          		                  
				          <xsl:otherwise>		            
				          </xsl:otherwise>
			        </xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/seriousnesscongenitalanomali[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Seriousness - Congenital anomaly/birth defect: </xsl:text>
    				<xsl:choose>
		            	<xsl:when test="/ichicsr/safetyreport/seriousnesscongenitalanomali = 1">
			            	Yes
			         	</xsl:when> 
			           	<xsl:when test="/ichicsr/safetyreport/seriousnesscongenitalanomali = 2">
			            	No
			          	</xsl:when> 		          		          		          		                  
			          	<xsl:otherwise>		            
			          	</xsl:otherwise>
			        </xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/seriousnessother[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Seriousness - Others: </xsl:text>
    				 <xsl:choose>
			          	<xsl:when test="/ichicsr/safetyreport/seriousnessother = 1">
			            	Yes
			          	</xsl:when> 
			           	<xsl:when test="/ichicsr/safetyreport/seriousnessother = 2">
			            	No
			          	</xsl:when> 		          		          		          		                  
			          	<xsl:otherwise>		            
			          	</xsl:otherwise>
		        	</xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/receivedate[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Date Report was first received from source: </xsl:text>
    				<xsl:variable name="receivedate" select="/ichicsr/safetyreport/receivedate"/>               
					 <xsl:value-of select="concat(substring($receivedate, 7,
					 2),'-')"/>
					 <xsl:choose>
						 <xsl:when test="substring($receivedate, 5, 2) = 01">JAN</xsl:when>
						 <xsl:when test="substring($receivedate, 5, 2) = 02">FEB</xsl:when>
						 <xsl:when test="substring($receivedate, 5, 2) = 03">MAR</xsl:when>
						 <xsl:when test="substring($receivedate, 5, 2) = 04">APR</xsl:when>
						 <xsl:when test="substring($receivedate, 5, 2) = 05">MAY</xsl:when>
						 <xsl:when test="substring($receivedate, 5, 2) = 06">JUN</xsl:when>
						 <xsl:when test="substring($receivedate, 5, 2) = 07">JUL</xsl:when>
						 <xsl:when test="substring($receivedate, 5, 2) = 08">AUG</xsl:when>
						 <xsl:when test="substring($receivedate, 5, 2) = 09">SEP</xsl:when>
						 <xsl:when test="substring($receivedate, 5, 2) = 10">OCT</xsl:when>
						 <xsl:when test="substring($receivedate, 5, 2) = 11">NOV</xsl:when>
						 <xsl:when test="substring($receivedate, 5, 2) = 12">DEC</xsl:when>
					 </xsl:choose>
					 <xsl:value-of select="concat('-',substring($receivedate, 1,
					 4))"/>			
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		<fo:table-row>    				
    			<xsl:if test="/ichicsr/safetyreport/receiptdate[.!='']">
    			<fo:table-cell>
    				<fo:block>
    				<xsl:text>Date of receipt of the most recent information for this report: </xsl:text>
    				<xsl:variable name="receiptdate" select="/ichicsr/safetyreport/receiptdate"/>               
					 <xsl:value-of select="concat(substring($receiptdate, 7,
					 2),'-')"/>
					 <xsl:choose>
						 <xsl:when test="substring($receiptdate, 5, 2) = 01">JAN</xsl:when>
						 <xsl:when test="substring($receiptdate, 5, 2) = 02">FEB</xsl:when>
						 <xsl:when test="substring($receiptdate, 5, 2) = 03">MAR</xsl:when>
						 <xsl:when test="substring($receiptdate, 5, 2) = 04">APR</xsl:when>
						 <xsl:when test="substring($receiptdate, 5, 2) = 05">MAY</xsl:when>
						 <xsl:when test="substring($receiptdate, 5, 2) = 06">JUN</xsl:when>
						 <xsl:when test="substring($receiptdate, 5, 2) = 07">JUL</xsl:when>
						 <xsl:when test="substring($receiptdate, 5, 2) = 08">AUG</xsl:when>
						 <xsl:when test="substring($receiptdate, 5, 2) = 09">SEP</xsl:when>
						 <xsl:when test="substring($receiptdate, 5, 2) = 10">OCT</xsl:when>
						 <xsl:when test="substring($receiptdate, 5, 2) = 11">NOV</xsl:when>
						 <xsl:when test="substring($receiptdate, 5, 2) = 12">DEC</xsl:when>
					 </xsl:choose>
					 <xsl:value-of select="concat('-',substring($receiptdate, 1,
					 4))"/>
    				</fo:block>
    			</fo:table-cell>
    			</xsl:if>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/additionaldocument[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Additional Available documents held by sender</xsl:text>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Are additional documents available: </xsl:text>
    				<xsl:choose>
		            	<xsl:when test="/ichicsr/safetyreport/additionaldocument = 1">
			            	Yes
			          	</xsl:when>
			          	<xsl:when test="/ichicsr/safetyreport/additionaldocument = 2">
			            	No
			          	</xsl:when> 		           		          		          		          		                  
			          	<xsl:otherwise>	          		            
			          	</xsl:otherwise>
		        	</xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>	
    		<xsl:if test="/ichicsr/safetyreport/documentlist[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>List of documents held by sender: </xsl:text>
    				<xsl:value-of select="/ichicsr/safetyreport/documentlist" />
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>	
    		<xsl:if test="/ichicsr/safetyreport/fulfillexpeditecriteria[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Does this case fulfill the local criteria for an expedited report?: </xsl:text>
    				<xsl:choose>
				        <xsl:when test="/ichicsr/safetyreport/fulfillexpeditecriteria = 1">
				            Yes
				        </xsl:when> 
				        <xsl:when test="/ichicsr/safetyreport/fulfillexpeditecriteria = 2">
				            No
				        </xsl:when> 		          		          		          		                  
				        <xsl:otherwise>		            
				        </xsl:otherwise>
		        	</xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>    				
    	</fo:table-body>
    	</fo:table>
    </fo:block>
    <!-- Worldwide unique case details--> 
    <fo:block font-size="10pt" font-family="Arial,Times">
		<fo:table margin="3pt">
		<fo:table-body>
    		<xsl:if test="/ichicsr/safetyreport/authoritynumb[.!=''] or /ichicsr/safetyreport/companynumb[.!='']">
    		<fo:table-row>
    			<fo:table-cell font-weight="bold">
    				<fo:block border-bottom-style="solid" border-bottom-width="1pt" border-color="#CCCCCC"><xsl:text>Worldwide unique case identification number</xsl:text></fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>    		
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/authoritynumb[.!='']">
    		<fo:table-row space-before="10pt">
    			<fo:table-cell>
    				<fo:block><xsl:text>Regulatory authority's case report number: </xsl:text>
    				<xsl:value-of select="/ichicsr/safetyreport/authoritynumb" />
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/companynumb[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Other sender's case report number: </xsl:text>
    				<xsl:value-of select="/ichicsr/safetyreport/companynumb" />
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/duplicate[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Other case identifiers in previous transmissions: </xsl:text>
    				<xsl:choose>			        	
			        	<xsl:when test="/ichicsr/safetyreport/duplicate = 1">
			        		Yes
			        	</xsl:when>
			        </xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/reportduplicate[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:table border-width="1pt" border-style="solid" border-color="#CCCCCC">
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell font-weight="bold" background-color="#DADADA">
								<fo:block><xsl:text>A.1.11 Report duplicate</xsl:text>    				
    							</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<xsl:for-each select="/ichicsr/safetyreport/reportduplicate">	
						<fo:table-row>
							<fo:table-cell>
								<fo:table>
								<fo:table-body>
									<xsl:if test="/ichicsr/safetyreport/duplicatesource[.!='']">
										<fo:table-row>
							    			<fo:table-cell>
							    				<fo:block><xsl:text>Source(s) of the case identifier: </xsl:text>
							    				<xsl:value-of select="/ichicsr/safetyreport/duplicatesource" />
							    				</fo:block>
							    			</fo:table-cell>    			
							    		</fo:table-row>
									</xsl:if>
									<xsl:if test="/ichicsr/safetyreport/duplicatenumb[.!='']">
										<fo:table-row>
							    			<fo:table-cell>
							    				<fo:block><xsl:text>Case identifiers: </xsl:text>
							    				<xsl:value-of select="/ichicsr/safetyreport/duplicatenumb" />
							    				</fo:block>
							    			</fo:table-cell>    			
							    		</fo:table-row>
									</xsl:if>
								</fo:table-body>
								</fo:table>
							</fo:table-cell>
						</fo:table-row>
						</xsl:for-each>
					</fo:table-body>
					</fo:table>    				
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/linkedreport/linkreportnumb[.!='']">
    		<fo:table-row>
				<fo:table-cell>
					<fo:table>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell font-weight="bold" background-color="#DADADA">
								<fo:block><xsl:text>A.1.12 Linked Report</xsl:text>    				
 								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<xsl:for-each select="/ichicsr/safetyreport/linkedreport">
						<fo:table-row>
							<fo:table-cell>
								<fo:block font-size="10pt" font-family="Arial,Times">
									<fo:table border-width="1pt" border-style="solid" border-color="#CCCCCC">
									<fo:table-body>
										<fo:table-row>
											<fo:table-cell>
												<fo:block><xsl:text>Identification number of the report which is linked to this report: </xsl:text>
							    				<xsl:value-of select="/ichicsr/safetyreport/linkedreport/linkreportnumb" />
							    				</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
									</fo:table>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>				
						</xsl:for-each>										
 					</fo:table-body>
 					</fo:table>		
				</fo:table-cell>
			</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/casenullification[.!='']">
    		<fo:table-row>
				<fo:table-cell>
					<fo:block><xsl:text>Report nullification: </xsl:text>
    				<xsl:choose>
		          		<xsl:when test="/ichicsr/safetyreport/casenullification = 1">
		           		 	Yes
		          		</xsl:when>        		          		          		                  
		          		<xsl:otherwise>		            
		          		</xsl:otherwise>
		        	</xsl:choose>
    				</fo:block>
				</fo:table-cell>
			</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/nullificationreason[.!='']">
    		<fo:table-row>
				<fo:table-cell>
					<fo:block><xsl:text>Reason for nullification: </xsl:text>
    				<xsl:value-of select="/ichicsr/safetyreport/nullificationreason" />
    				</fo:block>
				</fo:table-cell>
			</fo:table-row>
    		</xsl:if>
    		<xsl:if test="/ichicsr/safetyreport/medicallyconfirm[.!='']">
    		<fo:table-row>
				<fo:table-cell>
					<fo:block><xsl:text>Was the case medically confirmed, if not initially from health professional?: </xsl:text>
    				<xsl:choose>
			          <xsl:when test="/ichicsr/safetyreport/medicallyconfirm = 1">
			            	Yes
			          </xsl:when>  
			           <xsl:when test="/ichicsr/safetyreport/medicallyconfirm = 2">
			            	No
			          </xsl:when>
			            <xsl:otherwise>		            
	          			</xsl:otherwise>
	        		</xsl:choose>
    				</fo:block>
				</fo:table-cell>
			</fo:table-row>
    		</xsl:if>
    	</fo:table-body>
    	</fo:table>
    </fo:block>
    <!-- Primary source of information-->
    <fo:block font-size="10pt" font-family="Arial,Times">
		<fo:table margin="3pt">
		<fo:table-body>
			<fo:table-row font-weight="bold" background-color="#DADADA">
				<fo:table-cell number-columns-spanned="3">
					<fo:block><xsl:text>A.2 Primary Source of information</xsl:text>    				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<xsl:if test="reportertitle[.!='']">
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block><xsl:text>Reporter Title: </xsl:text>
					<xsl:value-of select="/ichicsr/safetyreport/primarysource/reportertitle" />    				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			</xsl:if>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/reportergivename[.!='']"> -->
							<xsl:text>Reporter given name: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/primarysource/reportergivename" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/reportermiddlename[.!='']"> -->
							<xsl:text>Reporter middle name: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/primarysource/reportermiddlename" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/reporterfamilyname[.!='']"> -->
							<xsl:text>Reporter family name: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/primarysource/reporterfamilyname" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/reporterorganization[.!='']"> -->
							<xsl:text>Reporter organization: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/primarysource/reporterorganization" /> 
						<!-- </xsl:if> -->    				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/reporterdepartment[.!='']"> -->
							<xsl:text>Reporter department: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/primarysource/reporterdepartment" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/reporterstreet[.!='']"> -->
							<xsl:text>Reporter street: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/primarysource/reporterstreet" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/reportercity[.!='']"> -->
							<xsl:text>Reporter city: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/primarysource/reportercity" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/reporterstate[.!='']"> -->
							<xsl:text>Reporter state or province: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/primarysource/reporterstate" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/reporterpostcode[.!='']"> -->
							<xsl:text>Reporter postcode: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/primarysource/reporterpostcode" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/reportercountry[.!='']"> -->
							<xsl:text>Reporter country code: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/primarysource/reportercountry" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>				
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/qualification[.!='']"> -->
							<xsl:text>Qualification: </xsl:text>
							<xsl:choose>
					        	<xsl:when test="/ichicsr/safetyreport/primarysource/qualification = 1">
					        		Physician
					          	</xsl:when> 
					           	<xsl:when test="/ichicsr/safetyreport/primarysource/qualification = 2">
					           		Pharmacist
					          	</xsl:when> 
					            <xsl:when test="/ichicsr/safetyreport/primarysource/qualification = 3">
					           		 Other Health Professional
					          	</xsl:when> 
					            <xsl:when test="/ichicsr/safetyreport/primarysource/qualification = 4">
					           		 Lawyer
					          	</xsl:when> 
					            <xsl:when test="/ichicsr/safetyreport/primarysource/qualification = 5">
					           		 Consumer or other non health professional
					          	</xsl:when>
					            <xsl:otherwise>		            
		          				</xsl:otherwise>
				        	</xsl:choose> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>				
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/literaturereference[.!='']"> -->
							<xsl:text>Literature reference(s): </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/primarysource/literaturereference" />
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>									
		</fo:table-body>
		</fo:table>
	</fo:block>	
        
    <fo:block font-size="10pt" font-family="Arial,Times">
		<fo:table margin="3pt">
		<fo:table-body>
			<fo:table-row>				
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/studyname[.!=''] or /ichicsr/safetyreport/primarysource/sponsorstudynumb[.!=''] or /ichicsr/safetyreport/primarysource/observestudytype[.!='']"> -->
							<xsl:text>Study identification: </xsl:text>							
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>				
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/studyname[.!='']"> -->
							<xsl:text>Study name: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/primarysource/studyname" />
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>				
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/sponsorstudynumb[.!='']"> -->
							<xsl:text>Sponsor study number: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/primarysource/sponsorstudynumb" />
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>				
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/primarysource/observestudytype[.!='']"> -->
							<xsl:text>Study type in which the reaction(s)/event(s) were observed: </xsl:text>
							<xsl:choose>
					          <xsl:when test="/ichicsr/safetyreport/primarysource/observestudytype = 1">
					            	Clinical trials
					          </xsl:when> 
					           <xsl:when test="/ichicsr/safetyreport/primarysource/observestudytype = 2">
					            	Individual patient use
					          </xsl:when> 
					           <xsl:when test="/ichicsr/safetyreport/primarysource/observestudytype = 3">
					            	Other studies
					          </xsl:when>
					          <xsl:otherwise>		            
		          			  </xsl:otherwise>
				        	</xsl:choose>
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>		
		</fo:table-body>
		</fo:table>
	</fo:block>					
	
	<fo:block font-size="10pt" font-family="Arial,Times">
		<fo:table margin="3pt">
		<fo:table-body>
			<fo:table-row font-weight="bold" background-color="#DADADA">
				<fo:table-cell number-columns-spanned="3">
					<fo:block><xsl:text>A.3.1 Information on Sender of Case Safety Report</xsl:text>    				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<!-- <xsl:if test="/ichicsr/safetyreport/sender/sendertype[.!='']"> -->
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block><xsl:text>Type: </xsl:text>
					<xsl:choose>
				          <xsl:when test="/ichicsr/safetyreport/sender/sendertype = 1">
				            	Pharmaceutical Company
				          </xsl:when> 
				           <xsl:when test="/ichicsr/safetyreport/sender/sendertype = 2">
				            	Regulatory Authority
				          </xsl:when> 
				            <xsl:when test="/ichicsr/safetyreport/sender/sendertype = 3">
				           		Health professional
				          </xsl:when>
				            <xsl:when test="/ichicsr/safetyreport/sender/sendertype = 4">
				           		Regional Pharmacovigilance Center
				          </xsl:when>
				            <xsl:when test="/ichicsr/safetyreport/sender/sendertype = 5">
				           		WHO Collaborating Center for International Drug Monitoring
				          </xsl:when>
				            <xsl:when test="/ichicsr/safetyreport/sender/sendertype = 6">
				           		Other
				          </xsl:when>
				          <xsl:otherwise>		            
		      			  </xsl:otherwise>
			        </xsl:choose>    				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<!-- </xsl:if> -->
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/senderorganization[.!='']"> -->
							<xsl:text>Organization: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/senderorganization" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/senderdepartment[.!='']"> -->
							<xsl:text>Department: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/senderdepartment" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<!-- <xsl:if test="/ichicsr/safetyreport/sender/sendertitle[.!='']"> -->
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block><xsl:text>Title: </xsl:text>
					<xsl:value-of select="/ichicsr/safetyreport/sender/sendertitle" />			
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<!-- </xsl:if> -->
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/sendergivename[.!='']"> -->
							<xsl:text>Given name: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/sendergivename" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/sendermiddlename[.!='']"> -->
							<xsl:text>Middle name: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/sendermiddlename" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/senderfamilyname[.!='']"> -->
							<xsl:text>Family name: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/senderfamilyname" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
			<!-- <xsl:if test="/ichicsr/safetyreport/sender/senderstreetaddress[.!='']"> -->
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block><xsl:text>Street address: </xsl:text>
					<xsl:value-of select="/ichicsr/safetyreport/sender/senderstreetaddress" />			
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<!-- </xsl:if> -->
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/sendercity[.!='']"> -->
							<xsl:text>City: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/sendercity" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/senderstate[.!='']"> -->
							<xsl:text>State or Province: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/senderstate" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/senderpostcode[.!='']"> -->
							<xsl:text>Postcode: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/senderpostcode" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/sendercountrycode[.!='']"> -->
							<xsl:text>Country Code: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/sendercountrycode" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/sendertel[.!='']"> -->
							<xsl:text>Telephone: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/sendertel" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/sendertelextension[.!='']"> -->
							<xsl:text>Telephone extension: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/sendertelextension" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/sendertelcountrycode[.!='']"> -->
							<xsl:text>Telephone country code: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/sendertelcountrycode" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/senderfax[.!='']"> -->
							<xsl:text>Fax: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/senderfax" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/senderfaxextension[.!='']"> -->
							<xsl:text>Fax extension: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/senderfaxextension" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/senderfaxcountrycode[.!='']"> -->
							<xsl:text>Fax country code: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/senderfaxcountrycode" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/sender/senderemailaddress[.!='']"> -->
							<xsl:text>E-mail address: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/sender/senderemailaddress" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
		</fo:table-body>
		</fo:table>
	</fo:block>
	
	<!-- Receiver information-->
    <fo:block font-size="10pt" font-family="Arial,Times">
		<fo:table margin="3pt">
		<fo:table-body>
			<fo:table-row font-weight="bold" background-color="#DADADA">
				<fo:table-cell number-columns-spanned="3">
					<fo:block><xsl:text>A.3.2 Information on Receiver of Case Safety Report</xsl:text>    				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<!-- <xsl:if test="/ichicsr/safetyreport/receiver/sendertype[.!='']"> -->
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block><xsl:text>Type: </xsl:text>
					<xsl:choose>
				          <xsl:when test="/ichicsr/safetyreport/receiver/receivertype = 1">
				            	Pharmaceutical Company
				          </xsl:when> 
				           <xsl:when test="/ichicsr/safetyreport/receiver/receivertype = 2">
				            	Regulatory Authority
				          </xsl:when> 
				            <xsl:when test="/ichicsr/safetyreport/receiver/receivertype = 3">
				           		Health professional
				          </xsl:when>
				            <xsl:when test="/ichicsr/safetyreport/receiver/receivertype = 4">
				           		Regional Pharmacovigilance Center
				          </xsl:when>
				            <xsl:when test="/ichicsr/safetyreport/receiver/receivertype = 5">
				           		WHO Collaborating Center for International Drug Monitoring
				          </xsl:when>
				            <xsl:when test="/ichicsr/safetyreport/receiver/receivertype = 6">
				           		Other
				          </xsl:when>
				          <xsl:otherwise>		            
	          			  </xsl:otherwise>
			        </xsl:choose>    				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<!-- </xsl:if> -->
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receiverorganization[.!='']"> -->
							<xsl:text>Organization: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receiverorganization" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receiverdepartment[.!='']"> -->
							<xsl:text>Department: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receiverdepartment" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receivertitle[.!='']"> -->
							<xsl:text>Title: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receivertitle" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receivergivename[.!='']"> -->
							<xsl:text>Given name: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receivergivename" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receivermiddlename[.!='']"> -->
							<xsl:text>Middle name: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receivermiddlename" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receiverfamilyname[.!='']"> -->
							<xsl:text>Family name: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receiverfamilyname" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receiverstreetaddress[.!='']"> -->
							<xsl:text>Street address: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receiverstreetaddress" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receivercity[.!='']"> -->
							<xsl:text>City: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receivercity" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receiverstate[.!='']"> -->
							<xsl:text>State or Province: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receiverstate" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receiverpostcode[.!='']"> -->
							<xsl:text>Postcode: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receiverpostcode" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receivercountrycode[.!='']"> -->
							<xsl:text>Country Code: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receivercountrycode" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receivertel[.!='']"> -->
							<xsl:text>Telephone: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receivertel" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receivertelextension[.!='']"> -->
							<xsl:text>Telephone extension: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receivertelextension" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receivertelcountrycode[.!='']"> -->
							<xsl:text>Telephone country code: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receivertelcountrycode" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receiverfax[.!='']"> -->
							<xsl:text>Fax: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receiverfax" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receiverfaxextension[.!='']"> -->
							<xsl:text>Fax extension: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receiverfaxextension" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receiverfaxcountrycode[.!='']"> -->
							<xsl:text>Fax country code: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receiverfaxcountrycode" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/receiver/receiveremailaddress[.!='']"> -->
							<xsl:text>E-mail address: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/receiver/receiveremailaddress" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
		</fo:table-body>
		</fo:table>
	</fo:block>
	
	<!-- Patient information-->
    <fo:block font-size="10pt" font-family="Arial,Times">
		<fo:table margin="3pt">
		<fo:table-body>
			<fo:table-row font-weight="bold" background-color="#DADADA">
				<fo:table-cell number-columns-spanned="3">
					<fo:block><xsl:text>B.1 Patient Information</xsl:text>    				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientinitial[.!='']"> -->
							<xsl:text>Initials: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/patientinitial" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientmedicalrecordnumb[.!='']"> -->
							<xsl:text>GP Medical Record Number: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/patientmedicalrecordnumb" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientspecialistrecordnumb[.!='']"> -->
							<xsl:text>Specialist Record Number: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/patientspecialistrecordnumb" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/patienthospitalrecordnumb[.!='']"> -->
							<xsl:text>Hospital Record Number: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/patienthospitalrecordnumb" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientinvestigationnumb[.!='']"> -->
							<xsl:text>Investigation Number: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/patientinvestigationnumb" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientbirthdate[.!='']"> -->
							<xsl:text>Birth Date: </xsl:text>
							<xsl:variable name="patientbirthdate" select="/ichicsr/safetyreport/patient/patientbirthdate"/>               
							 <xsl:value-of select="concat(substring($patientbirthdate, 7,
							 2),'-')"/>
							 <xsl:choose>
								 <xsl:when test="substring($patientbirthdate, 5, 2) = 01">JAN</xsl:when>
								 <xsl:when test="substring($patientbirthdate, 5, 2) = 02">FEB</xsl:when>
								 <xsl:when test="substring($patientbirthdate, 5, 2) = 03">MAR</xsl:when>
								 <xsl:when test="substring($patientbirthdate, 5, 2) = 04">APR</xsl:when>
								 <xsl:when test="substring($patientbirthdate, 5, 2) = 05">MAY</xsl:when>
								 <xsl:when test="substring($patientbirthdate, 5, 2) = 06">JUN</xsl:when>
								 <xsl:when test="substring($patientbirthdate, 5, 2) = 07">JUL</xsl:when>
								 <xsl:when test="substring($patientbirthdate, 5, 2) = 08">AUG</xsl:when>
								 <xsl:when test="substring($patientbirthdate, 5, 2) = 09">SEP</xsl:when>
								 <xsl:when test="substring($patientbirthdate, 5, 2) = 10">OCT</xsl:when>
								 <xsl:when test="substring($patientbirthdate, 5, 2) = 11">NOV</xsl:when>
								 <xsl:when test="substring($patientbirthdate, 5, 2) = 12">DEC</xsl:when>
							 </xsl:choose>
							 <xsl:value-of select="concat('-',substring($patientbirthdate, 1,
							 4))"/> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientonsetage[.!='']"> -->
							<xsl:text>Age at time of reaction: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/patientonsetage" />
			                <xsl:text> </xsl:text>	               
			                <xsl:choose>
						          <xsl:when test="/ichicsr/safetyreport/patient/patientonsetageunit = 800">
						           		 Decade(s)
						          </xsl:when> 
						           <xsl:when test="/ichicsr/safetyreport/patient/patientonsetageunit = 801">
						           		 Year(s)
						          </xsl:when> 	
							   		<xsl:when test="/ichicsr/safetyreport/patient/patientonsetageunit = 802">
									     Month(s)
								  </xsl:when> 	
		  		 				  <xsl:when test="/ichicsr/safetyreport/patient/patientonsetageunit = 803">
				            			 Week(s)
				          		   </xsl:when> 		          		          		          		                    	 
						          <xsl:when test="/ichicsr/safetyreport/patient/patientonsetageunit = 804">
						           		Day(s)
						          </xsl:when> 	
		   					      <xsl:when test="/ichicsr/safetyreport/patient/patientonsetageunit = 805">
				            			Hour(s)
				          		  </xsl:when>
				          		  <xsl:otherwise>		            
				          		  </xsl:otherwise>
				        	</xsl:choose> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/gestationperiod[.!='']"> -->
							<xsl:text>Gestation Period: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/gestationperiod" />
			                <xsl:text> </xsl:text>
			                <xsl:choose>             
			   					<xsl:when test="/ichicsr/safetyreport/patient/gestationperiodunit = 802">
					            		Month(s)
					            </xsl:when> 	
	  							<xsl:when test="/ichicsr/safetyreport/patient/gestationperiodunit= 803">
			            				Week(s)
			          			</xsl:when> 		          		          		          		                    	 
			          			<xsl:when test="/ichicsr/safetyreport/patient/gestationperiodunit = 804">
			           					Day(s)
			          			</xsl:when> 	
	   							<xsl:when test="/ichicsr/safetyreport/patient/gestationperiodunit = 810">
			            				Trimester(s)
			          			</xsl:when>
			          			<xsl:otherwise>		            
		          				</xsl:otherwise> 
			       			 </xsl:choose> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientagegroup[.!='']"> -->
							<xsl:text>Age Group: </xsl:text>
							<xsl:choose>           
								<xsl:when test="/ichicsr/safetyreport/patient/patientagegroup =1">
					           			Neonate
					          	</xsl:when> 	
			  		 			<xsl:when test="/ichicsr/safetyreport/patient/patientagegroup = 2">
					            		Infant
					          	</xsl:when> 
								<xsl:when test="/ichicsr/safetyreport/patient/patientagegroup = 3">
					         			Child
					          	</xsl:when> 	
			   					<xsl:when test="/ichicsr/safetyreport/patient/patientagegroup= 4">
					            		Adolescent
					          	</xsl:when> 	
								<xsl:when test="/ichicsr/safetyreport/patient/patientagegroup= 5">
					            		Adult
					          	</xsl:when> 	
								<xsl:when test="/ichicsr/safetyreport/patient/patientagegroup= 6">
					            		Elderly
					          	</xsl:when> 
					          	<xsl:otherwise>		            
				          		</xsl:otherwise>
					        </xsl:choose> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientweight[.!='']"> -->
							<xsl:text>>Weight (kg): </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/patientweight" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientheight[.!='']"> -->
							<xsl:text>Height (cm): </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/patientheight" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientsex[.!='']"> -->
							<xsl:text>Sex: </xsl:text>
							<xsl:choose>           
								<xsl:when test="/ichicsr/safetyreport/patient/patientsex =1">
								       Male
								</xsl:when> 	
						  		<xsl:when test="/ichicsr/safetyreport/patient/patientsex = 2">
								       Female
								</xsl:when> 
								<xsl:otherwise>		            
				          		</xsl:otherwise>
							</xsl:choose> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
							<xsl:text>Last Menstrual Date: </xsl:text>
							<xsl:choose>           
								<xsl:when test="/ichicsr/safetyreport/patient/lastmenstrualdateformat = 102 ">
								           <xsl:variable name="patientlastmenstrualdate" select="/ichicsr/safetyreport/patient/patientlastmenstrualdate"/>               
											 <xsl:value-of select="concat(substring($patientlastmenstrualdate, 7,
											 2),'-')"/>
											 <xsl:choose>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 01">JAN</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 02">FEB</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 03">MAR</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 04">APR</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 05">MAY</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 06">JUN</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 07">JUL</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 08">AUG</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 09">SEP</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 10">OCT</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 11">NOV</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 12">DEC</xsl:when>
											 </xsl:choose>
											 <xsl:value-of select="concat('-',substring($patientlastmenstrualdate, 1,
											 4))"/>		
								</xsl:when> 	
						  		<xsl:when test="/ichicsr/safetyreport/patient/lastmenstrualdateformat = 610 ">
								           <xsl:variable name="patientlastmenstrualdate" select="/ichicsr/safetyreport/patient/patientlastmenstrualdate"/>
											 <xsl:choose>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 01">JAN</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 02">FEB</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 03">MAR</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 04">APR</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 05">MAY</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 06">JUN</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 07">JUL</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 08">AUG</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 09">SEP</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 10">OCT</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 11">NOV</xsl:when>
												 <xsl:when test="substring($patientlastmenstrualdate, 5, 2) = 12">DEC</xsl:when>
											 </xsl:choose>
											 <xsl:value-of select="concat('-',substring($patientlastmenstrualdate, 1,
											 4))"/>		
								</xsl:when> 
								<xsl:when test="/ichicsr/safetyreport/patient/lastmenstrualdateformat = 602 ">
								           <xsl:variable name="patientlastmenstrualdate" select="/ichicsr/safetyreport/patient/patientlastmenstrualdate"/>               
								 		   <xsl:value-of select="substring($patientlastmenstrualdate, 1, 4)"/>
								</xsl:when>
								<xsl:otherwise>		            
				                </xsl:otherwise>
							</xsl:choose>						 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientmedicalhistorytext[.!='']"> -->
							<xsl:text>Medical History Text: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/patientmedicalhistorytext" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/resultstestsprocedures[.!='']"> -->
							<xsl:text>Results Test Procedure: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/resultstestsprocedures" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>			 
		</fo:table-body>
		</fo:table>
	</fo:block>			
	<xsl:if test="/ichicsr/safetyreport/patient/medicalhistoryepisode[.!='']">
		<!-- Patient Medical history information-->
		<fo:block font-size="10pt" font-family="Arial,Times">
		<fo:table margin="3pt">
		<fo:table-body>
			<fo:table-row font-weight="bold" background-color="#DADADA">
				<fo:table-cell number-columns-spanned="3">
					<fo:block><xsl:text>B.1.7 Relevant medical history and concurrent conditions</xsl:text>    				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<xsl:for-each select="/ichicsr/safetyreport/patient/medicalhistoryepisode">
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block font-size="10pt" font-family="Arial,Times">
						<fo:table border-width="1pt" border-style="solid" border-color="#CCCCCC">
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientepisodenamemeddraversion[.!='']"> -->
											<xsl:text>MedDRA version for Medical History: </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientepisodenamemeddraversion" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell number-columns-spanned="2">
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientepisodename[.!='']"> -->
											<xsl:text>Structured information: </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/medicalhistoryepisode/medicalhistoryepisode/patientepisodename" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>				
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalstartdate[.!='']"> -->
											<xsl:text>Medical Start Date: </xsl:text>
											<xsl:choose>           
												<xsl:when test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalstartdateformat = 102 ">
												          <xsl:variable name="patientmedicalstartdate" select="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalstartdate"/>               
															 <xsl:value-of select="concat(substring($patientmedicalstartdate, 7,
															 2),'-')"/>
															 <xsl:choose>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 01">JAN</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 02">FEB</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 03">MAR</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 04">APR</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 05">MAY</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 06">JUN</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 07">JUL</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 08">AUG</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 09">SEP</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 10">OCT</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 11">NOV</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 12">DEC</xsl:when>
															 </xsl:choose>
															 <xsl:value-of select="concat('-',substring($patientmedicalstartdate, 1,
															 4))"/>		
												</xsl:when> 	
										  		<xsl:when test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalstartdateformat = 610 ">
												           <xsl:variable name="patientmedicalstartdate" select="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalstartdate"/>
															 <xsl:choose>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 01">JAN</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 02">FEB</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 03">MAR</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 04">APR</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 05">MAY</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 06">JUN</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 07">JUL</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 08">AUG</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 09">SEP</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 10">OCT</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 11">NOV</xsl:when>
																 <xsl:when test="substring($patientmedicalstartdate, 5, 2) = 12">DEC</xsl:when>
															 </xsl:choose>
															 <xsl:value-of select="concat('-',substring($patientmedicalstartdate, 1,
															 4))"/>	
												</xsl:when> 
												<xsl:when test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalstartdateformat = 602 ">
												           <xsl:variable name="patientmedicalstartdate" select="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalstartdate"/>               
												 		   <xsl:value-of select="substring($patientmedicalstartdate, 1, 4)"/>
												</xsl:when>
												<xsl:otherwise>		            
								                </xsl:otherwise>
											</xsl:choose>						 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalcontinue[.!='']"> -->
											<xsl:text>Medical Continuing: </xsl:text>
											<xsl:choose>           
												<xsl:when test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalcontinue =1">
													           Yes
												</xsl:when> 	
											  	<xsl:when test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalcontinue = 2">
													          No
												</xsl:when> 
												<xsl:when test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalcontinue = 3">
													          Unknown
												</xsl:when>
												<xsl:otherwise>		            
						          				</xsl:otherwise> 
											</xsl:choose> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalenddate[.!='']"> -->
											<xsl:text>Medical End Date: </xsl:text>
											<xsl:choose>
									            <xsl:when test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalenddateformat = 102 ">
												            <xsl:variable name="patientmedicalenddate" select="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalenddate"/>               
														 <xsl:value-of select="concat(substring($patientmedicalenddate, 7,
														 2),'-')"/>
														 <xsl:choose>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 01">JAN</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 02">FEB</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 03">MAR</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 04">APR</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 05">MAY</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 06">JUN</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 07">JUL</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 08">AUG</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 09">SEP</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 10">OCT</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 11">NOV</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 12">DEC</xsl:when>
														 </xsl:choose>
														 <xsl:value-of select="concat('-',substring($patientmedicalenddate, 1,
														 4))"/>	
												</xsl:when> 	
										  		<xsl:when test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalenddateformat = 610 ">
										  			 <xsl:variable name="patientmedicalenddate" select="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalenddate"/>      									          
														 <xsl:choose>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 01">JAN</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 02">FEB</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 03">MAR</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 04">APR</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 05">MAY</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 06">JUN</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 07">JUL</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 08">AUG</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 09">SEP</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 10">OCT</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 11">NOV</xsl:when>
															 <xsl:when test="substring($patientmedicalenddate, 5, 2) = 12">DEC</xsl:when>
														 </xsl:choose>
														 <xsl:value-of select="concat('-',substring($patientmedicalenddate, 1,
														 4))"/>	
												</xsl:when> 
												<xsl:when test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalenddateformat = 602 ">
												           <xsl:variable name="patientmedicalenddate" select="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalenddate"/>               
												 		   <xsl:value-of select="substring($patientmedicalenddate, 1, 4)"/>
												</xsl:when>
												<xsl:otherwise>		            
								                </xsl:otherwise>
											</xsl:choose> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>					
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell number-columns-spanned="3">
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalcomment[.!='']"> -->
											<xsl:text>Comments: </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/medicalhistoryepisode/patientmedicalcomment" /> 
										<!-- </xsl:if> --> 
										</fo:block>
									</fo:table-cell>				
								</fo:table-row>
							</fo:table-body>
							</fo:table>
						</fo:block>	
					</fo:table-cell>
				</fo:table-row>
				</xsl:for-each>
			</fo:table-body>
			</fo:table>
		</fo:block>		
	</xsl:if>
	
	<!-- Patient past drug information-->
	<xsl:if test="/ichicsr/safetyreport/patient/patientpastdrugtherapy[.!='']">		
	<fo:block font-size="10pt" font-family="Arial,Times">
		<!-- Message header details-->  
		<fo:table margin="3pt">
		<fo:table-body>
    		<fo:table-row>
    			<fo:table-cell  font-weight="bold" background-color="#DADADA" number-columns-spanned="2">
    				<fo:block><xsl:text>B.1.8 Relevant past drug history</xsl:text></fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		<xsl:for-each select="/ichicsr/safetyreport/patient/patientpastdrugtherapy">
    		<fo:table-row>
    			<fo:table-cell number-columns-spanned="2">
    				<fo:block>
    					<fo:table border-width="1pt" border-style="solid" border-color="#CCCCCC">
						<fo:table-body>
							<fo:table-row>								
								<fo:table-cell number-columns-spanned="2">
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugname[.!='']"> -->
											<xsl:text>Name of Drug as reported: </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugname" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>				
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugstartdate[.!='']"> -->
											<xsl:text>Start Date: </xsl:text>
											<xsl:choose>
									            <xsl:when test="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugstartdateformat = 102 ">
												            <xsl:variable name="patientdrugstartdate" select="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugstartdate"/>               
															 <xsl:value-of select="concat(substring($patientdrugstartdate, 7,
															 2),'-')"/>
															 <xsl:choose>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 01">JAN</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 02">FEB</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 03">MAR</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 04">APR</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 05">MAY</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 06">JUN</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 07">JUL</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 08">AUG</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 09">SEP</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 10">OCT</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 11">NOV</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 12">DEC</xsl:when>
															 </xsl:choose>
															 <xsl:value-of select="concat('-',substring($patientdrugstartdate, 1,
															 4))"/>	
												</xsl:when> 	
										  		<xsl:when test="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugstartdateformat = 610 ">	
										  		<xsl:variable name="patientdrugstartdate" select="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugstartdate"/>  												          
															 <xsl:choose>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 01">JAN</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 02">FEB</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 03">MAR</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 04">APR</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 05">MAY</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 06">JUN</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 07">JUL</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 08">AUG</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 09">SEP</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 10">OCT</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 11">NOV</xsl:when>
																 <xsl:when test="substring($patientdrugstartdate, 5, 2) = 12">DEC</xsl:when>
															 </xsl:choose>
															 <xsl:value-of select="concat('-',substring($patientdrugstartdate, 1,
															 4))"/>	
												</xsl:when> 
												<xsl:when test="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugstartdateformat = 602 ">
												           <xsl:variable name="patientdrugstartdate" select="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugstartdate"/>               
												 		   <xsl:value-of select="substring($patientdrugstartdate, 1, 4)"/>
												</xsl:when>
												<xsl:otherwise>		            
								                </xsl:otherwise>
											</xsl:choose> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugenddate[.!='']"> -->
											<xsl:text>End Date: </xsl:text>
											<xsl:choose>
									            <xsl:when test="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugenddateformat = 102 ">
												           <xsl:variable name="patientdrugenddate" select="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugenddate"/>               
															 <xsl:value-of select="concat(substring($patientdrugenddate, 7,
															 2),'-')"/>
															 <xsl:choose>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 01">JAN</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 02">FEB</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 03">MAR</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 04">APR</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 05">MAY</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 06">JUN</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 07">JUL</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 08">AUG</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 09">SEP</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 10">OCT</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 11">NOV</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 12">DEC</xsl:when>
															 </xsl:choose>
															 <xsl:value-of select="concat('-',substring($patientdrugenddate, 1,
															 4))"/>	
												</xsl:when> 	
										  		<xsl:when test="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugenddateformat = 610 ">
										  			  <xsl:variable name="patientdrugenddate" select="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugenddate"/>    													          
															 <xsl:choose>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 01">JAN</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 02">FEB</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 03">MAR</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 04">APR</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 05">MAY</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 06">JUN</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 07">JUL</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 08">AUG</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 09">SEP</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 10">OCT</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 11">NOV</xsl:when>
																 <xsl:when test="substring($patientdrugenddate, 5, 2) = 12">DEC</xsl:when>
															 </xsl:choose>
															 <xsl:value-of select="concat('-',substring($patientdrugenddate, 1,
															 4))"/>	
												</xsl:when> 
												<xsl:when test="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugenddateformat = 602 ">
												           <xsl:variable name="patientdrugenddate" select="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugenddate"/>               
												 		   <xsl:value-of select="substring($patientdrugenddate, 1, 4)"/>
												</xsl:when>
												<xsl:otherwise>		            
								                </xsl:otherwise>
											</xsl:choose> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>				
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientindicationmeddraversion[.!='']"> -->
											<xsl:text>MedDRA Version for indication: </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientindicationmeddraversion" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugindication[.!='']"> -->
											<xsl:text>Indication: </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugindication" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>				
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugreactionmeddraversion[.!='']"> -->
											<xsl:text>MedDRA version for reaction: </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugreactionmeddraversion" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugreaction[.!='']"> -->
											<xsl:text>Reaction: </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/patientpastdrugtherapy/patientdrugreaction" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>				
							</fo:table-row>							
						</fo:table-body>
						</fo:table>
    				</fo:block>
    			</fo:table-cell>    		
    		</fo:table-row>
    		</xsl:for-each>
    	</fo:table-body>
    	</fo:table>
    </fo:block>
    </xsl:if>
    
    <!-- Patient past drug information-->
	<xsl:if test="/ichicsr/safetyreport/patient/patientpastdrugtherapy[.!='']">		
	<fo:block font-size="10pt" font-family="Arial,Times">
		<!-- Message header details-->  
		<fo:table margin="3pt">
		<fo:table-body>
    		<fo:table-row>
    			<fo:table-cell  font-weight="bold" background-color="#DADADA" number-columns-spanned="2">
    				<fo:block><xsl:text>B.2 Reaction</xsl:text></fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		<xsl:for-each select="/ichicsr/safetyreport/patient/reaction">
    		<fo:table-row>
    			<fo:table-cell number-columns-spanned="2">
    				<fo:block>
    					<fo:table border-width="1pt" border-style="solid" border-color="#CCCCCC">
						<fo:table-body>
							<fo:table-row>								
								<fo:table-cell number-columns-spanned="2">
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/reaction/primarysourcereaction[.!='']"> -->
											<xsl:text>Reaction/Event as reported by primary source: </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/reaction/primarysourcereaction" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>				
							</fo:table-row>
							<fo:table-row>								
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/reaction/reactionmeddraversionllt[.!='']"> -->
											<xsl:text>MedDRA version for reaction/event term LLT: </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/reaction/reactionmeddraversionllt" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/reaction/reactionmeddrallt[.!='']"> -->
											<xsl:text>Reaction/Event in MedDRA terminology (LLT): </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/reaction/reactionmeddrallt" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>					
							</fo:table-row>
							<fo:table-row>								
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/reaction/reactionmeddraversionpt[.!='']"> -->
											<xsl:text>MedDRA version for reaction/event term PT: </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/reaction/reactionmeddraversionpt" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/reaction/reactionmeddrapt[.!='']"> -->
											<xsl:text>Reaction/event MedDRA term (PT): </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/reaction/reactionmeddrapt" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>					
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell number-columns-spanned="2">
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/reaction/termhighlighted[.!='']"> -->
											<xsl:text>Term highlighted by the reporter: </xsl:text>
											<xsl:choose>           
												<xsl:when test="/ichicsr/safetyreport/patient/reaction/termhighlighted = 1 ">
													           Yes, highlighted by the reporter, NOT serious
												</xsl:when>
												<xsl:when test="/ichicsr/safetyreport/patient/reaction/termhighlighted = 2 ">
													           No, not highlighted by the reporter, NOT serious
												</xsl:when>
												<xsl:when test="/ichicsr/safetyreport/patient/reaction/termhighlighted = 3 ">
													           Yes, highlighted by the reporter, SERIOUS
												</xsl:when>
												<xsl:when test="/ichicsr/safetyreport/patient/reaction/termhighlighted = 4 ">
													           No, not highlighted by the reporter, SERIOUS
												</xsl:when>
											</xsl:choose> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>					
							</fo:table-row>
							<fo:table-row>								
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/reaction/reactionstartdate[.!='']"> -->
											<xsl:text>Date of start of reaction/event: </xsl:text>
											<xsl:choose>
								            <xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionstartdateformat = 102 ">
											           <xsl:variable name="reactionstartdate" select="/ichicsr/safetyreport/patient/reaction/reactionstartdate"/>               
																 <xsl:value-of select="concat(substring($reactionstartdate, 7,
																 2),'-')"/>
																 <xsl:choose>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($reactionstartdate, 1,
																 4))"/>	
											</xsl:when> 
											<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionstartdateformat = 203 ">
											           <xsl:variable name="reactionstartdate" select="/ichicsr/safetyreport/patient/reaction/reactionstartdate"/>               
																 <xsl:value-of select="concat(substring($reactionstartdate, 7,
																 2),'-')"/>
																 <xsl:choose>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($reactionstartdate, 1,
																 4))"/>	
														<xsl:text> </xsl:text>
														<xsl:value-of select="concat(substring($reactionstartdate, 9,
														2),':',substring($reactionstartdate, 11, 2))"/>
											</xsl:when>	
									  		<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionstartdateformat = 610 ">
											          <xsl:variable name="reactionstartdate" select="/ichicsr/safetyreport/patient/reaction/reactionstartdate"/>
																 <xsl:choose>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($reactionstartdate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($reactionstartdate, 1,
																 4))"/>	
											</xsl:when> 
											<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionstartdateformat = 602 ">
											           <xsl:variable name="reactionstartdate" select="/ichicsr/safetyreport/patient/reaction/reactionstartdate"/>               
											 		   <xsl:value-of select="substring($reactionstartdate, 1, 4)"/>
											</xsl:when>
											<xsl:otherwise>		            
							                </xsl:otherwise>
										</xsl:choose> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/reaction/reactionenddate[.!='']"> -->
											<xsl:text>Date of end of reaction/event: </xsl:text>
											<xsl:choose>
								            <xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionenddateformat = 102 ">
											          <xsl:variable name="reactionenddate" select="/ichicsr/safetyreport/patient/reaction/reactionenddate"/>               
																 <xsl:value-of select="concat(substring($reactionenddate, 7,
																 2),'-')"/>
																 <xsl:choose>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($reactionenddate, 1,
																 4))"/>	
											</xsl:when> 
											<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionenddateformat = 203 ">
											            <xsl:variable name="reactionenddate" select="/ichicsr/safetyreport/patient/reaction/reactionenddate"/>               
																 <xsl:value-of select="concat(substring($reactionenddate, 7,
																 2),'-')"/>
																 <xsl:choose>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($reactionenddate, 1,
																 4))"/>	
														<xsl:text> </xsl:text>
														<xsl:value-of select="concat(substring($reactionenddate, 9,
														2),':',substring($reactionenddate, 11, 2))"/>
											</xsl:when>	
									  		<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionenddateformat = 610 ">
											            <xsl:variable name="reactionenddate" select="/ichicsr/safetyreport/patient/reaction/reactionenddate"/>
																 <xsl:choose>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($reactionenddate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($reactionenddate, 1,
																 4))"/>	
											</xsl:when> 
											<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionenddateformat = 602 ">
											           <xsl:variable name="reactionenddate" select="/ichicsr/safetyreport/patient/reaction/reactionenddate"/>               
											 		   <xsl:value-of select="substring($reactionenddate, 1, 4)"/>
											</xsl:when>
											<xsl:otherwise>		            
							                </xsl:otherwise>
										</xsl:choose> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>					
							</fo:table-row>
							<fo:table-row>								
								<fo:table-cell number-columns-spanned="2">
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/reaction/reactionduration[.!='']"> -->
											<xsl:text>Duration of reaction/event: </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/reaction/reactionduration" />
											<xsl:text> </xsl:text>						
											<xsl:choose>           
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactiondurationunit = 801 ">
														           Year(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactiondurationunit = 802 ">
														           Month(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactiondurationunit = 803 ">
														           Week(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactiondurationunit = 804 ">
														           Day(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactiondurationunit = 805 ">
														           Hour(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactiondurationunit = 806 ">
														           Minute(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactiondurationunit = 807 ">
														           Second(s)
													</xsl:when>
											</xsl:choose> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>				
							</fo:table-row>
							<fo:table-row>								
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/reaction/reactionfirsttime[.!='']"> -->
											<xsl:text>Time interval between beginning of suspect drug administration and start of reaction/event: </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/reaction/reactionfirsttime" />	
											<xsl:text> </xsl:text>					
											<xsl:choose>           
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionfirsttimeunit = 801 ">
														           Year(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionfirsttimeunit = 802 ">
														           Month(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionfirsttimeunit = 803 ">
														           Week(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionfirsttimeunit = 804 ">
														           Day(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionfirsttimeunit = 805 ">
														           Hour(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionfirsttimeunit = 806 ">
														           Minute(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionfirsttimeunit = 807 ">
														           Second(s)
													</xsl:when>
											</xsl:choose>
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/reaction/reactionlasttime[.!='']"> -->
											<xsl:text>Time interval between last dose and start of reaction/event: </xsl:text>
											<xsl:value-of select="/ichicsr/safetyreport/patient/reaction/reactionlasttime" />
											<xsl:text> </xsl:text>							
											<xsl:choose>           
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionlasttimeunit = 801 ">
														           Year(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionlasttimeunit = 802 ">
														           Month(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionlasttimeunit = 803 ">
														           Week(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionlasttimeunit = 804 ">
														           Day(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionlasttimeunit = 805 ">
														           Hour(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionlasttimeunit = 806 ">
														           Minute(s)
													</xsl:when>
													<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionlasttimeunit = 807 ">
														           Second(s)
													</xsl:when>
											</xsl:choose>
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>				
							</fo:table-row>
							<fo:table-row>								
								<fo:table-cell number-columns-spanned="2">
									<fo:block>
										<!-- <xsl:if test="/ichicsr/safetyreport/patient/reaction/reactionoutcome[.!='']"> -->
											<xsl:text>Outcome of reaction/event at the time of last observation: </xsl:text>
											<xsl:choose>           
												<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionoutcome = 1 ">
													           Recovered/Resolved
												</xsl:when>
												<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionoutcome = 2 ">
													           Recovering/Resolving
												</xsl:when>
												<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionoutcome = 3 ">
													           Not recovered/Not resolved
												</xsl:when>
												<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionoutcome = 4 ">
													           Recovered/Resolved with Sequelae
												</xsl:when>
												<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionoutcome = 5 ">
													           Fatal
												</xsl:when>
												<xsl:when test="/ichicsr/safetyreport/patient/reaction/reactionoutcome = 6 ">
													           Unknown
												</xsl:when>								
											</xsl:choose>
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>				
							</fo:table-row>
						</fo:table-body>
						</fo:table>
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
			</xsl:for-each>
		</fo:table-body>
		</fo:table>
	</fo:block>
	</xsl:if>									
	
	<!-- Results of tests and procedures relevant to the investigation of the patient:-->
	<xsl:if test="/ichicsr/safetyreport/patient/test[.!='']">		
	<fo:block font-size="10pt" font-family="Arial,Times">
		<!-- Message header details-->  
		<fo:table margin="3pt">
		<fo:table-body>
    		<fo:table-row>
    			<fo:table-cell  font-weight="bold" background-color="#DADADA" number-columns-spanned="3">
    				<fo:block><xsl:text>B.3 Results of tests and procedures relevant to the investigation of the patient</xsl:text></fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		<xsl:for-each select="/ichicsr/safetyreport/patient/test">
    		<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/test/testdate[.!='']"> -->
							<xsl:text>Date: </xsl:text>
							<xsl:choose>
					            <xsl:when test="/ichicsr/safetyreport/patient/test/testdateformat = 102 ">
								            <xsl:variable name="testdate" select="/ichicsr/safetyreport/patient/test/testdate"/>               
													 <xsl:value-of select="concat(substring($testdate, 7,
													 2),'-')"/>
													 <xsl:choose>
														 <xsl:when test="substring($testdate, 5, 2) = 01">JAN</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 02">FEB</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 03">MAR</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 04">APR</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 05">MAY</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 06">JUN</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 07">JUL</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 08">AUG</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 09">SEP</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 10">OCT</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 11">NOV</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 12">DEC</xsl:when>
													 </xsl:choose>
													 <xsl:value-of select="concat('-',substring($testdate, 1,
													 4))"/>	
								</xsl:when> 	
						  		<xsl:when test="/ichicsr/safetyreport/patient/test/testdateformat = 610 ">
						  			  <xsl:variable name="testdate" select="/ichicsr/safetyreport/patient/test/testdate"/>        											           
													 <xsl:choose>
														 <xsl:when test="substring($testdate, 5, 2) = 01">JAN</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 02">FEB</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 03">MAR</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 04">APR</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 05">MAY</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 06">JUN</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 07">JUL</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 08">AUG</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 09">SEP</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 10">OCT</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 11">NOV</xsl:when>
														 <xsl:when test="substring($testdate, 5, 2) = 12">DEC</xsl:when>
													 </xsl:choose>
													 <xsl:value-of select="concat('-',substring($testdate, 1,
													 4))"/>	
								</xsl:when> 
								<xsl:when test="/ichicsr/safetyreport/patient/test/testdateformat = 602 ">
								           <xsl:variable name="testdate" select="/ichicsr/safetyreport/patient/test/testdate"/>               
								 		   <xsl:value-of select="substring($testdate, 1, 4)"/>
								</xsl:when>
								<xsl:otherwise>		            
				                </xsl:otherwise>
							</xsl:choose> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/test/testname[.!='']"> -->
							<xsl:text>Test: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/test/testname" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/test/testresult[.!='']"> -->
							<xsl:text>Result: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/test/testresult" />
							<xsl:text> </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/test/testunit" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/test/lowtestrange[.!='']"> -->
							<xsl:text>Normal low range: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/test/lowtestrange" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/test/hightestrange[.!='']"> -->
							<xsl:text>Normal high range: </xsl:text>
							<xsl:value-of select="/ichicsr/safetyreport/patient/test/hightestrange" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="/ichicsr/safetyreport/patient/test/moreinformation[.!='']"> -->
							<xsl:text>More information available (Y/N): </xsl:text>
							<xsl:choose>           
									<xsl:when test="/ichicsr/safetyreport/patient/test/moreinformation = 1 ">
										           Yes
									</xsl:when>
									<xsl:when test="/ichicsr/safetyreport/patient/test/moreinformation = 2 ">
										           No
									</xsl:when>					
							</xsl:choose> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			</xsl:for-each>
    	</fo:table-body>
    	</fo:table>
    </fo:block>
    </xsl:if>
    <!-- Drug information -->
    <xsl:if test="/ichicsr/safetyreport/patient/drug[.!='']">		
	<fo:block font-size="10pt" font-family="Arial,Times">
		<!-- Message header details-->  
		<fo:table margin="3pt">
		<fo:table-body>
    		<fo:table-row>
    			<fo:table-cell  font-weight="bold" background-color="#DADADA" number-columns-spanned="2">
    				<fo:block><xsl:text>B.4 Drug(s) Information</xsl:text></fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		<xsl:for-each select="/ichicsr/safetyreport/patient/drug">
    		<fo:table-row>
    			<fo:table-cell number-columns-spanned="2">
    			<fo:table border-width="1pt" border-style="solid" border-color="#CCCCCC">
    				<fo:table-body>
    					<fo:table-row>
    						<fo:table-cell number-columns-spanned="2">
			    				<fo:block>
			    					<xsl:text>Characterization of drug role: </xsl:text>
			    					<xsl:choose>           
											<xsl:when test="/ichicsr/safetyreport/patient/drug/drugcharacterization = 1 ">
												           Suspect
											</xsl:when>
											<xsl:when test="/ichicsr/safetyreport/patient/drug/drugcharacterization = 2 ">
												           Concomitant
											</xsl:when>	
											<xsl:when test="/ichicsr/safetyreport/patient/drug/drugcharacterization = 3 ">
												           Interacting
											</xsl:when>	
											<xsl:otherwise>		            
					          				</xsl:otherwise>			
									</xsl:choose>
			    				
			    				</fo:block>
    						</fo:table-cell>    			
    					</fo:table-row>
			    		<fo:table-row>
			    			<fo:table-cell number-columns-spanned="2">
			    				<fo:block>
			    					<xsl:text>Proprietary medicinal product name: </xsl:text>
			    					<xsl:value-of select="/ichicsr/safetyreport/patient/drug/medicinalproduct" />    				
			    				</fo:block>
			    			</fo:table-cell>    			
			    		</fo:table-row>
			    		<xsl:if test="/ichicsr/safetyreport/patient/drug/activesubstance/activesubstancename[.!='']">
			    		<fo:table-row>
			    			<fo:table-cell number-columns-spanned="2">
			    				<fo:table margin="3pt">
				    				<fo:table-body>			    		
							    		<fo:table-row>
							    			<fo:table-cell  font-weight="bold" background-color="#DADADA" number-columns-spanned="2">
							    				<fo:block><xsl:text>B.4.k.2.2 Active Drug Substance Name</xsl:text></fo:block>
							    			</fo:table-cell>    			
							    		</fo:table-row>
							    		<xsl:for-each select="/ichicsr/safetyreport/patient/drug/activesubstance">
							    		<fo:table-row>
							    			<fo:table-cell  number-columns-spanned="2">
							    				<fo:table border-width="1pt" border-style="solid" border-color="#CCCCCC">
							    				<fo:table-body>
							    					<fo:table-row>
							    						<fo:table-cell number-columns-spanned="2">
							    							<fo:block>
							    								<xsl:value-of select="/ichicsr/safetyreport/patient/drug/activesubstance/activesubstancename" />
							    							</fo:block>
							    						</fo:table-cell>    			
							    					</fo:table-row>
							    				</fo:table-body>
							    				</fo:table>
							    			</fo:table-cell>
							    		</fo:table-row>				
				    					</xsl:for-each>
				    				</fo:table-body>
				    			</fo:table>
				    		</fo:table-cell>
				    	</fo:table-row>				
			    		</xsl:if>
			    		<!-- <xsl:if test="/ichicsr/safetyreport/patient/drug/obtaindrugcountry[.!='']"> -->
			    		<fo:table-row>
			    			<fo:table-cell number-columns-spanned="2">
			    				<fo:block>
			    					<xsl:text>Identification of the country where the drug was obtained: </xsl:text>
			    					<xsl:value-of select="/ichicsr/safetyreport/patient/drug/obtaindrugcountry" />    				
			    				</fo:block>
			    			</fo:table-cell>    			
			    		</fo:table-row>
			    		<!-- </xsl:if> -->
			    		<!-- <xsl:if test="/ichicsr/safetyreport/patient/drug/drugbatchnumb[.!='']"> -->
			    		<fo:table-row>
			    			<fo:table-cell number-columns-spanned="2">
			    				<fo:block>
			    					<xsl:text>Batch/lot number: </xsl:text>
			    					<xsl:value-of select="/ichicsr/safetyreport/patient/drug/drugbatchnumb" />    				
			    				</fo:block>
			    			</fo:table-cell>    			
			    		</fo:table-row>
			    		<!-- </xsl:if> -->
			    		<!-- <xsl:if test="/ichicsr/safetyreport/patient/drug/drugauthorizationnumb[.!=''] or drugauthorizationcountry[.!=''] or drugauthorizationholder[.!='']"> -->
			    		<fo:table-row>
			    			<fo:table-cell number-columns-spanned="2">
			    				<fo:block>
			    					<xsl:text>Holder and authorization/application number of drug: </xsl:text>			
			    				</fo:block>
			    			</fo:table-cell>    			
			    		</fo:table-row>
			    		<!-- </xsl:if> -->
			    		<!-- <xsl:if test="/ichicsr/safetyreport/patient/drug/drugbatchnumb[.!='']"> -->
			    		<fo:table-row>
			    			<fo:table-cell number-columns-spanned="2">
			    				<fo:block>
			    					<xsl:text>Batch/lot number: </xsl:text>
			    					<xsl:value-of select="/ichicsr/safetyreport/patient/drug/drugbatchnumb" />    				
			    				</fo:block>
			    			</fo:table-cell>    			
			    		</fo:table-row>
			    		<!-- </xsl:if> -->
			    	</fo:table-body>
			    </fo:table>
			    </fo:table-cell>
			</fo:table-row>
    		</xsl:for-each>
    		
    	</fo:table-body>
    	</fo:table>
    </fo:block>
    </xsl:if>		
    		
	  
  </fo:flow>
</fo:page-sequence>  


</fo:root>
</xsl:template>
</xsl:stylesheet>