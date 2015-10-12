<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:fo="http://www.w3.org/1999/XSL/Format"
    exclude-result-prefixes="fo">

  <xsl:template match="ROWSET">
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

  	<!-- ************************************ PRIMO REPORT
************************************ -->

  	<fo:block font-size="12pt" font-family="Arial,Times,MS Gothic,Gulim,MingLiU" color="#008080"
font-weight="bold">
		<xsl:text>PRIMO REPORT</xsl:text>
	</fo:block>
    <fo:block font-size="8pt"  font-family="Arial,Times,MS Gothic,Gulim,MingLiU"  line-height="13pt"
border-width="1pt" border-style="solid" border-color="#CCCCCC">
    	<!-- border-width="1px" border-style="solid" border-color="#CCCCCC" -->
    	<fo:table margin="3pt">
    	<fo:table-body>
    		<fo:table-row>
    			<fo:table-cell  font-weight="bold" width="120pt">
    				<fo:block><xsl:text>Report ID</xsl:text></fo:block>
    			</fo:table-cell>
    			<fo:table-cell font-weight="normal">
    				<fo:block><xsl:value-of
select="/ROWSET/ROW/REPORT/LAU_REPORT_ID"/></fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		<fo:table-row>
    			<fo:table-cell  font-weight="bold" width="120pt">
    				<fo:block><xsl:text>Report Type</xsl:text></fo:block>
    			</fo:table-cell>
    			<fo:table-cell font-weight="normal">
    				<fo:block><xsl:value-of
select="/ROWSET/ROW/REPORT/REPORT_INITIAL_OR_FOLLOWUP"/></fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		<fo:table-row>
    			<fo:table-cell  font-weight="bold" width="120pt">
    				<fo:block><xsl:text>Report Classification</xsl:text></fo:block>
    			</fo:table-cell>
    			<fo:table-cell width="240pt">
    				<fo:table width="100%">
    					<fo:table-body>
    						<fo:table-row>
    							<fo:table-cell font-weight="bold"><fo:block><xsl:text>AE:
</xsl:text></fo:block></fo:table-cell>
    							<fo:table-cell font-weight="normal"><fo:block><xsl:value-of
select="/ROWSET/ROW/REPORT/ADVERSE_EVENT_FLAG"/></fo:block></fo:table-cell>
    							<fo:table-cell font-weight="bold"><fo:block><xsl:text>Drug PTC:
</xsl:text></fo:block></fo:table-cell>
    							<fo:table-cell font-weight="normal"><fo:block><xsl:value-of
select="/ROWSET/ROW/REPORT/PRODUCT_COMPLAINT_FLAG"/></fo:block></fo:table-cell>
    							<fo:table-cell font-weight="bold"><fo:block><xsl:text>Device PTC:
</xsl:text></fo:block></fo:table-cell>
    							<fo:table-cell font-weight="normal"><fo:block><xsl:value-of
select="/ROWSET/ROW/REPORT/DEVICE_PRODUCT_COMPLAINT_FLAG"/></fo:block></fo:table-cell>
                  <fo:table-cell
font-weight="bold"><fo:block><xsl:text>Rumor:
</xsl:text></fo:block></fo:table-cell>
    							<fo:table-cell font-weight="normal"><fo:block><xsl:value-of
select="/ROWSET/ROW/REPORT/RUMOR_FLAG"/></fo:block></fo:table-cell>
    						</fo:table-row>
    					</fo:table-body>
    				</fo:table>
    			</fo:table-cell>
    		</fo:table-row>
    		<fo:table-row>
    			<fo:table-cell  font-weight="bold" width="120pt">
    				<fo:block><xsl:text>Report Source Type</xsl:text></fo:block>
    			</fo:table-cell>
    			<fo:table-cell font-weight="normal">
    				<fo:block><xsl:value-of
select="/ROWSET/ROW/REPORT/REPORT_SOURCE_TYPE"/></fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		<fo:table-row>
    			<fo:table-cell  font-weight="bold" width="120pt">
    				<fo:block><xsl:text>Special Attention Required</xsl:text></fo:block>
    			</fo:table-cell>
    			<fo:table-cell font-weight="normal">
    				<fo:block><xsl:value-of
select="/ROWSET/ROW/REPORT/REPORT_ALERT"/></fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    		<fo:table-row>
    			<fo:table-cell  font-weight="bold" width="120pt">
    				<fo:block><xsl:text>Report Status</xsl:text></fo:block>
    			</fo:table-cell>
    			<fo:table-cell font-weight="normal">
    				<fo:block><xsl:value-of
select="/ROWSET/ROW/REPORT/REPORT_STATUS"/></fo:block>
    			</fo:table-cell>
    		</fo:table-row>
    	</fo:table-body>
    	</fo:table>
    </fo:block>


    <!-- ************************************ REPORT SUMMARY
************************************ -->

    <fo:block font-size="12pt"  font-family="Arial,Times,MS Gothic,Gulim,MingLiU"  color="#008080"
font-weight="bold" space-before="10pt">
      <xsl:text>REPORT SUMMARY</xsl:text>
    </fo:block>
    <fo:block font-size="8pt"  font-family="Arial,Times,MS Gothic,Gulim,MingLiU"  line-height="13pt"
border-width="1pt" border-style="solid" border-color="#CCCCCC">
      <fo:table margin="3pt">
        <fo:table-body>
          <fo:table-row>
            <fo:table-cell width="250pt">
              <fo:block margin-left="3pt" font-weight="bold"
color="#FF8040">REPORT SUMMARY</fo:block>
            </fo:table-cell>
            <fo:table-cell width="250pt">
              <fo:block margin-left="3pt" font-weight="bold"
color="#FF8040">EXTERNAL REPORT REFERENCES</fo:block>
            </fo:table-cell>
          </fo:table-row>
            <fo:table-row space-after="10pt">
              <fo:table-cell width="50%">
                <fo:table>
                  <fo:table-body>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Reporter Last Name</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:value-of
select="/ROWSET/ROW/REPORT/CONTACTS/CONTACTS_T/CONTACT_LAST_NAME"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Patient Initial</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:value-of
select="/ROWSET/ROW/REPORT/PATIENT/PATIENT_T/PT_INITIALS"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Birth Date</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                            <xsl:value-of
select="/ROWSET/ROW/REPORT/PATIENT/PATIENT_T/PT_DOB"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Sex</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:value-of
select="/ROWSET/ROW/REPORT/PATIENT/PATIENT_T/PT_SEX"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Trade Name</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:value-of
select="/ROWSET/ROW/REPORT/PRODUCT/PRODUCT_T/TRADE_NAME"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>INN</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:value-of
select="/ROWSET/ROW/REPORT/PRODUCT/PRODUCT_T/PRODUCT_NAME"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Event</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:value-of
select="/ROWSET/ROW/REPORT/EVENTS/EVENTS_T/EVENT_VERBATIM"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Date</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:call-template name="FormatDate">
                            <xsl:with-param name="DateTime"
select="/ROWSET/ROW/REPORT/EVENTS/EVENTS_T/ONSET_DATE"/>
                          </xsl:call-template>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Country where event occured</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:value-of
select="/ROWSET/ROW/REPORT/COUNTRY_EVENT_OCCURRED"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                  </fo:table-body>
                </fo:table>
              </fo:table-cell>
              <fo:table-cell width="50%">
			  <fo:block space-after="10pt">
                <xsl:for-each
select="/ROWSET/ROW/REPORT/EXTERNAL_REFERENCES/EXTERNAL_REFERENCES_T">
                  <xsl:variable name="EXTERNAL_REFERENCES_T" select="."/>                  
                    <fo:table>
                      <fo:table-body>
                        <fo:table-row>
                          <fo:table-cell  font-weight="bold" width="120pt">
                            <fo:block>
                              <xsl:text>Reference Type</xsl:text>
                            </fo:block>
                          </fo:table-cell>
                          <fo:table-cell font-weight="normal">
                            <fo:block>
                              <xsl:value-of
select="EXTERNAL_REFERENCE_TYPE"/>
                            </fo:block>
                          </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row>
                          <fo:table-cell  font-weight="bold" width="120pt">
                            <fo:block>
                              <xsl:text>Reference Number</xsl:text>
                            </fo:block>
                          </fo:table-cell>
                          <fo:table-cell font-weight="normal">
                            <fo:block>
                              <xsl:value-of
select="EXTERNAL_REFERENCE_NUMBER"/>
                            </fo:block>
                          </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row>
                          <fo:table-cell  font-weight="bold" width="120pt">
                            <fo:block>
                              <xsl:text>Source</xsl:text>
                            </fo:block>
                          </fo:table-cell>
                          <fo:table-cell font-weight="normal">
                            <fo:block>
                              <xsl:value-of
select="EXTERNAL_REFERENCE_SOURCE"/>
                            </fo:block>
                          </fo:table-cell>
                        </fo:table-row>
                      </fo:table-body>
                    </fo:table>                  
                </xsl:for-each>
				</fo:block>
              </fo:table-cell>
            </fo:table-row>
          <fo:table-row space-after="10pt">
            <fo:table-cell width="50%">
              <fo:block margin-left="3pt" font-weight="bold"
color="#FF8040">MOST RECENT RECEIVED DATES</fo:block>
            </fo:table-cell>
            <fo:table-cell width="50%">
              <fo:block margin-left="3pt" font-weight="bold"
color="#FF8040">REPORT COMMENTS</fo:block>
            </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
            <fo:table-cell>
              <fo:table width="100%">
                <fo:table-body border="1">
                  <fo:table-row>
                    <fo:table-cell font-weight="bold">
                      <fo:block>
                        <xsl:text>Local: </xsl:text>
                      </fo:block>
                    </fo:table-cell>
                    <fo:table-cell font-weight="normal">
                      <fo:block>
                        <xsl:call-template name="FormatDate">
                          <xsl:with-param name="DateTime"
select="/ROWSET/ROW/REPORT/LATEST_LOCAL_RCVD_DATE"/>
                        </xsl:call-template>
                      </fo:block>
                    </fo:table-cell>
                    <fo:table-cell font-weight="bold">
                      <fo:block>
                        <xsl:text>Company: </xsl:text>
                      </fo:block>
                    </fo:table-cell>
                    <fo:table-cell font-weight="normal">
                      <fo:block>
                        <xsl:call-template name="FormatDate">
                          <xsl:with-param name="DateTime"
select="/ROWSET/ROW/REPORT/LATEST_CORP_RCVD_DATE"/>
                        </xsl:call-template>
                      </fo:block>
                    </fo:table-cell>
                    <fo:table-cell font-weight="bold">
                      <fo:block>
                        <xsl:text>Partner: </xsl:text>
                      </fo:block>
                    </fo:table-cell>
                    <fo:table-cell font-weight="normal">
                      <fo:block>
                        <xsl:call-template name="FormatDate">
                          <xsl:with-param name="DateTime"
select="/ROWSET/ROW/REPORT/LATEST_PARTNER_RCVD_DATE"/>
                        </xsl:call-template>
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>
            </fo:table-cell>
            <fo:table-cell width="50%">
              <fo:table width="100%">
                <fo:table-body border="1">
                  <fo:table-row>
                    <fo:table-cell font-weight="normal">
                      <fo:block>
                        <xsl:value-of
select="/ROWSET/ROW/REPORT/REPORT_COMMENTS"/>
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>
            </fo:table-cell>
          </fo:table-row>
        </fo:table-body>
      </fo:table>
    </fo:block>

    <!-- ************************************ Report Activities
************************************ -->

    <fo:block font-size="12pt"  font-family="Arial,Times,MS Gothic,Gulim,MingLiU"  color="#008080"
font-weight="bold" space-before="10pt">
      <xsl:text>REPORT ACTIVITIES</xsl:text>
    </fo:block>
    <fo:block font-size="8pt"  font-family="Arial,Times,MS Gothic,Gulim,MingLiU"  line-height="13pt"
border-width="1pt" border-style="solid" border-color="#CCCCCC">
      <fo:table margin="3pt">
        <fo:table-body>
          <fo:table-row>
            <fo:table-cell width="250pt">
              <fo:block margin-left="3pt" font-weight="bold"
color="#FF8040">ACTIVITY DETAILS</fo:block>
            </fo:table-cell>
            <fo:table-cell width="250pt">
              <fo:block margin-left="3pt" font-weight="bold"
color="#FF8040">ATTACHMENT DETAILS</fo:block>
            </fo:table-cell>
          </fo:table-row>
          <xsl:for-each
select="/ROWSET/ROW/REPORT/REPORT_ACTIVITIES/REPORT_ACTIVITIES_T">
            <xsl:variable name="REPORT_ACTIVITIES_T" select="."/>
			<xsl:variable name="ACTIVITY_ID_PARENT"><xsl:value-of select="ACTIVITY_ID"/></xsl:variable>
            <fo:table-row>
              <fo:table-cell width="50%">
                <fo:table>
                  <fo:table-body>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Direction</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:value-of select="DIRECTION"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Promote?</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:value-of select="PROMOTE_ACTIVITY"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Activity Type</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:value-of select="ACTIVITY_TYPE"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Activity Description</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:value-of select="ACTIVITY_DESCRIPTION"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Contact</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:value-of select="ASSOC_CONTACT_LAST_NAME"/>,
<xsl:value-of select="ASSOC_CONTACT_FIRST_NAME"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Local Received Date</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:call-template name="FormatDate">
                            <xsl:with-param name="DateTime"
select="LOCAL_RECEIVED_DATE"/>
                          </xsl:call-template>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Company Received Date</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:call-template name="FormatDate">
                            <xsl:with-param name="DateTime"
select="CORPORATE_RECEIVED_DATE"/>
                          </xsl:call-template>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Partner Received Date</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:call-template name="FormatDate">
                            <xsl:with-param name="DateTime"
select="PARTNER_RECEIVED_DATE"/>
                          </xsl:call-template>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
                  </fo:table-body>
                </fo:table>
              </fo:table-cell>
              <fo:table-cell width="50%">
              <fo:block space-after="10pt">
                <xsl:for-each
select="/ROWSET/ROW/REPORT/REPORT_ACTIVITIES/REPORT_ACTIVITIES_T/ACTIVITY_ATTACHMENT/ACTIVITY_ATTACHMENT_T">
                  <xsl:variable name="ACTIVITY_ATTACHMENT_T" select="."/>
				  <xsl:variable name="ACTIVITY_ID_CHILD"><xsl:value-of select="ACTIVITY_ID"/></xsl:variable>
				  <xsl:choose>
					<xsl:when test="(($ACTIVITY_ID_PARENT) != ($ACTIVITY_ID_CHILD))">
					</xsl:when>
					<xsl:otherwise>                   
						<fo:table>
						  <fo:table-body>
							<fo:table-row>
							  <fo:table-cell  font-weight="bold" width="120pt">
								<fo:block>
								  <xsl:text>Document Name</xsl:text>
								</fo:block>
							  </fo:table-cell>
							  <fo:table-cell font-weight="normal">
								<fo:block>
								  <xsl:value-of select="DOCUMENT_NAME"/>
								</fo:block>
							  </fo:table-cell>
							</fo:table-row>
						  </fo:table-body>
						</fo:table>
					</xsl:otherwise>
					</xsl:choose>
                </xsl:for-each>
                </fo:block>
              </fo:table-cell>
            </fo:table-row>
            <fo:table-row height="5pt">
				<fo:table-cell><fo:block></fo:block></fo:table-cell>
            </fo:table-row>
          </xsl:for-each>
        </fo:table-body>
      </fo:table>
    </fo:block>


    <!-- ************************************ CONTACT INFORMATION - PATIENT
************************************ -->

   	<fo:block font-size="12pt"  font-family="Arial,Times,MS Gothic,Gulim,MingLiU"  color="#008080"
font-weight="bold" space-before="10pt">
		<xsl:text>CONTACT INFORMATION &#8211; PATIENT</xsl:text>
	</fo:block>
	<fo:block font-size="8pt"  font-family="Arial,Times,MS Gothic,Gulim,MingLiU"  line-height="13pt"
border-width="1pt" border-style="solid" border-color="#CCCCCC">
    	<fo:table margin="3pt">
    	<fo:table-body>
    		<fo:table-row>
    		 	<fo:table-cell width="250pt">
    		 		<fo:block margin-left="3pt" font-weight="bold"
color="#FF8040"><xsl:text>NAME - ADDRESS</xsl:text></fo:block>
    		 	</fo:table-cell>
    		 	<fo:table-cell width="250pt">
    		 		<fo:block margin-left="3pt" font-weight="bold"
color="#FF8040"><xsl:text>DEMOGRAPHIC DETAILS</xsl:text></fo:block>
    		 	</fo:table-cell>
    		 </fo:table-row>
    		 <xsl:for-each select="/ROWSET/ROW/REPORT/PATIENT/PATIENT_T">
    		 	<xsl:variable name="PATIENT_T" select="."/>
    		 	<fo:table-row space-after="10pt">
    		 		<fo:table-cell width="50%">
    		 			<fo:table>
    		 				<fo:table-body>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Title</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="PT_TITLE"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>First Name</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="PT_FIRST_NAME"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Middle</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="PT_MIDDLE_NAME"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Last Name</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="PT_LAST_NAME"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Street Address</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="PT_ADDRESS1"/></fo:block>
					    				<fo:block><xsl:value-of select="PT_ADDRESS2"/></fo:block>
					    				<fo:block><xsl:value-of select="PT_ADDRESS3"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>City</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="PT_CITY"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>State</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="PT_STATE"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Postal Code</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="PT_POSTAL_CODE"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Country</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="PT_COUNTRY"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Phone Number</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="PT_PHONE_NUMBER"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Fax Number</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="PT_FAX_NUMBER"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Email</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="PT_EMAIL"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 				</fo:table-body>
    		 			</fo:table>
    		 		</fo:table-cell>
    		 		<fo:table-cell width="50%">
    		 			<fo:table>
    		 				<fo:table-body>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Sex</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="PT_SEX"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block>
                        <xsl:text>Birth Date</xsl:text>
                      </fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block>
                          <xsl:value-of select="PT_DOB"/>
                      </fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Age at Onset</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block>
					    					<xsl:value-of select="PT_AGE"/>
                                            <xsl:text> </xsl:text>
                                            <xsl:value-of
select="PT_AGE_UNITS"/>
                                        </fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Age Group</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="PT_AGE_GROUP"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 					<fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Study</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="STUDY_ID"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
                  <fo:table-row>
                    <fo:table-cell  font-weight="bold" width="120pt">
                      <fo:block>
                        <xsl:text>Center ID</xsl:text>
                      </fo:block>
                    </fo:table-cell>
                    <fo:table-cell font-weight="normal">
                      <fo:block>
                        <xsl:value-of select="STUDY_CENTER_ID"/>
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row>
    		 						<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Patient</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="STUDY_PATIENT_ID"/></fo:block>
					    			</fo:table-cell>
    		 					</fo:table-row>
    		 				</fo:table-body>
    		 			</fo:table>
    		 		</fo:table-cell>
    		 	</fo:table-row>
    		 </xsl:for-each>
    	</fo:table-body>
    	</fo:table>
    </fo:block>

    <!-- ************************************ CONTACT INFORMATION - REPORTER
************************************ -->

    <fo:block font-size="12pt"  font-family="Arial,Times,MS Gothic,Gulim,MingLiU"  color="#008080"
font-weight="bold" space-before="10pt">
		<xsl:text>CONTACT INFORMATION &#8211; REPORTER</xsl:text>
	</fo:block>
	<fo:block font-size="8pt"  font-family="Arial,Times,MS Gothic,Gulim,MingLiU"  line-height="13pt"
border-width="1pt" border-style="solid" border-color="#CCCCCC">
    	<fo:table margin="3pt">
    	<fo:table-body>
    		<fo:table-row>
    		 	<fo:table-cell width="250pt">
    		 		<fo:block margin-left="3pt" font-weight="bold"
color="#FF8040"><xsl:text>NAME - ADDRESS</xsl:text></fo:block>
    		 	</fo:table-cell>
    		 	<fo:table-cell width="250pt">
    		 		<fo:block margin-left="3pt" font-weight="bold"
color="#FF8040"><xsl:text>REPORTER DETAILS</xsl:text></fo:block>
    		 	</fo:table-cell>
    		 </fo:table-row>
			<xsl:for-each select="/ROWSET/ROW/REPORT/CONTACTS/CONTACTS_T">
				<xsl:variable name="CONTACTS_T" select="."/>
				<fo:table-row>
    		 		<fo:table-cell width="50%">
    		 			<fo:table>
    		 				<fo:table-body>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Title</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="CONTACT_TITLE"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>First name</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of
select="CONTACT_FIRST_NAME"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Middle</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of
select="CONTACT_MIDDLE_NAME"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Last Name</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="CONTACT_LAST_NAME"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Street Address</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="CONTACT_ADDRESS1"/></fo:block>
					    				<fo:block><xsl:value-of select="CONTACT_ADDRESS2"/></fo:block>
					    				<fo:block><xsl:value-of select="CONTACT_ADDRESS3"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>City</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="CONTACT_CITY"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>State</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="CONTACT_STATE"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Postal Code</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of
select="CONTACT_POSTAL_CODE"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Country</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="CONTACT_COUNTRY"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Phone Number</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of
select="CONTACT_PHONE_NUMBER"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Fax Number</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of
select="CONTACT_FAX_NUMBER"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Email</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="CONTACT_EMAIL"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
    		 				</fo:table-body>
    		 			</fo:table>
    		 		</fo:table-cell>
    		 		<fo:table-cell width="50%">
    		 			<fo:table>
    		 				<fo:table-body>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Reporter Type</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of select="REPORTER_TYPE"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Reporter Source</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of
select="REPORTER_SOURCE_TYPE"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
					 			<fo:table-row>
				 					<fo:table-cell  font-weight="bold" width="120pt">
					    				<fo:block><xsl:text>Occupation</xsl:text></fo:block>
					    			</fo:table-cell>
					    			<fo:table-cell font-weight="normal">
					    				<fo:block><xsl:value-of
select="CONTACT_OCCUPATION"/></fo:block>
					    			</fo:table-cell>
					 			</fo:table-row>
                  <fo:table-row>
                    <fo:table-cell  font-weight="bold" width="120pt">
                      <fo:block>
                        <xsl:text>Speciality</xsl:text>
                      </fo:block>
                    </fo:table-cell>
                    <fo:table-cell font-weight="normal">
                      <fo:block>
                        <xsl:value-of select="CONTACT_SPECIALTY"/>
                      </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
    		 				</fo:table-body>
    		 			</fo:table>
    		 		</fo:table-cell>
    		 	</fo:table-row>
				<fo:table-row height="5pt">
					<fo:table-cell><fo:block></fo:block></fo:table-cell>
				</fo:table-row>
            </xsl:for-each>
    	</fo:table-body>
    	</fo:table>
    </fo:block>


    	<!-- ************************************ EVENTS
************************************ -->

		<fo:block font-size="12pt"  font-family="Arial,Times,MS Gothic,Gulim,MingLiU"  color="#008080"
font-weight="bold" space-before="10pt">
			<xsl:text>EVENTS</xsl:text>
		</fo:block>
		<fo:block font-size="8pt"  font-family="Arial,Times,MS Gothic,Gulim,MingLiU"  line-height="13pt"
border-width="1pt" border-style="solid" border-color="#CCCCCC">
			<fo:table margin="3pt">
			<fo:table-body>
				<fo:table-row>
		    		 	<fo:table-cell width="250pt">
		    		 		<fo:block margin-left="3pt" font-weight="bold"
color="#FF8040"><xsl:text>REPORTED EVENT DETAILS</xsl:text></fo:block>
		    		 	</fo:table-cell>
		    	</fo:table-row>
		    	<xsl:for-each select="/ROWSET/ROW/REPORT/EVENTS/EVENTS_T">
                 	<xsl:variable name="EVENTS_T" select="."/>
	            	<fo:table-row>
	    		 		<fo:table-cell width="50%">
	    		 			<fo:table>
	    		 				<fo:table-body>
	    		 					<fo:table-row>
					 					<fo:table-cell  font-weight="bold" width="120pt">
						    				<fo:block><xsl:text>As Reported Term</xsl:text></fo:block>
						    			</fo:table-cell>
						    			<fo:table-cell font-weight="normal">
						    				<fo:block><xsl:value-of select="EVENT_VERBATIM"/></fo:block>
						    			</fo:table-cell>
						 			</fo:table-row>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Onset Date</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:call-template name="FormatDate">
                            <xsl:with-param name="DateTime"
select="ONSET_DATE"/>
                          </xsl:call-template>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
	    		 				</fo:table-body>
	    		 			</fo:table>
	    		 		</fo:table-cell>
	    		 	</fo:table-row>
					<fo:table-row height="5pt">
						<fo:table-cell><fo:block></fo:block></fo:table-cell>
					</fo:table-row>
          </xsl:for-each>
	    	</fo:table-body>
	    	</fo:table>
	    </fo:block>

    	<!-- ************************************ PRODUCTS - SUSPECT PRODUCTS
************************************ -->

		<fo:block font-size="12pt"  font-family="Arial,Times,MS Gothic,Gulim,MingLiU"  color="#008080"
font-weight="bold" space-before="10pt">
			<xsl:text>PRODUCTS &#8211; SUSPECT PRODUCTS</xsl:text>
		</fo:block>
		<fo:block font-size="8pt"  font-family="Arial,Times,MS Gothic,Gulim,MingLiU"  line-height="13pt"
border-width="1pt" border-style="solid" border-color="#CCCCCC">
			<fo:table margin="3pt">
			<fo:table-body>
				<fo:table-row>
		    		 	<fo:table-cell width="250pt">
		    		 		<fo:block margin-left="3pt" font-weight="bold"
color="#FF8040">SUSPECT PRODUCT DETAILS</fo:block>
		    		 	</fo:table-cell>
		    	</fo:table-row>
		    	<xsl:for-each select="/ROWSET/ROW/REPORT/PRODUCT/PRODUCT_T">
                 	<xsl:variable name="SUSPECT_PRODUCTS_T" select="."/>
	            	<fo:table-row>
	    		 		<fo:table-cell width="100%">
	    		 			<fo:table>
	    		 				<fo:table-body>
                    <fo:table-row>
                      <fo:table-cell  font-weight="bold" width="120pt">
                        <fo:block>
                          <xsl:text>Product Type</xsl:text>
                        </fo:block>
                      </fo:table-cell>
                      <fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:value-of select="MARKETED_PRODUCT_TYPE"/>
                        </fo:block>
                      </fo:table-cell>
                    </fo:table-row>
	    		 					<fo:table-row>
					 					<fo:table-cell  font-weight="bold" width="120pt">
						    				<fo:block><xsl:text>As Reported Product</xsl:text></fo:block>
						    			</fo:table-cell>
						    			<fo:table-cell font-weight="normal">
                        <fo:block>
                          <xsl:value-of select="TRADE_NAME"/>
                        </fo:block>
						    			</fo:table-cell>
						 			</fo:table-row>
						 			<fo:table-row>
					 					<fo:table-cell  font-weight="bold" width="120pt">
						    				<fo:block><xsl:text>Product Inn</xsl:text></fo:block>
						    			</fo:table-cell>
						    			<fo:table-cell font-weight="normal">
                        <fo:block><xsl:value-of
select="PRODUCT_NAME"/></fo:block>
						    			</fo:table-cell>
						 			</fo:table-row>
	    		 				</fo:table-body>
	    		 			</fo:table>
	    		 		</fo:table-cell>
	    		 	</fo:table-row>
					<fo:table-row height="5pt">
						<fo:table-cell><fo:block></fo:block></fo:table-cell>
					</fo:table-row>
	    		 </xsl:for-each>
	    	</fo:table-body>
	    	</fo:table>
	    </fo:block>


  </fo:flow>
</fo:page-sequence>

</fo:root>
</xsl:template>

  <xsl:template name="FormatDate">
    <xsl:param name="DateTime" />
    <!-- new date format 2006-01-14T08:55:22 -->
    <xsl:variable name="year">
      <xsl:value-of select="substring($DateTime,1,4)" />
    </xsl:variable>
    <xsl:variable name="mo">
      <xsl:value-of select="substring($DateTime,5,2)" />
    </xsl:variable>
    <xsl:variable name="day">
      <xsl:value-of select="substring($DateTime,7,2)" />
    </xsl:variable>
    <xsl:variable name="HideDate">
      <xsl:value-of select="Blinded" />
    </xsl:variable>
    <xsl:if test="($day !=null)">
      <xsl:if test="(string-length($day) &lt; 2)">
        <xsl:value-of select="0"/>
      </xsl:if>
    </xsl:if>


    <xsl:choose>
      <xsl:when test="contains(DateTime, 'UNK')">
        <xsl:variable name="FormattedDate">
          <xsl:value-of select="Unknown" />
        </xsl:variable>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$day"/>
        <xsl:value-of select="' '"/>
        <xsl:choose>
          <xsl:when test="$mo = '01'">JAN</xsl:when>
          <xsl:when test="$mo = '02'">FEB</xsl:when>
          <xsl:when test="$mo = '03'">MAR</xsl:when>
          <xsl:when test="$mo = '04'">APR</xsl:when>
          <xsl:when test="$mo = '05'">MAY</xsl:when>
          <xsl:when test="$mo = '06'">JUN</xsl:when>
          <xsl:when test="$mo = '07'">JUL</xsl:when>
          <xsl:when test="$mo = '08'">AUG</xsl:when>
          <xsl:when test="$mo = '09'">SEP</xsl:when>
          <xsl:when test="$mo = '10'">OCT</xsl:when>
          <xsl:when test="$mo = '11'">NOV</xsl:when>
          <xsl:when test="$mo = '12'">DEC</xsl:when>
        </xsl:choose>
        <xsl:value-of select="' '"/>
        <xsl:value-of select="$year"/>
      </xsl:otherwise>
    </xsl:choose>

  </xsl:template>
</xsl:stylesheet>