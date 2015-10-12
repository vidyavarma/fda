package com.nrg.lau.utils;

import java.util.Iterator;

import org.apache.log4j.Logger;
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchConstraints;
import com.novell.ldap.LDAPSearchResults;



@SuppressWarnings("unchecked")
public class LDAPFetch {

	// LDAP Objects
	public LDAPConnection lc;
	public LDAPConstants consts;
	public LDAPSearchConstraints cons;
	static Logger log 		= Logger.getLogger("LDAPFetch");

	/**
	 * This method makes a connection to LDAP
	 */
	public boolean ldapConnect(String userName,String userPassword) {
		boolean connected = true;
		try {
			lc 		= new LDAPConnection();
			consts 	= new LDAPConstants();
			
			lc.connect(consts.getHost(), consts.getPort());
			lc.bind(consts.getVersion(), userName, 
					userPassword.getBytes("UTF8"));

			cons = lc.getSearchConstraints();
			cons.setMaxResults(1);
			return connected;

		} catch (Exception e) {
			connected = false;
			return connected;			
		}
	}

	/**
	 * This method disconnects the current LDAP Connection
	 */
	public void ldapDisconnect() {
		try {
			lc.disconnect();
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	public String getUserAttributeForDBCheck(String userID)
	{
		String  userAttribute = "";
		try
		{		
			String searchStr = "(&(|(CN=" + userID + "))(objectclass=" + consts.getObjUser() + "))";
			String attList[] = { "mail" };
			
			for(LDAPSearchResults searchResults = lc.search(consts.getUserSearch(), 
					consts.getSrchScope(), searchStr, attList, false, cons); searchResults.hasMore();)
			{
				LDAPEntry nextEntry = null;
				try	{	
					nextEntry = searchResults.next();
				}	catch(LDAPException e)	{
					log.error("LDAP - No attributes found for userID -> " + userID);
					break;
				}
				
				if(nextEntry != null)
				{						
					LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
					for(Iterator allAttributes = attributeSet.iterator(); allAttributes.hasNext();)
					{
						LDAPAttribute attribute = (LDAPAttribute)allAttributes.next();							
						userAttribute = attribute.getStringValue();
						log.info("LDAP - User email is -> " + userAttribute);
					}
					
				}
			}		
		}
		catch(LDAPException e)	{
			log.error(e, e);
		}
		return userAttribute;
	}
}
