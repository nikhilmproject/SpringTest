/**
 * 
 */
package com.mo.springtest.util;

/**
 * @author Admin
 *
 */
public class BillFactory {
	
	private static final String ITEMIZED_BILL = "itemized";
	
	public static Bill createBill(final String type){
		
		if (ITEMIZED_BILL.equals(type)){
			return new ItemizedBill();
		}else {
			return new GeneralBill();
		}
	}

}
