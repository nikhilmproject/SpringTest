package com.mo.springtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Main bootstrapper class for application. Registers all controllers and dependencies.
 * Launches an embedded tomcat as http listener. It avoids the need of packaging app as war file
 * or need to have any web.xml.
 * @author Admin
 *
 */
@SpringBootApplication
public class Application {
	
	private final static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
    	log.info("***Launching spring-test-service application.***");
        SpringApplication.run(Application.class, args);
    }
}
