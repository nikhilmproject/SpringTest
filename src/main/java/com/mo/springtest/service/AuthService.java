/**
 * 
 */
package com.mo.springtest.service;

import org.springframework.stereotype.Service;

/**
 * Authentication service for application. For now it just checks the passed in userId but can be extended
 * to talk to a datastore or an authentication provider.
 * 
 * @author Admin
 *
 */
@Service
public class AuthService implements IAuthService {

	private static final String VALID_USER = "nikhil.mayaskar@db.com";
	@Override
	public boolean authenticate(String userId) {
		
		if (VALID_USER.equals(userId)){
			return true;
		}
		return false;
	}

}
