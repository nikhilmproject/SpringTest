package com.mo.springtest.util;

import java.util.List;

import com.mo.springtest.domain.Product;

public class ItemizedBill implements Bill {

	@Override
	public String generateBill(List<Product> products) {
		
		if (products.size() == 0){
			return "";
		}
		
		double total = 0.0;
		StringBuilder report = new StringBuilder();
		report.append("Itemized Report");
		report.append(System.lineSeparator());
		report.append(createHeader());
		report.append(System.lineSeparator());
		report.append("===================================================================");
		report.append(System.lineSeparator());
		for (Product product : products) {
			report.append(System.lineSeparator());
			report.append(product.print());	
			report.append(System.lineSeparator());
			total += product.getNetCost();
		}
		report.append(System.lineSeparator());
		report.append("===================================================================");
		report.append(System.lineSeparator());
		report.append("Total Cost : " + total);
		return report.toString();
	}
	
   private String createHeader() {
		
		StringBuilder header = new StringBuilder();
		header.append("Product Name     " + "Category      " + "Product Cost  " + "Sales Tax  " + "Net Cost");
		return header.toString();
	}


}
