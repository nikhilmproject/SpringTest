package com.mo.springtest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mo.springtest.service.ISpringTestService;

/**
 * Controller class for test assignment.
 * 
 * @author Admin
 *
 */
@RestController
public class SpringTestController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ISpringTestService testService;

	/**
	 * Implemented as a GET method since we are trying to "Get" report from server for the products to be checked out.
	 *  
	 * @param productIds comma separated identifier for products to be checkout.
	 * @param userId the logged in userId. This can be obtained from websso but for now passed in URL.
	 * @return the bill report
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkout")
	public String checkout(
			@RequestParam(value = "productIds") String productIds,
			@RequestParam(value = "userId") String userId)
			throws Exception {
		log.debug("Request recieved for checking out products with ids : "
				+ productIds);
		return testService.checkout(productIds);
	}

}
