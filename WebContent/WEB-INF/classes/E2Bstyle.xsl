<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:output method = "html" indent = "yes"/>
	
    <xsl:template match="/">
    <html>
      <head>      	
      	<style>       	
      		.TR
			{
			    font-family: Arial;
			    font-size: 12px;
			    background-color:#DADADA;
			    font-weight:bold;
			}     	
			  			
      		td
			{
			    font-family: Arial, Times;
			    font-size: 12px;
			    color: #737373;
			}
      	</style>       
      </head>
      <body> 
      <xsl:apply-templates />      
     </body>
    </html>
</xsl:template>

<!-- Message header details-->  
<xsl:template match="ichicsrmessageheader">
      <table border="0" cellspacing="0" cellpadding="2" width="100%"> 
          <tr class="TR">          	
          	  <td width="1%"></td>
              <td>
                <b>M.1 Message Header Details </b>
              </td>
          </tr>
          
          <xsl:if test="messagetype[.!='']">
	          <tr>          	
	          	<td width="1%"></td>
	            <td>
	            	<b> Message Type: </b><xsl:value-of select="messagetype" />                
	            </td>
	          </tr>
          </xsl:if>
          
          <xsl:if test="messageformatversion[.!='']">
	          <tr>
	          	<td width="1%"></td>
	          	<td>
	          		<b> Message Format Version: </b><xsl:value-of select="messageformatversion" />                
	            </td>
	          </tr>
          </xsl:if>
          
          <xsl:if test="messageformatrelease[.!='']">
          	  <tr>
            	 <td width="1%"></td>
              	 <td>
              	 	<b> Message Format Release: </b><xsl:value-of select="messageformatrelease" />                
              	 </td>
              </tr>
          </xsl:if>
         
        
         	<xsl:if test="messagenumb[.!='']">
         		 <tr>
	            	<td width="1%"></td>
	              	<td>              	
	                	<b> Message Number: </b><xsl:value-of select="messagenumb" />                
	              	</td>
           		 </tr>
         	</xsl:if>                  
                
         <xsl:if test="messagesenderidentifier[.!='']">
         	<tr>
         		<td width="1%"></td>
              	<td>              
	                <b> Message Sender Identifier: </b>
	                <xsl:value-of select="messagesenderidentifier" />                
              	</td>
            </tr>
         </xsl:if>
         
         <xsl:if test="messagereceiveridentifier[.!='']">
         	<tr>
         		<td width="1%"></td>
              	<td>              	
	                <b> Message Receiver Identifier: </b>
	                <xsl:value-of select="messagereceiveridentifier" />                 
              	</td>
            </tr>         
         </xsl:if>
         
         <xsl:if test="messagedate[.!='']">  
            <tr>
            	<td width="1%"></td>
              	<td>              	
	                <b> Message Date: </b>                
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
              	</td>              
            </tr>          
         </xsl:if>
      </table>
      <br />
</xsl:template>

<!-- Safety Report details-->
<xsl:template match="safetyreport"> 	
      <table border="0" cellpadding="2" cellspacing="0" width="100%">      	         
      	<tr class="TR">      		
      		<td width="1%"></td>
      		<td colspan="2">
      			<b>A.1 Safety Report Details </b>               
          	</td>
        </tr>
        
        <xsl:if test="safetyreportversion[.!='']">
	        <tr>
	        	<td width="1%"></td>
	          	<td colspan="2">          	
		            <b> Safety Report Version: </b>
		            <xsl:value-of select="safetyreportversion" />            
	          	</td>
	        </tr>
        </xsl:if>
        
        <xsl:if test="safetyreportid[.!='']">
	        <tr>
	        	<td width="1%"></td>
	          	<td colspan="2">          	
		            <b> Safety Report Identifer: </b>
		            <xsl:value-of select="safetyreportid" />            
	          	</td>
	        </tr>
        </xsl:if>
        
        <tr>
        	<td width="1%"></td>
        	<xsl:if test="primarysourcecountry[.!='']">
        		<td width="50%">
        			<b> Country of Primary Source: </b>
        			<xsl:value-of select="primarysourcecountry" />
        		</td>  
        	</xsl:if>
        	<xsl:if test="occurcountry[.!='']"> 
        		<td width="50%">
        			<b> Country where Event occured: </b>
            		<xsl:value-of select="occurcountry" />
          		</td>
          	</xsl:if> 
        </tr>
        
        <xsl:if test="transmissiondate[.!='']">
	        <tr>
	        	<td width="1%"></td>        	                      
          		<td>
            		<b> Transmission Date: </b>               
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
          		</td>           	
        	</tr>
        </xsl:if>
        
        <xsl:if test="reporttype[.!='']"> 
	        <tr>        	
	        	<td width="1%"></td>
	        	<td colspan="2">              	
	        		<b> Report Type: </b>               
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
	      		</td>          	
	        </tr>
        </xsl:if>
        
        <xsl:if test="serious[.!='']">	
        <tr>
        	<td width="1%"></td>
        	<td colspan="2">
            	<b> Serious: </b>               
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
	         </td>          	
        </tr>
        </xsl:if>
        
        <xsl:if test="seriousnessdeath[.!='']">
	        <tr>
	        	<td width="1%"></td>
	        	<td colspan="2">
		            <b> Seriousness - Results in death: </b>               
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
			    </td>         
	        </tr>
        </xsl:if>
        
        <xsl:if test="seriousnesslifethreatening[.!='']">
	        <tr>
	        	<td width="1%"></td>
	        	<td colspan="2">
		            <b> Seriousness - Life Threatening: </b>               
		            <xsl:choose>
		            	<xsl:when test="seriousnesslifethreatening = 1">
		            		Yes
				        </xsl:when> 
				        <xsl:when test="seriousnesslifethreatening = 2">
				            No
				        </xsl:when> 		          		          		          		                  
				        <xsl:otherwise>		            
				        </xsl:otherwise>
			        </xsl:choose>
		       </td>          
	        </tr>
        </xsl:if>
        
        <xsl:if test="seriousnesshospitalization[.!='']">
	        <tr>
	        	<td width="1%"></td>
	        	<td colspan="2">
		            <b> Seriousness - Caused/Prolonged Hospitalization: </b>               
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
		         </td>	        
	        </tr>
        </xsl:if>
        
        <xsl:if test="seriousnessdisabling[.!='']">
	        <tr> 
	        	<td width="1%"></td>
		        <td colspan="2">
		            <b> Seriousness - Disabling/Incapacitating : </b>                
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
		        </td>          
	        </tr>
        </xsl:if>
        
        <xsl:if test="seriousnesscongenitalanomali[.!='']">
	        <tr>
	        	<td width="1%"></td>
	          	<td colspan="2">
		            <b> Seriousness - Congenital anomaly/birth defect : </b>               
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
	          </td>
	        </tr>
        </xsl:if>
        
        <xsl:if test="seriousnessother[.!='']">
        <tr>
        	<td width="1%"></td>
          	<td colspan="2">
	            <b> Seriousness - Others: </b>               
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
          	</td>
        </tr>
        </xsl:if>
                
        <tr>
        	<td width="1%"></td>
        	<xsl:if test="receivedate[.!='']">                     
	          	<td>          		
		            <b> Date Report was first received from source: </b>               
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
		         </td>
	         </xsl:if>
	         
	         <xsl:if test="receiptdate[.!='']">           
		         <td>	         	
		            <b> Date of receipt of the most recent information for this report: </b>                            
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
		         </td>
	         </xsl:if>
        </tr>
        
        <xsl:if test="additionaldocument[.!='']">
        <tr>
        	<td height="15px" />
        </tr>
        
        <tr>
        	<td width="1%"></td>
          	<td colspan="2">
          		<b><u>Additional Available documents held by sender</u></b>
          	</td>
        </tr>        
        
        <tr>
        	<td width="1%"></td>
          	<td colspan="2">
	            <b> Are additional documents available: </b>               
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
          	</td>
        </tr>
        </xsl:if>
        
        <xsl:if test="documentlist[.!='']">
	        <tr>
	        	<td width="1%" /> 
	        	<td colspan="2">
	        		<b> List of documents held by sender: </b>
	       			<xsl:value-of select="documentlist" />
	            </td>       
	        </tr>
        </xsl:if>
        
        <xsl:if test="fulfillexpeditecriteria[.!='']">
	        <tr>
	         	<td width="1%"></td>
	          	<td colspan="2">
	            	<b> Does this case fulfill the local criteria for an expedited report?: </b>               
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
	          	</td>
	        </tr>
        </xsl:if>
    </table>
            
    <!-- Worldwide unique case details-->    
    <br />
    <table border="0" cellspacing="0" cellpadding="2" width="100%">
    	
    	<xsl:if test="authoritynumb[.!=''] or companynumb[.!='']">
	    	<tr>
	    		<td width="1%" />
	    		<td colspan="3">
				     <b><u>Worldwide unique case identification number</u></b>               
				</td>
			</tr>
		</xsl:if>	
		
			<xsl:if test="authoritynumb[.!='']">			
			    <tr>
			    	<td width="1%"></td>
				    <td colspan="3">
				        <b>Regulatory authority's case report number: </b>
				        <xsl:value-of select="authoritynumb" />
				    </td>
			    </tr>
			</xsl:if>	
		
			<xsl:if test="companynumb[.!='']">
			    <tr>
			    	<td width="1%"></td>
		     	 	<td colspan="3">
				        <b>Other sender's case report number: </b>
				        <xsl:value-of select="companynumb" />
			      	</td>
			    </tr>
			</xsl:if>
		
		
		<xsl:if test="duplicate[.!='']">
		    <tr>
		    	<td width="1%"></td>
		        <td colspan="3">
			        <b>Other case identifiers in previous transmissions: </b>               
			        <xsl:choose>			        	
			        	<xsl:when test="duplicate = 1">
			        		Yes
			        	</xsl:when>
			        </xsl:choose>
		     	</td>
		    </tr>
		 </xsl:if>
	     
	    <xsl:if test="reportduplicate[.!='']"> 
	      <tr>
	     	<td height="15px" />
	     </tr>
	     <tr>
	     	<td colspan="4">
	     		<table border="1" cellpadding="2" cellspacing="0" width="100%">
	     			<tr class="TR">
	     				<td colspan="2">
	     					<b>A.1.11 Report duplicate</b>
		    			</td>
		    		</tr>
		    		
		    		<xsl:for-each select="reportduplicate">		    			
				        <tr>			            
			            	<td>
			            		<table border="0" cellpadding="2" cellspacing="0" width="100%">
			            			<xsl:if test="duplicatesource[.!='']">
				            			<tr>
				            				<td width="1%"></td>
				            				<td>
				            					<b>Source(s) of the case identifier: </b>
				            					<xsl:value-of select="duplicatesource" />
				            				</td>
				            			</tr>
			            			</xsl:if>
			            			
			            			<xsl:if test="duplicatenumb[.!='']">
				            			<tr>
				            				<td width="1%"></td>
				            				<td>
				            					<b>Case identifiers: </b>
				            					<xsl:value-of select="duplicatenumb" />
				            				</td>
				            		    </tr>
			            		    </xsl:if>
			            		</table>
			            	</td>
			            </tr> 
			        </xsl:for-each>
		    	</table>
		    </td>            		
		</tr>		
		</xsl:if>
						
		<xsl:if test="linkedreport/linkreportnumb[.!='']">
		<tr><td height="10px"></td></tr>
		<tr>
	    	<td colspan="4">
	    		<table border="1" cellpadding="2" cellspacing="0" width="100%">
	    			<tr class="TR">		    				
	    				<td colspan="2">
	    					<b>A.1.12 Linked Report</b>
	    				</td>
	    			</tr>
	    			
		    			<xsl:for-each select="linkedreport">
				            <tr>			            
				            	<td>
				            		<table border="0" cellpadding="2" cellspacing="0" width="100%">
				            			<tr>
				            				<td width="1%"></td>
				            				<td>
			            						<b>Identification number of the report which is linked to this report: </b>
		        								<xsl:value-of select="linkreportnumb" />
				            				</td>
				            			</tr>			            			
				            		</table>
				            	</td>
				            </tr> 
			            </xsl:for-each>		            
	    		</table>
	    	</td>            		
		 </tr> 
		 </xsl:if>
		 
		 <tr><td height="10px"></td></tr>
		 
		 <tr>
	    	 <td width="1%"></td>
	    	 
	    	 <xsl:if test="casenullification[.!='']">
		      	 <td width="50%">
		        	<b>Report nullification: </b>               
		         	<xsl:choose>
		          		<xsl:when test="casenullification = 1">
		           		 	Yes
		          		</xsl:when>        		          		          		                  
		          		<xsl:otherwise>		            
		          		</xsl:otherwise>
		        	</xsl:choose>               
		      	  </td> 
	        </xsl:if>
	        
	        <xsl:if test="nullificationreason[.!='']">		          
		        <td width="50%">
		        	<b>Reason for nullification: </b>
		        	<xsl:value-of select="nullificationreason" />
		      	</td>
	      	</xsl:if>
	    </tr>
	    
	    <xsl:if test="medicallyconfirm[.!='']">
		    <tr>
		    	<td width="1%"></td>
	      	    <td colspan="2">
	      	    	<b>Was the case medically confirmed, if not initially from health professional?: </b>                
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
	      		</td>
			</tr>
		</xsl:if>
	</table>
		
	<br />	
    <xsl:apply-templates select="primarysource"/>
    <br />
    <xsl:apply-templates select="sender"/>
    <br /> 
    <xsl:apply-templates select="receiver"/>
    <br />
    <xsl:apply-templates select="patient"/>
    <br /> 
     
</xsl:template>

<!-- Primary source of information-->
<xsl:template match="primarysource">
      <table border="0" cellpadding="2" cellspacing="0" width="100%">
        	<tr class="TR">        		
        		<td width="1%" />
         		<td colspan="3">
            		<b>A.2 Primary Source of information </b>               
          		</td>
       		</tr>
       		
       		<xsl:if test="reportertitle[.!='']">
	        	<tr>
			          <td width="1%" />
			          <td colspan="3">
				            <b>Reporter Title: </b>
				            <xsl:value-of select="reportertitle" />
			          </td>
	        	</tr>
        	</xsl:if>
        	
       		<tr>
       			<td width="1%" />
       			
       			<xsl:if test="reportergivename[.!='']">
			        <td width="33%">
			            <b>Reporter given name: </b>
			            <xsl:value-of select="reportergivename" />
			        </td>
			    </xsl:if>
			    
			    <xsl:if test="reportermiddlename[.!='']">      
			        <td width="33%">
			            <b>Reporter middle name: </b>
			            <xsl:value-of select="reportermiddlename" />
			        </td>
		        </xsl:if>
		        
		        <xsl:if test="reporterfamilyname[.!='']">          
			        <td width="33%">
			            <b>Reporter family name: </b>
			            <xsl:value-of select="reporterfamilyname" />
			        </td>
		        </xsl:if>
        	</tr>
        	
	        <tr>
	        	<td width="1%" />
	        	
	        	<xsl:if test="reporterorganization[.!='']">
			        <td>
			            <b>Reporter organization: </b>
			            <xsl:value-of select="reporterorganization" />
			        </td>
		        </xsl:if>
		         
		        <td></td>
		        
		        <xsl:if test="reporterdepartment[.!='']">          
			        <td>
			            <b>Reporter department: </b>
			            <xsl:value-of select="reporterdepartment" />
			        </td>
		        </xsl:if>		         
	        </tr>
	        
	        <xsl:if test="reporterstreet[.!='']">
		        <tr>
		         	<td width="1%" />
			        <td colspan="3">
			            <b>Reporter street: </b>
			            <xsl:value-of select="reporterstreet" />
			        </td>
		        </tr>
	        </xsl:if>
	        
	        <tr>
	        	<td width="1%" />
	        	
	        	<xsl:if test="reportercity[.!='']">
			        <td>
			            <b>Reporter city: </b>
			            <xsl:value-of select="reportercity" />
			        </td>
		        </xsl:if>
		        
		        <td></td>
		        
		        <xsl:if test="reporterstate[.!='']">              
			        <td>
			            <b>Reporter state or province : </b>
			            <xsl:value-of select="reporterstate" />
			        </td>
		        </xsl:if>
	        </tr>
	        
	        <tr>
	        	<td width="1%" />
	        	
	        	<xsl:if test="reporterpostcode[.!='']"> 
			        <td>
			            <b>Reporter postcode: </b>
			            <xsl:value-of select="reporterpostcode" />
			        </td> 
		        </xsl:if> 
		         
		        <td></td>
		        
		        <xsl:if test="reportercountry[.!='']">            
			        <td>
			            <b>Reporter country code: </b>
			            <xsl:value-of select="reportercountry" />
			        </td>
		        </xsl:if>
	        </tr>
	        
	        <xsl:if test="qualification[.!='']"> 
		        <tr>
		        	<td width="1%" />
			        <td colspan="3">
			            <b>Qualification: </b> 
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
			         </td>
		        </tr>
	        </xsl:if>
	        
	        <xsl:if test="literaturereference[.!='']">
		        <tr>
			          <td width="1%" />
			          <td colspan="3">
			            	<b>Literature reference(s): </b>
			            	<xsl:value-of select="literaturereference" />
			          </td>
		        </tr>
	        </xsl:if>
      </table>

      <table border="0" cellpadding="2" cellspacing="0" width="100%">      	
	       <xsl:if test="studyname[.!=''] or sponsorstudynumb[.!=''] or observestudytype[.!='']">
	          <tr>
	          	<td height="15px" />
	          </tr>
	          <tr>
	          	<td width="1%" />
	          	<td>
	          		<b><u>Study identification:</u></b>              
		        </td>
	          </tr>
	        </xsl:if>
	        
	          <xsl:if test="studyname[.!='']">
		          <tr>	          	
		          	<td width="1%" />
			        <td>
			            <b>Study name: </b>
			            <xsl:value-of select="studyname" />
			        </td>
		          </tr>
	          </xsl:if>
	          
	          <xsl:if test="sponsorstudynumb[.!='']">
	          	<tr>
	         	  <td width="1%" />
		          <td>
		            <b>Sponsor study number: </b>
		            <xsl:value-of select="sponsorstudynumb" />
		          </td>
	         	</tr>
	           </xsl:if>
	           
	           <xsl:if test="observestudytype[.!='']">	         
		          <tr>
	          		<td width="1%" />
	          		<td>
			            <b>Study type in which the reaction(s)/event(s) were observed: </b>                
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
		           </td>
		       	  </tr>
		        </xsl:if>
        </table>     
</xsl:template>

<!-- Sender information-->
<xsl:template match="sender">
      <table border="0" cellpadding="2" cellspacing="0" width="100%">             
         <tr class="TR">         	
         	<td width="1%" />
          	<td colspan="3">
            	<b>A.3.1 Information on Sender of Case Safety Report </b>              
            </td>
         </tr>
         
         <xsl:if test="sendertype[.!='']">
		     <tr>
		     	<td width="1%" />
			    <td colspan="3">
			         <b>Type: </b>               
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
			     </td>
		    </tr>
		 </xsl:if>		 
		 
         <tr>
         	<td width="1%" />
         	
         	<xsl:if test="senderorganization[.!='']">
	        <td width="33%">
	            <b>Organization: </b>
	            <xsl:value-of select="senderorganization" />
	        </td> 
	        </xsl:if>	
	          
	        <td></td>
	        
	        <xsl:if test="senderdepartment[.!='']">        
		        <td width="33%">
		            <b>Department: </b>
		            <xsl:value-of select="senderdepartment" />
		        </td>
	        </xsl:if>
        </tr>
        
        <xsl:if test="sendertitle[.!='']">
	        <tr>
	        	<td width="1%" />
		        <td colspan="3">
		            <b>Title: </b>
		            <xsl:value-of select="sendertitle" />
		        </td> 
	        </tr>
        </xsl:if>
        
        <tr> 
        	<td width="1%" />
        	
        	<xsl:if test="sendergivename[.!='']">        	        
	          <td>
	            <b>Given name: </b>
	            <xsl:value-of select="sendergivename" />  
	          </td>
	        </xsl:if>
	        
	     	<xsl:if test="sendermiddlename[.!='']">             
	          <td>
	            <b>Middle name: </b>
	            <xsl:value-of select="sendermiddlename" />
	          </td>
	        </xsl:if>
	        
	        <xsl:if test="senderfamilyname[.!='']">            
	          <td>
	            <b>Family name: </b>
	            <xsl:value-of select="senderfamilyname" />
	          </td>
	        </xsl:if>
        </tr>
        
        <xsl:if test="senderstreetaddress[.!='']">   
	        <tr>
         	  <td width="1%" />
         	  <td colspan="3">
	            <b>Street address: </b>
	            <xsl:value-of select="senderstreetaddress" />
         	  </td>
	        </tr>
        </xsl:if>
        
        <tr>
      	  <td width="1%" />
      	  
      	  <xsl:if test="sendercity[.!='']"> 
	          <td>
	            <b>City: </b>
	            <xsl:value-of select="sendercity" />
	          </td> 
          </xsl:if>
          
          <td></td>  
          
          <xsl:if test="senderstate[.!='']">       
	          <td>
	            <b>State or Province: </b>
	            <xsl:value-of select="senderstate" />
	          </td>
          </xsl:if>          
        </tr>
        
        <tr>
        	<td width="1%" />
        	
        	<xsl:if test="senderpostcode[.!='']"> 
			    <td>
			         <b>Postcode: </b>
			         <xsl:value-of select="senderpostcode" />
			    </td>
		     </xsl:if>
		        
		     <td></td>
		     
		     <xsl:if test="sendercountrycode[.!='']">		              
			     <td>
			         <b>Country Code: </b>
			         <xsl:value-of select="sendercountrycode" />
			     </td>
		     </xsl:if>
        </tr>
        
        <tr>
        	<td width="1%" />
        	
        	<xsl:if test="sendertel[.!='']">
	          	<td>
		            <b>Telephone: </b>
		            <xsl:value-of select="sendertel" />
	          	</td>
          	</xsl:if>
          	           
	        <xsl:if test="sendertelextension[.!='']">
	        	<td>
		            <b>Telephone extension: </b>
		            <xsl:value-of select="sendertelextension" />
	          	</td>
	        </xsl:if>
	                  
            <xsl:if test="sendertelcountrycode[.!='']">
             	<td>
		            <b>Telephone country code: </b>
		            <xsl:value-of select="sendertelcountrycode" />
                </td>
            </xsl:if>
        </tr>
        
        <tr>
            <td width="1%" />
            
            <xsl:if test="senderfax[.!='']">
	          	<td>
		            <b>Fax: </b>
		            <xsl:value-of select="senderfax" />
	            </td>
            </xsl:if>
            
            <xsl:if test="senderfaxextension[.!='']">           
	            <td>
		            <b>Fax extension: </b>
		            <xsl:value-of select="senderfaxextension" />
	            </td>
            </xsl:if>
            
            <xsl:if test="senderfaxcountrycode[.!='']">          
	            <td>
		            <b>Fax country code: </b>
		            <xsl:value-of select="senderfaxcountrycode" />
	            </td>
            </xsl:if>            
        </tr>
        
        <xsl:if test="senderemailaddress[.!='']">
	        <tr>
	            <td width="1%" />
	            <td colspan="3">
		            <b>E-mail address: </b>
		            <xsl:value-of select="senderemailaddress" />
	            </td>
	        </tr>
        </xsl:if>
    </table>
</xsl:template> 

<!-- Receiver information-->
<xsl:template match="receiver">
      <table border="0" cellpadding="2" cellspacing="0" width="100%">
      	<tr class="TR">      		
			<td width="1%"></td>
      		<td colspan="3">
        		<b>A.3.2 Information on Receiver of Case Safety Report </b>               
      		</td>
        </tr>
        
        <xsl:if test="receivertype[.!='']">
	        <tr>
				<td width="1%"></td>
	            <td colspan="3">
		            <b>Type: </b>               
		             <xsl:choose>
				          <xsl:when test="receivertype = 1">
				            	Pharmaceutical Company
				          </xsl:when> 
				           <xsl:when test="receivertype = 2">
				            	Regulatory Authority
				          </xsl:when> 
				            <xsl:when test="receivertype = 3">
				           		Health professional
				          </xsl:when>
				            <xsl:when test="receivertype = 4">
				           		Regional Pharmacovigilance Center
				          </xsl:when>
				            <xsl:when test="receivertype = 5">
				           		WHO Collaborating Center for International Drug Monitoring
				          </xsl:when>
				            <xsl:when test="receivertype = 6">
				           		Other
				          </xsl:when>
				          <xsl:otherwise>		            
	          			  </xsl:otherwise>
			        </xsl:choose>
	          </td>
	    	</tr>
    	</xsl:if>
    	
    	<tr>
    		<td width="1%" />
    		
    		<xsl:if test="receiverorganization[.!='']">
	     		<td width="33%">
		            <b>Organization: </b>
		            <xsl:value-of select="receiverorganization" />
	        	</td>
        	</xsl:if>
        	
   			<td width="33%"></td>
   			
   			<xsl:if test="receiverdepartment[.!='']">    
	      		<td width="33%">
		            <b>Department: </b>
		            <xsl:value-of select="receiverdepartment" />
	      		</td>
      		</xsl:if>
    	</tr>
    	
    	<xsl:if test="receivertitle[.!='']">
	    	<tr>
				<td width="1%"></td>
	      		<td>
		            <b>Title: </b>
		            <xsl:value-of select="receivertitle" />
	     		</td>
	    	</tr>
    	</xsl:if>
    	
    	<tr>
			<td width="1%"></td>
			
			<xsl:if test="receivergivename[.!='']">
	      		<td>
		            <b>Given name: </b>
		            <xsl:value-of select="receivergivename" /> 
	      		</td>
      		</xsl:if>
      		
      		<xsl:if test="receivermiddlename[.!='']">           
	      		<td>
		            <b>Middle name: </b>
		            <xsl:value-of select="receivermiddlename" /> 
	      		</td>
      		</xsl:if>
      		
      		<xsl:if test="receiverfamilyname[.!='']">
				<td>
		            <b>Family name: </b>
		            <xsl:value-of select="receiverfamilyname" /> 
	      		</td>
      		</xsl:if>      		
    	</tr>
    	
    	<xsl:if test="receiverstreetaddress[.!='']"> 
		    <tr>
				<td width="1%"></td>
		        <td colspan="3">
		            <b>Street address: </b>
		            <xsl:value-of select="receiverstreetaddress" />
		         </td>
		   </tr>
	   </xsl:if>
	   
       <tr>
			<td width="1%"></td>			
			<xsl:if test="receivercity[.!='']"> 
	          <td>
		            <b>City: </b>
		            <xsl:value-of select="receivercity" />
	          </td>
	        </xsl:if>	           
	        <td></td>
	        <xsl:if test="receiverstate[.!='']">          
	          <td>
		            <b>State or Province: </b>
		            <xsl:value-of select="receiverstate" />
	          </td>
	        </xsl:if>
   	   </tr>
   	   
       <tr>       	
			<td width="1%"></td>
			<xsl:if test="receiverpostcode[.!='']"> 
	          <td>
		            <b>Postcode: </b>
		            <xsl:value-of select="receiverpostcode" />
	          </td> 
	        </xsl:if>    
	        <td></td>	           
	        <xsl:if test="receivercountrycode[.!='']">          
	          <td>
		            <b>Country Code: </b>
		            <xsl:value-of select="receivercountrycode" />
	          </td>
	         </xsl:if>	          
    	</tr>
    	
        <tr>
			<td width="1%"></td>
			<xsl:if test="receivertel[.!='']">
	          <td>
		            <b>Telephone: </b>
		            <xsl:value-of select="receivertel" />
	          </td>
	        </xsl:if> 
	        <xsl:if test="receivertelextension[.!='']">          
	          <td>
		            <b>Telephone extension: </b>
		            <xsl:value-of select="receivertelextension" />
	          </td>
	        </xsl:if>
	        <xsl:if test="receivertelcountrycode[.!='']">          
	          <td>
		            <b>Telephone country code: </b>
		            <xsl:value-of select="receivertelcountrycode" />
	          </td>
	        </xsl:if>
    	</tr>    	
    	
        <tr>
			<td width="1%"></td>
			<xsl:if test="receiverfax[.!='']">
	          <td>
		            <b>Fax: </b>
		            <xsl:value-of select="receiverfax" />
		      </td>
		      </xsl:if>
		      <xsl:if test="receiverfaxextension[.!='']">          
	          <td>
		            <b>Fax extension: </b>
		            <xsl:value-of select="receiverfaxextension" />
	          </td>
	          </xsl:if>
	          <xsl:if test="receiverfaxcountrycode[.!='']">          
	          <td>
		            <b>Fax country code: </b>
		            <xsl:value-of select="receiverfaxcountrycode" />
	          </td>
	          </xsl:if>
    	</tr>
    	
    	<xsl:if test="receiveremailaddress[.!='']">
	      	<tr>
				<td width="1%"></td>
	      		<td colspan="3">
		            <b>E-mail address: </b>
		            <xsl:value-of select="receiveremailaddress" />
	      		</td>
	    	</tr>
    	</xsl:if>    	
   </table>
</xsl:template> 
   
<!-- Patient information-->   
<xsl:template match="patient">
      <table border="0" cellpadding="2" cellspacing="0" width="100%">
        	<tr class="TR">
				<td width="1%"></td>
              	<td colspan="3">
                	<b>B.1 Patient Information </b>               
              </td>
            </tr>
            
            <xsl:if test="patientinitial[.!='']">
	            <tr>
					<td width="1%"></td>
	              	<td colspan="3">
		                <b>Initials: </b>
		 				<xsl:value-of select="patientinitial" /> 
	              </td>
	            </tr>
            </xsl:if>
            
			<tr>
				<td width="1%"></td>
				<xsl:if test="patientmedicalrecordnumb[.!='']">
		              <td width="33%">
			                <b>GP Medical Record Number: </b>
			                <xsl:value-of select="patientmedicalrecordnumb" />
		              </td>
		              <td width="33%"></td>
	            </xsl:if>	            
	           		           	  
	           	<xsl:if test="patientspecialistrecordnumb[.!='']">    
		            <td width="33%">
			                <b>Specialist Record Number: </b>
			                <xsl:value-of select="patientspecialistrecordnumb" />
		            </td>
	            </xsl:if>
	        </tr>
	        	        
            <tr>
				 <td width="1%"></td>
				 <xsl:if test="patienthospitalrecordnumb[.!='']">
		              <td>
			                <b>Hospital Record Number: </b>
			                <xsl:value-of select="patienthospitalrecordnumb" />
		              </td>
		              <td></td>
		         </xsl:if>
           		 
           		 <xsl:if test="patientinvestigationnumb[.!='']">    
	                 <td>
			                <b>Investigation Number: </b>
			                <xsl:value-of select="patientinvestigationnumb" />
	                 </td>
	             </xsl:if>
            </tr>
            
            <tr>
				<td width="1%"></td>
				<xsl:if test="patientbirthdate[.!='']">
	                <td>
	                	<b>Birth Date: </b>
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
	                </td>
                </xsl:if>
                <xsl:if test="patientonsetage[.!='']">           
	                <td>
		                <b>Age at time of reaction: </b>
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
	               </td>
               </xsl:if>
               <xsl:if test="gestationperiod[.!='']">            
	              	<td>
			                <b>Gestation Period: </b>
			                <xsl:value-of select="gestationperiod" />
			                <xsl:text> </xsl:text>
			                <xsl:choose>             
			   					<xsl:when test="gestationperiodunit = 802">
					            		Month(s)
					            </xsl:when> 	
	  							<xsl:when test=" gestationperiodunit= 803">
			            				Week(s)
			          			</xsl:when> 		          		          		          		                    	 
			          			<xsl:when test=" gestationperiodunit = 804">
			           					Day(s)
			          			</xsl:when> 	
	   							<xsl:when test=" gestationperiodunit = 810">
			            				Trimester(s)
			          			</xsl:when>
			          			<xsl:otherwise>		            
		          				</xsl:otherwise> 
			       			 </xsl:choose>
	                </td>
                </xsl:if>        
            </tr>
            
            <xsl:if test="patientagegroup[.!='']"> 
	            <tr>
					<td width="1%"></td>
	              	<td colspan="3">
		                <b>Age Group: </b>    
						<xsl:choose>           
							<xsl:when test=" patientagegroup =1">
				           			Neonate
				          	</xsl:when> 	
		  		 			<xsl:when test=" patientagegroup = 2">
				            		Infant
				          	</xsl:when> 
							<xsl:when test=" patientagegroup = 3">
				         			Child
				          	</xsl:when> 	
		   					<xsl:when test=" patientagegroup= 4">
				            		Adolescent
				          	</xsl:when> 	
							<xsl:when test=" patientagegroup= 5">
				            		Adult
				          	</xsl:when> 	
							<xsl:when test=" patientagegroup= 6">
				            		Elderly
				          	</xsl:when> 
				          	<xsl:otherwise>		            
			          		</xsl:otherwise>
				        </xsl:choose>
	              </td> 
	            </tr>
            </xsl:if>
            
            <tr>
				<td width="1%"></td>
				<xsl:if test="patientweight[.!='']">
	              	<td>
		                <b>Weight (kg): </b>
		                <xsl:value-of select=" patientweight" />
	             	</td>
             	</xsl:if>
             	<xsl:if test="patientheight[.!='']">           
	              	<td>
		                <b>Height (cm): </b>
		                <xsl:value-of select=" patientheight" />
	              	</td>
              	</xsl:if>
              	<xsl:if test="patientsex[.!='']">          
	              	<td>
	                	<b>Sex: </b>                
						<xsl:choose>           
							<xsl:when test=" patientsex =1">
							       Male
							</xsl:when> 	
					  		<xsl:when test=" patientsex = 2">
							       Female
							</xsl:when> 
							<xsl:otherwise>		            
			          		</xsl:otherwise>
						</xsl:choose>
	              </td>
              </xsl:if>
            </tr>
            
            <xsl:if test="patientlastmenstrualdate[.!='']"> 
	            <tr>
	            	<td width="1%"></td>
	              	<td colspan="3">
		                	<b>Last Menstrual Date: </b> 
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
								           <xsl:variable name="patientlastmenstrualdate" select=" patientlastmenstrualdate"/>               
								 		   <xsl:value-of select="substring($patientlastmenstrualdate, 1, 4)"/>
								</xsl:when>
								<xsl:otherwise>		            
				                </xsl:otherwise>
							</xsl:choose>
	             	</td> 
	            </tr>
            </xsl:if>
            
            <xsl:if test="patientmedicalhistorytext[.!='']">
	            <tr>
					<td width="1%"></td>
		            <td colspan="3">
			                <b>Medical History Text: </b>
			                <xsl:value-of select=" patientmedicalhistorytext" />
		            </td>
	            </tr>
            </xsl:if>
            
            <xsl:if test="resultstestsprocedures[.!='']">	
	            <tr>
					<td width="1%"></td>
	              	<td colspan="3">
			                <b>Results Test Procedure: </b>
			                <xsl:value-of select=" resultstestsprocedures" />
	             	</td>
	            </tr>
            </xsl:if>            
         </table>
      
       <!-- Patient Medical history information-->
      <xsl:if test="medicalhistoryepisode[.!='']">
      <br />
      <table border="1" cellpadding="2" cellspacing="0" width="100%">
	 	<tr class="TR">
	          <td colspan="4">                    
					<b>B.1.7 Relevant medical history and concurrent conditions </b>          
	          </td>
   		</tr>
   		
		<xsl:for-each select="medicalhistoryepisode">
			<tr>
				  <td>
					<table border="0" cellpadding="2" cellspacing="0" width="100%">
					<tr>
						<td width="1%"></td>
						
						<xsl:if test="patientepisodenamemeddraversion[.!='']">	
		      				<td width="40%">
					            <b>MedDRA version for Medical History: </b>
								<xsl:value-of select="patientepisodenamemeddraversion" /> 
		      				</td>
		      				<td width="20%"></td>
	      				</xsl:if> 
						
						<xsl:if test="patientepisodename[.!='']">	
				            <td width="40%">
					            <b>Structured information: </b>
					            <xsl:value-of select="patientepisodename" />
				          	</td>
			          	</xsl:if> 
					</tr> 
					         
			        <tr>
						<td width="1%"></td>
						
						<xsl:if test="patientmedicalstartdate[.!='']">
			          	<td>
				            <b>Medical Start Date: </b>
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
			          	</td>
			          	</xsl:if> 
			          	
			          	<xsl:if test="patientmedicalcontinue[.!='']">          
			          	<td>
				            <b>Medical Continuing: </b>                
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
					      </td>
					      </xsl:if> 
					      
					      <xsl:if test="patientmedicalenddate[.!='']">
							  <td>
						            <b>Medical End Date: </b>
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
				         	  </td>
			         	  </xsl:if> 
			        </tr>
			        
			        <xsl:if test="patientmedicalcomment[.!='']">
				        <tr>
							<td width="1%"></td>
					        <td colspan="3">
					            <b>Comments: </b>
					            <xsl:value-of select="patientmedicalcomment" />
					        </td> 
				        </tr>
			        </xsl:if>
				</table>
			</td>
		</tr>
	</xsl:for-each> 
</table>
	  </xsl:if>
	  
      <!-- Patient past drug information-->	
      <xsl:if test="patientpastdrugtherapy[.!='']">	
	  <br />
	  <table border="1" cellpadding="2" cellspacing="0" width="100%">
		 	<tr class="TR">	
	              <td colspan="4">                    
	 				<b>B.1.8 Relevant past drug history </b>          
	              </td>
	        </tr>
			<xsl:for-each select="patientpastdrugtherapy">
			<tr>
				<td>
					<table border="0" cellpadding="2" cellspacing="0" width="100%">
	 					 <xsl:if test="patientdrugname[.!='']">	
	 					 <tr>
	 	 					<td width="1%"></td>
	 	 					<td colspan="3">
					                <b>Name of Drug as reported: </b>
					 				<xsl:value-of select="patientdrugname" /> 
              					</td>
              				</tr>								
						</xsl:if>
								        
	            				<tr>
									<td width="1%"></td>
									
									<xsl:if test="patientdrugstartdate[.!='']">
		              					<td width="50%">
							               	 <b>Start Date: </b>
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
						              	</td>
					              	</xsl:if>
					              	
					              	<xsl:if test="patientdrugenddate[.!='']">
		              					<td width="50%">
		                					<b>End Date: </b>
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
		             					</td>
	             					</xsl:if>	
	            				</tr>
	            				<tr>
									  <td width="1%"></td>
									  <xsl:if test="patientindicationmeddraversion[.!='']">
							              <td>
								                <b>MedDRA Version for indication: </b>
								                <xsl:value-of select="patientindicationmeddraversion" />
							              </td>
						              </xsl:if>
						              <xsl:if test="patientdrugindication[.!='']">									     
				 						  <td>
								                <b>Indication: </b>
								                <xsl:value-of select="patientdrugindication" />
			              				  </td>
		              				  </xsl:if> 
	            				</tr>
							    <tr>
									  <td width="1%"></td>
									  <xsl:if test="patientdrugreactionmeddraversion[.!='']">
								          <td>
								                <b>MedDRA version for reaction: </b>
								                <xsl:value-of select="patientdrugreactionmeddraversion" />
								          </td>
							          </xsl:if>
							          <xsl:if test="patientdrugreaction[.!='']">									     
									 	  <td>
								                <b>Reaction: </b>
								                <xsl:value-of select="patientdrugreaction" />
								          </td>
							          </xsl:if> 
							     </tr>
							 							
						 
					</table>
				  </td>
				</tr>
				</xsl:for-each> 
			</table>
      </xsl:if>       
      
      <xsl:apply-templates select="patientdeath"/>      
      <xsl:apply-templates select="parent"/>     
       
      <!-- Reaction information-->
      <xsl:if test="reaction[.!='']">	
      <br />
	  <table border="1" cellpadding="2" cellspacing="0" width="100%">
		<tr class="TR">
			<td colspan="4">B.2 Reaction</td>
		</tr>
		<xsl:for-each select="reaction">
		<tr>
			<td>
				<table border="0" cellpadding="2" cellspacing="0" width="100%">
					<xsl:if test="primarysourcereaction[.!='']">
						<tr>
							<td width="1%"></td>
							<td colspan="3">
								<b>Reaction/Event as reported by primary source: </b>
								<xsl:value-of select="primarysourcereaction" />
							</td>
						</tr>
					</xsl:if>
					
					<tr>
						<td width="1%"></td>
						<xsl:if test="reactionmeddraversionllt[.!='']">
							<td width="48%">
								<b>MedDRA version for reaction/event term LLT: </b>
								<xsl:value-of select="reactionmeddraversionllt" />
							</td>
							<td width="4%"></td>
						</xsl:if>
						
						<xsl:if test="reactionmeddrallt[.!='']">
							<td width="48%">
								<b>Reaction/Event in MedDRA terminology (LLT): </b>
								<xsl:value-of select="reactionmeddrallt" />	
							</td>
						</xsl:if>
					</tr>
					
					<tr>
						<td width="1%"></td>
						<xsl:if test="reactionmeddraversionpt[.!='']">
							<td>
								<b>MedDRA version for reaction/event term PT: </b>
								<xsl:value-of select="reactionmeddraversionpt" />
							</td>
							<td width="4%"></td>
						</xsl:if>
						
						<xsl:if test="reactionmeddrapt[.!='']">
							<td>
								<b>Reaction/event MedDRA term (PT): </b>
								<xsl:value-of select="reactionmeddrapt" />	
							</td>
						</xsl:if>
					</tr>
					
					<xsl:if test="termhighlighted[.!='']">
					<tr>
						<td width="1%"></td>
						<td colspan="3">
							<b>Term highlighted by the reporter: </b>						
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
						</td>					
					</tr>
					</xsl:if>
					
					<tr>
						<td width="1%"></td>
						
						<xsl:if test="reactionstartdate[.!='']">
							<td>
								<b>Date of start of reaction/event: </b>
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
							</td>
							<td width="4%"></td>
						</xsl:if>
												
						<xsl:if test="reactionenddate[.!='']">
							<td>
								<b>Date of end of reaction/event: </b>
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
							</td>
						</xsl:if>					
					</tr>
					
					<xsl:if test="reactionduration[.!='']">
						<tr>
							<td width="1%"></td>
							<td colspan="3">
								<b>Duration of reaction/event: </b>
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
							</td>					
						</tr>
					</xsl:if>
					
					<tr>
						<td width="1%"></td>
						
						<xsl:if test="reactionfirsttime[.!='']">
							<td>
								<b>Time interval between beginning of suspect drug administration and start of reaction/event: </b>
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
							</td>
							<td width="4%"></td>
						</xsl:if>
												
						<xsl:if test="reactionlasttime[.!='']">
							<td>
								<b>Time interval between last dose and start of reaction/event: </b>
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
							</td>
						</xsl:if>						
					</tr>
					
					<xsl:if test="reactionoutcome[.!='']">
						<tr>
							<td width="1%"></td>
							<td colspan="3">
								<b>Outcome of reaction/event at the time of last observation: </b>						
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
							</td>
						</tr>
					</xsl:if>								
				</table>
			</td>
		</tr>
	  </xsl:for-each>
	</table>
	  </xsl:if>
	  
	  <!-- Results of tests and procedures relevant to the investigation of the patient:-->
	  <xsl:if test="test[.!='']">
	  <br />
	  <table border="1" cellpadding="2" cellspacing="0" width="100%">
		<tr class="TR">
			<td colspan="4">B.3 Results of tests and procedures relevant to the investigation of the patient</td>
		</tr>
		<xsl:for-each select="test">
		<tr>
			<td>
				<table border="0" cellpadding="2" cellspacing="0" width="100%">
					<tr>
						<td width="1%"></td>
						
						<xsl:if test="testdate[.!='']">
							<td width="33%">
								<b>Date: </b>												 
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
							</td>
						</xsl:if>
						
						<xsl:if test="testname[.!='']">				
							<td width="33%">
								<b>Test: </b>
								<xsl:value-of select="testname" />
							</td>
						</xsl:if>
						
						<xsl:if test="testresult[.!='']">
							<td width="33%">
								<b>Result: </b>
								<xsl:value-of select="testresult" />
								<xsl:text> </xsl:text>
								<xsl:value-of select="testunit" />		
							</td>
						</xsl:if>
					</tr>
					
					<tr>
						<td width="1%"></td>
						
						<xsl:if test="lowtestrange[.!='']">
							<td>
								<b>Normal low range: </b>
								<xsl:value-of select="lowtestrange" />
							</td>
						</xsl:if>
						
						<xsl:if test="hightestrange[.!='']">						
							<td>
								<b>Normal high range: </b>
								<xsl:value-of select="hightestrange" />	
							</td>
						</xsl:if>
						
						<xsl:if test="moreinformation[.!='']">
							<td>
								<b>More information available (Y/N):</b>							
								<xsl:choose>           
										<xsl:when test="moreinformation = 1 ">
											           Yes
										</xsl:when>
										<xsl:when test="moreinformation = 2 ">
											           No
										</xsl:when>					
								</xsl:choose>	
							</td>
						</xsl:if>
					</tr>								
				</table>
			</td>
		</tr>
	  </xsl:for-each>		
	</table>	
      </xsl:if>
      
      <!-- Drug information -->
      <xsl:if test="drug[.!='']">
	  <br />
 	  <table border="1" cellpadding="2" cellspacing="0" width="100%">
		<tr class="TR">
			<td colspan="6">B.4 Drug(s) Information</td>
		</tr>
		<xsl:for-each select="drug">
		<tr>			
			<td>
				<table border="0" cellpadding="2" cellspacing="0" width="100%">
					
					<xsl:if test="drugcharacterization[.!='']">
						<tr>
							<td width="1%"></td>
							<td colspan="5">
								<b>Characterization of drug role: </b>			
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
							</td>
						</tr>
					</xsl:if>
					
					<xsl:if test="medicinalproduct[.!='']">
						<tr>
							<td width="1%"></td>
							<td colspan="5">
								<b>Proprietary medicinal product name: </b>
								<xsl:value-of select="medicinalproduct" />
							</td>
						</tr>
						<tr><td height="10px"></td></tr>
					</xsl:if>
					
					<xsl:if test="activesubstance/activesubstancename[.!='']">					
						<tr>
							<td width="1%"></td>
							<td colspan="2">
								<table border="1" cellpadding="2" cellspacing="0" width="100%">
									<tr class="TR">
										<td>
											<b>B.4.k.2.2 Active Drug Substance Name</b>
										</td>
									</tr>
									<xsl:for-each select="activesubstance">													
									<tr>
										<td>
											<table border="0" cellpadding="2" cellspacing="0" width="100%">										
												<tr>
													<td>
														<xsl:value-of select="activesubstancename" />	
													</td>
												</tr>										
											</table>							
										</td>
									</tr>
									</xsl:for-each>						
								</table>						
							</td>				
						</tr>
						<tr><td height="10px"></td></tr>
					</xsl:if>
										
					<xsl:if test="obtaindrugcountry[.!='']">	
						<tr>
							<td width="1%"></td>
							<td colspan="5">
								<b>Identification of the country where the drug was obtained: </b>
								<xsl:value-of select="obtaindrugcountry" />									
							</td>
						</tr>
					</xsl:if>
					
					<xsl:if test="drugbatchnumb[.!='']">
						<tr>
							<td width="1%"></td>
							<td colspan="5">
								<b>Batch/lot number: </b>
								<xsl:value-of select="drugbatchnumb" />
							</td>
						</tr>						
					</xsl:if>
					
					<xsl:if test="drugauthorizationnumb[.!=''] or drugauthorizationcountry[.!=''] or drugauthorizationholder[.!='']">
					<tr><td height="10px"></td></tr>									
					<tr>
						<td width="1%"></td>
						<td colspan="5">
							<b><u>Holder and authorization/application number of drug</u></b>
						</td>
					</tr>
					</xsl:if>
														
					<tr>
						<td width="1%"></td>
						
						<xsl:if test="drugauthorizationnumb[.!='']">
							<td>
								<b>Authorization/Application Number: </b>
								<xsl:value-of select="drugauthorizationnumb" />
							</td>
							<td width="4%"></td>
						</xsl:if>
												
						<xsl:if test="drugauthorizationcountry[.!='']">
							<td>
								<b>Country of authorization/application: </b>
								<xsl:value-of select="drugauthorizationcountry" />
							</td>
							<td width="4%"></td>
						</xsl:if>
						
						<xsl:if test="drugauthorizationholder[.!='']">
							<td>
								<b>Name of holder/applicant: </b>
								<xsl:value-of select="drugauthorizationholder" />
							</td>
						</xsl:if>					
					</tr>
					
					<xsl:if test="drugstructuredosagenumb[.!=''] or
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
								drugstartdate[.!='']">
								
						<tr><td height="20px"></td></tr>					
						<tr>
							<td width="1%"></td>
							<td colspan="5">
								<b><u>Structured Dosage Information </u></b>
							</td>
						</tr>
					</xsl:if>
					
					<tr>
						<td width="1%"></td>
						
						<xsl:if test="drugstructuredosagenumb[.!='']">
							<td>
								<b>Dose: </b>
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
							</td>
							<td width="4%"></td>
						</xsl:if>
												
						<xsl:if test="drugseparatedosagenumb[.!='']">
							<td>
								<b>Number of separate dosages: </b>
								<xsl:value-of select="drugseparatedosagenumb" />							
							</td>
							<td width="4%"></td>
						</xsl:if>
												
						<xsl:if test="drugintervaldosageunitnumb[.!='']">
							<td>
							<b>Number of Units in the interval: </b>
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
									  <xsl:otherwise>		            
		          </xsl:otherwise>							
							</xsl:choose>
						</td>
						</xsl:if>					
					</tr>
					<tr>
						<td width="1%"></td>
						
						<xsl:if test="drugcumulativedosagenumb[.!='']">
							<td>
								<b>Cumulative dose to first reaction: </b>
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
							</td>
							<td width="4%"></td>
						</xsl:if>
						
						<xsl:if test="drugdosagetext[.!='']">
							<td>
								<b>Dosage text: </b>
								<xsl:value-of select="drugdosagetext" />							
							</td>
							<td width="4%"></td>
						</xsl:if>
												
						<xsl:if test="drugdosageform[.!='']">
							<td>
								<b>Pharmaceutical form (Dosage form): </b>
								<xsl:value-of select="drugdosageform" />
							</td>
						</xsl:if>									
					</tr>
						
					<tr>
						<td width="1%"></td>
						
						<xsl:if test="drugadministrationroute[.!='']">
							<td>
							<b>Route of administration: </b>							
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
						</td>
							<td width="4%"></td>
						</xsl:if>
					
						<xsl:if test="drugparadministration[.!='']">						
							<td>
							<b>Parent route of administration (in case of a parent child/fetus report): </b>
						
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
						</td>
							<td width="4%"></td>
						</xsl:if>
					</tr>
					<tr>
						<td width="1%"></td>
						
						<xsl:if test="reactiongestationperiod[.!='']">
							<td>
								<b>Gestation period at time of exposure: </b>
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
							</td>
							<td width="4%"></td>
						</xsl:if>						
						
						<xsl:if test="drugindicationmeddraversion[.!='']">						
							<td>
								<b>MedDRA version for indication: </b>
								<xsl:value-of select="drugindicationmeddraversion" />							
							</td>
							<td width="4%"></td>
						</xsl:if>
						
						<xsl:if test="drugindication[.!='']">
							<td>
								<b>Indication for use in the case: </b>
								<xsl:value-of select="drugindication" />
							</td>
						</xsl:if>
					</tr>
					
					<xsl:if test="drugstartdate[.!='']">	
						<tr>
							<td width="1%"></td>
							<td colspan="5">
								<b>Date of start of drug: </b>
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
							</td>						
						</tr>
					</xsl:if>
					
					<xsl:if test="drugstartperiod[.!=''] or
									drugstartperiodunit[.!=''] or
									druglastperiod[.!=''] or
									druglastperiodunit[.!=''] or
									drugenddateformat[.!=''] or
									drugenddate[.!=''] or
									drugtreatmentduration[.!=''] or
									drugtreatmentdurationunit[.!=''] or
									actiondrug[.!='']">
				
						<tr><td height="10px"></td></tr>
						<tr>
							<td width="1%"></td>
							<td colspan="5">
								<b><u>Time interval between drug administration and start of reaction/event </u></b>													
							</td>
						</tr>
					</xsl:if>				
					<tr>
						<td width="1%"></td>
						
						<xsl:if test="drugstartperiod[.!='']">
							<td>
							<b>Time interval between beginning  of drug administration and start of reaction/event: </b>
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
						</td>
							<td width="4%"></td>
						</xsl:if>
												
						<xsl:if test="druglastperiod[.!='']">						
							<td>
							<b>Time interval between last dose of drug and start of reaction/event: </b>
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
						</td>
							<td width="4%"></td>
						</xsl:if>
						
						<xsl:if test="drugenddate[.!='']">
							<td>
							<b>Date of last administration: </b>
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
						</td>
						</xsl:if>						
					</tr>
					
					<xsl:if test="drugtreatmentduration[.!='']">
						<tr>
						<td width="1%"></td>
						<td colspan="5">
							<b>Duration of drug administration: </b>
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
						</td>
					</tr>
					</xsl:if>	
					
					<xsl:if test="actiondrug[.!='']">
						<tr>
							<td width="1%"></td>
							<td colspan="5">
								<b>Actions(s) taken with drug: </b>						
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
							</td>
						</tr>
					</xsl:if>					
				
					<xsl:if test="drugrecurreadministration[.!=''] or
									drugrecurrence[.!=''] or
									drugrecuractionmeddraversion[.!=''] or
									drugrecuraction[.!=''] or
									drugreactionrelatedness[.!=''] or
									drugreactionassesmeddraversion[.!=''] or
									drugreactionasses[.!=''] or
									drugassessmentsource[.!=''] or
									drugassessmentmethod[.!=''] or
									drugresult[.!=''] or
									drugadditional[.!='']">													
					
						<tr><td height="20px"></td></tr>										
						<tr>
							<td width="1%"></td>
							<td colspan="5">
								<b><u>Effect of rechallenge (or re-exposure), for suspect drug(s) only: </u></b>														
							</td>
						</tr>				
					</xsl:if>
					
					<xsl:if test="drugrecurreadministration[.!='']">
						<tr>
							<td width="1%"></td>
							<td colspan="5">
								<b>Did reaction recur on readministration?: </b>						
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
							</td>
						</tr>
					</xsl:if>
					
					<xsl:if test="drugrecurrence[.!='']">
					<tr><td height="10px"></td></tr>
					<tr>						
						<td width="1%"></td>
						<td colspan="5">
							<table border="1" cellpadding="2" cellspacing="0" width="100%">
								<tr class="TR">
									<td>
										<b>B.4.k.17.2 Drug recurrence</b>
									</td>									
								</tr>
								<xsl:for-each select="drugrecurrence">
									<tr>
										<td>
											<table border="0" cellpadding="2" cellspacing="0" width="100%">										
												<tr>
													<xsl:if test="drugrecuractionmeddraversion[.!='']">
														<td width="48%">
															<b>MedDRA version for reaction(s)/event(s) recurred: </b>
															<xsl:value-of select="drugrecuractionmeddraversion" />
														</td>
														<td width="4%"></td>
													</xsl:if>
													
													<xsl:if test="drugrecuraction[.!='']">
														<td width="48%">
															<b>If yes, which reaction(s)/event(s) recurred? </b>
															<xsl:value-of select="drugrecuraction" />
														</td>
													</xsl:if>
												</tr>																				
											</table>
										</td>
									</tr>
								</xsl:for-each>	
							</table>
						</td>					
					</tr>
					</xsl:if>
					
					<xsl:if test="drugreactionrelatedness[.!='']">
					<tr><td height="10px"></td></tr>				
					<tr>
						<td width="1%"></td>
						<td colspan="5">
							<table border="1" cellpadding="2" cellspacing="0" width="100%">
								<tr class="TR">
									<td>
										<b>B.4.k.18 Relatedness of drug to reaction(s)/event(s)</b>	
									</td>
								</tr>
								<xsl:for-each select="drugreactionrelatedness">								
									<tr>
										<td>
											<table border="0" cellpadding="2" cellspacing="0" width="100%">
												<tr>
													<xsl:if test="drugreactionassesmeddraversion[.!='']">
														<td width="48%">
															<b>MedDRA version for Reaction assessed: </b>
															<xsl:value-of select="drugreactionassesmeddraversion" />
														</td>
														<td width="4%"></td>
													</xsl:if>
													
													<xsl:if test="drugreactionasses[.!='']">
														<td width="48%">
															<b>Reaction assessed: </b>
															<xsl:value-of select="drugreactionasses" />
														</td>
													</xsl:if>	
												</tr>
												<xsl:if test="drugassessmentsource[.!='']">
													<tr>
														<td colspan="3">
															<b>Source of assessment: </b>
															<xsl:value-of select="drugassessmentsource" />							
														</td>
													</tr>
												</xsl:if>
												<xsl:if test="drugassessmentmethod[.!='']">
													<tr>
														<td colspan="3">
															<b>Method of assessment: </b>
															<xsl:value-of select="drugassessmentmethod" />	
														</td>
													</tr>
												</xsl:if>
												<xsl:if test="drugresult[.!='']">
													<tr>
														<td colspan="3">
															<b>Result: </b>
															<xsl:value-of select="drugresult" />
														</td>
													</tr>
												</xsl:if>
											</table>
										</td>
									</tr>							
								</xsl:for-each>
							</table>
						</td>					
					</tr>					
					</xsl:if>
					
					<xsl:if test="drugadditional[.!='']">											
						<tr><td height="10px"></td></tr>
						<tr>
							<td width="1%"></td>
							<td colspan="5">
								<b>Additional information on drug: </b>
								<xsl:value-of select="drugadditional" />							
							</td>						
						</tr>
					</xsl:if>																					
				</table>
			</td>
		</tr>
	  </xsl:for-each>		
	</table>
	  </xsl:if>
	
    <xsl:apply-templates select="summary"/> 
         
</xsl:template>	

<!--In Case of Death-->
<xsl:template match="patientdeath">
	  <br />	      
	<table border="0" cellpadding="2" cellspacing="0" width="100%">
           <tr class="TR">
			      <td width="1%"></td>
	              <td colspan="2">
	                <b>B.1.9 In Case of Death</b>               
	              </td>
       	   </tr>
           <tr>
				<td width="1%"></td>
				<xsl:if test="patientdeathdate[.!='']">
         			<td width="50%">
           			 <b>Date of Death: </b>
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
         		</td>
         		</xsl:if>
         		
         		<xsl:if test="patientautopsyyesno[.!='']">					
  					<td width="50%">
	            		<b>Autopsy: </b>				
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
          			</td>
          		</xsl:if>
       	  </tr>
       	  
       	  <xsl:if test="patientdeathcause[.!='']">       	  
       	  <tr>
       	  	<td height="15px"></td>
       	  </tr>
       	  <tr>
		    	<td colspan="3">
		    		<table border="1" cellpadding="2" cellspacing="0" width="100%">
		    			<tr class="TR">
		    				<td colspan="3">
		    					<b>B.1.9.2 Patient's Death Cause</b>
		    				</td>
		    			</tr>
		    			 <xsl:for-each select="patientdeathcause">
						     <tr>
						     	<td>
						     		<table border="0" cellpadding="2" cellspacing="0" width="100%">
						     			<tr>
						     				<td width="1%"></td>
						     				<xsl:if test="patientdeathreportmeddraversion[.!='']">
							     				<td width="50%">
							     					<b>MedDRA version for reported cause(s) of death: </b>
								                	<xsl:value-of select="patientdeathreportmeddraversion" />
							             	 	</td>
						             	 	</xsl:if>
						             	 	<xsl:if test="patientdeathreport[.!='']">						            		   
							              		<td width="50%">
									                <b>Reported cause(s) of death: </b>
									                <xsl:value-of select="patientdeathreport" />
							     				</td>
						     				</xsl:if>
						     			</tr>
						     		</table>
						     	</td>
						      </tr>
					       </xsl:for-each> 					   
		    		</table>
		    	</td>            		
		    </tr>
		  </xsl:if>
		   
		  <xsl:if test="patientautopsy[.!='']">  
		   <tr><td height="10px"></td></tr>
		    <tr>
		    	<td colspan="3">
		    		<table border="1" cellpadding="2" cellspacing="0" width="100%">
		    			<tr class="TR">		    				
		    				<td colspan="3">
		    					<b>B.1.9.3 Patient's Autopsy</b>
		    				</td>
		    			</tr>
		    			<xsl:for-each select="patientautopsy">
							<tr>
								<td>
									<table border="0" cellpadding="2" cellspacing="0" width="100%">
										<tr>
											<td width="1%"></td>
											<xsl:if test="patientdetermautopsmeddraversion[.!='']">
												<td width="50%">
													<b>MedDRA version for autopsy-determined cause(s) of death: </b>
													<xsl:value-of select="patientdetermautopsmeddraversion" />
												</td>
											</xsl:if>
											<xsl:if test="patientdetermineautopsy[.!='']">										
												<td width="50%">
													<b>Autopsy-determined cause(s) of death: </b>
													<xsl:value-of select="patientdetermineautopsy" />
												</td>
											</xsl:if>
										</tr>
									</table>
								</td>
							</tr>			  			
			 			 </xsl:for-each>  
				    </table>
		    	</td>            		
		    </tr>
		   </xsl:if>        	  
    </table>
</xsl:template>


<xsl:template match="parent">
	  <!-- Parent information-->	  
	  <br />	  
      <table border="0" cellpadding="2" cellspacing="0" width="100%">      	
               <tr class="TR">
					<td width="1%"></td>
		            <td colspan="3">
		                <b>B.1.10 For a parent-child/fetus report, information concerning the parent</b>               
		            </td>
	            </tr>
            
            <xsl:if test="parentidentification[.!='']">
	            <tr>
					<td width="1%"></td>
	              	<td colspan="3">
		                <b>Initials: </b>
		 				<xsl:value-of select="parentidentification" /> 
	              	</td>
	            </tr>
            </xsl:if>

            <tr>
			<td width="1%"></td>
			
			<xsl:if test="parentbirthdate[.!='']">
	              <td width="33%">
		                <b>Birth Date: </b>               
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
	              </td>
	              <td width="33%"></td> 
              </xsl:if> 
              
              <xsl:if test="parentage[.!='']">        
	              <td width="33%">
	                <b>Age: </b>
	                <xsl:value-of select="parentage" />
	                <xsl:text> </xsl:text>
	         			<xsl:choose>           
							<xsl:when test="  parentageunit = 801 ">
								   Year(s)
							</xsl:when>
							<xsl:otherwise>		            
	          				</xsl:otherwise>
						</xsl:choose>                 
	              </td>
              </xsl:if>                             
            </tr>
            
            <xsl:if test="parentlastmenstrualdate[.!='']"> 
            <tr>
				<td width="1%"></td>
              	<td colspan="3">
	                <b>Last Menstrual Date: </b>                
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
	              </td>
            </tr>
            </xsl:if> 
            
              <tr>
				<td width="1%"></td>
				
				<xsl:if test="parentweight[.!='']"> 
	              	<td>
		                <b>Weight (kg): </b>
		                <xsl:value-of select="  parentweight" />
	              	</td> 
	             </xsl:if>
	             
	             <xsl:if test="parentheight[.!='']">
		              <td>
		                <b>Height (cm): </b>
		                <xsl:value-of select="  parentheight" />
		              </td> 
		          </xsl:if>
		          
		          <xsl:if test="parentsex[.!='']">               
		              <td>
		            	<b>Sex: </b>
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
		          	  </td>
	             </xsl:if>
            </tr>
            
            <xsl:if test="parentmedicalrelevanttext[.!='']">   
	            <tr>
	            	<td width="1%"></td>
	            	<td colspan="3">
	            		<b>Text for relevant medical history and concurrent conditions of parent (not including reaction/event): </b>
	            		<xsl:value-of select="  parentmedicalrelevanttext" />
	            	</td>
	            </tr> 
            </xsl:if>
      </table>      
          
	  <!--Parent medical history information-->
	  <xsl:if test="parentmedicalhistoryepisode[.!='']">
	  <br /> 		
      <table border="1" cellpadding="2" cellspacing="0" width="100%">	
               <tr class="TR">					
             		<td colspan="4">
              		  <b>B.1.10.7 Relevant medical history and concurrent conditions</b>               
            		</td>
               </tr>
               <xsl:for-each select="parentmedicalhistoryepisode">
               <tr>
					<td>
						<table border="0" cellpadding="2" cellspacing="0" width="100%">
							<tr>
							    <td width="1%"></td>
							    <xsl:if test="parentmdepisodemeddraversion[.!='']">
								<td width="40%">
									<b>MedDRA version for Medical History: </b>
									<xsl:value-of select="parentmdepisodemeddraversion" /> 
								</td>
								<td width="20%"></td> 
								</xsl:if>       
								
								<xsl:if test="parentmedicalepisodename[.!='']">
		            			<td width="40%">
		            				<b>Structured information: </b>
		            				<xsl:value-of select="parentmedicalepisodename" />
		            			</td>
		            			</xsl:if>
							</tr>
							<tr>
								<td width="1%"></td>
								<xsl:if test="parentmedicalstartdate[.!='']">
		              			<td>
		               				 <b>Medical Start Date: </b>		                			   
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
							
		             			</td>
		             			</xsl:if>
		             			<xsl:if test="parentmedicalcontinue[.!='']">          
		              			<td>
		               				 <b>Medical Continuing: </b>		                			
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
		              			</td>
		              			</xsl:if>
		              			<xsl:if test="parentmedicalenddate[.!='']">
					  			<td>
		                			 <b>Medical End Date: </b>	
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
		              			</td>
		              			</xsl:if>
           		 			</tr>
           		 			<xsl:if test="parentmedicalcomment[.!='']">
           		 			<tr>
								<td width="1%"></td>
		              			<td colspan="3">
		                			<b>Comments: </b>
		                			<xsl:value-of select="parentmedicalcomment" />
		             			</td> 
	            			</tr>
	            			</xsl:if>	            		
						</table>					
					</td>             		
				</tr>
			</xsl:for-each>  
		</table> 
	  </xsl:if>

  	  <!-- Parent past drug information-->
  	  <xsl:if test="parentpastdrugtherapy[.!='']">	
	  <br />
      <table border="1" cellpadding="2" cellspacing="0" width="100%">
 		 	<tr class="TR">	
          		<td colspan="3">                    
					<b>B.1.10.8 Relevant past drug history of Parent</b>          
          		</td>
        	</tr>
			<xsl:for-each select="parentpastdrugtherapy">
			<tr>
				<td>
				<table border="0" cellpadding="2" cellspacing="0" width="100%">
 	 				<tr>
 	 					<td width="1%"></td>
 						<td>
 							<xsl:if test="parentdrugname[.!='']">
 	 	 					<tr>
								<td width="1%"></td>
              					<td colspan="2">
					                <b>Name of Drug as reported: </b>
					 				<xsl:value-of select="parentdrugname" /> 
              					</td>
							</tr> 
							</xsl:if>
							         
           					<tr>
								<td width="1%"></td>
								
								<xsl:if test="parentdrugstartdate[.!='']">
	              				<td width="50%">
						                 <b>Start Date: </b> 
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
	              				</td>
	              				</xsl:if>
	              				
	              				<xsl:if test="parentdrugenddate[.!='']">
	              				<td width="50%">
					                	<b>End Date: </b> 
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
	              				</td>
	              				</xsl:if>	
            				</tr>
            				<tr>
								<td width="1%"></td>
								<xsl:if test="parentdrgindicationmeddraversion[.!='']">
					              <td width="50%">
						                <b>MedDRA Version for indication: </b>
						                <xsl:value-of select="parentdrgindicationmeddraversion" />
					              </td>
					              </xsl:if>	
					              <xsl:if test="parentdrugindication[.!='']">							  
	 							  <td width="50%">
						                <b>Indication: </b>
						                <xsl:value-of select="parentdrugindication" />
              					  </td>
              					  </xsl:if> 
            				</tr>
						   <tr>
								<td width="1%"></td>
								<xsl:if test="parentdrgreactionmeddraversion[.!='']">
					              <td>
						                <b>MedDRA version for reaction: </b>
						                <xsl:value-of select="parentdrgreactionmeddraversion" />
					              </td>
					              </xsl:if>
					              <xsl:if test="parentdrugreaction[.!='']">								 
							 	  <td>
						                <b>Reaction: </b>
						                <xsl:value-of select="parentdrugreaction" />
						          </td> 
						          </xsl:if>
						        </tr>	
						 	  </td>
						 	</tr>
						</table>
					</td>
				  </tr>
				  </xsl:for-each> 
			</table>
      </xsl:if>
     
</xsl:template>

<!-- Narrative case summary and further information -->
<xsl:template match="summary">
	<br/>
      <table border="0" cellpadding="2" cellspacing="0" width="100%">             
             <tr class="TR">
             	<td width="1%" />
              <td>
                <b>B.5 Narrative case summary and further information</b>              
              </td>
            </tr> 
            <xsl:if test="narrativeincludeclinical[.!='']">             
            <tr>
              <td width="1%" />
              <td>
                <b>Case narrative including clinical course, therapeutic measures, outcome and additional relevant information: </b>
                <xsl:value-of select="narrativeincludeclinical" />
              </td> 
            </tr>
            </xsl:if>
            
            <xsl:if test="reportercomment[.!='']">
            <tr> 
            	<td width="1%" />         
                <td>
	                <b>Reporter's comments: </b>
	                <xsl:value-of select="reportercomment" />  
              </td>
            </tr>
            </xsl:if>
            
            <xsl:if test="senderdiagnosismeddraversion[.!='']"> 
             <tr>
             	<td width="1%" />
              <td>
                <b>MedDRA Version for Senders diagnosis: </b>
                <xsl:value-of select="senderdiagnosismeddraversion" />
              </td>
            </tr>
            </xsl:if>
            
            <xsl:if test="senderdiagnosis[.!='']">
              <tr>
              <td width="1%" />
              <td>
                <b>Sender's diagnosis/syndrome and/or reclassification of reaction/event: </b>
                <xsl:value-of select="senderdiagnosis" />
              </td>               
            </tr>
            </xsl:if>
            
            <xsl:if test="sendercomment[.!='']">
              <tr>              	
              <td width="1%" />
              <td>
                <b>Sender's comments: </b>
                <xsl:value-of select="sendercomment" />
              </td> 
            </tr>
            </xsl:if>
            
            </table>
 </xsl:template> 
	
</xsl:stylesheet>


