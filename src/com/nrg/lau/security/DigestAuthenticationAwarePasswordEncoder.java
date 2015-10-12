/**
 * 
 */
package com.nrg.lau.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.codec.Hex;

/**
 *
 *
 */
public class DigestAuthenticationAwarePasswordEncoder implements
		PasswordEncoder {

	 @Override
	    public String encodePassword(String rawPass, Object salt) throws DataAccessException {
	        String a1 = salt + rawPass;
	        return md5Hex(a1);
	    }
	 
	    @Override
	    public boolean isPasswordValid(String encPass, String rawPass, Object salt) throws DataAccessException {
	        return encPass.equals(rawPass);
	    }
	  
	    private String md5Hex(String data) {
	        MessageDigest digest;
	        try {
	            digest = MessageDigest.getInstance("MD5");
	        } catch (NoSuchAlgorithmException e) {
	            throw new IllegalStateException("No MD5 algorithm available!");
	        }
	 
	        return new String(Hex.encode(digest.digest(data.getBytes())));
	    }

}
