package com.mo.springtest.service;


/**
 * Interface for Spring test assignment services
 * @author Admin
 *
 */

public interface ISpringTestService {
	
	/**
	 * The api for checking out the list of product. The contract says that the products identified by the passed in ids 
	 * will be used for generating bill report and will no longer be available to checkout.
	 * 
	 * @param productIds the comma separated string for products identifiers to be checked out.
	 * @return the bill report.
	 */
	public String checkout(String productIds) throws Exception;
}
