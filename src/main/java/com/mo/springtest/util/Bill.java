package com.mo.springtest.util;

import java.util.List;

import com.mo.springtest.domain.Product;

public interface Bill {
	
	public String generateBill(List<Product> products);
	
}
