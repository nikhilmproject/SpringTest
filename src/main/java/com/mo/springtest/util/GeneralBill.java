/**
 * 
 */
package com.mo.springtest.util;

import java.util.List;

import com.mo.springtest.domain.Product;

/**
 * A bill implementation that just calculates the TOtal Cost
 * 
 * @author Admin
 *
 */
public class GeneralBill implements Bill {

	/* (non-Javadoc)
	 * @see com.mo.springtest.domain.Bill#generateBill(java.util.List)
	 */
	@Override
	public String generateBill(List<Product> products) {
		
		return String.valueOf(products.stream().map(Product::getNetCost).reduce(0.0, Double::sum));
	}

}
