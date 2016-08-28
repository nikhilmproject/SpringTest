package com.mo.springtest.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mo.springtest.domain.Product;
import com.mo.springtest.domain.ProductRepository;
import com.mo.springtest.util.Bill;
import com.mo.springtest.util.BillFactory;

@Service
public class SpringTestService implements ISpringTestService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static final String ITEMIZED_BILL = "itemized";
	
	@Autowired
	private ProductRepository productRepository;
	

	@Override
	public String checkout(final String productIds) throws Exception{
		
		log.debug("Request recieved for checking out products with ids : " + productIds);
		
		String[] ids = productIds.replaceAll("\\[", "").replaceAll("\\]","").split(",");
		List<Product> products = new ArrayList<Product>();
		
		//Get the products from database from their Ids
		for (String id : ids) {
			Product product = productRepository.findOne(Long.valueOf(id.trim()));
			//add only if valid products found since the api above can return null
			if (product != null){
				products.add(product);
			}			
		}
		
		Bill bill = BillFactory.createBill(ITEMIZED_BILL);
		String report = bill.generateBill(products);
		
		//Since bill is generated delete the checkedout products.
		productRepository.delete(products);
		return report;	    
	}	
}
