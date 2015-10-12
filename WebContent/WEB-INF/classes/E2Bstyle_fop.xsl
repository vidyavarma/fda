<?xml version="1.0" encoding="UTF-8"?>
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
   <xsl:apply-templates/> 
   </fo:flow>
</fo:page-sequence>
</fo:root>
</xsl:template>

<xsl:template match="ichicsrmessageheader"> 
	<xsl:apply-templates/>
    <fo:block font-size="10pt" font-family="Arial,Times" >
		<fo:table margin="3pt">
		<fo:table-body>
    		<fo:table-row>
    			<fo:table-cell  font-weight="bold" background-color="#DADADA">
    				<fo:block><xsl:text>M.1 Message Header Details</xsl:text></fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		<xsl:if test="messagetype[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Message Type: </xsl:text>
    				<xsl:value-of select="messagetype" />
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		 <xsl:if test="messageformatversion[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Message Format Version: </xsl:text>
    				<xsl:value-of select="messageformatversion" />
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="messageformatrelease[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Message Format Release: </xsl:text>
    				<xsl:value-of select="messageformatrelease"/>
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="messagenumb[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Message Number: </xsl:text>
    				<xsl:value-of select="messagenumb" />
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="messagesenderidentifier[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Message Sender Identifier: </xsl:text>
    				<xsl:value-of select="messagesenderidentifier"/>
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="messagereceiveridentifier[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Message Receiver Identifier: </xsl:text>
    				<xsl:value-of select="messagereceiveridentifier" />
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="messagedate[.!='']">  
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Message Date: </xsl:text>
    				<xsl:variable name="messagedate" select="messagedate"/>               
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
</xsl:template>

<xsl:template match="safetyreport"> 
	<xsl:apply-templates/>
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
    		<xsl:if test="safetyreportversion[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Safety Report Version: </xsl:text>
    				<xsl:value-of select="safetyreportversion" />
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="safetyreportid[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Safety Report Identifer: </xsl:text>
    				<xsl:value-of select="safetyreportid" />
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block>
						<xsl:if test="primarysourcecountry[.!='']">
			    			<xsl:text>Country of Primary Source: </xsl:text>
			    			<xsl:value-of select="primarysourcecountry" />						    			
			    		</xsl:if>			    		
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
			<fo:table-row>	
				<fo:table-cell>
					<fo:block>
						<xsl:if test="occurcountry[.!='']">
			    			<xsl:text>Country where Event occured: </xsl:text>
			    			<xsl:value-of select="occurcountry" />						    			
			    		</xsl:if>
					</fo:block>
				</fo:table-cell>
    		</fo:table-row>
    		<xsl:if test="transmissiondate[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Transmission Date: </xsl:text>
    				<xsl:variable name="transmissiondate" select="transmissiondate"/>               
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
    		<xsl:if test="reporttype[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Report Type: </xsl:text>
    				<xsl:choose>            			
	          			<xsl:when test="reporttype = 1">	          				
	            			Spontaneous
				        </xsl:when> 
				        <xsl:when test="reporttype = 2">
				            Report from study
				        </xsl:when> 
				        <xsl:when test="reporttype = 3">
				          	Other
				        </xsl:when>
				        <xsl:when test="reporttype = 4">
				          	Not available to sender (unknown)
				        </xsl:when>		          		          		                  
				        <xsl:otherwise>		            
				        </xsl:otherwise>
				    </xsl:choose>  
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="serious[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Serious: </xsl:text>
    				<xsl:choose>
		            	<xsl:when test="serious = 1">
			            	Yes
			          	</xsl:when> 
			           	<xsl:when test="serious = 2">
			            	No
			          	</xsl:when> 		          		          		          		                  
			          	<xsl:otherwise>		            
			          	</xsl:otherwise>
		        	</xsl:choose>
    				</fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="seriousnessdeath[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Seriousness - Results in death: </xsl:text>
    				 <xsl:choose>
				          <xsl:when test="seriousnessdeath = 1">
				            	Yes
				          </xsl:when> 
				           <xsl:when test="seriousnessdeath = 2">
				           	 	No
				          </xsl:when> 		          		          		          		                  
				          <xsl:otherwise>		            
				          </xsl:otherwise>
			        </xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="seriousnesshospitalization[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Seriousness - Caused/Prolonged Hospitalization: </xsl:text>
    				<xsl:choose>
				          <xsl:when test="seriousnesshospitalization = 1">
				            	Yes
				          </xsl:when> 
				           <xsl:when test="seriousnesshospitalization = 2">
				            	No
				          </xsl:when> 		          		          		          		                  
				          <xsl:otherwise>		            
				          </xsl:otherwise>
			        </xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>    		
    		<xsl:if test="seriousnessdisabling[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Seriousness - Disabling/Incapacitating: </xsl:text>
    				<xsl:choose>
				          <xsl:when test="seriousnessdisabling = 1">
				            	Yes
				          </xsl:when> 
				           <xsl:when test="seriousnessdisabling = 2">
				            	No
				          </xsl:when> 		          		          		          		                  
				          <xsl:otherwise>		            
				          </xsl:otherwise>
			        </xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="seriousnesscongenitalanomali[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Seriousness - Congenital anomaly/birth defect: </xsl:text>
    				<xsl:choose>
		            	<xsl:when test="seriousnesscongenitalanomali = 1">
			            	Yes
			         	</xsl:when> 
			           	<xsl:when test="seriousnesscongenitalanomali = 2">
			            	No
			          	</xsl:when> 		          		          		          		                  
			          	<xsl:otherwise>		            
			          	</xsl:otherwise>
			        </xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="seriousnessother[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Seriousness - Others: </xsl:text>
    				 <xsl:choose>
			          	<xsl:when test="seriousnessother = 1">
			            	Yes
			          	</xsl:when> 
			           	<xsl:when test="seriousnessother = 2">
			            	No
			          	</xsl:when> 		          		          		          		                  
			          	<xsl:otherwise>		            
			          	</xsl:otherwise>
		        	</xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="receivedate[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Date Report was first received from source: </xsl:text>
    				<xsl:variable name="receivedate" select="receivedate"/>               
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
    			<xsl:if test="receiptdate[.!='']">
    			<fo:table-cell>
    				<fo:block>
    				<xsl:text>Date of receipt of the most recent information for this report: </xsl:text>
    				<xsl:variable name="receiptdate" select="receiptdate"/>               
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
    		<xsl:if test="additionaldocument[.!='']">
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
		            	<xsl:when test="additionaldocument = 1">
			            	Yes
			          	</xsl:when>
			          	<xsl:when test="additionaldocument = 2">
			            	No
			          	</xsl:when> 		           		          		          		          		                  
			          	<xsl:otherwise>	          		            
			          	</xsl:otherwise>
		        	</xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>	
    		<xsl:if test="documentlist[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>List of documents held by sender: </xsl:text>
    				<xsl:value-of select="documentlist" />
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>	
    		<xsl:if test="fulfillexpeditecriteria[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Does this case fulfill the local criteria for an expedited report?: </xsl:text>
    				<xsl:choose>
				        <xsl:when test="fulfillexpeditecriteria = 1">
				            Yes
				        </xsl:when> 
				        <xsl:when test="fulfillexpeditecriteria = 2">
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
       <!-- Worldwide unique case details--> 
    	<fo:table margin="3pt">
		<fo:table-body>
    		<xsl:if test="authoritynumb[.!=''] or companynumb[.!='']">
    		<fo:table-row>
    			<fo:table-cell font-weight="bold">
    				<fo:block><xsl:text>Worldwide unique case identification number</xsl:text></fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>    		
    		</xsl:if>
    		<xsl:if test="authoritynumb[.!='']">
    		<fo:table-row space-before="10pt">
    			<fo:table-cell>
    				<fo:block><xsl:text>Regulatory authority's case report number: </xsl:text>
    				<xsl:value-of select="authoritynumb" />
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="companynumb[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Other sender's case report number: </xsl:text>
    				<xsl:value-of select="companynumb" />
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="duplicate[.!='']">
    		<fo:table-row>
    			<fo:table-cell>
    				<fo:block><xsl:text>Other case identifiers in previous transmissions: </xsl:text>
    				<xsl:choose>			        	
			        	<xsl:when test="duplicate = 1">
			        		Yes
			        	</xsl:when>
			        </xsl:choose>
    				</fo:block>
    			</fo:table-cell>    			
    		</fo:table-row>
    		</xsl:if>
    		<xsl:if test="reportduplicate[.!='']">
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
									<xsl:if test="duplicatesource[.!='']">
										<fo:table-row>
							    			<fo:table-cell>
							    				<fo:block><xsl:text>Source(s) of the case identifier: </xsl:text>
							    				<xsl:value-of select="duplicatesource" />
							    				</fo:block>
							    			</fo:table-cell>    			
							    		</fo:table-row>
									</xsl:if>
									<xsl:if test="duplicatenumb[.!='']">
										<fo:table-row>
							    			<fo:table-cell>
							    				<fo:block><xsl:text>Case identifiers: </xsl:text>
							    				<xsl:value-of select="duplicatenumb" />
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
    		<xsl:if test="linkedreport/linkreportnumb[.!='']">
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
							    				<xsl:value-of select="linkreportnumb" />
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
    		<xsl:if test="casenullification[.!='']">
    		<fo:table-row>
				<fo:table-cell>
					<fo:block><xsl:text>Report nullification: </xsl:text>
    				<xsl:choose>
		          		<xsl:when test="casenullification = 1">
		           		 	Yes
		          		</xsl:when>        		          		          		                  
		          		<xsl:otherwise>		            
		          		</xsl:otherwise>
		        	</xsl:choose>
    				</fo:block>
				</fo:table-cell>
			</fo:table-row>
    		</xsl:if>
    		<xsl:if test="nullificationreason[.!='']">
    		<fo:table-row>
				<fo:table-cell>
					<fo:block><xsl:text>Reason for nullification: </xsl:text>
    				<xsl:value-of select="nullificationreason" />
    				</fo:block>
				</fo:table-cell>
			</fo:table-row>
    		</xsl:if>
    		<xsl:if test="medicallyconfirm[.!='']">
    		<fo:table-row>
				<fo:table-cell>
					<fo:block><xsl:text>Was the case medically confirmed, if not initially from health professional?: </xsl:text>
    				<xsl:choose>
			          <xsl:when test="medicallyconfirm = 1">
			            	Yes
			          </xsl:when>  
			           <xsl:when test="medicallyconfirm = 2">
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
    <xsl:apply-templates select="primarysource"/>
    <xsl:apply-templates select="sender"/>
    <xsl:apply-templates select="receiver"/>
    <xsl:apply-templates select="patient"/>	   
</xsl:template>

<xsl:template match="primarysource"> 
	<xsl:apply-templates/>
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
					<xsl:value-of select="reportertitle" />    				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			</xsl:if>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="reportergivename[.!='']"> -->
							<xsl:text>Reporter given name: </xsl:text>
							<xsl:value-of select="reportergivename" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="reportermiddlename[.!='']"> -->
							<xsl:text>Reporter middle name: </xsl:text>
							<xsl:value-of select="reportermiddlename" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="reporterfamilyname[.!='']"> -->
							<xsl:text>Reporter family name: </xsl:text>
							<xsl:value-of select="reporterfamilyname" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="reporterorganization[.!='']"> -->
							<xsl:text>Reporter organization: </xsl:text>
							<xsl:value-of select="reporterorganization" /> 
						<!-- </xsl:if> -->    				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="reporterdepartment[.!='']"> -->
							<xsl:text>Reporter department: </xsl:text>
							<xsl:value-of select="reporterdepartment" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="reporterstreet[.!='']"> -->
							<xsl:text>Reporter street: </xsl:text>
							<xsl:value-of select="reporterstreet" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="reportercity[.!='']"> -->
							<xsl:text>Reporter city: </xsl:text>
							<xsl:value-of select="reportercity" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="reporterstate[.!='']"> -->
							<xsl:text>Reporter state or province: </xsl:text>
							<xsl:value-of select="reporterstate" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="reporterpostcode[.!='']"> -->
							<xsl:text>Reporter postcode: </xsl:text>
							<xsl:value-of select="reporterpostcode" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="reportercountry[.!='']"> -->
							<xsl:text>Reporter country code: </xsl:text>
							<xsl:value-of select="reportercountry" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>				
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="qualification[.!='']"> -->
							<xsl:text>Qualification: </xsl:text>
							<xsl:choose>
					        	<xsl:when test="qualification = 1">
					        		Physician
					          	</xsl:when> 
					           	<xsl:when test="qualification = 2">
					           		Pharmacist
					          	</xsl:when> 
					            <xsl:when test="qualification = 3">
					           		 Other Health Professional
					          	</xsl:when> 
					            <xsl:when test="qualification = 4">
					           		 Lawyer
					          	</xsl:when> 
					            <xsl:when test="qualification = 5">
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
						<!-- <xsl:if test="literaturereference[.!='']"> -->
							<xsl:text>Literature reference(s): </xsl:text>
							<xsl:value-of select="literaturereference" />
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
						<!-- <xsl:if test="studyname[.!=''] or sponsorstudynumb[.!=''] or observestudytype[.!='']"> -->
							<xsl:text>Study identification: </xsl:text>							
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>				
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="studyname[.!='']"> -->
							<xsl:text>Study name: </xsl:text>
							<xsl:value-of select="studyname" />
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>				
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="sponsorstudynumb[.!='']"> -->
							<xsl:text>Sponsor study number: </xsl:text>
							<xsl:value-of select="sponsorstudynumb" />
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>				
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="observestudytype[.!='']"> -->
							<xsl:text>Study type in which the reaction(s)/event(s) were observed: </xsl:text>
							<xsl:choose>
					          <xsl:when test="observestudytype = 1">
					            	Clinical trials
					          </xsl:when> 
					           <xsl:when test="observestudytype = 2">
					            	Individual patient use
					          </xsl:when> 
					           <xsl:when test="observestudytype = 3">
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
</xsl:template>		

<!-- Sender information-->
<xsl:template match="sender"> 
	<xsl:apply-templates/>	
	<fo:block font-size="10pt" font-family="Arial,Times">
		<fo:table margin="3pt">
		<fo:table-body>
			<fo:table-row font-weight="bold" background-color="#DADADA">
				<fo:table-cell number-columns-spanned="3">
					<fo:block><xsl:text>A.3.1 Information on Sender of Case Safety Report</xsl:text>    				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<!-- <xsl:if test="sendertype[.!='']"> -->
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block><xsl:text>Type: </xsl:text>
					<xsl:choose>
				          <xsl:when test="sendertype = 1">
				            	Pharmaceutical Company
				          </xsl:when> 
				           <xsl:when test="sendertype = 2">
				            	Regulatory Authority
				          </xsl:when> 
				            <xsl:when test="sendertype = 3">
				           		Health professional
				          </xsl:when>
				            <xsl:when test="sendertype = 4">
				           		Regional Pharmacovigilance Center
				          </xsl:when>
				            <xsl:when test="sendertype = 5">
				           		WHO Collaborating Center for International Drug Monitoring
				          </xsl:when>
				            <xsl:when test="sendertype = 6">
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
						<!-- <xsl:if test="senderorganization[.!='']"> -->
							<xsl:text>Organization: </xsl:text>
							<xsl:value-of select="senderorganization" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="senderdepartment[.!='']"> -->
							<xsl:text>Department: </xsl:text>
							<xsl:value-of select="senderdepartment" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<!-- <xsl:if test="sendertitle[.!='']"> -->
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block><xsl:text>Title: </xsl:text>
					<xsl:value-of select="sendertitle" />			
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<!-- </xsl:if> -->
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="sendergivename[.!='']"> -->
							<xsl:text>Given name: </xsl:text>
							<xsl:value-of select="sendergivename" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="sendermiddlename[.!='']"> -->
							<xsl:text>Middle name: </xsl:text>
							<xsl:value-of select="sendermiddlename" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="senderfamilyname[.!='']"> -->
							<xsl:text>Family name: </xsl:text>
							<xsl:value-of select="senderfamilyname" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
			<!-- <xsl:if test="senderstreetaddress[.!='']"> -->
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block><xsl:text>Street address: </xsl:text>
					<xsl:value-of select="senderstreetaddress" />			
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<!-- </xsl:if> -->
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="sendercity[.!='']"> -->
							<xsl:text>City: </xsl:text>
							<xsl:value-of select="sendercity" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="senderstate[.!='']"> -->
							<xsl:text>State or Province: </xsl:text>
							<xsl:value-of select="senderstate" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="senderpostcode[.!='']"> -->
							<xsl:text>Postcode: </xsl:text>
							<xsl:value-of select="senderpostcode" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="sendercountrycode[.!='']"> -->
							<xsl:text>Country Code: </xsl:text>
							<xsl:value-of select="sendercountrycode" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="sendertel[.!='']"> -->
							<xsl:text>Telephone: </xsl:text>
							<xsl:value-of select="sendertel" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="sendertelextension[.!='']"> -->
							<xsl:text>Telephone extension: </xsl:text>
							<xsl:value-of select="sendertelextension" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="sendertelcountrycode[.!='']"> -->
							<xsl:text>Telephone country code: </xsl:text>
							<xsl:value-of select="sendertelcountrycode" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="senderfax[.!='']"> -->
							<xsl:text>Fax: </xsl:text>
							<xsl:value-of select="senderfax" /> 
						<!-- </xsl:if> -->   				
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="senderfaxextension[.!='']"> -->
							<xsl:text>Fax extension: </xsl:text>
							<xsl:value-of select="senderfaxextension" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="senderfaxcountrycode[.!='']"> -->
							<xsl:text>Fax country code: </xsl:text>
							<xsl:value-of select="senderfaxcountrycode" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="senderemailaddress[.!='']"> -->
							<xsl:text>E-mail address: </xsl:text>
							<xsl:value-of select="senderemailaddress" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
		</fo:table-body>
		</fo:table>
	</fo:block>
</xsl:template>

<xsl:template match="receiver"> 
	<xsl:apply-templates/>
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
</xsl:template>		

<xsl:template match="patient"> 
	<xsl:apply-templates/>
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
						<!-- <xsl:if test="patientinitial[.!='']"> -->
							<xsl:text>Initials: </xsl:text>
							<xsl:value-of select="patientinitial" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="patientmedicalrecordnumb[.!='']"> -->
							<xsl:text>GP Medical Record Number: </xsl:text>
							<xsl:value-of select="patientmedicalrecordnumb" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="patientspecialistrecordnumb[.!='']"> -->
							<xsl:text>Specialist Record Number: </xsl:text>
							<xsl:value-of select="patientspecialistrecordnumb" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="patienthospitalrecordnumb[.!='']"> -->
							<xsl:text>Hospital Record Number: </xsl:text>
							<xsl:value-of select="patienthospitalrecordnumb" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell number-columns-spanned="2">
					<fo:block>
						<!-- <xsl:if test="patientinvestigationnumb[.!='']"> -->
							<xsl:text>Investigation Number: </xsl:text>
							<xsl:value-of select="patientinvestigationnumb" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="patientbirthdate[.!='']"> -->
							<xsl:text>Birth Date: </xsl:text>
							<xsl:variable name="patientbirthdate" select="patientbirthdate"/>               
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
						<!-- <xsl:if test="patientonsetage[.!='']"> -->
							<xsl:text>Age at time of reaction: </xsl:text>
							<xsl:value-of select="patientonsetage" />
			                <xsl:text> </xsl:text>	               
			                <xsl:choose>
						          <xsl:when test="patientonsetageunit = 800">
						           		 Decade(s)
						          </xsl:when> 
						           <xsl:when test="patientonsetageunit = 801">
						           		 Year(s)
						          </xsl:when> 	
							   		<xsl:when test="patientonsetageunit = 802">
									     Month(s)
								  </xsl:when> 	
		  		 				  <xsl:when test="patientonsetageunit = 803">
				            			 Week(s)
				          		   </xsl:when> 		          		          		          		                    	 
						          <xsl:when test="patientonsetageunit = 804">
						           		Day(s)
						          </xsl:when> 	
		   					      <xsl:when test="patientonsetageunit = 805">
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
						<!-- <xsl:if test="gestationperiod[.!='']"> -->
							<xsl:text>Gestation Period: </xsl:text>
							<xsl:value-of select="gestationperiod" />
			                <xsl:text> </xsl:text>
			                <xsl:choose>             
			   					<xsl:when test="gestationperiodunit = 802">
					            		Month(s)
					            </xsl:when> 	
	  							<xsl:when test="gestationperiodunit= 803">
			            				Week(s)
			          			</xsl:when> 		          		          		          		                    	 
			          			<xsl:when test="gestationperiodunit = 804">
			           					Day(s)
			          			</xsl:when> 	
	   							<xsl:when test="gestationperiodunit = 810">
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
						<!-- <xsl:if test="patientagegroup[.!='']"> -->
							<xsl:text>Age Group: </xsl:text>
							<xsl:choose>           
								<xsl:when test="patientagegroup =1">
					           			Neonate
					          	</xsl:when> 	
			  		 			<xsl:when test="patientagegroup = 2">
					            		Infant
					          	</xsl:when> 
								<xsl:when test="patientagegroup = 3">
					         			Child
					          	</xsl:when> 	
			   					<xsl:when test="patientagegroup= 4">
					            		Adolescent
					          	</xsl:when> 	
								<xsl:when test="patientagegroup= 5">
					            		Adult
					          	</xsl:when> 	
								<xsl:when test="patientagegroup= 6">
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
						<!-- <xsl:if test="patientweight[.!='']"> -->
							<xsl:text>>Weight (kg): </xsl:text>
							<xsl:value-of select="patientweight" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="patientheight[.!='']"> -->
							<xsl:text>Height (cm): </xsl:text>
							<xsl:value-of select="patientheight" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="patientsex[.!='']"> -->
							<xsl:text>Sex: </xsl:text>
							<xsl:choose>           
								<xsl:when test="patientsex =1">
								       Male
								</xsl:when> 	
						  		<xsl:when test="patientsex = 2">
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
								<xsl:when test="lastmenstrualdateformat = 102 ">
								           <xsl:variable name="patientlastmenstrualdate" select="patientlastmenstrualdate"/>               
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
						  		<xsl:when test="lastmenstrualdateformat = 610 ">
								           <xsl:variable name="patientlastmenstrualdate" select="patientlastmenstrualdate"/>
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
								<xsl:when test="lastmenstrualdateformat = 602 ">
								           <xsl:variable name="patientlastmenstrualdate" select="patientlastmenstrualdate"/>               
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
						<!-- <xsl:if test="patientmedicalhistorytext[.!='']"> -->
							<xsl:text>Medical History Text: </xsl:text>
							<xsl:value-of select="patientmedicalhistorytext" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell number-columns-spanned="3">
					<fo:block>
						<!-- <xsl:if test="resultstestsprocedures[.!='']"> -->
							<xsl:text>Results Test Procedure: </xsl:text>
							<xsl:value-of select="resultstestsprocedures" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>			 
		</fo:table-body>
		</fo:table>
	</fo:block>			
	<xsl:if test="medicalhistoryepisode[.!='']">
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
										<!-- <xsl:if test="medicalhistoryepisode/patientepisodenamemeddraversion[.!='']"> -->
											<xsl:text>MedDRA version for Medical History: </xsl:text>
											<xsl:value-of select="patientepisodenamemeddraversion" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell number-columns-spanned="2">
									<fo:block>
										<!-- <xsl:if test="patientepisodename[.!='']"> -->
											<xsl:text>Structured information: </xsl:text>
											<xsl:value-of select="patientepisodename" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>				
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="patientmedicalstartdate[.!='']"> -->
											<xsl:text>Medical Start Date: </xsl:text>
											<xsl:choose>           
												<xsl:when test="patientmedicalstartdateformat = 102 ">
												          <xsl:variable name="patientmedicalstartdate" select="patientmedicalstartdate"/>               
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
										  		<xsl:when test="patientmedicalstartdateformat = 610 ">
												           <xsl:variable name="patientmedicalstartdate" select="patientmedicalstartdate"/>
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
												<xsl:when test="patientmedicalstartdateformat = 602 ">
												           <xsl:variable name="patientmedicalstartdate" select="patientmedicalstartdate"/>               
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
										<!-- <xsl:if test="patientmedicalcontinue[.!='']"> -->
											<xsl:text>Medical Continuing: </xsl:text>
											<xsl:choose>           
												<xsl:when test="patientmedicalcontinue =1">
													           Yes
												</xsl:when> 	
											  	<xsl:when test="patientmedicalcontinue = 2">
													          No
												</xsl:when> 
												<xsl:when test="patientmedicalcontinue = 3">
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
										<!-- <xsl:if test="patientmedicalenddate[.!='']"> -->
											<xsl:text>Medical End Date: </xsl:text>
											<xsl:choose>
									            <xsl:when test="patientmedicalenddateformat = 102 ">
												            <xsl:variable name="patientmedicalenddate" select="patientmedicalenddate"/>               
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
										  		<xsl:when test="patientmedicalenddateformat = 610 ">
										  			 <xsl:variable name="patientmedicalenddate" select="patientmedicalenddate"/>      									          
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
												<xsl:when test="patientmedicalenddateformat = 602 ">
												           <xsl:variable name="patientmedicalenddate" select="patientmedicalenddate"/>               
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
										<!-- <xsl:if test="patientmedicalcomment[.!='']"> -->
											<xsl:text>Comments: </xsl:text>
											<xsl:value-of select="patientmedicalcomment" /> 
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
	<xsl:if test="patientpastdrugtherapy[.!='']">		
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
										<!-- <xsl:if test="patientpastdrugtherapy/patientdrugname[.!='']"> -->
											<xsl:text>Name of Drug as reported: </xsl:text>
											<xsl:value-of select="patientdrugname" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>				
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="patientdrugstartdate[.!='']"> -->
											<xsl:text>Start Date: </xsl:text>
											<xsl:choose>
									            <xsl:when test="patientdrugstartdateformat = 102 ">
												            <xsl:variable name="patientdrugstartdate" select="patientdrugstartdate"/>               
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
										  		<xsl:when test="patientdrugstartdateformat = 610 ">	
										  		<xsl:variable name="patientdrugstartdate" select="patientdrugstartdate"/>  												          
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
												<xsl:when test="patientdrugstartdateformat = 602 ">
												           <xsl:variable name="patientdrugstartdate" select="patientdrugstartdate"/>               
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
										<!-- <xsl:if test="patientdrugenddate[.!='']"> -->
											<xsl:text>End Date: </xsl:text>
											<xsl:choose>
									            <xsl:when test="patientdrugenddateformat = 102 ">
												           <xsl:variable name="patientdrugenddate" select="patientdrugenddate"/>               
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
										  		<xsl:when test="patientdrugenddateformat = 610 ">
										  			  <xsl:variable name="patientdrugenddate" select="patientdrugenddate"/>    													          
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
												<xsl:when test="patientdrugenddateformat = 602 ">
												           <xsl:variable name="patientdrugenddate" select="patientdrugenddate"/>               
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
										<!-- <xsl:if test="patientindicationmeddraversion[.!='']"> -->
											<xsl:text>MedDRA Version for indication: </xsl:text>
											<xsl:value-of select="patientindicationmeddraversion" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="patientdrugindication[.!='']"> -->
											<xsl:text>Indication: </xsl:text>
											<xsl:value-of select="patientdrugindication" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>				
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="patientdrugreactionmeddraversion[.!='']"> -->
											<xsl:text>MedDRA version for reaction: </xsl:text>
											<xsl:value-of select="patientdrugreactionmeddraversion" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="patientdrugreaction[.!='']"> -->
											<xsl:text>Reaction: </xsl:text>
											<xsl:value-of select="patientdrugreaction" /> 
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
    
    <xsl:apply-templates select="patientdeath"/>      
    <xsl:apply-templates select="parent"/>
    
    <!-- Reaction information-->
	<xsl:if test="reaction[.!='']">		
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
										<!-- <xsl:if test="primarysourcereaction[.!='']"> -->
											<xsl:text>Reaction/Event as reported by primary source: </xsl:text>
											<xsl:value-of select="primarysourcereaction" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>				
							</fo:table-row>
							<fo:table-row>								
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="reactionmeddraversionllt[.!='']"> -->
											<xsl:text>MedDRA version for reaction/event term LLT: </xsl:text>
											<xsl:value-of select="reactionmeddraversionllt" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="reactionmeddrallt[.!='']"> -->
											<xsl:text>Reaction/Event in MedDRA terminology (LLT): </xsl:text>
											<xsl:value-of select="reactionmeddrallt" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>					
							</fo:table-row>
							<fo:table-row>								
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="reactionmeddraversionpt[.!='']"> -->
											<xsl:text>MedDRA version for reaction/event term PT: </xsl:text>
											<xsl:value-of select="reactionmeddraversionpt" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="reactionmeddrapt[.!='']"> -->
											<xsl:text>Reaction/event MedDRA term (PT): </xsl:text>
											<xsl:value-of select="reactionmeddrapt" /> 
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>					
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell number-columns-spanned="2">
									<fo:block>
										<!-- <xsl:if test="termhighlighted[.!='']"> -->
											<xsl:text>Term highlighted by the reporter: </xsl:text>
											<xsl:choose>           
												<xsl:when test="termhighlighted = 1 ">
													           Yes, highlighted by the reporter, NOT serious
												</xsl:when>
												<xsl:when test="termhighlighted = 2 ">
													           No, not highlighted by the reporter, NOT serious
												</xsl:when>
												<xsl:when test="termhighlighted = 3 ">
													           Yes, highlighted by the reporter, SERIOUS
												</xsl:when>
												<xsl:when test="termhighlighted = 4 ">
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
										<!-- <xsl:if test="reactionstartdate[.!='']"> -->
											<xsl:text>Date of start of reaction/event: </xsl:text>
											<xsl:choose>
								            <xsl:when test="reactionstartdateformat = 102 ">
											           <xsl:variable name="reactionstartdate" select="reactionstartdate"/>               
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
											<xsl:when test="reactionstartdateformat = 203 ">
											           <xsl:variable name="reactionstartdate" select="reactionstartdate"/>               
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
									  		<xsl:when test="reactionstartdateformat = 610 ">
											          <xsl:variable name="reactionstartdate" select="reactionstartdate"/>
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
											<xsl:when test="reactionstartdateformat = 602 ">
											           <xsl:variable name="reactionstartdate" select="reactionstartdate"/>               
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
										<!-- <xsl:if test="reactionenddate[.!='']"> -->
											<xsl:text>Date of end of reaction/event: </xsl:text>
											<xsl:choose>
								            <xsl:when test="reactionenddateformat = 102 ">
											          <xsl:variable name="reactionenddate" select="reactionenddate"/>               
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
											<xsl:when test="reactionenddateformat = 203 ">
											            <xsl:variable name="reactionenddate" select="reactionenddate"/>               
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
									  		<xsl:when test="reactionenddateformat = 610 ">
											            <xsl:variable name="reactionenddate" select="reactionenddate"/>
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
											<xsl:when test="reactionenddateformat = 602 ">
											           <xsl:variable name="reactionenddate" select="reactionenddate"/>               
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
										<!-- <xsl:if test="reactionduration[.!='']"> -->
											<xsl:text>Duration of event: </xsl:text>
											<xsl:value-of select="reactionduration" />
											<xsl:text> </xsl:text>						
											<xsl:choose>           
													<xsl:when test="reactiondurationunit = 801 ">
														           Year(s)
													</xsl:when>
													<xsl:when test="reactiondurationunit = 802 ">
														           Month(s)
													</xsl:when>
													<xsl:when test="reactiondurationunit = 803 ">
														           Week(s)
													</xsl:when>
													<xsl:when test="reactiondurationunit = 804 ">
														           Day(s)
													</xsl:when>
													<xsl:when test="reactiondurationunit = 805 ">
														           Hour(s)
													</xsl:when>
													<xsl:when test="reactiondurationunit = 806 ">
														           Minute(s)
													</xsl:when>
													<xsl:when test="reactiondurationunit = 807 ">
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
										<!-- <xsl:if test="reactionfirsttime[.!='']"> -->
											<xsl:text>Time interval between beginning of suspect drug administration and start of event: </xsl:text>
											<xsl:value-of select="reactionfirsttime" />	
											<xsl:text> </xsl:text>					
											<xsl:choose>           
													<xsl:when test="reactionfirsttimeunit = 801 ">
														           Year(s)
													</xsl:when>
													<xsl:when test="reactionfirsttimeunit = 802 ">
														           Month(s)
													</xsl:when>
													<xsl:when test="reactionfirsttimeunit = 803 ">
														           Week(s)
													</xsl:when>
													<xsl:when test="reactionfirsttimeunit = 804 ">
														           Day(s)
													</xsl:when>
													<xsl:when test="reactionfirsttimeunit = 805 ">
														           Hour(s)
													</xsl:when>
													<xsl:when test="reactionfirsttimeunit = 806 ">
														           Minute(s)
													</xsl:when>
													<xsl:when test="reactionfirsttimeunit = 807 ">
														           Second(s)
													</xsl:when>
											</xsl:choose>
										<!-- </xsl:if> --> 
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<!-- <xsl:if test="reactionlasttime[.!='']"> -->
											<xsl:text>Time interval between last dose and start of reaction/event: </xsl:text>
											<xsl:value-of select="reactionlasttime" />
											<xsl:text> </xsl:text>							
											<xsl:choose>           
													<xsl:when test="reactionlasttimeunit = 801 ">
														           Year(s)
													</xsl:when>
													<xsl:when test="reactionlasttimeunit = 802 ">
														           Month(s)
													</xsl:when>
													<xsl:when test="reactionlasttimeunit = 803 ">
														           Week(s)
													</xsl:when>
													<xsl:when test="reactionlasttimeunit = 804 ">
														           Day(s)
													</xsl:when>
													<xsl:when test="reactionlasttimeunit = 805 ">
														           Hour(s)
													</xsl:when>
													<xsl:when test="reactionlasttimeunit = 806 ">
														           Minute(s)
													</xsl:when>
													<xsl:when test="reactionlasttimeunit = 807 ">
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
										<!-- <xsl:if test="reactionoutcome[.!='']"> -->
											<xsl:text>Outcome of reaction/event at the time of last observation: </xsl:text>
											<xsl:choose>           
												<xsl:when test="reactionoutcome = 1 ">
													           Recovered/Resolved
												</xsl:when>
												<xsl:when test="reactionoutcome = 2 ">
													           Recovering/Resolving
												</xsl:when>
												<xsl:when test="reactionoutcome = 3 ">
													           Not recovered/Not resolved
												</xsl:when>
												<xsl:when test="reactionoutcome = 4 ">
													           Recovered/Resolved with Sequelae
												</xsl:when>
												<xsl:when test="reactionoutcome = 5 ">
													           Fatal
												</xsl:when>
												<xsl:when test="reactionoutcome = 6 ">
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
	<xsl:if test="test[.!='']">		
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
						<!-- <xsl:if test="test/testdate[.!='']"> -->
							<xsl:text>Date: </xsl:text>
							<xsl:choose>
					            <xsl:when test="testdateformat = 102 ">
								            <xsl:variable name="testdate" select="testdate"/>               
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
						  		<xsl:when test="testdateformat = 610 ">
						  			  <xsl:variable name="testdate" select="testdate"/>        											           
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
								<xsl:when test="testdateformat = 602 ">
								           <xsl:variable name="testdate" select="testdate"/>               
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
						<!-- <xsl:if test="testname[.!='']"> -->
							<xsl:text>Test: </xsl:text>
							<xsl:value-of select="testname" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="testresult[.!='']"> -->
							<xsl:text>Result: </xsl:text>
							<xsl:value-of select="testresult" />
							<xsl:text> </xsl:text>
							<xsl:value-of select="testunit" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="lowtestrange[.!='']"> -->
							<xsl:text>Normal low range: </xsl:text>
							<xsl:value-of select="lowtestrange" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="hightestrange[.!='']"> -->
							<xsl:text>Normal high range: </xsl:text>
							<xsl:value-of select="hightestrange" /> 
						<!-- </xsl:if> --> 
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block>
						<!-- <xsl:if test="moreinformation[.!='']"> -->
							<xsl:text>More information available (Y/N): </xsl:text>
							<xsl:choose>           
									<xsl:when test="moreinformation = 1 ">
										           Yes
									</xsl:when>
									<xsl:when test="moreinformation = 2 ">
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
    <xsl:if test="drug[.!='']">		
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
											<xsl:when test="drugcharacterization = 1 ">
												           Suspect
											</xsl:when>
											<xsl:when test="drugcharacterization = 2 ">
												           Concomitant
											</xsl:when>	
											<xsl:when test="drugcharacterization = 3 ">
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
			    					<xsl:value-of select="medicinalproduct" />    				
			    				</fo:block>
			    			</fo:table-cell>    			
			    		</fo:table-row>
			    		<xsl:if test="activesubstance/activesubstancename[.!='']">
			    		<fo:table-row>
			    			<fo:table-cell number-columns-spanned="2">
			    				<fo:table margin="3pt">
				    				<fo:table-body>			    		
							    		<fo:table-row>
							    			<fo:table-cell  font-weight="bold" background-color="#DADADA" number-columns-spanned="2">
							    				<fo:block><xsl:text>B.4.k.2.2 Active Drug Substance Name</xsl:text></fo:block>
							    			</fo:table-cell>    			
							    		</fo:table-row>
							    		<xsl:for-each select="/ichicsr/safetyreport/patient/activesubstance">
							    		<fo:table-row>
							    			<fo:table-cell  number-columns-spanned="2">
							    				<fo:table border-width="1pt" border-style="solid" border-color="#CCCCCC">
							    				<fo:table-body>
							    					<fo:table-row>
							    						<fo:table-cell number-columns-spanned="2">
							    							<fo:block>
							    								<xsl:value-of select="activesubstancename" />
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
			    		<!-- <xsl:if test="obtaindrugcountry[.!='']"> -->
			    		<fo:table-row>
			    			<fo:table-cell number-columns-spanned="2">
			    				<fo:block>
			    					<xsl:text>Identification of the country where the drug was obtained: </xsl:text>
			    					<xsl:value-of select="obtaindrugcountry" />    				
			    				</fo:block>
			    			</fo:table-cell>    			
			    		</fo:table-row>
			    		<!-- </xsl:if> -->
			    		<!-- <xsl:if test="drugbatchnumb[.!='']"> -->
			    		<fo:table-row>
			    			<fo:table-cell number-columns-spanned="2">
			    				<fo:block>
			    					<xsl:text>Batch/lot number: </xsl:text>
			    					<xsl:value-of select="drugbatchnumb" />    				
			    				</fo:block>
			    			</fo:table-cell>    			
			    		</fo:table-row>
			    		<!-- </xsl:if> -->
			    		<!-- <xsl:if test="drugauthorizationnumb[.!=''] or drugauthorizationcountry[.!=''] or drugauthorizationholder[.!='']"> -->
			    		<fo:table-row>
			    			<fo:table-cell number-columns-spanned="2">
			    				<fo:block>
			    					<xsl:text>Holder and authorization/application number of drug: </xsl:text>			
			    				</fo:block>
			    			</fo:table-cell>    			
			    		</fo:table-row>
			    		<!-- </xsl:if> -->
			    		<!-- <xsl:if test="drugbatchnumb[.!='']"> -->
			    		<fo:table-row>
			    			<fo:table-cell number-columns-spanned="2">
			    				<fo:block>
			    					<xsl:text>Batch/lot number: </xsl:text>
			    					<xsl:value-of select="drugbatchnumb" />    				
			    				</fo:block>
			    			</fo:table-cell>    			
			    		</fo:table-row>
			    		<!-- </xsl:if> -->
			    		<!-- <xsl:if test="drugauthorizationnumb[.!=''] or drugauthorizationcountry[.!=''] or drugauthorizationholder[.!='']"> -->
			    		<fo:table-row>
			    			<fo:table-cell number-columns-spanned="2">
			    				<fo:block>
			    					<xsl:text>Holder and authorization/application number of drug</xsl:text>			        				
			    				</fo:block>
			    			</fo:table-cell>    			
			    		</fo:table-row>
			    		<!-- </xsl:if> -->
			    		<!-- <xsl:if test="drugauthorizationnumb[.!='']"> -->
			    		<fo:table-row>
			    			<fo:table-cell>
			    				<fo:block>
			    					<xsl:text>Authorization/Application Number: </xsl:text>
			    					<xsl:value-of select="drugauthorizationnumb" />    				
			    				</fo:block>
			    			</fo:table-cell>
			    			<!-- </xsl:if> -->
			    			<!-- <xsl:if test="drugauthorizationcountry[.!='']"> -->  
			    			<fo:table-cell>
			    				<fo:block>
			    					<xsl:text>Country of authorization/application: </xsl:text>
			    					<xsl:value-of select="drugauthorizationcountry" />    				
			    				</fo:block>
			    			</fo:table-cell>    			
			    		</fo:table-row>
			    		<!-- </xsl:if> -->
			    		<!-- <xsl:if test="drugauthorizationholder[.!='']"> -->
			    		<fo:table-row>
			    			<fo:table-cell number-columns-spanned="2">
			    				<fo:block>
			    					<xsl:text>Name of holder/applicant: </xsl:text>
			    					<xsl:value-of select="drugauthorizationholder" />    				
			    				</fo:block>
			    			</fo:table-cell>    			
			    		</fo:table-row>
			    		<!-- </xsl:if> -->
			    		<!-- <xsl:if test="drugstructuredosagenumb[.!=''] or
								drugstructuredosageunit[.!=''] or
								drugseparatedosagenumb[.!=''] or
								drugintervaldosageunitnumb[.!=''] or
								drugintervaldosagedefinition[.!=''] or
								drugcumulativedosagenumb[.!=''] or
								drugcumulativedosageunit[.!=''] or
								drugdosagetext[.!=''] or
								drugdosageform[.!=''] or
								drugadministrationroute[.!=''] or
								drugparadministration[.!=''] or
								reactiongestationperiod[.!=''] or
								reactiongestationperiodunit[.!=''] or
								drugindicationmeddraversion[.!=''] or
								drugindication[.!=''] or
								drugstartdateformat[.!=''] or
								drugstartdate[.!='']"> -->
								
						<fo:table-row>
			    			<fo:table-cell number-columns-spanned="2">
			    				<fo:block>
			    					<xsl:text>Structured Dosage Information: </xsl:text>    				
			    				</fo:block>
			    			</fo:table-cell>    			
			    		</fo:table-row>						
					<!-- </xsl:if> -->
					
					<fo:table-row>
		    			<!-- <xsl:if test="drugstructuredosagenumb[.!='']"> -->
		    			<fo:table-cell>
		    				<fo:block>
		    					<xsl:text>Dose: </xsl:text>
		    					<xsl:value-of select="drugstructuredosagenumb" />
								<xsl:text> </xsl:text>							
								<xsl:choose>           
									<xsl:when test="drugstructuredosageunit = 001 ">
										           kilogram(s)
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 002 ">
										           gram(s) 
									</xsl:when>	
									<xsl:when test="drugstructuredosageunit = 003 ">
										           milligram(s) 
									</xsl:when>	
									<xsl:when test="drugstructuredosageunit = 004 ">
										           microgram(s) 
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 005 ">
										           nanogram(s)
									</xsl:when>	
									<xsl:when test="drugstructuredosageunit = 006 ">
										           picogram(s)
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 007 ">
										           milligram(s)/kilogram
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 008 ">
										           microgram(s)/kilogram
									</xsl:when>	
									<xsl:when test="drugstructuredosageunit = 009 ">
										           milligram(s)/sq. meter
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 010 ">
										           microgram(s)/ sq. Meter
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 011 ">
										           litre(s)
									</xsl:when>	
									<xsl:when test="drugstructuredosageunit = 012 ">
										           millilitre(s)
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 013 ">
										           microlitre(s)
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 014 ">
										           becquerel(s)
									</xsl:when>	
									<xsl:when test="drugstructuredosageunit = 015 ">
										           gigabecquerel(s)
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 016 ">
										           megabecquerel(s)
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 017 ">
										           kilobecquerel(s)
									</xsl:when>	
									<xsl:when test="drugstructuredosageunit = 018 ">
										           curie(s)
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 019 ">
										           millicurie(s)
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 020 ">
										           microcurie(s)
									</xsl:when>	
									<xsl:when test="drugstructuredosageunit = 021 ">
										           nanocurie(s)
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 022 ">
										           mole(s)
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 023 ">
										           millimole(s)
									</xsl:when>	
									<xsl:when test="drugstructuredosageunit = 024 ">
										           micromole(s)
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 025 ">
										           international unit(s)
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 026 ">
										           iu(1000s)
									</xsl:when>	
									<xsl:when test="drugstructuredosageunit = 027 ">
										           iu(1,000,000s)
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 028 ">
										           iu/kilogram
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 029 ">
										           milliequivalent(s)
									</xsl:when>	
									<xsl:when test="drugstructuredosageunit = 030 ">
										           percent
									</xsl:when>	
									<xsl:when test="drugstructuredosageunit = 031 ">
										           drop(s)
									</xsl:when>
									<xsl:when test="drugstructuredosageunit = 032 ">
										           dosage form
									</xsl:when>
									<xsl:otherwise>		            
			          				</xsl:otherwise>									
								</xsl:choose>    				
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>
		    			<xsl:if test="drugseparatedosagenumb[.!='']"> -->
		    			<fo:table-cell>
		    				<fo:block>
		    				<xsl:text>Number of separate dosages: </xsl:text>
		    				<xsl:value-of select="drugseparatedosagenumb" />		    				
		    				</fo:block>		    				
		    			</fo:table-cell>
		    			<!-- </xsl:if>    	 -->		
		    		</fo:table-row>	
		    		<fo:table-row>
		    			<!--  <xsl:if test="drugintervaldosageunitnumb[.!='']">-->
		    			<fo:table-cell number-columns-spanned="2">
		    				<fo:block>
			    				<xsl:text>Number of Units in the interval: </xsl:text>
			    				<xsl:value-of select="drugintervaldosageunitnumb" />
								<xsl:text> </xsl:text>							
								<xsl:choose>           
									<xsl:when test="drugintervaldosagedefinition = 801 ">
										           Year(s)
									</xsl:when>
									<xsl:when test="drugintervaldosagedefinition = 802 ">
										           Month(s)
									</xsl:when>
									<xsl:when test="drugintervaldosagedefinition = 803 ">
										           Week(s)
									</xsl:when>
									<xsl:when test="drugintervaldosagedefinition = 804 ">
										           Day(s)
									</xsl:when>
									<xsl:when test="drugintervaldosagedefinition = 805 ">
										           Hour(s)
									</xsl:when>
									<xsl:when test="drugintervaldosagedefinition = 806 ">
										           Minute(s)
									</xsl:when>	
									  <xsl:otherwise></xsl:otherwise>							
								</xsl:choose>		    				
		    				</fo:block>		    				
		    			</fo:table-cell>
		    			<!-- </xsl:if>-->
		    		</fo:table-row>
		    		<fo:table-row>
		    			<!-- <xsl:if test="drugcumulativedosagenumb[.!='']">-->
		    			<fo:table-cell>
		    				<fo:block>
		    					<xsl:text>Cumulative dose to first reaction: </xsl:text>
		    					<xsl:value-of select="drugcumulativedosagenumb" />
								<xsl:text> </xsl:text>								
								<xsl:choose>           
									<xsl:when test="drugcumulativedosageunit = 001 ">
										           kilogram(s)
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 002 ">
										           gram(s) 
									</xsl:when>	
									<xsl:when test="drugcumulativedosageunit = 003 ">
										           milligram(s) 
									</xsl:when>	
									<xsl:when test="drugcumulativedosageunit = 004 ">
										           microgram(s) 
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 005 ">
										           nanogram(s)
									</xsl:when>	
									<xsl:when test="drugcumulativedosageunit = 006 ">
										           picogram(s)
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 007 ">
										           milligram(s)/kilogram
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 008 ">
										           microgram(s)/kilogram
									</xsl:when>	
									<xsl:when test="drugcumulativedosageunit = 009 ">
										           milligram(s)/sq. meter
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 010 ">
										           microgram(s)/ sq. Meter
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 011 ">
										           litre(s)
									</xsl:when>	
									<xsl:when test="drugcumulativedosageunit = 012 ">
										           millilitre(s)
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 013 ">
										           microlitre(s)
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 014 ">
										           becquerel(s)
									</xsl:when>	
									<xsl:when test="drugcumulativedosageunit = 015 ">
										           gigabecquerel(s)
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 016 ">
										           megabecquerel(s)
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 017 ">
										           kilobecquerel(s)
									</xsl:when>	
									<xsl:when test="drugcumulativedosageunit = 018 ">
										           curie(s)
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 019 ">
										           millicurie(s)
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 020 ">
										           microcurie(s)
									</xsl:when>	
									<xsl:when test="drugcumulativedosageunit = 021 ">
										           nanocurie(s)
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 022 ">
										           mole(s)
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 023 ">
										           millimole(s)
									</xsl:when>	
									<xsl:when test="drugcumulativedosageunit = 024 ">
										           micromole(s)
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 025 ">
										           international unit(s)
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 026 ">
										           iu(1000s)
									</xsl:when>	
									<xsl:when test="drugcumulativedosageunit = 027 ">
										           iu(1,000,000s)
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 028 ">
										           iu/kilogram
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 029 ">
										           milliequivalent(s)
									</xsl:when>	
									<xsl:when test="drugcumulativedosageunit = 030 ">
										           percent
									</xsl:when>	
									<xsl:when test="drugcumulativedosageunit = 031 ">
										           drop(s)
									</xsl:when>
									<xsl:when test="drugcumulativedosageunit = 032 ">
										           dosage form
									</xsl:when>
									<xsl:otherwise>		            
			          				</xsl:otherwise>									
								</xsl:choose>
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>
		    			<xsl:if test="drugdosagetext[.!='']">-->
		    			<fo:table-cell>
		    				<fo:block>
		    					<xsl:text>Dosage text: </xsl:text>
		    					<xsl:value-of select="drugdosagetext" />
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>-->
		    		</fo:table-row>
		    		<fo:table-row>
		    			<!-- <xsl:if test="drugdosageform[.!='']">-->
		    			<fo:table-cell number-columns-spanned="2">
		    				<fo:block>
		    					<xsl:text>Pharmaceutical form (Dosage form): </xsl:text>
		    					<xsl:value-of select="drugdosageform" />
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>	-->
		    		</fo:table-row>
					
					<fo:table-row>
		    			<!-- <xsl:if test="drugadministrationroute[.!='']">-->
		    			<fo:table-cell>
		    				<fo:block>
		    					<xsl:text>Route of administration: </xsl:text>
		    					<xsl:choose>           
								<xsl:when test="drugadministrationroute = 001 ">
									           Auricular (otic)
								</xsl:when>
								<xsl:when test="drugadministrationroute = 002 ">
									           Buccal 
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 003 ">
									           Cutaneous 
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 004 ">
									           Dental 
								</xsl:when>
								<xsl:when test="drugadministrationroute = 005 ">
									           Endocervical
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 006 ">
									          Endosinusial
								</xsl:when>
								<xsl:when test="drugadministrationroute = 007 ">
									           Endotracheal
								</xsl:when>
								<xsl:when test="drugadministrationroute = 008 ">
									          Epidural
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 009 ">
									           Extra-amniotic
								</xsl:when>
								<xsl:when test="drugadministrationroute = 010 ">
									           Hemodialysis
								</xsl:when>
								<xsl:when test="drugadministrationroute = 011 ">
									           Intra corpus cavernosum
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 012 ">
									           Intra-amniotic
								</xsl:when>
								<xsl:when test="drugadministrationroute = 013 ">
									           Intra-arterial
								</xsl:when>
								<xsl:when test="drugadministrationroute = 014 ">
									           Intra-articular
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 015 ">
									           Intra-uterine
								</xsl:when>
								<xsl:when test="drugadministrationroute = 016 ">
									           Intracardiac
								</xsl:when>
								<xsl:when test="drugadministrationroute = 017 ">
									           Intracavernous
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 018 ">
									           Intracerebral
								</xsl:when>
								<xsl:when test="drugadministrationroute = 019 ">
									           Intracervical
								</xsl:when>
								<xsl:when test="drugadministrationroute = 020 ">
									           Intracisternal
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 021 ">
									           Intracorneal
								</xsl:when>
								<xsl:when test="drugadministrationroute = 022 ">
									           Intracoronary
								</xsl:when>
								<xsl:when test="drugadministrationroute = 023 ">
									           Intradermal
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 024 ">
									           Intradiscal (intraspinal)
								</xsl:when>
								<xsl:when test="drugadministrationroute = 025 ">
									           Intrahepatic
								</xsl:when>
								<xsl:when test="drugadministrationroute = 026 ">
									           Intralesional
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 027 ">
									           Intralymphatic
								</xsl:when>
								<xsl:when test="drugadministrationroute = 028 ">
									          Intramedullar (bone marrow)
								</xsl:when>
								<xsl:when test="drugadministrationroute = 029 ">
									           Intrameningeal
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 030 ">
									           Intramuscular
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 031 ">
									          Intraocular
								</xsl:when>
								<xsl:when test="drugadministrationroute = 032 ">
									           Intrapericardial
								</xsl:when>
								<xsl:when test="drugadministrationroute = 033 ">
									           Intraperitoneal
								</xsl:when>
								<xsl:when test="drugadministrationroute = 034 ">
									           Intrapleural 
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 035 ">
									           Intrasynovial 
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 036 ">
									           Intratumor 
								</xsl:when>
								<xsl:when test="drugadministrationroute = 037 ">
									           Intrathecal
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 038 ">
									           Intrathoracic
								</xsl:when>
								<xsl:when test="drugadministrationroute = 039 ">
									           Intratracheal
								</xsl:when>
								<xsl:when test="drugadministrationroute = 040 ">
									           Intravenous bolus
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 041 ">
									           Intravenous drip
								</xsl:when>
								<xsl:when test="drugadministrationroute = 042 ">
									          Intravenous (not otherwise specified)
								</xsl:when>
								<xsl:when test="drugadministrationroute = 043 ">
									           Intravesical
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 044 ">
									           Iontophoresis
								</xsl:when>
								<xsl:when test="drugadministrationroute = 045 ">
									           Nasal
								</xsl:when>
								<xsl:when test="drugadministrationroute = 046 ">
									           Occlusive dressing technique
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 047 ">
									          Ophthalmic
								</xsl:when>
								<xsl:when test="drugadministrationroute = 048 ">
									           Oral
								</xsl:when>
								<xsl:when test="drugadministrationroute = 049 ">
									          Oropharingeal
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 050 ">
									           Other
								</xsl:when>
								<xsl:when test="drugadministrationroute = 051 ">
									           Parenteral
								</xsl:when>
								<xsl:when test="drugadministrationroute = 052 ">
									           Periarticular
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 053 ">
									           Perineural
								</xsl:when>
								<xsl:when test="drugadministrationroute = 054 ">
									           Rectal
								</xsl:when>
								<xsl:when test="drugadministrationroute = 055 ">
									          Respiratory (inhalation)
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 056 ">
									           Retrobulbar
								</xsl:when>
								<xsl:when test="drugadministrationroute = 057 ">
									           Sunconjunctival
								</xsl:when>
								<xsl:when test="drugadministrationroute = 058 ">
									           Subcutaneous
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 059 ">
									           Subdermal
								</xsl:when>
								<xsl:when test="drugadministrationroute = 060 ">
									           Sublingual
								</xsl:when>
								<xsl:when test="drugadministrationroute = 061 ">
									           Topical
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 062 ">
									           Transdermal
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 063 ">
									           Transmammary
								</xsl:when>
								<xsl:when test="drugadministrationroute = 064 ">
									           Transplacental
								</xsl:when>
								<xsl:when test="drugadministrationroute = 065 ">
									           Unknown
								</xsl:when>	
								<xsl:when test="drugadministrationroute = 066 ">
									           Urethral
								</xsl:when>
								<xsl:when test="drugadministrationroute = 067 ">
									           Vaginal
								</xsl:when>
								<xsl:otherwise>		            
		          				</xsl:otherwise>									
								</xsl:choose>
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>
		    			<xsl:if test="drugparadministration[.!='']">-->
		    			<fo:table-cell>
		    				<fo:block>
		    					<xsl:text>Parent route of administration (in case of a parent child/fetus report): </xsl:text>
		    					<xsl:choose>           
								<xsl:when test="drugparadministration = 001 ">
									           Auricular (otic)
								</xsl:when>
								<xsl:when test="drugparadministration = 002 ">
									           Buccal 
								</xsl:when>	
								<xsl:when test="drugparadministration = 003 ">
									           Cutaneous 
								</xsl:when>	
								<xsl:when test="drugparadministration = 004 ">
									           Dental 
								</xsl:when>
								<xsl:when test="drugparadministration = 005 ">
									           Endocervical
								</xsl:when>	
								<xsl:when test="drugparadministration = 006 ">
									          Endosinusial
								</xsl:when>
								<xsl:when test="drugparadministration = 007 ">
									           Endotracheal
								</xsl:when>
								<xsl:when test="drugparadministration = 008 ">
									          Epidural
								</xsl:when>	
								<xsl:when test="drugparadministration = 009 ">
									           Extra-amniotic
								</xsl:when>
								<xsl:when test="drugparadministration = 010 ">
									           Hemodialysis
								</xsl:when>
								<xsl:when test="drugparadministration = 011 ">
									           Intra corpus cavernosum
								</xsl:when>	
								<xsl:when test="drugparadministration = 012 ">
									           Intra-amniotic
								</xsl:when>
								<xsl:when test="drugparadministration = 013 ">
									           Intra-arterial
								</xsl:when>
								<xsl:when test="drugparadministration = 014 ">
									           Intra-articular
								</xsl:when>	
								<xsl:when test="drugparadministration = 015 ">
									           Intra-uterine
								</xsl:when>
								<xsl:when test="drugparadministration = 016 ">
									           Intracardiac
								</xsl:when>
								<xsl:when test="drugparadministration = 017 ">
									           Intracavernous
								</xsl:when>	
								<xsl:when test="drugparadministration = 018 ">
									           Intracerebral
								</xsl:when>
								<xsl:when test="drugparadministration = 019 ">
									           Intracervical
								</xsl:when>
								<xsl:when test="drugparadministration = 020 ">
									           Intracisternal
								</xsl:when>	
								<xsl:when test="drugparadministration = 021 ">
									           Intracorneal
								</xsl:when>
								<xsl:when test="drugparadministration = 022 ">
									           Intracoronary
								</xsl:when>
								<xsl:when test="drugparadministration = 023 ">
									           Intradermal
								</xsl:when>	
								<xsl:when test="drugparadministration = 024 ">
									           Intradiscal (intraspinal)
								</xsl:when>
								<xsl:when test="drugparadministration = 025 ">
									           Intrahepatic
								</xsl:when>
								<xsl:when test="drugparadministration = 026 ">
									           Intralesional
								</xsl:when>	
								<xsl:when test="drugparadministration = 027 ">
									           Intralymphatic
								</xsl:when>
								<xsl:when test="drugparadministration = 028 ">
									          Intramedullar (bone marrow)
								</xsl:when>
								<xsl:when test="drugparadministration = 029 ">
									           Intrameningeal
								</xsl:when>	
								<xsl:when test="drugparadministration = 030 ">
									           Intramuscular
								</xsl:when>	
								<xsl:when test="drugparadministration = 031 ">
									          Intraocular
								</xsl:when>
								<xsl:when test="drugparadministration = 032 ">
									           Intrapericardial
								</xsl:when>
								<xsl:when test="drugparadministration = 033 ">
									           Intraperitoneal
								</xsl:when>
								<xsl:when test="drugparadministration = 034 ">
									           Intrapleural 
								</xsl:when>	
								<xsl:when test="drugparadministration = 035 ">
									           Intrasynovial 
								</xsl:when>	
								<xsl:when test="drugparadministration = 036 ">
									           Intratumor 
								</xsl:when>
								<xsl:when test="drugparadministration = 037 ">
									           Intrathecal
								</xsl:when>	
								<xsl:when test="drugparadministration = 038 ">
									           Intrathoracic
								</xsl:when>
								<xsl:when test="drugparadministration = 039 ">
									           Intratracheal
								</xsl:when>
								<xsl:when test="drugparadministration = 040 ">
									           Intravenous bolus
								</xsl:when>	
								<xsl:when test="drugparadministration = 041 ">
									           Intravenous drip
								</xsl:when>
								<xsl:when test="drugparadministration = 042 ">
									          Intravenous (not otherwise specified)
								</xsl:when>
								<xsl:when test="drugparadministration = 043 ">
									           Intravesical
								</xsl:when>	
								<xsl:when test="drugparadministration = 044 ">
									           Iontophoresis
								</xsl:when>
								<xsl:when test="drugparadministration = 045 ">
									           Nasal
								</xsl:when>
								<xsl:when test="drugparadministration = 046 ">
									           Occlusive dressing technique
								</xsl:when>	
								<xsl:when test="drugparadministration = 047 ">
									          Ophthalmic
								</xsl:when>
								<xsl:when test="drugparadministration = 048 ">
									           Oral
								</xsl:when>
								<xsl:when test="drugparadministration = 049 ">
									          Oropharingeal
								</xsl:when>	
								<xsl:when test="drugparadministration = 050 ">
									           Other
								</xsl:when>
								<xsl:when test="drugparadministration = 051 ">
									           Parenteral
								</xsl:when>
								<xsl:when test="drugparadministration = 052 ">
									           Periarticular
								</xsl:when>	
								<xsl:when test="drugparadministration = 053 ">
									           Perineural
								</xsl:when>
								<xsl:when test="drugparadministration = 054 ">
									           Rectal
								</xsl:when>
								<xsl:when test="drugparadministration = 055 ">
									          Respiratory (inhalation)
								</xsl:when>	
								<xsl:when test="drugparadministration = 056 ">
									      
						     			     Retrobulbar
								</xsl:when>
								<xsl:when test="drugparadministration = 057 ">
									           Sunconjunctival
								</xsl:when>
								<xsl:when test="drugparadministration = 058 ">
									           Subcutaneous
								</xsl:when>	
								<xsl:when test="drugparadministration = 059 ">
									           Subdermal
								</xsl:when>
								<xsl:when test="drugparadministration = 060 ">
									           Sublingual
								</xsl:when>
								<xsl:when test="drugparadministration = 061 ">
									           Topical
								</xsl:when>	
								<xsl:when test="drugparadministration = 062 ">
									           Transdermal
								</xsl:when>	
								<xsl:when test="drugparadministration = 063 ">
									           Transmammary
								</xsl:when>
								<xsl:when test="drugparadministration = 064 ">
									  
										         Transplacental
								</xsl:when>
								<xsl:when test="drugparadministration = 065 ">
									
								           Unknown
								</xsl:when>	
								<xsl:when test="drugparadministration = 066 ">
									           Urethral
								</xsl:when>
								<xsl:when test="drugparadministration = 067 ">
									           Vaginal
								</xsl:when>
								<xsl:otherwise>		            
		          				</xsl:otherwise>									
								</xsl:choose>
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>-->	
		    		</fo:table-row>
		    		<fo:table-row>
						<!-- <xsl:if test="reactiongestationperiod[.!='']">-->
		    			<fo:table-cell>
		    				<fo:block>
		    					<xsl:text>Gestation period at time of exposure: </xsl:text>
		    					<xsl:value-of select="reactiongestationperiod" />
								<xsl:text> </xsl:text>							
								<xsl:choose>
									<xsl:when test="reactiongestationperiodunit = 802 ">
										           Month(s)
									</xsl:when>
									<xsl:when test="reactiongestationperiodunit = 803 ">
										           Week(s)
									</xsl:when>
									<xsl:when test="reactiongestationperiodunit = 804 ">
										           Day(s)
									</xsl:when>
									<xsl:when test="reactiongestationperiodunit = 805 ">
										           Trimester(s)
									</xsl:when>
								    <xsl:otherwise>		            
		         					</xsl:otherwise>															
								</xsl:choose>
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>  
		    			<xsl:if test="drugindicationmeddraversion[.!='']">	-->
		    			<fo:table-cell>
		    				<fo:block>
		    					<xsl:text>MedDRA version for indication: </xsl:text>
		    					<xsl:value-of select="drugindicationmeddraversion" />
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>	-->    		
		    		</fo:table-row>
					<fo:table-row>
						<!-- <xsl:if test="drugindication[.!='']">-->
		    			<fo:table-cell number-columns-spanned="2">
		    				<fo:block>
		    					<xsl:text>Indication for use in the case: </xsl:text>
		    					<xsl:value-of select="drugindication" />
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>-->		    			
					</fo:table-row>
			    	<fo:table-row>
			    		<!-- <xsl:if test="drugstartdate[.!='']">-->
		    			<fo:table-cell number-columns-spanned="2">
		    				<fo:block>
		    					<xsl:text>Date of start of drug: </xsl:text>
		    					<xsl:choose>
						            <xsl:when test="drugstartdateformat = 102 ">
						            	  <xsl:variable name="drugstartdate" select="drugstartdate"/>    
									           <xsl:value-of select="concat(substring($drugstartdate, 7,
														 2),'-')"/>
														 <xsl:choose>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 01">JAN</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 02">FEB</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 03">MAR</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 04">APR</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 05">MAY</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 06">JUN</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 07">JUL</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 08">AUG</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 09">SEP</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 10">OCT</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 11">NOV</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 12">DEC</xsl:when>
														 </xsl:choose>
														 <xsl:value-of select="concat('-',substring($drugstartdate, 1,
														 4))"/>	
									</xsl:when> 	
							  		<xsl:when test="drugstartdateformat = 610 ">
							  			 <xsl:variable name="drugstartdate" select="drugstartdate"/>    											         
														 <xsl:choose>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 01">JAN</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 02">FEB</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 03">MAR</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 04">APR</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 05">MAY</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 06">JUN</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 07">JUL</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 08">AUG</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 09">SEP</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 10">OCT</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 11">NOV</xsl:when>
															 <xsl:when test="substring($drugstartdate, 5, 2) = 12">DEC</xsl:when>
														 </xsl:choose>
														 <xsl:value-of select="concat('-',substring($drugstartdate, 1,
														 4))"/>	
									</xsl:when> 
									<xsl:when test="drugstartdateformat = 602 ">
									           <xsl:variable name="drugstartdate" select="drugstartdate"/>               
									 		   <xsl:value-of select="substring($drugstartdate, 1, 4)"/>
									</xsl:when>
									<xsl:otherwise>		            
					                </xsl:otherwise>
								</xsl:choose>
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>-->		    			
			    	</fo:table-row>
			    	<fo:table-row>
			    		<!-- <xsl:if test="drugstartperiod[.!=''] or
									drugstartperiodunit[.!=''] or
									druglastperiod[.!=''] or
									druglastperiodunit[.!=''] or
									drugenddateformat[.!=''] or
									drugenddate[.!=''] or
									drugtreatmentduration[.!=''] or
									drugtreatmentdurationunit[.!=''] or
									actiondrug[.!='']">-->
		    			<fo:table-cell number-columns-spanned="2">
		    				<fo:block>
		    					<xsl:text>Time interval between drug administration and start of reaction/event</xsl:text>		    					
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>		-->	    	
			    	</fo:table-row>
			    	
			    	<fo:table-row>
			    		<!-- <xsl:if test="drugstartperiod[.!='']">-->
		    			<fo:table-cell>
		    				<fo:block>
		    					<xsl:text>Time interval between beginning  of drug administration and start of reaction/event: </xsl:text>
		    					<xsl:value-of select="drugstartperiod" />
								<xsl:text> </xsl:text>						
								<xsl:choose>           
										<xsl:when test="drugstartperiodunit = 801 ">
											           Year(s)
										</xsl:when>
										<xsl:when test="drugstartperiodunit = 802 ">
											           Month(s)
										</xsl:when>
										<xsl:when test="drugstartperiodunit = 803 ">
											           Week(s)
										</xsl:when>
										<xsl:when test="drugstartperiodunit = 804 ">
											           Day(s)
										</xsl:when>
										<xsl:when test="drugstartperiodunit = 805 ">
											           Hour(s)
										</xsl:when>
										<xsl:when test="drugstartperiodunit = 806 ">
											           Minute(s)
										</xsl:when>	
										<xsl:when test="drugstartperiodunit = 807 ">
											           Second(s)
										</xsl:when>	
										<xsl:otherwise>		            
			          					</xsl:otherwise>						
								</xsl:choose>		    					
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>	
		    			<xsl:if test="druglastperiod[.!='']">-->	
		    			<fo:table-cell>
		    				<fo:block>
		    					<xsl:text>Time interval between last dose of drug and start of reaction/event: </xsl:text>
		    					<xsl:value-of select="druglastperiod" />
								<xsl:text> </xsl:text>							
								<xsl:choose>           
										<xsl:when test="druglastperiodunit = 801 ">
											           Year(s)
										</xsl:when>
										<xsl:when test="druglastperiodunit = 802 ">
											           Month(s)
										</xsl:when>
										<xsl:when test="druglastperiodunit = 803 ">
											           Week(s)
										</xsl:when>
										<xsl:when test="druglastperiodunit = 804 ">
											           Day(s)
										</xsl:when>
										<xsl:when test="druglastperiodunit = 805 ">
											           Hour(s)
										</xsl:when>
										<xsl:when test="druglastperiodunit = 806 ">
											           Minute(s)
										</xsl:when>	
											<xsl:when test="druglastperiodunit = 807 ">
											           Second(s)
										</xsl:when>
										<xsl:otherwise>		            
			          					</xsl:otherwise>							
								</xsl:choose>		    							    					
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>-->			    	
			    	</fo:table-row>
			    	<fo:table-row>
			    		<!-- <xsl:if test="drugenddate[.!='']">-->
		    			<fo:table-cell>
		    				<fo:block>
		    					<xsl:text>Date of last administration: </xsl:text>
		    					<xsl:choose>
						            <xsl:when test="drugenddateformat = 102 ">
						            	<xsl:variable name="drugenddate" select="drugenddate"/>
									          <xsl:value-of select="concat(substring($drugenddate, 7,
															 2),'-')"/>
															 <xsl:choose>
																 <xsl:when test="substring($drugenddate, 5, 2) = 01">JAN</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 02">FEB</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 03">MAR</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 04">APR</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 05">MAY</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 06">JUN</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 07">JUL</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 08">AUG</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 09">SEP</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 10">OCT</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 11">NOV</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 12">DEC</xsl:when>
															 </xsl:choose>
															 <xsl:value-of select="concat('-',substring($drugenddate, 1,
															 4))"/>	
									</xsl:when> 	
							  		<xsl:when test="drugenddateformat = 610 ">	
							  		<xsl:variable name="drugenddate" select="drugenddate"/>									         
															 <xsl:choose>
																 <xsl:when test="substring($drugenddate, 5, 2) = 01">JAN</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 02">FEB</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 03">MAR</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 04">APR</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 05">MAY</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 06">JUN</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 07">JUL</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 08">AUG</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 09">SEP</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 10">OCT</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 11">NOV</xsl:when>
																 <xsl:when test="substring($drugenddate, 5, 2) = 12">DEC</xsl:when>
															 </xsl:choose>
															 <xsl:value-of select="concat('-',substring($drugenddate, 1,
															 4))"/>	
									</xsl:when> 
									<xsl:when test="drugenddateformat = 602 ">
									           <xsl:variable name="drugenddate" select="drugenddate"/>               
									 		   <xsl:value-of select="substring($drugenddate, 1, 4)"/>
									</xsl:when>
									<xsl:otherwise>		            
					                </xsl:otherwise>
								</xsl:choose>		    					
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>
		    			<xsl:if test="drugtreatmentduration[.!='']"> -->
		    			<fo:table-cell>
		    				<fo:block>
		    					<xsl:text>Duration of drug administration: </xsl:text>
		    					<xsl:value-of select="drugtreatmentduration" />	
								<xsl:text> </xsl:text>							
								<xsl:choose>           
									<xsl:when test="drugtreatmentdurationunit = 801 ">
										           Year(s)
									</xsl:when>
									<xsl:when test="drugtreatmentdurationunit = 802 ">
										           Month(s)
									</xsl:when>
									<xsl:when test="drugtreatmentdurationunit = 803 ">
										           Week(s)
									</xsl:when>
									<xsl:when test="drugtreatmentdurationunit = 804 ">
										           Day(s)
									</xsl:when>
									<xsl:when test="drugtreatmentdurationunit = 805 ">
										           Hour(s)
									</xsl:when>
									<xsl:when test="drugtreatmentdurationunit = 806 ">
										           Minute(s)
									</xsl:when>
									<xsl:otherwise>		            
		          					</xsl:otherwise>			
								</xsl:choose>		    					
		    				</fo:block>
		    			</fo:table-cell>
		    			<!-- </xsl:if>-->		    	
			    	</fo:table-row>
			    	<fo:table-row>
			    		<!-- <xsl:if test="actiondrug[.!='']">-->
		    			<fo:table-cell number-columns-spanned="2">
		    				<fo:block>
		    					<xsl:text>Actions(s) taken with drug: </xsl:text>
		    					<xsl:choose>           
										<xsl:when test="actiondrug = 1 ">
											           Drug Withdrawn
										</xsl:when>
										<xsl:when test="actiondrug = 2 ">
											           Dose reduced
										</xsl:when>
										<xsl:when test="actiondrug = 3 ">
											           Dose increased
										</xsl:when>
										<xsl:when test="actiondrug = 4 ">
											           Dose not changed
										</xsl:when>
										<xsl:when test="actiondrug = 5 ">
											           Unknown
										</xsl:when>
										<xsl:when test="actiondrug = 6 ">
											           Not applicable
										</xsl:when>
										<xsl:otherwise>		            
			          					</xsl:otherwise>			
								</xsl:choose>		    					
		    				</fo:block>
		    			</fo:table-cell>
		    			<!--</xsl:if>-->		    			    	
			    	</fo:table-row>
			    	<fo:table-row>
			    		<!--<xsl:if test="drugrecurreadministration[.!=''] or
									drugrecurrence[.!=''] or
									drugrecuractionmeddraversion[.!=''] or
									drugrecuraction[.!=''] or
									drugreactionrelatedness[.!=''] or
									drugreactionassesmeddraversion[.!=''] or
									drugreactionasses[.!=''] or
									drugassessmentsource[.!=''] or
									drugassessmentmethod[.!=''] or
									drugresult[.!=''] or
									drugadditional[.!='']">-->
		    			<fo:table-cell number-columns-spanned="2">
		    				<fo:block>
		    					<xsl:text>Effect of rechallenge (or re-exposure), for suspect drug(s) only: </xsl:text>		    					
		    				</fo:block>
		    			</fo:table-cell>
		    			<!--</xsl:if>-->		    					    	
			    	</fo:table-row>	
			    	<fo:table-row>
			    		<!--<xsl:if test="drugrecurreadministration[.!='']">-->
		    			<fo:table-cell number-columns-spanned="2">
		    				<fo:block>
		    					<xsl:text>Did reaction recur on readministration: </xsl:text>
		    					<xsl:choose>           
									<xsl:when test="drugrecurreadministration = 1 ">
										           Yes
									</xsl:when>
									<xsl:when test="drugrecurreadministration = 2 ">
										           No
									</xsl:when>
									<xsl:when test="drugrecurreadministration = 3 ">
										           Unknown
									</xsl:when>	
									<xsl:otherwise>		            
		          					</xsl:otherwise>																
								</xsl:choose>	    					
		    				</fo:block>
		    			</fo:table-cell>
		    			<!--</xsl:if>-->		    				    	
			    	</fo:table-row>	
			    	<xsl:if test="drugrecurrence[.!='']">
			    		<fo:table-row>
		    			<fo:table-cell number-columns-spanned="2">
		    				<fo:table margin="3pt">
				    				<fo:table-body>			    		
							    		<fo:table-row>
							    			<fo:table-cell  font-weight="bold" background-color="#DADADA" number-columns-spanned="2">
							    				<fo:block><xsl:text>B.4.k.17.2 Drug recurrence</xsl:text></fo:block>
							    			</fo:table-cell>    			
							    		</fo:table-row>
							    		<xsl:for-each select="/ichicsr/safetyreport/patient/drugrecurrence">
							    		<fo:table-row>
							    			<fo:table-cell  number-columns-spanned="2">
							    				<fo:table border-width="1pt" border-style="solid" border-color="#CCCCCC">
							    				<fo:table-body>
							    					<fo:table-row>
							    						<fo:table-cell>
							    							<fo:block>
							    								<xsl:if test="drugrecuractionmeddraversion[.!='']">
							    								<xsl:text>MedDRA version for reaction(s)/event(s) recurred: </xsl:text>
							    								<xsl:value-of select="drugrecuractionmeddraversion" />
							    								</xsl:if>
							    							</fo:block>
							    						</fo:table-cell>
							    						<fo:table-cell>
							    							<fo:block>
							    								<xsl:if test="drugrecuraction[.!='']">
							    								<xsl:text>If yes, which reaction(s)/event(s) recurred? </xsl:text>
							    								<xsl:value-of select="drugrecuraction" />
							    								</xsl:if>
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
			    	<xsl:if test="drugreactionrelatedness[.!='']">
			    	<fo:table-row>
			    		<fo:table-cell number-columns-spanned="2">
		    				<fo:table margin="3pt">
				    				<fo:table-body>			    		
							    		<fo:table-row>
							    			<fo:table-cell  font-weight="bold" background-color="#DADADA" number-columns-spanned="2">
							    				<fo:block><xsl:text>B.4.k.18 Relatedness of drug to reaction(s)/event(s)</xsl:text></fo:block>
							    			</fo:table-cell>    			
							    		</fo:table-row>
							    		<xsl:for-each select="/ichicsr/safetyreport/patient/drugreactionrelatedness">
							    		<fo:table-row>
							    			<fo:table-cell  number-columns-spanned="2">
							    				<fo:table border-width="1pt" border-style="solid" border-color="#CCCCCC">
							    				<fo:table-body>
							    					<fo:table-row>
							    						<fo:table-cell>
							    							<fo:block>
							    								<xsl:if test="drugreactionassesmeddraversion[.!='']">
							    								<xsl:text>MedDRA version for Reaction assessed: </xsl:text>
							    								<xsl:value-of select="drugreactionassesmeddraversion" />
							    								</xsl:if>
							    							</fo:block>
							    						</fo:table-cell>
							    						<fo:table-cell>
							    							<fo:block>
							    								<xsl:if test="drugreactionasses[.!='']">
							    								<xsl:text>Reaction assessed: </xsl:text>
							    								<xsl:value-of select="drugreactionasses" />
							    								</xsl:if>
							    							</fo:block>
							    						</fo:table-cell>    			
							    					</fo:table-row>
							    					<fo:table-row>
							    						<fo:table-cell>
							    							<fo:block>
							    								<xsl:if test="drugassessmentsource[.!='']">
							    								<xsl:text>Source of assessment: </xsl:text>
							    								<xsl:value-of select="drugassessmentsource" />
							    								</xsl:if>
							    							</fo:block>
							    						</fo:table-cell>
							    						<fo:table-cell>
							    							<fo:block>
							    								<xsl:if test="drugassessmentmethod[.!='']">
							    								<xsl:text>Method of assessment: </xsl:text>
							    								<xsl:value-of select="drugassessmentmethod" />
							    								</xsl:if>
							    							</fo:block>
							    						</fo:table-cell>    			
							    					</fo:table-row>
							    					<fo:table-row>
							    						<fo:table-cell number-columns-spanned="2">
							    							<fo:block>
							    								<xsl:if test="drugresult[.!='']">
							    								<xsl:text>Result: </xsl:text>
							    								<xsl:value-of select="drugresult" />
							    								</xsl:if>
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
			    	</fo:table-body>
			    </fo:table>
			    </fo:table-cell>
			</fo:table-row>
    		</xsl:for-each>    		
    	</fo:table-body>
    	</fo:table>
    </fo:block>
    </xsl:if>
     <xsl:apply-templates select="summary"/>    		
</xsl:template>

<xsl:template match="patientdeath"> 
	<xsl:apply-templates/>	
	<fo:block font-size="10pt" font-family="Arial,Times">
		<fo:table margin="3pt">
		<fo:table-body>
			<fo:table-row font-weight="bold" background-color="#DADADA">
				<fo:table-cell number-columns-spanned="2">
					<fo:block><xsl:text>B.1.9 In Case of Death</xsl:text>    				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<fo:table-row>
	    		<fo:table-cell>
    				<xsl:if test="patientdeathdate[.!='']">
    				<fo:block>
    					<xsl:text>Date of Death: </xsl:text>
    					<xsl:choose>
				            <xsl:when test="patientdeathdateformat = 102 ">
				            	<xsl:variable name="patientdeathdate" select="patientdeathdate"/>  
							           <xsl:value-of select="concat(substring($patientdeathdate, 7,
																 2),'-')"/>
																 <xsl:choose>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($patientdeathdate, 1,
																 4))"/>	
							</xsl:when> 	
					  		<xsl:when test="patientdeathdateformat = 610 ">	
					  		<xsl:variable name="patientdeathdate" select="patientdeathdate"/>  						         
																 <xsl:choose>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($patientdeathdate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($patientdeathdate, 1,
																 4))"/>	
							</xsl:when> 
							<xsl:when test="patientdeathdateformat = 602 ">
							           <xsl:variable name="patientdeathdate" select="patientdeathdate"/>               
							 		   <xsl:value-of select="substring($patientdeathdate, 1, 4)"/>
							</xsl:when>
							<xsl:otherwise>		            
			                </xsl:otherwise>
						</xsl:choose>		    					
    				</fo:block>
    				</xsl:if>
    			</fo:table-cell>    			
    			<fo:table-cell>
    				<xsl:if test="patientautopsyyesno[.!='']">
    				<fo:block>
    					<xsl:text>Autopsy: </xsl:text>
    					<xsl:choose>           
							<xsl:when test="patientautopsyyesno =1">
								           Yes
							</xsl:when> 	
						  	<xsl:when test="patientautopsyyesno = 2">
								          No
							</xsl:when> 
							<xsl:when test="patientautopsyyesno = 3">
								          Unknown
							</xsl:when> 
						    <xsl:otherwise>		            
	          				</xsl:otherwise>
						</xsl:choose>		    					
    				</fo:block>
    				</xsl:if>
    			</fo:table-cell>    				    	
	    	</fo:table-row>
	    	<xsl:if test="patientdeathcause[.!='']">
	    		<fo:table-row>
    			<fo:table-cell number-columns-spanned="2">
    				<fo:table margin="3pt">
		    				<fo:table-body>			    		
					    		<fo:table-row>
					    			<fo:table-cell  font-weight="bold" background-color="#DADADA" number-columns-spanned="2">
					    				<fo:block><xsl:text>B.1.9.2 Patient's Death Cause</xsl:text></fo:block>
					    			</fo:table-cell>    			
					    		</fo:table-row>
					    		<xsl:for-each select="/ichicsr/safetyreport/patient/patientdeathcause">
					    		<fo:table-row>
					    			<fo:table-cell  number-columns-spanned="2">
					    				<fo:table border-width="1pt" border-style="solid" border-color="#CCCCCC">
					    				<fo:table-body>
					    					<fo:table-row>
					    						<fo:table-cell>
					    							<fo:block>
					    								<xsl:if test="patientdeathreportmeddraversion[.!='']">
					    								<xsl:text>MedDRA version for reported cause(s) of death: </xsl:text>
					    								<xsl:value-of select="patientdeathreportmeddraversion" />
					    								</xsl:if>
					    							</fo:block>
					    						</fo:table-cell>
					    						<fo:table-cell>
					    							<fo:block>
					    								<xsl:if test="patientdeathreport[.!='']">	
					    								<xsl:text>Reported cause(s) of death: </xsl:text>
					    								<xsl:value-of select="patientdeathreport" />
					    								</xsl:if>
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
	    	<xsl:if test="patientautopsy[.!='']">
	    		<fo:table-row>
    			<fo:table-cell number-columns-spanned="2">
    				<fo:table margin="3pt">
		    				<fo:table-body>			    		
					    		<fo:table-row>
					    			<fo:table-cell  font-weight="bold" background-color="#DADADA" number-columns-spanned="2">
					    				<fo:block><xsl:text>B.1.9.3 Patient's Autopsy</xsl:text></fo:block>
					    			</fo:table-cell>    			
					    		</fo:table-row>
					    		<xsl:for-each select="/ichicsr/safetyreport/patient/patientautopsy">
					    		<fo:table-row>
					    			<fo:table-cell  number-columns-spanned="2">
					    				<fo:table border-width="1pt" border-style="solid" border-color="#CCCCCC">
					    				<fo:table-body>
					    					<fo:table-row>
					    						<fo:table-cell>
					    							<fo:block>
					    								<xsl:if test="patientdetermautopsmeddraversion[.!='']">
					    								<xsl:text>MedDRA version for autopsy-determined cause(s) of death: </xsl:text>
					    								<xsl:value-of select="patientdetermautopsmeddraversion" />
					    								</xsl:if>
					    							</fo:block>
					    						</fo:table-cell>
					    						<fo:table-cell>
					    							<fo:block>
					    								<xsl:if test="patientdetermineautopsy[.!='']">		
					    								<xsl:text>Autopsy-determined cause(s) of death: </xsl:text>
					    								<xsl:value-of select="patientdetermineautopsy" />
					    								</xsl:if>
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
		</fo:table-body>
		</fo:table>
	</fo:block>	
</xsl:template>		

<xsl:template match="parent"> 
	<xsl:apply-templates/>
	 <!-- Parent information-->
	 <fo:block font-size="10pt" font-family="Arial,Times">
		<fo:table margin="3pt">
		<fo:table-body>
			<fo:table-row font-weight="bold" background-color="#DADADA">
				<fo:table-cell number-columns-spanned="2">
					<fo:block><xsl:text>B.1.10 For a parent-child/fetus report, information concerning the parent</xsl:text>    				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<xsl:if test="parentidentification[.!='']">
			<fo:table-row>
	    		<fo:table-cell number-columns-spanned="2">
    				<fo:block>
    					<xsl:text>Initials: </xsl:text>
    					<xsl:value-of select="parentidentification" /> 	    					
    				</fo:block>
    			</fo:table-cell>    					    	
	    	</fo:table-row>
	    	</xsl:if>
	    	<xsl:if test="parentbirthdate[.!='']">
	    		<fo:table-row>
	    		<fo:table-cell>
    				<fo:block>
    					<xsl:if test="parentbirthdate[.!='']">
    					<xsl:text>Birth Date: </xsl:text>
    					<xsl:variable name="parentbirthdate" select="parentbirthdate"/>               
						  <xsl:value-of select="concat(substring($parentbirthdate, 7,
																 2),'-')"/>
							 <xsl:choose>
								 <xsl:when test="substring($parentbirthdate, 5, 2) = 01">JAN</xsl:when>
								 <xsl:when test="substring($parentbirthdate, 5, 2) = 02">FEB</xsl:when>
								 <xsl:when test="substring($parentbirthdate, 5, 2) = 03">MAR</xsl:when>
								 <xsl:when test="substring($parentbirthdate, 5, 2) = 04">APR</xsl:when>
								 <xsl:when test="substring($parentbirthdate, 5, 2) = 05">MAY</xsl:when>
								 <xsl:when test="substring($parentbirthdate, 5, 2) = 06">JUN</xsl:when>
								 <xsl:when test="substring($parentbirthdate, 5, 2) = 07">JUL</xsl:when>
								 <xsl:when test="substring($parentbirthdate, 5, 2) = 08">AUG</xsl:when>
								 <xsl:when test="substring($parentbirthdate, 5, 2) = 09">SEP</xsl:when>
								 <xsl:when test="substring($parentbirthdate, 5, 2) = 10">OCT</xsl:when>
								 <xsl:when test="substring($parentbirthdate, 5, 2) = 11">NOV</xsl:when>
								 <xsl:when test="substring($parentbirthdate, 5, 2) = 12">DEC</xsl:when>
							 </xsl:choose>
							 <xsl:value-of select="concat('-',substring($parentbirthdate, 1,
							 4))"/>	
						</xsl:if>	  	    					
    				</fo:block>
    			</fo:table-cell> 
    			<fo:table-cell>
    				<fo:block>
    					<xsl:if test="parentage[.!='']">
    					<xsl:text>Age: </xsl:text>
    					<xsl:value-of select="parentage" />
	                	<xsl:text> </xsl:text>
	         			<xsl:choose>           
							<xsl:when test="  parentageunit = 801 ">
								   Year(s)
							</xsl:when>
							<xsl:otherwise>		            
	          				</xsl:otherwise>
						</xsl:choose>
    					</xsl:if> 	    					
    				</fo:block>
    			</fo:table-cell>    					    	
	    	</fo:table-row>
	    	</xsl:if>
	    	<xsl:if test="parentlastmenstrualdate[.!='']">
	    	<fo:table-row>
	    		<fo:table-cell number-columns-spanned="2">
    				<fo:block>
    					<xsl:text>Last Menstrual Date: </xsl:text>
    					<xsl:variable name="parentlastmenstrualdate" select="  parentlastmenstrualdate"/>               
					  	<xsl:value-of select="concat(substring($parentlastmenstrualdate, 7,
																 2),'-')"/>
							 <xsl:choose>
								 <xsl:when test="substring($parentlastmenstrualdate, 5, 2) = 01">JAN</xsl:when>
								 <xsl:when test="substring($parentlastmenstrualdate, 5, 2) = 02">FEB</xsl:when>
								 <xsl:when test="substring($parentlastmenstrualdate, 5, 2) = 03">MAR</xsl:when>
								 <xsl:when test="substring($parentlastmenstrualdate, 5, 2) = 04">APR</xsl:when>
								 <xsl:when test="substring($parentlastmenstrualdate, 5, 2) = 05">MAY</xsl:when>
								 <xsl:when test="substring($parentlastmenstrualdate, 5, 2) = 06">JUN</xsl:when>
								 <xsl:when test="substring($parentlastmenstrualdate, 5, 2) = 07">JUL</xsl:when>
								 <xsl:when test="substring($parentlastmenstrualdate, 5, 2) = 08">AUG</xsl:when>
								 <xsl:when test="substring($parentlastmenstrualdate, 5, 2) = 09">SEP</xsl:when>
								 <xsl:when test="substring($parentlastmenstrualdate, 5, 2) = 10">OCT</xsl:when>
								 <xsl:when test="substring($parentlastmenstrualdate, 5, 2) = 11">NOV</xsl:when>
								 <xsl:when test="substring($parentlastmenstrualdate, 5, 2) = 12">DEC</xsl:when>
							 </xsl:choose>
							 <xsl:value-of select="concat('-',substring($parentlastmenstrualdate, 1,
							 4))"/> 	    					
    				</fo:block>
    			</fo:table-cell>    					    	
	    	</fo:table-row> 
	    	</xsl:if>
	    	<fo:table-row>
	    		<fo:table-cell>
    				<fo:block>
    					<xsl:if test="parentweight[.!='']">
    					<xsl:text>Weight (kg): </xsl:text>
    					<xsl:value-of select="  parentweight" />
    					</xsl:if>		    					
    				</fo:block>
    			</fo:table-cell>    			
    			<fo:table-cell>
    				<fo:block>
    					<xsl:if test="parentheight[.!='']">
    					<xsl:text>Height (cm): </xsl:text>
    					<xsl:value-of select="  parentheight" />
    					</xsl:if>		    					
    				</fo:block>
    			</fo:table-cell>    					    	
	    	</fo:table-row>
	    	<xsl:if test="parentsex[.!='']">	
	    	<fo:table-row>
	    		<fo:table-cell number-columns-spanned="2">
    				<fo:block>
    					<xsl:text>Sex: </xsl:text>
    					<xsl:choose>           
							<xsl:when test="  parentsex = 1 ">
								    Male
							</xsl:when>
							<xsl:when test="  parentsex = 2 ">
								    Female
							</xsl:when>
							<xsl:otherwise>		            
		      				</xsl:otherwise>
						</xsl:choose>    							    					
    				</fo:block>
    			</fo:table-cell>    			   					    	
	    	</fo:table-row>
	    	</xsl:if>
	    	<xsl:if test="parentmedicalrelevanttext[.!='']">  	
	    	<fo:table-row>
	    		<fo:table-cell number-columns-spanned="2">
    				<fo:block>
    					<xsl:text>Text for relevant medical history and concurrent conditions of parent (not including reaction/event): </xsl:text>
    					<xsl:value-of select="  parentmedicalrelevanttext" />   							    					
    				</fo:block>
    			</fo:table-cell>    			   					    	
	    	</fo:table-row>
	    	</xsl:if>    	
		</fo:table-body>
		</fo:table>
		<!--Parent medical history information-->
  		<xsl:if test="parentmedicalhistoryepisode[.!='']">
  		<fo:table margin="3pt">
			<fo:table-body>
				<fo:table-row font-weight="bold" background-color="#DADADA">
					<fo:table-cell number-columns-spanned="3">
						<fo:block><xsl:text>B.1.10.7 Relevant medical history and concurrent conditions</xsl:text>    				
						</fo:block>
					</fo:table-cell>				
				</fo:table-row>
				<xsl:for-each select="/ichicsr/safetyreport/parent/parentmedicalhistoryepisode">
	    		<fo:table-row>
	    			<fo:table-cell  number-columns-spanned="3">
	    				<fo:table border-width="1pt" border-style="solid" border-color="#CCCCCC">
	    				<fo:table-body>
	    					<fo:table-row>
	    						<fo:table-cell>
	    							<fo:block>
	    								<xsl:if test="parentmdepisodemeddraversion[.!='']">
	    								<xsl:text>MedDRA version for Medical History: </xsl:text>
	    								<xsl:value-of select="parentmdepisodemeddraversion" />
	    								</xsl:if>
	    							</fo:block>
	    						</fo:table-cell>
	    						<fo:table-cell number-columns-spanned="2">
	    							<fo:block>
	    								<xsl:if test="parentmedicalepisodename[.!='']">	
	    								<xsl:text>Structured information: </xsl:text>
	    								<xsl:value-of select="parentmedicalepisodename" />
	    								</xsl:if>
	    							</fo:block>
	    						</fo:table-cell>    			
	    					</fo:table-row>
	    					<fo:table-row>
	    						<fo:table-cell>
	    							<fo:block>
	    								<xsl:if test="parentmedicalstartdate[.!='']">
	    								<xsl:text>Medical Start Date: </xsl:text>
	    								<xsl:choose>
							            <xsl:when test="parentmedicalstartdateformat = 102 ">
										           <xsl:variable name="parentmedicalstartdate" select="parentmedicalstartdate"/>               
										 		   <xsl:value-of select="concat(substring($parentmedicalstartdate, 7,
																 2),'-')"/>
																 <xsl:choose>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($parentmedicalstartdate, 1,
																 4))"/>	
										</xsl:when> 	
								  		<xsl:when test="parentmedicalstartdateformat = 610 ">
										           <xsl:variable name="parentmedicalstartdate" select="parentmedicalstartdate"/>
															<xsl:choose>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($parentmedicalstartdate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($parentmedicalstartdate, 1,
																 4))"/>	
										</xsl:when> 
										<xsl:when test="parentmedicalstartdateformat = 602 ">
										           <xsl:variable name="parentmedicalstartdate" select="parentmedicalstartdate"/>               
										 		   <xsl:value-of select="substring($parentmedicalstartdate, 1, 4)"/>
										</xsl:when>
										<xsl:otherwise>		            
						                </xsl:otherwise>
										</xsl:choose>
	    								</xsl:if>
	    							</fo:block>
	    						</fo:table-cell>
	    						<fo:table-cell>
	    							<fo:block>
	    								<xsl:if test="parentmedicalcontinue[.!='']">
	    								<xsl:text>Medical Continuing: </xsl:text>
	    								<xsl:choose>           
											<xsl:when test="parentmedicalcontinue = 1 ">
												           Yes
											</xsl:when>
											<xsl:when test="parentmedicalcontinue = 2 ">
												           No
											</xsl:when>
												<xsl:when test="parentmedicalcontinue = 3 ">
												           Unknown
											</xsl:when>
										</xsl:choose>
	    								</xsl:if>
	    							</fo:block>
	    						</fo:table-cell> 
	    						<fo:table-cell>
	    							<fo:block>
	    								<xsl:if test="parentmedicalenddate[.!='']">
	    								<xsl:text>Medical End Date: </xsl:text>
	    								<xsl:choose>
							            <xsl:when test="parentmedicalenddateformat = 102 ">
										           <xsl:variable name="parentmedicalenddate" select="parentmedicalenddate"/>               
										 		   <xsl:value-of select="concat(substring($parentmedicalenddate, 7,
																 2),'-')"/>
																 <xsl:choose>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($parentmedicalenddate, 1,
																 4))"/>	
										</xsl:when> 	
								  		<xsl:when test="parentmedicalenddateformat = 610 ">
										           <xsl:variable name="parentmedicalenddate" select="parentmedicalenddate"/>
																 <xsl:choose>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($parentmedicalenddate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($parentmedicalenddate, 1,
																 4))"/>	
										</xsl:when> 
										<xsl:when test="parentmedicalenddateformat = 602 ">
										           <xsl:variable name="parentmedicalenddate" select="parentmedicalenddate"/>               
										 		   <xsl:value-of select="substring($parentmedicalenddate, 1, 4)"/>
										</xsl:when>
										<xsl:otherwise>		            
						                </xsl:otherwise>
										</xsl:choose>
	    								</xsl:if>
	    							</fo:block>
	    						</fo:table-cell>	    						    			
	    					</fo:table-row>
	    					<fo:table-row>
								<fo:table-cell number-columns-spanned="3">
									<fo:block>
										<xsl:if test="parentmedicalcomment[.!='']">
										<xsl:text>Comments: </xsl:text>
										<xsl:value-of select="parentmedicalcomment" />
										</xsl:if>		    					
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
  		</xsl:if>
  		<!-- Parent past drug information-->
  	  	<xsl:if test="parentpastdrugtherapy[.!='']">
  	  	<fo:table margin="3pt">
			<fo:table-body>
				<fo:table-row font-weight="bold" background-color="#DADADA">
					<fo:table-cell number-columns-spanned="3">
						<fo:block><xsl:text>B.1.10.8 Relevant past drug history of Parent</xsl:text>    				
						</fo:block>
					</fo:table-cell>				
				</fo:table-row>
				<xsl:for-each select="/ichicsr/safetyreport/parent/parentpastdrugtherapy">
	    		<fo:table-row>
	    			<fo:table-cell  number-columns-spanned="3">
	    				<fo:table border-width="1pt" border-style="solid" border-color="#CCCCCC">
	    				<fo:table-body>
	    					<fo:table-row>
	    						<fo:table-cell number-columns-spanned="3">
	    							<fo:block>
	    								<xsl:if test="parentdrugname[.!='']">
	    								<xsl:text>Name of Drug as reported: </xsl:text>
	    								<xsl:value-of select="parentdrugname" />
	    								</xsl:if>
	    							</fo:block>
	    						</fo:table-cell>   			
	    					</fo:table-row>
	    					<fo:table-row>
	    						<fo:table-cell>
	    							<fo:block>
	    								<xsl:if test="parentdrugstartdate[.!='']">
	    								<xsl:text>Start Date: </xsl:text>
	    								<xsl:choose>
							            <xsl:when test="parentdrugstartdateformat = 102 ">
										           <xsl:variable name="parentdrugstartdate" select="parentdrugstartdate"/>               
										 		   <xsl:value-of select="concat(substring($parentdrugstartdate, 7,
																 2),'-')"/>
																 <xsl:choose>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($parentdrugstartdate, 1,
																 4))"/>	
										</xsl:when> 	
								  		<xsl:when test="parentdrugstartdateformat = 610 ">
										           <xsl:variable name="parentdrugstartdate" select="parentdrugstartdate"/>
																 <xsl:choose>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($parentdrugstartdate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($parentdrugstartdate, 1,
																 4))"/>	
										</xsl:when> 
										<xsl:when test="parentdrugstartdateformat = 602 ">
										           <xsl:variable name="parentdrugstartdate" select="parentdrugstartdate"/>               
										 		   <xsl:value-of select="substring($parentdrugstartdate, 1, 4)"/>
										</xsl:when>
										<xsl:otherwise>		            
						                </xsl:otherwise>
										</xsl:choose>
	    								</xsl:if>
	    							</fo:block>
	    						</fo:table-cell>
	    						<fo:table-cell number-columns-spanned="2">
	    							<fo:block>
	    								<xsl:if test="parentdrugenddate[.!='']">
	    								<xsl:text>End Date: </xsl:text>
	    								<xsl:choose>
							            <xsl:when test="parentdrugenddateformat = 102 ">
										           <xsl:variable name="parentdrugenddate" select="parentdrugenddate"/>               
										 		   <xsl:value-of select="concat(substring($parentdrugenddate, 7,
																 2),'-')"/>
																 <xsl:choose>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($parentdrugenddate, 1,
																 4))"/>	
										</xsl:when> 	
								  		<xsl:when test="parentdrugenddateformat = 610 ">
										           <xsl:variable name="parentdrugenddate" select="parentdrugenddate"/>
																 <xsl:choose>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 01">JAN</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 02">FEB</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 03">MAR</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 04">APR</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 05">MAY</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 06">JUN</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 07">JUL</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 08">AUG</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 09">SEP</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 10">OCT</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 11">NOV</xsl:when>
																	 <xsl:when test="substring($parentdrugenddate, 5, 2) = 12">DEC</xsl:when>
																 </xsl:choose>
																 <xsl:value-of select="concat('-',substring($parentdrugenddate, 1,
																 4))"/>	
										</xsl:when> 
										<xsl:when test="parentdrugenddateformat = 602 ">
										           <xsl:variable name="parentdrugenddate" select="parentdrugenddate"/>               
										 		   <xsl:value-of select="substring($parentdrugenddate, 1, 4)"/>
										</xsl:when>
										<xsl:otherwise>		            
						                </xsl:otherwise>
										</xsl:choose>
	    								</xsl:if>
	    							</fo:block>
	    						</fo:table-cell>  			
	    					</fo:table-row>
	    					<fo:table-row>
								<fo:table-cell>
									<fo:block>
										<xsl:if test="parentdrgindicationmeddraversion[.!='']">
										<xsl:text>MedDRA Version for indication: </xsl:text>
										<xsl:value-of select="parentdrgindicationmeddraversion" />
										</xsl:if>		    					
									</fo:block>
								</fo:table-cell>    			
								<fo:table-cell number-columns-spanned="2">
									<fo:block>
										<xsl:if test="parentdrugindication[.!='']">	
										<xsl:text>Indication: </xsl:text>
										<xsl:value-of select="parentdrugindication" />
										</xsl:if>		    					
									</fo:block>
								</fo:table-cell>    					    	
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block>
										<xsl:if test="parentdrgreactionmeddraversion[.!='']">
										<xsl:text>MedDRA version for reaction: </xsl:text>
										<xsl:value-of select="parentdrgreactionmeddraversion" />
										</xsl:if>		    					
									</fo:block>
								</fo:table-cell>    			
								<fo:table-cell number-columns-spanned="2">
									<fo:block>
										<xsl:if test="parentdrugreaction[.!='']">
										<xsl:text>Reaction: </xsl:text>
										<xsl:value-of select="parentdrugreaction" />
										</xsl:if>		    					
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
  	  	</xsl:if>  		
	</fo:block>	 
</xsl:template>
<!-- Narrative case summary and further information -->
<xsl:template match="summary"> 
	<xsl:apply-templates/>
	 <fo:block font-size="10pt" font-family="Arial,Times">
		<fo:table margin="3pt">
		<fo:table-body>
			<fo:table-row font-weight="bold" background-color="#DADADA">
				<fo:table-cell number-columns-spanned="2">
					<fo:block><xsl:text>B.5 Narrative case summary and further information</xsl:text>    				
					</fo:block>
				</fo:table-cell>				
			</fo:table-row>
			<xsl:if test="narrativeincludeclinical[.!='']"> 
			<fo:table-row>
	    		<fo:table-cell number-columns-spanned="2">
    				<fo:block>
    					<xsl:text>Case narrative including clinical course, therapeutic measures, outcome and additional relevant information: </xsl:text>
    					<xsl:value-of select="narrativeincludeclinical" />	    					
    				</fo:block>
    			</fo:table-cell>    					    	
	    	</fo:table-row>
	    	</xsl:if>
	    	 <xsl:if test="reportercomment[.!='']"> 
			<fo:table-row>
	    		<fo:table-cell number-columns-spanned="2">
    				<fo:block>
    					<xsl:text>Reporter's comments: </xsl:text>
    					<xsl:value-of select="reportercomment" />	    					
    				</fo:block>
    			</fo:table-cell>    					    	
	    	</fo:table-row>
	    	</xsl:if>
	    	<xsl:if test="senderdiagnosismeddraversion[.!='']"> 
			<fo:table-row>
	    		<fo:table-cell number-columns-spanned="2">
    				<fo:block>
    					<xsl:text>MedDRA Version for Sender's diagnosis: </xsl:text>
    					<xsl:value-of select="senderdiagnosismeddraversion" />	    					
    				</fo:block>
    			</fo:table-cell>    					    	
	    	</fo:table-row>
	    	</xsl:if>
	    	<xsl:if test="senderdiagnosis[.!='']">
	    	<fo:table-row>
	    		<fo:table-cell number-columns-spanned="2">
    				<fo:block>
    					<xsl:text>Sender's diagnosis/syndrome and/or reclassification of reaction/event: </xsl:text>
    					<xsl:value-of select="senderdiagnosis" />	    					
    				</fo:block>
    			</fo:table-cell>    					    	
	    	</fo:table-row>
	    	</xsl:if>
	    	<xsl:if test="sendercomment[.!='']">
	    	<fo:table-row>
	    		<fo:table-cell number-columns-spanned="2">
    				<fo:block>
    					<xsl:text>Sender's comments: </xsl:text>
    					<xsl:value-of select="sendercomment" />	    					
    				</fo:block>
    			</fo:table-cell>    					    	
	    	</fo:table-row>
	    	</xsl:if>
	    </fo:table-body>
	   </fo:table>
	  </fo:block>  	
</xsl:template>		


</xsl:stylesheet>