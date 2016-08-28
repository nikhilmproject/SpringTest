/**
 * 
 */
package com.mo.springtest.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mo.springtest.service.IAuthService;

/**
 * Security aspect for application.
 * Intercepts the controller api's for valid user access. Delegates to a service for authentication.
 * 
 * @author Admin
 *
 */
@Aspect
@Component
public class SecurityInterceptor {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAuthService authService;

	@Before("execution(* com.mo.springtest.controller.*.*(..))")
	public void checkSecurity(JoinPoint thisJoinPoint)
			throws Throwable {
		
		log.info("***** Starting security check: " + thisJoinPoint.getSignature().getName()
				+ " *****");
		 Object[] arguments = thisJoinPoint.getArgs();
		boolean validUser = authService.authenticate((String)arguments[1]);
		log.info("***** Ending security check: " + thisJoinPoint.getSignature().getName()
				+ " *****");
		if (!validUser){
			throw new Exception("Invalid User : Authentication failed");
		}

	}

}
