/**
 * 
 */
package com.nrg.lau.security;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 *
 */
public class DigestAuthenticationAwareSaltSource implements SaltSource {

	private String digestRealm;
	  
    @Override
    public Object getSalt(UserDetails user) {
        return String.format("%s:%s:", user.getUsername(), digestRealm);
    }
 
    public void setDigestRealm(String digestRealm) {
        this.digestRealm = digestRealm;
    }

}
